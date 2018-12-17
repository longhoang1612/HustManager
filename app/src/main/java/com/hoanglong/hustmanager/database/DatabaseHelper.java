package com.hoanglong.hustmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "hustmanager_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create Subjects table
        db.execSQL(Subject.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Subject.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertSuject(Subject subject) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Subject.COLUMN_NAME, subject.getSubjectName());
        values.put(Subject.COLUMN_SUBJECT_CODE, subject.getSubjectCode());
        values.put(Subject.COLUMN_NUMBER_CREDITS, subject.getSubjectSoTinChi());

        // insert row
        long id = db.insert(Subject.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Subject getSubject(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Subject.TABLE_NAME,
                new String[]{Subject.COLUMN_ID, Subject.COLUMN_NAME, Subject.COLUMN_SUBJECT_CODE, Subject.COLUMN_NUMBER_CREDITS},
                Subject.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subject object
        Subject subject = null;
        if (cursor != null) {
            subject = new Subject(
                    cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Subject.COLUMN_SUBJECT_CODE)),
                    cursor.getString(cursor.getColumnIndex(Subject.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_NUMBER_CREDITS)));
            cursor.close();
            return subject;
        }
        return null;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Subject.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Subject subject = new Subject();
                subject.setIdSubject(cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_ID)));
                subject.setSubjectCode(cursor.getString(cursor.getColumnIndex(Subject.COLUMN_SUBJECT_CODE)));
                subject.setSubjectName(cursor.getString(cursor.getColumnIndex(Subject.COLUMN_NAME)));
                subject.setSubjectSoTinChi(cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_NUMBER_CREDITS)));

                subjects.add(subject);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subjects list
        return subjects;
    }

    public int getSubjectsCount() {
        String countQuery = "SELECT  * FROM " + Subject.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Subject.COLUMN_NAME, subject.getSubjectName());
        values.put(Subject.COLUMN_SUBJECT_CODE, subject.getSubjectCode());
        values.put(Subject.COLUMN_NUMBER_CREDITS, subject.getSubjectSoTinChi());

        // updating row
        return db.update(Subject.TABLE_NAME, values, Subject.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subject.getIdSubject())});
    }

    public void deleteSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Subject.TABLE_NAME, Subject.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subject.getIdSubject())});
        db.close();
    }
}
