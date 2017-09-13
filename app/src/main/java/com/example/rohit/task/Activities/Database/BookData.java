package com.example.rohit.task.Activities.Database;

/**
 * Created by ROHIT on 9/11/2017.
 */

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;


public class BookData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "NAME";
    public static final String COLUMN_3 = "AUTHOR";
    public static final String COLUMN_4 = "ISBN";
    public static final String COLUMN_5 = "COVER";


    public BookData(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /** Create the database.implementing SQL query **/
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,AUTHOR TEXT,ISBN INTEGER)");
        //db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,AUTHOR TEXT,ISBN INTEGER,COVER BLOB)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

   /* public void insertData(String name,String author,String isbn, byte[] cover) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2,name);
        contentValues.put(COLUMN_3,author);
        contentValues.put(COLUMN_4,isbn);
        contentValues.put(COLUMN_5,cover);
        db.insert(TABLE_NAME, null, contentValues);

    }*/


    /** Function to insert data in the next coloumn **/
    public void insertData(String name,String author,String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2,name);
        contentValues.put(COLUMN_3,author);
        contentValues.put(COLUMN_4,isbn);
        db.insert(TABLE_NAME, null, contentValues);

    }

    /** Function to fetch the whole data **/
    public Cursor getAllData() {
        //Instance of SQLiteDatabase class
        SQLiteDatabase db = this.getWritableDatabase();

        //Instance of Cursor class
        Cursor output = db.rawQuery("select * from " + TABLE_NAME, null);
        return output;
    }


    /** Function to update database **/
    public boolean updateData(String id,String name,String author,String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1,id);
        contentValues.put(COLUMN_2,name);
        contentValues.put(COLUMN_3,author);
        contentValues.put(COLUMN_4,isbn);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    /** Function to delete a column in database **/
    public Integer deleteData (String id) {
        //Instance of SQLiteDatabase class
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}