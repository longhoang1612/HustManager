package com.hoanglong.hustmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.hoanglong.hustmanager.database.User.KEY_EMAIL;
import static com.hoanglong.hustmanager.database.User.KEY_ID;
import static com.hoanglong.hustmanager.database.User.KEY_PASSWORD;
import static com.hoanglong.hustmanager.database.User.KEY_USER_NAME;
import static com.hoanglong.hustmanager.database.User.TABLE_USERS;

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
        db.execSQL(Teacher.CREATE_TABLE);
        db.execSQL(Class.CREATE_TABLE);
        db.execSQL(Student.CREATE_TABLE);
        db.execSQL(User.SQL_TABLE_USERS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Subject.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Teacher.TABLET_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Class.TABLET_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLET_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_USERS);
        // Create tables again
        onCreate(db);
    }

    //User
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(User.TABLE_USERS,// Selecting Table
                new String[]{User.KEY_ID, User.KEY_USER_NAME, User.KEY_EMAIL, User.KEY_PASSWORD},//Selecting columns want to query
                User.KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }

    public User getUser(String email) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(User.TABLE_USERS,
                new String[]{User.KEY_ID, User.KEY_EMAIL,
                        User.KEY_USER_NAME, User.KEY_PASSWORD},
                User.KEY_EMAIL + "=?",
                new String[]{String.valueOf(email)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subject object
        User user;
        if (cursor != null) {
            user = new User(
                    cursor.getString(cursor.getColumnIndex(User.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(User.KEY_USER_NAME)),
                    cursor.getString(cursor.getColumnIndex(User.KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(User.KEY_PASSWORD))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    //Teacher
    public long insertGV(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Teacher.COLUMN_USER_NAME, teacher.getUserName());
        values.put(Teacher.COLUMN_PASSWORD, teacher.getPassword());

        // insert row
        long id = db.insert(Teacher.TABLET_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long addClass(Class objClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Class.COLUMN_ID, objClass.getId());
        values.put(Class.COLUMN_CLASS_HK, objClass.getHocky());
        values.put(Class.COLUMN_CLASS_MALH, objClass.getMaLH());
        values.put(Class.COLUMN_CLASS_TENHP, objClass.getTenHP());
        values.put(Class.COLUMN_CLASS_SOTC, objClass.getSoTC());
        values.put(Class.COLUMN_CLASS_TENGV, objClass.getTenGV());
        values.put(Class.COLUMN_CLASS_IDGV, objClass.getIdGV());

        // insert row
        long id = db.insert(Class.TABLET_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<Class> getClassWithGV(String nameGV) {
        List<Class> classes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Class.TABLET_NAME + " WHERE  giaovien = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nameGV});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Class aClass = new Class();
                aClass.setId(cursor.getString(cursor.getColumnIndex(Class.COLUMN_ID)));
                aClass.setHocky(cursor.getInt(cursor.getColumnIndex(Class.COLUMN_CLASS_HK)));
                aClass.setIdGV(cursor.getString(cursor.getColumnIndex(Class.COLUMN_CLASS_IDGV)));
                aClass.setMaLH(cursor.getString(cursor.getColumnIndex(Class.COLUMN_CLASS_MALH)));
                aClass.setSoTC(cursor.getInt(cursor.getColumnIndex(Class.COLUMN_CLASS_SOTC)));
                aClass.setTenGV(cursor.getString(cursor.getColumnIndex(Class.COLUMN_CLASS_TENGV)));
                aClass.setTenHP(cursor.getString(cursor.getColumnIndex(Class.COLUMN_CLASS_TENHP)));

                classes.add(aClass);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subjects list
        return classes;
    }

    //Student

    public long insertStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Student.COLUMN_STUDENT_NAME, student.getNameStudent());
        values.put(Student.COLUMN_STUDENT_DIEMCK, student.getDiemCK());
        values.put(Student.COLUMN_STUDENT_DIEMQT, student.getDiemQT());
        values.put(Student.COLUMN_STUDENT_LOPHOC, student.getMaLopHoc());
        values.put(Student.COLUMN_STUDENT_MAHP, student.getMaLopMonHoc());
        values.put(Student.COLUMN_STUDENT_MASV, student.getMaSV());
        values.put(Student.COLUMN_STUDENT_IMAGE, student.getImage());

        // insert row
        long id = db.insert(Student.TABLET_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<Student> getStudentWithIdClass(String maHP) {
        List<Student> students = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Student.TABLET_NAME + " WHERE " + Student.COLUMN_STUDENT_MAHP + " = ? ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{maHP});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setImage(cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_IMAGE)));
                student.setId(cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_ID)));
                student.setNameStudent(cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_NAME)));
                student.setMaSV(cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_MASV)));
                student.setMaLopMonHoc(cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_MAHP)));
                student.setMaLopHoc(cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_LOPHOC)));
                student.setDiemCK(cursor.getFloat(cursor.getColumnIndex(Student.COLUMN_STUDENT_DIEMCK)));
                student.setDiemQT(cursor.getFloat(cursor.getColumnIndex(Student.COLUMN_STUDENT_DIEMQT)));

                students.add(student);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subjects list
        return students;
    }

    public Student getStudent(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Student.TABLET_NAME,
                new String[]{Student.COLUMN_STUDENT_ID, Student.COLUMN_STUDENT_NAME, Student.COLUMN_STUDENT_IMAGE,
                        Student.COLUMN_STUDENT_MASV, Student.COLUMN_STUDENT_MAHP,
                        Student.COLUMN_STUDENT_LOPHOC, Student.COLUMN_STUDENT_DIEMCK, Student.COLUMN_STUDENT_DIEMQT},
                Student.COLUMN_STUDENT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subject object
        Student student;
        if (cursor != null) {
            student = new Student(
                    cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_NAME)),
                    cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_LOPHOC)),
                    cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_MAHP)),
                    cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_MASV)),
                    cursor.getFloat(cursor.getColumnIndex(Student.COLUMN_STUDENT_DIEMQT)),
                    cursor.getFloat(cursor.getColumnIndex(Student.COLUMN_STUDENT_DIEMCK)),
                    cursor.getString(cursor.getColumnIndex(Student.COLUMN_STUDENT_ID))
            );
            cursor.close();
            return student;
        }
        return null;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Student.COLUMN_STUDENT_IMAGE, student.getImage());
        values.put(Student.COLUMN_STUDENT_NAME, student.getNameStudent());
        values.put(Student.COLUMN_STUDENT_DIEMQT, student.getDiemQT());
        values.put(Student.COLUMN_STUDENT_DIEMCK, student.getDiemCK());
        values.put(Student.COLUMN_STUDENT_LOPHOC, student.getMaLopHoc());
        values.put(Student.COLUMN_STUDENT_MASV, student.getMaSV());

        // updating row
        return db.update(Student.TABLET_NAME, values, Student.COLUMN_STUDENT_MASV + " = ?",
                new String[]{student.getMaSV()});
    }

    public void deleteStudent(String msv) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Student.TABLET_NAME, Student.COLUMN_STUDENT_MASV + " = ?",
                new String[]{msv});
        db.close();
    }

    //POINT

    public long insertPoint(Subject subject) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Subject.COLUMN_HOCKY, subject.getHocKy());
        values.put(Subject.COLUMN_SUBJECT_CODE, subject.getSubjectCode());
        values.put(Subject.COLUMN_NAME, subject.getSubjectName());
        values.put(Subject.COLUMN_NUMBER_CREDITS, subject.getSubjectSoTinChi());
        values.put(Subject.COLUMN_DIEMQT, subject.getDiemQT());
        values.put(Subject.COLUMN_DIEMCK, subject.getDiemThi());
        values.put(Subject.COLUMN_POINTS, subject.getPointSubject());

        // insert row
        long id = db.insert(Subject.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Subject getPoint(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Subject.TABLE_NAME,
                new String[]{Subject.COLUMN_ID, Subject.COLUMN_HOCKY, Subject.COLUMN_NAME,
                        Subject.COLUMN_SUBJECT_CODE, Subject.COLUMN_NUMBER_CREDITS,
                        Subject.COLUMN_DIEMQT, Subject.COLUMN_DIEMCK, Subject.COLUMN_POINTS},
                Subject.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subject object
        Subject subject;
        if (cursor != null) {
            subject = new Subject(
                    cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_HOCKY)),
                    cursor.getFloat(cursor.getColumnIndex(Subject.COLUMN_DIEMQT)),
                    cursor.getFloat(cursor.getColumnIndex(Subject.COLUMN_DIEMCK)),
                    cursor.getString(cursor.getColumnIndex(Subject.COLUMN_SUBJECT_CODE)),
                    cursor.getString(cursor.getColumnIndex(Subject.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_NUMBER_CREDITS)),
                    cursor.getString(cursor.getColumnIndex(Subject.COLUMN_POINTS))
            );
            cursor.close();
            return subject;
        }
        return null;
    }

    public List<Subject> getAllPoint() {
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
                subject.setDiemQT(cursor.getFloat(cursor.getColumnIndex(Subject.COLUMN_DIEMQT)));
                subject.setDiemThi(cursor.getFloat(cursor.getColumnIndex(Subject.COLUMN_DIEMCK)));
                subject.setSubjectCode(cursor.getString(cursor.getColumnIndex(Subject.COLUMN_SUBJECT_CODE)));
                subject.setSubjectName(cursor.getString(cursor.getColumnIndex(Subject.COLUMN_NAME)));
                subject.setSubjectSoTinChi(cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_NUMBER_CREDITS)));
                subject.setPointSubject(cursor.getString(cursor.getColumnIndex(Subject.COLUMN_POINTS)));
                subject.setHocKy(cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_HOCKY)));
                subjects.add(subject);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subjects list
        return subjects;
    }

    public int getPointsCount() {
        String countQuery = "SELECT  * FROM " + Subject.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int updatePoint(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Subject.COLUMN_DIEMQT, subject.getDiemQT());
        values.put(Subject.COLUMN_DIEMCK, subject.getDiemThi());
        values.put(Subject.COLUMN_POINTS, subject.getPointSubject());

        // updating row
        return db.update(Subject.TABLE_NAME, values, Subject.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subject.getIdSubject())});
    }

    public void deletePoint(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Subject.TABLE_NAME, Subject.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subject.getIdSubject())});
        db.close();
    }


    //SUBJECT

    public long insertSubject(Subject subject) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Subject.COLUMN_NAME, subject.getSubjectName());
        values.put(Subject.COLUMN_SUBJECT_CODE, subject.getSubjectCode());
        values.put(Subject.COLUMN_NUMBER_CREDITS, subject.getSubjectSoTinChi());
        values.put(Subject.COLUMN_POINTS, subject.getPointSubject());

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
                new String[]{Subject.COLUMN_ID, Subject.COLUMN_NAME, Subject.COLUMN_SUBJECT_CODE, Subject.COLUMN_NUMBER_CREDITS, Subject.COLUMN_POINTS},
                Subject.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subject object
        Subject subject;
        if (cursor != null) {
            subject = new Subject(
                    cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Subject.COLUMN_SUBJECT_CODE)),
                    cursor.getString(cursor.getColumnIndex(Subject.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_NUMBER_CREDITS)),
                    cursor.getString(cursor.getColumnIndex(Subject.COLUMN_POINTS))
            );
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
                subject.setPointSubject(cursor.getString(cursor.getColumnIndex(Subject.COLUMN_POINTS)));
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
        values.put(Subject.COLUMN_POINTS, subject.getPointSubject());

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
