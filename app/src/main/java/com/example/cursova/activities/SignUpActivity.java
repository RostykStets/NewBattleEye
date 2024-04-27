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

public class SignUpActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private EditText confirm_password;
    private Button signUp;

    private CheckBox remember_me;

    private TextView loginLink;
    private BusinessLogic BL;

    //DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        login = (EditText) findViewById(R.id.login_input);
        password = (EditText) findViewById(R.id.password_input);
        confirm_password = (EditText) findViewById(R.id.confirm_password_input);

        BL = new BusinessLogic(this);

        signUp = (Button) findViewById(R.id.signUpButton);

        remember_me = findViewById(R.id.rememberMeCheckBox);

        //DB = new DBHelper(this);
        //Toast.makeText(SignUpActivity.this, DB.getDatabasePath(), Toast.LENGTH_LONG).show();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login_ = login.getText().toString();
                String pass = password.getText().toString();
                String conf_pass = confirm_password.getText().toString();
                int rememberMe = remember_me.isChecked() ? 1 : 0;
                if (login_.equals(""))
                    Toast.makeText(SignUpActivity.this, "Login is empty!", Toast.LENGTH_LONG).show();
                if(pass.equals(""))
                    Toast.makeText(SignUpActivity.this, "Password is empty!", Toast.LENGTH_LONG).show();
                if(conf_pass.equals(""))
                    Toast.makeText(SignUpActivity.this, "Confirm password is empty!", Toast.LENGTH_LONG).show();

                if(!pass.equals("") && !conf_pass.equals("") && conf_pass.equals(pass))
                {
                    BL.signUpUser(login_, pass, rememberMe);
//                    try {
//                        if(DB.checkUser(login_, pass) == 0)
//                        {
//                            Toast.makeText(SignUpActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            boolean insert = DB.insertUser(login_, pass, rememberMe);
//
//                            if (insert) {
//                                Toast.makeText(SignUpActivity.this, "User registered successfully!", Toast.LENGTH_LONG).show();
//                                Toast.makeText(SignUpActivity.this, "Session ID: " + DBHelper.sessionId, Toast.LENGTH_LONG).show();
//                                DB.close();
//
//                                Intent intent = new Intent(SignUpActivity.this, MainPageActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
////                            else {
////                                //Toast.makeText(SignUpActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
////                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Toast.makeText(SignUpActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
                }
            }
        });


        loginLink = findViewById(R.id.loginLink);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}