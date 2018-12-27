package com.hoanglong.hustmanager.database;

public class Teacher {

    static final String TABLET_NAME = "giaovien";

    static final String COLUMN_ID = "id";
    static final String COLUMN_USER_NAME = "user_name";
    static final String COLUMN_PASSWORD = "password";

    private String mUserName;
    private String mPassword;
    private int mId;

    // Create table SQL query
    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLET_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USER_NAME + " TEXT,"
                    + COLUMN_PASSWORD + " TEXT"
                    + ")";

    public Teacher(String userName, String password, int id) {
        mUserName = userName;
        mPassword = password;
        mId = id;
    }

    public Teacher(String userName, String password) {
        mUserName = userName;
        mPassword = password;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
