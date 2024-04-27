package com.example.cursova;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DBHelper extends SQLiteOpenHelper {

    public static int sessionId = 0;
    public static String user_email = "";
    private final Context context;
    public DBHelper(Context context){
        super(context, "Cursova", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Users(id INTEGER PRIMARY KEY AUTOINCREMENT, email varchar(30)NOT NULL, password varchar(20) NOT NULL, remember_me INTEGER DEFAULT 0, date_joined TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
        DB.execSQL("CREATE TABLE Images(id INTEGER PRIMARY KEY AUTOINCREMENT, fk_user_id INTEGER, imgref VARCHAR(150) NOT NULL, creation_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (fk_user_id) REFERENCES Users(id) ON DELETE CASCADE ON UPDATE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS Users");
        DB.execSQL("DROP TABLE IF EXISTS Images");
    }

    public String getDatabasePath() {
        return context.getDatabasePath("Cursova").getAbsolutePath();
    }

    public File getDataBaseFile()
    {
        return context.getDatabasePath("Cursova").getAbsoluteFile();
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

}
