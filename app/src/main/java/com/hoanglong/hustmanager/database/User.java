package com.hoanglong.hustmanager.database;

public class User {

    static final String TABLET_NAME = "users";

    static final String COLUMN_ID = "id";
    static final String COLUMN_USER_NAME = "user_name";
    static final String COLUMN_PASSWORD = "password";
    static final String COLUMN_CLASS = "class";
    static final String COLUMN_POINTS = "points";
    static final String COLUMN_HOCKY = "hocky";
    static final String COLUMN_TOTAL_TC = "total_tc";

    private String mUserName;
    private String mPassword;
    private String mClass;
    private int totalTC;
    private String mIdUser;
}
