package com.example.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Students.db";

    public static final String TABLE_STUDENTS = "students";
    public static final String COL_STUDENT_ID = "STUDENT_ID";
    public static final String COL_FIRST_NAME = "FISRT_NAME";
    public static final String COL_LAST_NAME = "LAST_NAME";
    public static final String COL_SUBJECT = "SUBJECT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_STUDENTS+" ("+COL_STUDENT_ID+" INTEGER PRIMARY KEY, "+COL_FIRST_NAME+" TEXT, "+COL_LAST_NAME+" TEXT, "+COL_SUBJECT+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_STUDENTS);
        onCreate(db);
    }

    public void addData(String firstName, String lastName, String subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor maxIdResult = db.rawQuery("SELECT max("+COL_STUDENT_ID+")FROM "+TABLE_STUDENTS, null);
        maxIdResult.moveToFirst();
        String maxId =  maxIdResult.getString(0);
        int nextId = Integer.parseInt(maxId) + 1;

        db.execSQL("INSERT INTO " + TABLE_STUDENTS + " VALUES ("+nextId+",?,?,?)",new String[] {firstName, lastName, subject});
    }

    public Cursor searchById(String searchId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COL_STUDENT_ID + " = ?", new String[] {searchId});
        return result;
    }

    public Cursor viewAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);
        return result;
    }

    public void deleteById(String deleteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_STUDENTS + " WHERE " + COL_STUDENT_ID + " = ?", new String[] {deleteId});
    }

    public void updateById(String updateId, String colName, String newValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_STUDENTS + " SET "+ colName+" = ? WHERE " + COL_STUDENT_ID + " = ?", new String[] {newValue, updateId});
    }
}
