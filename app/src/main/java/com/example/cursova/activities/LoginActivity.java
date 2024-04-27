package com.example.cursova.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
 import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cursova.BusinessLogic;
import com.example.cursova.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText login_input;
    private EditText password_input;
    private CheckBox remember_meChBx;
    private TextView signUpLink;

    private BusinessLogic businessLogic;
    //DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //DB = new DBHelper(this);
        businessLogic = new BusinessLogic(this);

        loginBtn = findViewById(R.id.loginButton);
        login_input = findViewById(R.id.l_login_input);
        password_input = findViewById(R.id.l_password_input);
        remember_meChBx = findViewById(R.id.rememberMeCheckBox);
        signUpLink = findViewById(R.id.signUpLink);

        //if(!DBHelper.user_email.equals(""))
        if(!BusinessLogic.user_email.equals(""))
            login_input.setText(BusinessLogic.user_email);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = login_input.getText().toString();
                String password = password_input.getText().toString();
                int remember_me = remember_meChBx.isChecked() ? 1 : 0;
                if (login.equals(""))
                    Toast.makeText(LoginActivity.this, "Login is empty!", Toast.LENGTH_LONG).show();
                if(password.equals(""))
                    Toast.makeText(LoginActivity.this, "Password is empty!", Toast.LENGTH_LONG).show();


                businessLogic.authorizeUser(login, password, remember_me);

//                try {
//                    int user_check = DB.checkUser(login, password);
//                    DB.close();
//
//                    switch (user_check)
//                    {
//                        case 0:
//                            SQLiteDatabase db = DB.getWritableDatabase();
//                            ContentValues conVal = new ContentValues();
//                            conVal.put("remember_me", (remember_me == 0) ? 0 : 1);
//                            db.update("Users", conVal, "email = ? AND password = ?", new String[] {login, password});
//                            DBHelper.sessionId = DB.getUserId(login, password);
//                            db.close();
//
//                            Toast.makeText(LoginActivity.this, "User logged in successfully!", Toast.LENGTH_LONG).show();
//                            Toast.makeText(LoginActivity.this, "Session ID: " + DBHelper.sessionId, Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
//                            startActivity(intent);
//                            finish();
//                            break;
//                        case 1:
//                            Toast.makeText(LoginActivity.this, "Password is wrong", Toast.LENGTH_LONG).show();
//                            break;
//                        case 2:
//                            Toast.makeText(LoginActivity.this, "Login is wrong", Toast.LENGTH_LONG).show();
//                            break;
//                        case 3:
//                            Toast.makeText(LoginActivity.this, "User does not exists!", Toast.LENGTH_LONG).show();
//                            break;
//                        default:
//                            throw new Exception("Something went wrong!");
//                    }
//
////                    if(user_check == 0)
////                    {
////                        SQLiteDatabase db = DB.getWritableDatabase();
////                        ContentValues conVal = new ContentValues();
////                        conVal.put("remember_me", String.valueOf(remember_me));
////                        db.update("Users", conVal, "email = ? AND password = ?", new String[] {login, password});
////
////                        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
////                        startActivity(intent);
////                        finish();
////                    }
////                    if(user_check == 1)
////                    {
////                        Toast.makeText(LoginActivity.this, "Password is wrong", Toast.LENGTH_LONG).show();
////                    } if (user_check == 2) {
////                        Toast.makeText(LoginActivity.this, "Login is wrong", Toast.LENGTH_LONG).show();
////                    } else
////                    {
////                        Toast.makeText(LoginActivity.this, "User does not exists!", Toast.LENGTH_LONG).show();
////                    }
//                }catch (Exception e)
//                {
//                    e.printStackTrace();
//                    Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}