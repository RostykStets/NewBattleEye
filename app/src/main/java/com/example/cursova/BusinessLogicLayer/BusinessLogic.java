package com.example.cursova.BusinessLogicLayer;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cursova.DataAccessLayer.DBHelper;
import com.example.cursova.PresentationLayer.Activities.EditProfileActivity;
import com.example.cursova.PresentationLayer.Activities.LoginActivity;
import com.example.cursova.PresentationLayer.Activities.MainPageActivity;
import com.example.cursova.PresentationLayer.Activities.StartPageActivity;
import com.example.cursova.R;

import java.util.ArrayList;

public class BusinessLogic {
    public static int sessionID = 0;
    public static String user_email = "";
    private final Context context;
    private DBHelper DB;
    public BusinessLogic(Context context)
    {
        this.context = context;
        try {
            this.DB = new DBHelper(this.context);
        }catch (Exception e)
        {
            Toast.makeText(this.context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<String> getUsers()
    {
        return DB.getUsersDb();
    }

    public void signUpUser(String email, String password, int rememberMe)
    {
        try {
            if(DB.checkUserDb(email, password) < 2)
            {
                Toast.makeText(this.context, "User already exists!", Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                int insert = DB.insertUserDb(email, password, rememberMe);

                if (-1 != insert) {
                    sessionID = insert;
                    user_email = email;
                    Toast.makeText(this.context, "User registered successfully!",
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(this.context, "Session ID: " + sessionID,
                            Toast.LENGTH_LONG).show();
                    //DB.close();

                    this.context.startActivity(new Intent(this.context, MainPageActivity.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void authorizeUser(String email, String password, int rememberMe)
    {
        try {
            int user_check = DB.checkUserDb(email, password);

            switch (user_check)
            {
                case 0:
                    if (0 != DB.setRememberMeDb(email, rememberMe))
                    {
                        Toast.makeText(this.context, "setRememberMe failed!",
                                Toast.LENGTH_LONG).show();
                        break;
                    }

                    sessionID = DB.getUserIdDb(email);
                    if (-1 == sessionID)
                    {
                        Toast.makeText(this.context, "getUserIdDb failed!",
                                Toast.LENGTH_LONG).show();
                        break;
                    }

                    Toast.makeText(this.context, "User logged in successfully!",
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(this.context, "Session ID: " + sessionID,
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this.context, MainPageActivity.class);
                    this.context.startActivity(intent);
                    break;
                case 1:
                    Toast.makeText(this.context, "Password is wrong",
                            Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(this.context, "Login is wrong", // WTF
                            Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(this.context, "User does not exist!",
                            Toast.LENGTH_LONG).show();
                    break;
                default:
                    throw new Exception("Something went wrong!");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this.context, "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void userEmailOnClick(String email)
    {
        try {
            int isUserRemembered = DB.isUserRememberedDb(email);
            user_email = email;
            if (isUserRemembered == 0){
                this.context.startActivity(new Intent(this.context, LoginActivity.class));
            }else if(isUserRemembered == 1)
            {
                sessionID = DB.getUserIdDb(email);
                if(-1 != sessionID)
                {
                    Toast.makeText(context, "Session ID: " + sessionID,
                            Toast.LENGTH_SHORT).show();
                    this.context.startActivity(new Intent(this.context, MainPageActivity.class));
                }
            }
            else {
                Toast.makeText(context, "There is no user with such email!",
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
        {
            e.getStackTrace();
            Toast.makeText(this.context, "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<String> getImagePaths()
    {
        return DB.getImagePathsDb(sessionID);
    }

    public boolean menuItems(MenuItem menuItem)
    {
        int selectedItem = menuItem.getItemId();

        if(selectedItem == R.id.item1)
        {
            sessionID = 0;
            user_email = "";
            this.context.startActivity(new Intent(this.context, StartPageActivity.class));
            return true;
        }
        else if(selectedItem == R.id.item2)
        {
            this.context.startActivity(new Intent(this.context, EditProfileActivity.class));
            return true;
        }
        else if(selectedItem == R.id.item3)
        {
            int isUserDeleted = DB.deleteUserDb(sessionID, user_email);
            switch (isUserDeleted)
            {
                case 0:
                    sessionID = 0;
                    user_email = "";
                    this.context.startActivity(new Intent(this.context, StartPageActivity.class));
                    break;
                case -1:
                    Toast.makeText(this.context, "Користувач не видалився!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return true;
        }
        else {
            return false;
        }
    }

    public int updateUserEmail(String newEmail)
    {
        int result = -1;
        if(newEmail != null && !newEmail.isEmpty()) {
            boolean isUpdated = DB.updateUserEmail(sessionID, user_email, newEmail);
            if(isUpdated)
                result = 0;
            else
                result = 1;
        }
        return result;
    }

    public int updateUserPassword(String password, String newPassword)
    {

        int rightPassword = DB.checkUserDb(user_email, password);
        if(rightPassword != 0)
            return -1;
        int result = -1;
        if(password.equals(newPassword) && !newPassword.isEmpty()) {
            boolean isUpdated = DB.updateUserPassword(sessionID, password, newPassword);
            if(isUpdated)
                result = 0;
            else
                result = 1;
        }

        return result;
    }
}
