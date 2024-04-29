package com.example.cursova;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private final Context context;
    public DBHelper(Context context){
        super(context, "Cursova", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Users(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email varchar(30)NOT NULL," +
                "password varchar(20) NOT NULL," +
                "remember_me INTEGER DEFAULT 0," +
                "date_joined TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
        DB.execSQL("CREATE TABLE Images(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fk_user_id INTEGER, imgref VARCHAR(150) NOT NULL," +
                "creation_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (fk_user_id) REFERENCES Users(id)" +
                "ON DELETE CASCADE ON UPDATE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS Images");
        DB.execSQL("DROP TABLE IF EXISTS Users");
    }

    public String getDatabasePath() {
        return context.getDatabasePath("Cursova")
                .getAbsolutePath();
    }

    public File getDataBaseFile()
    {
        return context.getDatabasePath("Cursova")
                .getAbsoluteFile();
    }

    public boolean updateUserEmail(int id, String email, String newEmail)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues conVal = new ContentValues();
        if (newEmail != null) conVal.put("email", newEmail);
        int rowsAffected = DB.update("Users", conVal, "email = ? AND id = ?", new String[] {email, String.valueOf(id)});
        return rowsAffected > 0;
    }

    public boolean updateUserPassword(int id, String password, String newPassword)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues conVal = new ContentValues();
        conVal.put("password", newPassword);
        int rowsAffected = DB.update("Users", conVal, "password = ? AND id = ?", new String[] {password, String.valueOf(id)});
        return rowsAffected > 0;
    }

//    public int checkUser(String email, String password)
//    {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        Cursor cursor = DB.rawQuery("SELECT * FROM Users WHERE email = ? AND password = ?", new String[] {email, password});
//        if(cursor.getCount() > 0) {
//            cursor.close();
//            return 0;
//        }
//        else
//        {
//            cursor = DB.rawQuery("SELECT * FROM Users WHERE email = ?", new String[] {email});
//            if (cursor.getCount() > 0) {
//                cursor.close();
//                return 1;
//            }
//            else {
//                cursor = DB.rawQuery("SELECT * FROM Users WHERE password = ?", new String[] {password});
//                if(cursor.getCount() > 0) {
//                    cursor.close();
//                    return 2;
//                }
//                else {
//                    cursor.close();
//                    return 3;
//                }
//            }
//        }
//    }
//    public int getUserId(String email, String password) {
//        int id = 0;
//        SQLiteDatabase DB = this.getWritableDatabase();
//        Cursor cursor = DB.rawQuery("SELECT id FROM Users WHERE email = ? AND password = ?", new String[] {email, password});
//        if(cursor.moveToNext()) {
//            id = Integer.parseInt(cursor.getString(0));
//            cursor.close();
//        }
//        return id;
//    }

//    public boolean insertUser(String email, String password, int remember_me)
//    {
//        try{
//            SQLiteDatabase DB = this.getWritableDatabase();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("email", email);
//            contentValues.put("password", password);
//            contentValues.put("remember_me", (remember_me == 0) ? 0 : 1);
//            long result = DB.insert("Users", null, contentValues);
//            Cursor cursor = DB.rawQuery("SELECT id FROM Users WHERE email = ? AND password = ?", new String[] {email, password});
//            if(cursor.moveToNext())
//            {
//                sessionId = Integer.parseInt(cursor.getString(0));
//            }
//            cursor.close();
//            DB.close();
//            return result != -1;
//        }
//        catch (Exception e)
//        {
//            e.getStackTrace();
//            Toast.makeText(this.context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            return false;
//        }
//    }

//    public ArrayList<String> getUsers()
//    {
//        SQLiteDatabase DB = this.getReadableDatabase();
//        ArrayList<String> data = new ArrayList<String>();
//
//        try {
//            Cursor cursor = DB.rawQuery("SELECT email FROM Users", null);
//            while(cursor.moveToNext())
//            {
//                data.add(cursor.getString(0));
//            }
//            cursor.close();
//        }catch (Exception e)
//        {
//            e.getStackTrace();
//
//        }
//
//        return data;
//    }

//    public int isUserRemembered(String user_email)
//    {
//        SQLiteDatabase DB = this.getReadableDatabase();
//        int isRemembered = -1;
//        if(DB != null)
//        {
//            try{
//                Cursor cursor = DB.rawQuery("SELECT remember_me FROM Users WHERE email = ?", new String[]{user_email});
//                if (cursor.moveToNext()) {
//                    isRemembered = Integer.parseInt(cursor.getString(0));
//                    DB.close();
//                    cursor.close();
//                }
//            }catch (Exception e)
//            {
//                e.getStackTrace();
//                Toast.makeText(this.context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }
//        return isRemembered;
//    }

    public int checkUserDb(String email, String password)
    {
        int result = -1;
        SQLiteDatabase db = this.getWritableDatabase();

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
    }

    public int insertUserDb(String email, String password, int remember_me)
    {
        int sessionId = -1;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("remember_me", (remember_me == 0) ? 0 : 1);
            long result = db.insert("Users", null, contentValues);
            Cursor cursor = db.rawQuery("SELECT id FROM Users WHERE email = ? AND password = ?",
                    new String[] {email, password});
            if(cursor.moveToNext())
            {
                sessionId = Integer.parseInt(cursor.getString(0));
            }
            cursor.close();
            db.close();
        }
        catch (Exception e)
        {
            e.getStackTrace();
            Toast.makeText(this.context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return sessionId;
    }

    public int setRememberMeDb(String email, int rememberMe)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues conVal = new ContentValues();
            conVal.put("remember_me", (rememberMe == 0) ? 0 : 1);
            db.update("Users", conVal, "email = ?",
                    new String[]{email});

            db.close();
        } catch (Exception e)
        {
            e.getStackTrace();
            Toast.makeText(this.context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

            return -1;
        }

        return 0;
    }

    public int isUserRememberedDb(String user_email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int isRemembered = -1;

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

        return isRemembered;
    }

    public int getUserIdDb(String email) {
        int id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM Users WHERE email = ?",
                new String[] {email});
        if(cursor.moveToNext()) {
            id = Integer.parseInt(cursor.getString(0));
            db.close();
            cursor.close();
        }
        return id;
    }

    public ArrayList<String> getUsersDb()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> data = new ArrayList<String>();

        try {
            Cursor cursor = db.rawQuery("SELECT email FROM Users", null);
            while(cursor.moveToNext())
            {
                data.add(cursor.getString(0));
            }
            db.close();
            cursor.close();
        }catch (Exception e)
        {
            e.getStackTrace();

        }

        return data;
    }

    public int deleteUserDb(int sessionId, String email)
    {
        int toReturn = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int deletedRow = db.delete("Users", "id = ? AND email = ?",
                    new String[] {String.valueOf(sessionId), email});
            if(deletedRow > 0)
                toReturn = 0;
        }catch (Exception e)
        {
            e.getStackTrace();
            Toast.makeText(this.context, "Error: " + e, Toast.LENGTH_LONG).show();
        }

        return toReturn;
    }

    public ArrayList<String> getImagePathsDb(int userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> paths = new ArrayList<String>();

        try {
            Cursor cursor = db.rawQuery("SELECT imgref FROM Images WHERE fk_user_id = ?",
                    new String[]{String.valueOf(userId)});
            while(cursor.moveToNext())
            {
                paths.add(cursor.getString(0));
            }
            db.close();
            cursor.close();
        }catch (Exception e)
        {
            e.getStackTrace();

        }

        return paths;
    }

}
