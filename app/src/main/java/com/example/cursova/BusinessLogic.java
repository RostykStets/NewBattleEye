package com.example.cursova;

//import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Environment;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cursova.activities.LoginActivity;
import com.example.cursova.activities.MainPageActivity;
import com.example.cursova.activities.StartPageActivity;

//import java.io.File;
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

    /*public int checkUser(String email, String password)
    {
        SQLiteDatabase db = this.DB.getWritableDatabase();
        int result;
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE email = ? AND password = ?",
                new String[] {email, password});
        if(cursor.getCount() > 0) {
            result = 0;
        }
        else
        {
            cursor = db.rawQuery("SELECT * FROM Users WHERE email = ?",
                    new String[] {email});
            if (cursor.getCount() > 0) {
                result = 1;
            }
            else {
                cursor = db.rawQuery("SELECT * FROM Users WHERE password = ?",
                        new String[] {password}); // Daamn
                if(cursor.getCount() > 0) {
                    result = 2;
                }
                else {
                    result = 3;
                }
            }
        }
        cursor.close();
        db.close();
        return result;
    }*/

    /*public int getUserId(String email, String password) {
        int id = 0;
        SQLiteDatabase db = this.DB.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM Users WHERE email = ? AND password = ?",
                new String[] {email, password});
        if(cursor.moveToNext()) {
            id = Integer.parseInt(cursor.getString(0));
            db.close();
            cursor.close();
        }
        return id;
    }*/

    /*public boolean insertUser(String email, String password, int remember_me)
    {
        try{
            SQLiteDatabase db = this.DB.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("remember_me", (remember_me == 0) ? 0 : 1);
            long result = db.insert("Users", null, contentValues);
            Cursor cursor = db.rawQuery("SELECT id FROM Users WHERE email = ? AND password = ?",
                    new String[] {email, password});
            if(cursor.moveToNext())
            {
                sessionID = Integer.parseInt(cursor.getString(0));
            }
            cursor.close();
            db.close();
            return result != -1;
        }
        catch (Exception e)
        {
            e.getStackTrace();
            Toast.makeText(this.context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }*/

    public ArrayList<String> getUsers()
    {
        return DB.getUsersDb();
    }

    /*public int isUserRemembered(String user_email)
    {
        SQLiteDatabase db = this.DB.getReadableDatabase();
        int isRemembered = -1;
        if(DB != null)
        {
            try{
                Cursor cursor = db.rawQuery("SELECT remember_me FROM Users WHERE email = ?",
                        new String[]{user_email});
                if (cursor.moveToNext()) {
                    isRemembered = Integer.parseInt(cursor.getString(0));
                    db.close();
                    cursor.close();
                }
            }catch (Exception e)
            {
                e.getStackTrace();
                Toast.makeText(this.context, "Error: " + e.getMessage(),Toast.LENGTH_LONG)
                        .show();
            }
        }
        return isRemembered;
    }*/

    public void signUpUser(String email, String password, int rememberMe)
    {
        try {
            //DBHelper DB = new DBHelper(this.context);
            if(DB.checkUserDb(email, password) < 2)
            //if(this.checkUser(email, password) == 0)
            {
                Toast.makeText(this.context, "User already exists!", Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                int insert = DB.insertUserDb(email, password, rememberMe);
                //boolean insert = this.insertUser(email, password, rememberMe);

                if (-1 != insert) {
                    //sessionID = DB.getUserId(email, password);
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
            //int user_check = this.checkUser(email, password);

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

    /*public int deleteUser()
    {
        try {
            SQLiteDatabase db = this.DB.getWritableDatabase();
            int deletedRow = db.delete("Users", "id = ? AND email = ?",
                    new String[] {String.valueOf(sessionID), user_email});
            if(deletedRow > 0)
                return 0;
            else
                return 1;
        }catch (Exception e)
        {
            e.getStackTrace();
            Toast.makeText(this.context, "Error: " + e, Toast.LENGTH_LONG).show();
            return -1;
        }
    }*/

    public void userEmailOnClick(String email)
    {
        try {
            //int isUserRemembered = new DBHelper(this.context).isUserRemembered(email);
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
        if(menuItem.getItemId() == R.id.item1)
        {
            sessionID = 0;
            user_email = "";
            this.context.startActivity(new Intent(this.context, StartPageActivity.class));
            return true;
        }
        else if(menuItem.getItemId() == R.id.item2)
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
}
