package com.hoanglong.hustmanager.database;

import android.os.Parcel;
import android.os.Parcelable;

public class Class implements Parcelable {

    public static final String TABLET_NAME = "class";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CLASS_MALH = "malop";
    public static final String COLUMN_CLASS_HK = "hocky";
    public static final String COLUMN_CLASS_TENGV = "giaovien";
    public static final String COLUMN_CLASS_IDGV = "id_giaovien";
    public static final String COLUMN_CLASS_TENHP = "tenhp";
    public static final String COLUMN_CLASS_SOTC = "sotinchi";

    private String maLH;
    private int soTC;
    private String tenHP;
    private String tenGV;
    private String idGV;
    private int hocky;
    private String id;

    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLET_NAME + "("
                    + COLUMN_ID + " TEXT PRIMARY KEY,"
                    + COLUMN_CLASS_HK + " INTEGER,"
                    + COLUMN_CLASS_SOTC + " INTEGER,"
                    + COLUMN_CLASS_MALH + " TEXT,"
                    + COLUMN_CLASS_TENGV + " TEXT,"
                    + COLUMN_CLASS_IDGV + " TEXT,"
                    + COLUMN_CLASS_TENHP + " TEXT"
                    + ")";

    public Class() {
    }

    public Class(String id, String maLH, int soTC, String tenHP, String tenGV, String idGV, int hocky) {
        this.id = id;
        this.maLH = maLH;
        this.soTC = soTC;
        this.tenHP = tenHP;
        this.tenGV = tenGV;
        this.idGV = idGV;
        this.hocky = hocky;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected Class(Parcel in) {
        maLH = in.readString();
        soTC = in.readInt();
        tenHP = in.readString();
        tenGV = in.readString();
        idGV = in.readString();
        hocky = in.readInt();
    }

    public static final Creator<Class> CREATOR = new Creator<Class>() {
        @Override
        public Class createFromParcel(Parcel in) {
            return new Class(in);
        }

        @Override
        public Class[] newArray(int size) {
            return new Class[size];
        }
    };

    public String getMaLH() {
        return maLH;
    }

    public void setMaLH(String maLH) {
        this.maLH = maLH;
    }

    public int getSoTC() {
        return soTC;
    }

    public void setSoTC(int soTC) {
        this.soTC = soTC;
    }

    public String getTenHP() {
        return tenHP;
    }

    public void setTenHP(String tenHP) {
        this.tenHP = tenHP;
    }

    public String getTenGV() {
        return tenGV;
    }

    public void setTenGV(String tenGV) {
        this.tenGV = tenGV;
    }

    public String getIdGV() {
        return idGV;
    }

    public void setIdGV(String idGV) {
        this.idGV = idGV;
    }

    public int getHocky() {
        return hocky;
    }

    public void setHocky(int hocky) {
        this.hocky = hocky;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(maLH);
        parcel.writeInt(soTC);
        parcel.writeString(tenHP);
        parcel.writeString(tenGV);
        parcel.writeString(idGV);
        parcel.writeInt(hocky);
    }
}
