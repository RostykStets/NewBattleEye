package com.example.cursova.PresentationLayer.Activities;


import android.Manifest;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cursova.BusinessLogicLayer.BusinessLogic;
import com.example.cursova.DataAccessLayer.DBHelper;
import com.example.cursova.R;

import java.io.File;


public class MainPageActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageView imageCaptured;
    private Button btnCam;
    private ImageButton settings;
    private Button history;
    private String currentPhotoPath;
    private TextView user_email;
    private BusinessLogic businessLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        businessLogic = new BusinessLogic(this);

        user_email = findViewById(R.id.user);
        user_email.setText(BusinessLogic.user_email);

        imageCaptured = findViewById(R.id.imageCaptured);

        history = findViewById(R.id.history);

        btnCam = findViewById(R.id.openCamera);
        settings = findViewById(R.id.settings);


        btnCam.setOnClickListener(view -> {
            try {
                if(ContextCompat.checkSelfPermission(MainPageActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainPageActivity.this, new String[]
                            {Manifest.permission.CAMERA}, 100);
                }
            }
            catch(Exception e) {
                e.getStackTrace();
                Toast.makeText(MainPageActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            String fileName = "photo";
            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            try {
                File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);

                currentPhotoPath = imageFile.getAbsolutePath();

                Uri imageUri = FileProvider.getUriForFile(MainPageActivity.this,
                        "com.example.cursova.fileprovider", imageFile);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(intent, 1);
            }catch(Exception e)
            {
                e.getStackTrace();
                Toast.makeText(MainPageActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        history.setOnClickListener(view -> {
            startActivity(new Intent(MainPageActivity.this, HistoryActivity.class));
            finish();
        });

        settings.setOnClickListener((View view) -> {
            PopupMenu popupMenu = new PopupMenu(MainPageActivity.this, view);
            popupMenu.setOnMenuItemClickListener(MainPageActivity.this);
            popupMenu.inflate(R.menu.settings_menu);
            popupMenu.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            try {

                this.businessLogic.insertImage(BusinessLogic.sessionID, currentPhotoPath);

                int imageCount = this.businessLogic.getImageCount(BusinessLogic.sessionID);

                Toast.makeText(MainPageActivity.this, "Count of images: " + imageCount, Toast.LENGTH_SHORT).show();

            }catch (Exception e)
            {
                e.getStackTrace();
                Toast.makeText(MainPageActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            imageCaptured.setImageBitmap(bitmap);

        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        boolean result = this.businessLogic.menuItems(menuItem);
        return result;
    }
}