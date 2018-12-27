package com.hoanglong.hustmanager.database;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String nameStudent;
    private String maLopHoc;
    private String maLopMonHoc;
    private String maSV;
    private Float diemQT;
    private Float diemCK;
    private int id;

    public static final String COLUMN_STUDENT_NAME = "name_student";
    public static final String COLUMN_STUDENT_LOPHOC = "lophoc";
    public static final String COLUMN_STUDENT_MAHP = "ma_hocphan";
    public static final String COLUMN_STUDENT_MASV = "ma_sinhvien";
    public static final String COLUMN_STUDENT_DIEMQT = "diem_qt";
    public static final String COLUMN_STUDENT_DIEMCK = "diem_ck";
    public static final String COLUMN_STUDENT_ID = "id";
    public static final String TABLET_NAME = "student";

    public Student() {
    }

    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLET_NAME + "("
                    + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_STUDENT_NAME + " TEXT,"
                    + COLUMN_STUDENT_LOPHOC + " TEXT,"
                    + COLUMN_STUDENT_MAHP + " TEXT,"
                    + COLUMN_STUDENT_MASV + " TEXT,"
                    + COLUMN_STUDENT_DIEMQT + " FLOAT,"
                    + COLUMN_STUDENT_DIEMCK + " FLOAT"
                    + ")";

    public Student(String nameStudent, String maLopHoc, String maLopMonHoc, String maSV, Float diemQT, Float diemCK, int id) {
        this.nameStudent = nameStudent;
        this.maLopHoc = maLopHoc;
        this.maLopMonHoc = maLopMonHoc;
        this.maSV = maSV;
        this.diemQT = diemQT;
        this.diemCK = diemCK;
        this.id = id;
    }

    private Student(Parcel in) {
        nameStudent = in.readString();
        maLopHoc = in.readString();
        maLopMonHoc = in.readString();
        maSV = in.readString();
        if (in.readByte() == 0) {
            diemQT = null;
        } else {
            diemQT = in.readFloat();
        }
        if (in.readByte() == 0) {
            diemCK = null;
        } else {
            diemCK = in.readFloat();
        }
        id = in.readInt();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nameStudent);
        parcel.writeString(maLopHoc);
        parcel.writeString(maLopMonHoc);
        parcel.writeString(maSV);
        if (diemQT == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(diemQT);
        }
        if (diemCK == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(diemCK);
        }
        parcel.writeInt(id);
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
    }

    public String getMaLopMonHoc() {
        return maLopMonHoc;
    }

    public void setMaLopMonHoc(String maLopMonHoc) {
        this.maLopMonHoc = maLopMonHoc;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public Float getDiemQT() {
        return diemQT;
    }

    public void setDiemQT(Float diemQT) {
        this.diemQT = diemQT;
    }

    public Float getDiemCK() {
        return diemCK;
    }

    public void setDiemCK(Float diemCK) {
        this.diemCK = diemCK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
