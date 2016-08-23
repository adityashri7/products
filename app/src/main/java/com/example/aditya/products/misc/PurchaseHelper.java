package com.example.aditya.products.misc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.UnknownFormatConversionException;

/**
 * Created by trust on 8/1/2016.
 */
public class PurchaseHelper extends SQLiteOpenHelper {

    public PurchaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table items" + "(user text, item text, amount integer, bought integer, image text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertItem(String user, String item, Integer amount, String encodedImage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user", user);
        values.put("item", item);
        values.put("amount", amount);
        values.put("bought", 0);
        values.put("image", encodedImage);
        db.insert("items", null, values);
        return true;
    }

    public int updateItem (String user, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item", item);
        values.put("bought", 1);
        return db.update("items", values, "user=? and item=?", new String[] {user, item});

    }

    public ArrayList<String> getPurchased(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from items where user=? and bought = 1", new String[] {user});
        ArrayList<String> list = new ArrayList<>();
        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            Log.e("PurchasedList", "Cursor: " + cursor.getColumnNames().toString());
            while(cursor.isAfterLast() == false){
                list.add(cursor.getString(cursor.getColumnIndex("item")));
                Log.e("PurchasedList", "Adding: " + cursor.getColumnIndex("item"));
                cursor.moveToNext();
            }
            return list;
        }
        Log.e("PurchasedList", "Returning null");
        return null;
    }


    public ArrayList getPending(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from items where user =? and bought = 0", new String[] {user});
        //Cursor cursor = db.query("items", null, "amount=?", new String[] {"0"}, null, null, null);
        ArrayList<String> list = new ArrayList<>();
        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            Log.e("PurchasedList", "Cursor: " + cursor.getColumnNames().toString());
            while(cursor.isAfterLast() == false){
                list.add(cursor.getString(cursor.getColumnIndex("item")));
                Log.e("PurchasedList", "Adding: " + cursor.getColumnIndex("item"));
                cursor.moveToNext();
            }
            return list;
        }
        Log.e("PendingList", "Returning null");
        return null;
    }

    public ArrayList getPendingImages(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from items where user =? and bought = 0", new String[] {user});
        //Cursor cursor = db.query("items", null, "amount=?", new String[] {"0"}, null, null, null);
        ArrayList<String> list = new ArrayList<>();
        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            Log.e("PendingList", "Cursor: " + cursor.getColumnNames().toString());
            while(cursor.isAfterLast() == false){
                list.add(cursor.getString(cursor.getColumnIndex("image")));
                cursor.moveToNext();
            }
            return list;
        }
        Log.e("PendingList", "Returning null");
        return null;
    }
}
