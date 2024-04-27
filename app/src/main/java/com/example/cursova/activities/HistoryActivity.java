package com.example.cursova.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cursova.BusinessLogic;
import com.example.cursova.ImgAdapter;
import com.example.cursova.R;
import com.example.cursova.UserProfileActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextView user_email;
    Button back_to_main;

    ImageButton settings;
    BusinessLogic businessLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        back_to_main = findViewById(R.id.back_to_main);
        user_email = findViewById(R.id.user);
        businessLogic = new BusinessLogic(this);
        settings = findViewById(R.id.settings);

        user_email.setText(BusinessLogic.user_email);

        ArrayList<String> imgPaths = businessLogic.getImagePaths();

        RecyclerView imagesRecyclerView = findViewById(R.id.imagesRecyclerView);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        imagesRecyclerView.setAdapter(new ImgAdapter(HistoryActivity.this, imgPaths));

        back_to_main.setOnClickListener(view -> {
            startActivity(new Intent(HistoryActivity.this, MainPageActivity.class));
            finish();
        });

        settings.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(HistoryActivity.this, view);
            popupMenu.setOnMenuItemClickListener(HistoryActivity.this);
            popupMenu.inflate(R.menu.settings_menu);
            popupMenu.show();
        });

        user_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryActivity.this, UserProfileActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        boolean result = this.businessLogic.menuItems(menuItem);
        return result;
    }
}