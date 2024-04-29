package com.example.cursova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.cursova.activities.EditProfileActivity;

public class UserProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Button editProfileBtn;
    private TextView userEmail;
    private ImageButton settings;
    BusinessLogic businessLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        businessLogic = new BusinessLogic(this);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        userEmail = findViewById(R.id.user);
        userEmail.setText(BusinessLogic.user_email);
        TextView userLabel = findViewById(R.id.userLabel);
        userLabel.setText(userEmail.getText());

        settings = findViewById(R.id.settings);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this, EditProfileActivity.class));
                finish();
            }
        });

        settings.setOnClickListener((View view) -> {
            PopupMenu popupMenu = new PopupMenu(UserProfileActivity.this, view);
            popupMenu.setOnMenuItemClickListener(UserProfileActivity.this);
            popupMenu.inflate(R.menu.settings_menu);
            popupMenu.show();
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        return this.businessLogic.menuItems(menuItem);
    }
}