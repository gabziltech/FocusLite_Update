package com.gabzil.focuslite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LoginDataBase";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Login";
    public static final String TABLE_NAME1 = "Session";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Login (Id Integer PRIMARY KEY AUTOINCREMENT,"
                + "UserName TEXT, Password TEXT)");
        db.execSQL("CREATE TABLE Session (Id Integer PRIMARY KEY AUTOINCREMENT,"
                + "SessionTake TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME1);
        onCreate(db);
        System.out.println("On Upgrade Call");
    }

    public String getLogin(String user_email, String user_password) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where UserName= '" + user_email + "' and Password= '" + user_password + "'", null);

        if (cursor.moveToFirst()) {
            do {
                String Pass = cursor.getString(cursor.getColumnIndex("Password"));
                return Pass;
            } while (cursor.moveToNext());
        }
        cursor.close();

        return "";
    }

    public String getSessionTake() {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME1, null);

            if (cursor.moveToFirst()) {
                do {
                    String Session = cursor.getString(cursor.getColumnIndex("SessionTake"));
                    return Session;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (Exception e){
            e.getMessage();
        }
        return "";
    }

    public Integer getID() {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME1, null);

            if (cursor.moveToFirst()) {
                do {
                    Integer id = cursor.getInt(cursor.getColumnIndex("Id"));
                    return id;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (Exception e){
            e.getMessage();
        }
        return 0;
    }
}
