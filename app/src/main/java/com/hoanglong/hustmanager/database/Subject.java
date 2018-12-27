package com.hoanglong.hustmanager.database;

import android.os.Parcel;
import android.os.Parcelable;

public class Subject implements Parcelable {

    static final String TABLE_NAME = "subjects";

    static final String COLUMN_ID = "id";
    static final String COLUMN_SUBJECT_CODE = "subject_code";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_NUMBER_CREDITS = "number_credits";
    static final String COLUMN_POINTS = "points";
    static final String COLUMN_HOCKY = "hocky";
    static final String COLUMN_DIEMQT = "diemQT";
    static final String COLUMN_DIEMCK = "diemCK";

    private int mIdSubject;
    private int mHocKy;
    private float mDiemQT;
    private float mDiemThi;
    private String mSubjectCode;
    private String mSubjectName;
    private int mSubjectSoTinChi;
    private String mPointSubject;
    private boolean isHeader = false;

    public Subject() {
    }

    // Create table SQL query
    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_HOCKY + " TEXT,"
                    + COLUMN_SUBJECT_CODE + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_NUMBER_CREDITS + " INTEGER,"
                    + COLUMN_DIEMQT + " FLOAT,"
                    + COLUMN_DIEMCK + " FLOAT,"
                    + COLUMN_POINTS + " TEXT"
                    + ")";

    protected Subject(Parcel in) {
        mIdSubject = in.readInt();
        mHocKy = in.readInt();
        mDiemQT = in.readFloat();
        mDiemThi = in.readFloat();
        mSubjectCode = in.readString();
        mSubjectName = in.readString();
        mSubjectSoTinChi = in.readInt();
        mPointSubject = in.readString();
        isHeader = in.readByte() != 0;
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public Subject(int idSubject, String subjectCode, String subjectName, int subjectSoTinChi, String pointSubject) {
        mIdSubject = idSubject;
        mSubjectCode = subjectCode;
        mSubjectName = subjectName;
        mSubjectSoTinChi = subjectSoTinChi;
        mPointSubject = pointSubject;
    }

    public Subject(String subjectCode, String subjectName, int subjectSoTinChi, String pointSubject) {
        mSubjectCode = subjectCode;
        mSubjectName = subjectName;
        mSubjectSoTinChi = subjectSoTinChi;
        mPointSubject = pointSubject;
    }

    public Subject(int idSubject, int hocKy, float diemQT, float diemThi, String subjectCode, String subjectName, int subjectSoTinChi, String pointSubject) {
        mIdSubject = idSubject;
        mHocKy = hocKy;
        mDiemQT = diemQT;
        mDiemThi = diemThi;
        mSubjectCode = subjectCode;
        mSubjectName = subjectName;
        mSubjectSoTinChi = subjectSoTinChi;
        mPointSubject = pointSubject;
    }

    public Subject(int hocKy, float diemQT, float diemThi, String subjectCode, String subjectName, int subjectSoTinChi, String pointSubject) {
        mHocKy = hocKy;
        mDiemQT = diemQT;
        mDiemThi = diemThi;
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

    public String getPointSubject() {
        return mPointSubject;
    }

    void setPointSubject(String pointSubject) {
        mPointSubject = pointSubject;
    }

    public int getHocKy() {
        return mHocKy;
    }

    public void setHocKy(int hocKy) {
        mHocKy = hocKy;
    }

    public float getDiemQT() {
        return mDiemQT;
    }

    public void setDiemQT(float diemQT) {
        mDiemQT = diemQT;
    }

    public float getDiemThi() {
        return mDiemThi;
    }

    public void setDiemThi(float diemThi) {
        mDiemThi = diemThi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIdSubject);
        dest.writeInt(mHocKy);
        dest.writeFloat(mDiemQT);
        dest.writeFloat(mDiemThi);
        dest.writeString(mSubjectCode);
        dest.writeString(mSubjectName);
        dest.writeInt(mSubjectSoTinChi);
        dest.writeString(mPointSubject);
        dest.writeByte((byte) (isHeader ? 1 : 0));
    }
}
