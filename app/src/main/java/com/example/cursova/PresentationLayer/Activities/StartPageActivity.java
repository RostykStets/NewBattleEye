package com.example.cursova.PresentationLayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cursova.BusinessLogicLayer.BusinessLogic;
import com.example.cursova.PresentationLayer.Adapters.MyAdapter;
import com.example.cursova.R;

import java.util.ArrayList;

public class StartPageActivity extends AppCompatActivity {

    BusinessLogic businessLogic;
    private Button toRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        businessLogic = new BusinessLogic(this);

        toRegister = findViewById(R.id.toRegister);

        RecyclerView usersRecyclerView = findViewById(R.id.usersRecyclerView);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> users = businessLogic.getUsers();
        usersRecyclerView.setAdapter(new MyAdapter(users, StartPageActivity.this));

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartPageActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }
}