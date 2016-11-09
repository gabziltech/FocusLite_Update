package com.gabzil.focuslite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LoginDataBase";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Login";
    public static final String TABLE_NAME1 = "Session";
    public static final String TABLE_NAME2 = "Inputs";
    Context context;

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Login (Id Integer PRIMARY KEY AUTOINCREMENT,"
                + "UserName TEXT, Password TEXT)");
        db.execSQL("CREATE TABLE Session (Id Integer PRIMARY KEY AUTOINCREMENT,"
                + "SessionTake TEXT)");
        db.execSQL("CREATE TABLE Inputs (Id Integer PRIMARY KEY AUTOINCREMENT,"
                + "Speed TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME2);
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

    public String getInputs() {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery("Select * from " + TABLE_NAME2, null);

//            cursor.moveToFirst();
//            String Speed = cursor.getString(cursor.getColumnIndex("Speed"));
//            return Speed;
        } catch (Exception e) {
            e.getMessage();
        }
        if (cursor.moveToLast()) {
            do {
                String Speed = cursor.getString(cursor.getColumnIndex("Speed"));
                return Speed;
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

    public void ExportFile() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME2, null);
            int rowcount = 0;
            int colcount = 0;

            File sdCard = Environment.getExternalStorageDirectory();
            String filename = "MyBackUp.csv";
            File saveFile = new File(sdCard, filename);
            saveFile.createNewFile();
            FileWriter fw = new FileWriter(saveFile);
            BufferedWriter bw = new BufferedWriter(fw);
            rowcount = cursor.getCount();
            colcount = cursor.getColumnCount();
            if (rowcount > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < colcount; i++) {
                    if (i != colcount - 1) {
                        bw.write(cursor.getColumnName(i) + ",");
                    } else {
                        bw.write(cursor.getColumnName(i));
                    }
                }
                bw.newLine();
                for (int i = 0; i < rowcount; i++) {
                    cursor.moveToPosition(i);
                    for (int j = 0; j < colcount; j++) {
                        if (j != colcount - 1)
                        bw.write(cursor.getString(j) + ",");
                        else
                        bw.write(cursor.getString(j));
                    }
                    bw.newLine();
                }
                bw.flush();
                Toast.makeText(context,"Exported Successfully. " +saveFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context,"No record found, so file is not generated" +saveFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            if (db.isOpen()) {
                db.close();
                Toast.makeText(context,"Error: " +e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        } finally {
        }
    }

    public Cursor getDetails() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from " + TABLE_NAME2, null);
    }
}
