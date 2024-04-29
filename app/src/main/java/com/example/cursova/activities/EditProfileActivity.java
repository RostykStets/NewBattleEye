package com.example.cursova.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.cursova.BusinessLogic;
import com.example.cursova.R;

public class EditProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private BusinessLogic _businessLogic;
    private ImageButton _settings;
    private TextView _userEmail;
    private EditText _newEmail;
    private EditText _password;
    private EditText _newPassword;
    private Button _changeEmailBtn;
    private Button _changePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        _businessLogic = new BusinessLogic(this);

        _userEmail = findViewById(R.id.user);
        _userEmail.setText(BusinessLogic.user_email);

        _newEmail = findViewById(R.id.newEmail);
        _password = findViewById(R.id.password_input);
        _newPassword = findViewById(R.id.new_password_input);

        _settings = findViewById(R.id.settings);

        _settings.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(EditProfileActivity.this, view);
            popupMenu.setOnMenuItemClickListener(EditProfileActivity.this);
            popupMenu.inflate(R.menu.settings_menu);
            popupMenu.show();
        });

        _changeEmailBtn = findViewById(R.id.changeEmailBtn);
        _changePasswordBtn = findViewById(R.id.changePasswordBtn);

        _changeEmailBtn.setOnClickListener(view -> {
            if(_newEmail == null || _newEmail.getText().toString().isEmpty())
            {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(EditProfileActivity.this);
                dlgAlert.setMessage("Ви не ввели новий email!");
                dlgAlert.setTitle("Не знайдено нового email");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            else
            {
                updateEmail(_newEmail.getText().toString());
            }
        });
        _changePasswordBtn.setOnClickListener(view -> {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(EditProfileActivity.this);
            if(_password == null || _newPassword == null)
            {
                dlgAlert.setMessage("Ви не ввели старий чи новий пароль!");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            else if(_password.getText().toString().isEmpty())
            {
                dlgAlert.setMessage("Ви не ввели старий пароль!");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            else if(_newPassword.getText().toString().isEmpty())
            {
                dlgAlert.setMessage("Ви не ввели новий пароль!");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            else if(!_password.getText().toString().equals(_newPassword.getText().toString()))
            {
                dlgAlert.setMessage("Ви не ввели старий чи новий пароль!");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            else
            {
                updatePassword(_password.getText().toString(), _newPassword.getText().toString());
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        return this._businessLogic.menuItems(menuItem);
    }

    public void updateEmail(String newEmail)
    {
        int result = _businessLogic.updateUserEmail(newEmail);
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(EditProfileActivity.this);
        switch (result)
        {
            case 0:
                dlgAlert.setMessage("Ваш email успішно змінено!");
                dlgAlert.setTitle("Успішна зміна email!");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                BusinessLogic.user_email = newEmail;
                break;
            case 1:
                dlgAlert.setMessage("Не вдалось змінити Ваш email!");
                dlgAlert.setTitle("Error!");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                break;
            default:
                break;
        }
    }

    public void updatePassword(String password, String newPassword)
    {
        int result = _businessLogic.updateUserPassword(password, newPassword);
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(EditProfileActivity.this);
        switch(result)
        {
            case -1:
                dlgAlert.setMessage("Користувача з таким паролем не знайдено");
                dlgAlert.setTitle("Error!");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                break;
            case 1:
                dlgAlert.setMessage("Не вдалось змінити Ваш пароль!");
                dlgAlert.setTitle("Error!");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                break;
            case 0:
                dlgAlert.setMessage("Ваш пароль успішно змінено!");
                dlgAlert.setTitle("Успішна зміна паролю!");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                break;
            default:
                break;
        }
    }
}