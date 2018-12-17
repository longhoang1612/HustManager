package com.hoanglong.hustmanager.database;

public class Subject {

    static final String TABLE_NAME = "subjects";

    static final String COLUMN_ID = "id";
    static final String COLUMN_SUBJECT_CODE = "subject_code";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_NUMBER_CREDITS = "number_credits";
    public static final String COLUMN_POINTS = "points";

    private int mIdSubject;
    private String mSubjectCode;
    private String mSubjectName;
    private int mSubjectSoTinChi;
    private String mPointSubject;

    Subject() {
    }

    // Create table SQL query
    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SUBJECT_CODE + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_NUMBER_CREDITS + " INTEGER,"
                    + COLUMN_POINTS + " TEXT"
                    + ")";

    public Subject(String subjectCode, String subjectName, int subjectSoTinChi) {
        mSubjectCode = subjectCode;
        mSubjectName = subjectName;
        mSubjectSoTinChi = subjectSoTinChi;
    }

    Subject(int idSubject, String subjectCode, String subjectName, int subjectSoTinChi) {
        mSubjectCode = subjectCode;
        mSubjectName = subjectName;
        mSubjectSoTinChi = subjectSoTinChi;
        mIdSubject = idSubject;
    }

    public Subject(int idSubject, String subjectCode, String subjectName, int subjectSoTinChi, String pointSubject) {
        mIdSubject = idSubject;
        mSubjectCode = subjectCode;
        mSubjectName = subjectName;
        mSubjectSoTinChi = subjectSoTinChi;
        mPointSubject = pointSubject;
    }

    public String getSubjectCode() {
        return mSubjectCode;
    }

    void setSubjectCode(String subjectCode) {
        mSubjectCode = subjectCode;
    }

    public String getSubjectName() {
        return mSubjectName;
    }

    void setSubjectName(String subjectName) {
        mSubjectName = subjectName;
    }

    public int getSubjectSoTinChi() {
        return mSubjectSoTinChi;
    }

    void setSubjectSoTinChi(int subjectSoTinChi) {
        mSubjectSoTinChi = subjectSoTinChi;
    }

    int getIdSubject() {
        return mIdSubject;
    }

    void setIdSubject(int idSubject) {
        mIdSubject = idSubject;
    }
}
