package com.hoanglong.hustmanager.data;

public class Subject {

    private String mSubjectCode;
    private String mSubjectName;
    private String mSubjectSoTinChi;

    public Subject() {
    }

    public Subject(String subjectCode, String subjectName, String subjectSoTinChi) {
        mSubjectCode = subjectCode;
        mSubjectName = subjectName;
        mSubjectSoTinChi = subjectSoTinChi;
    }

    public String getSubjectCode() {
        return mSubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        mSubjectCode = subjectCode;
    }

    public String getSubjectName() {
        return mSubjectName;
    }

    public void setSubjectName(String subjectName) {
        mSubjectName = subjectName;
    }

    public String getSubjectSoTinChi() {
        return mSubjectSoTinChi;
    }

    public void setSubjectSoTinChi(String subjectSoTinChi) {
        mSubjectSoTinChi = subjectSoTinChi;
    }
}
