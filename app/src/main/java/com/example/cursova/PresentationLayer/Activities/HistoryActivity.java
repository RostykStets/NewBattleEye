package com.example.cursova.PresentationLayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.cursova.BusinessLogicLayer.BusinessLogic;
import com.example.cursova.PresentationLayer.Adapters.ImgAdapter;
import com.example.cursova.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextView userEmail;
    Button back_to_main;

    ImageButton settings;
    BusinessLogic businessLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        back_to_main = findViewById(R.id.back_to_main);
        userEmail = findViewById(R.id.user);
        businessLogic = new BusinessLogic(this);
        settings = findViewById(R.id.settings);

        userEmail.setText(BusinessLogic.user_email);

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

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        boolean result = this.businessLogic.menuItems(menuItem);
        return result;
    }
}