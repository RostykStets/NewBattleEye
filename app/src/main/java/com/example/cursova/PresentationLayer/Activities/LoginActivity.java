package com.example.cursova.PresentationLayer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
 import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cursova.BusinessLogicLayer.BusinessLogic;
import com.example.cursova.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText login_input;
    private EditText password_input;
    private CheckBox remember_meChBx;
    private TextView signUpLink;
    private BusinessLogic businessLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        businessLogic = new BusinessLogic(this);

        loginBtn = findViewById(R.id.loginButton);
        login_input = findViewById(R.id.l_login_input);
        password_input = findViewById(R.id.l_password_input);
        remember_meChBx = findViewById(R.id.rememberMeCheckBox);
        signUpLink = findViewById(R.id.signUpLink);

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