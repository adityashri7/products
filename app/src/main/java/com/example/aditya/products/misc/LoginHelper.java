package com.example.aditya.products.misc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by trust on 8/1/2016.
 */
public class LoginHelper extends SQLiteOpenHelper {

    public LoginHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users" + "(username text, password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertUser(String username, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("users", null, "username=? and password=?", new String[] {username, pass}, null, null, null);
        if (cursor.getCount() > 0){
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", pass);
        db.insert("users", null, values);
        return true;
    }

    public boolean getUser(String username, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", null, "username=? and password=?", new String[] { username,pass}, null, null, null);
        if (cursor.getCount() == 1){
            Log.e("getUser", "Success getting user");
            return true;
        }
        Log.e("getUser", "Error getting user");
        return false;
    }


}
