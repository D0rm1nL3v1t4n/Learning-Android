package com.example.tabbedactivityapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "categories.db";

    public static final String TABLE_CATEGORIES = "primary_categories";
    public static final String COL_CATEGORY_ID = "id";
    public static final String COL_CATEGORY_NAME = "name";

    public static final String TABLE_BUDGET = "budget";
    public static final String COL_BUDGET_ID = "id";
    public static final String COL_BUDGET_VALUE = "budget_value";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_CATEGORIES+" ("+COL_CATEGORY_ID+" INTEGER PRIMARY KEY, "+COL_CATEGORY_NAME+" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table "+TABLE_BUDGET+" ("+COL_BUDGET_ID+" INTEGER PRIMARY KEY, "+COL_BUDGET_VALUE+" TEXT)");
    }

    public void addData(String tableName, String newData) {
        SQLiteDatabase db = this.getWritableDatabase();

        String maxId = getMaxId(tableName);
        int nextId;
        if (maxId == null)
            nextId = 1;
        else
            nextId = Integer.parseInt(maxId) + 1;

        db.execSQL("INSERT INTO " + tableName + " VALUES (" + nextId + ",?)", new String[] {newData});
    }

    public Cursor searchData(String tableName, String searchCol, String searchData) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + searchCol + " = ?", new String[] {searchData});
        return result;
    }

    public Cursor viewAll(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + tableName, null);
        return result;
    }

    public void deleteData(String tableName, String searchCol, String searchData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName + " WHERE " + searchCol + " = ?", new String[] {searchData});
    }

    public void updateData(String tableName, String searchCol, String searchData, String updateCol, String newValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + tableName + " SET "+ updateCol+" = ? WHERE " + searchCol + " = ?", new String[] {newValue, searchData});
    }

    public String getMaxId(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor maxId = db.rawQuery("SELECT max(id) FROM " + tableName, null);
        maxId.moveToFirst();
        return maxId.getString(0);
    }
}
