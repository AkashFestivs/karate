package com.ama.karate.dto;

public class StudentDto {
    
    private String studentName;
    private String studentBelt;
    private String studentProfileUrl;
    private String studentLid;
    private String className;
    private String phoneNo;

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentBelt() {
        return studentBelt;
    }
    public void setStudentBelt(String studentBelt) {
        this.studentBelt = studentBelt;
    }
    public String getStudentProfileUrl() {
        return studentProfileUrl;
    }
    public void setStudentProfileUrl(String studentProfileUrl) {
        this.studentProfileUrl = studentProfileUrl;
    }

    public String getStudentLid() {
        return studentLid;
    }

    public void setStudentLid(String studentLid) {
        this.studentLid = studentLid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    
    @Override
    public String toString() {
        return "{" +
                "\"studentName\":\"" + studentName + "\"," +
                "\"studentBelt\":\"" + studentBelt + "\"," +
                "\"studentProfileUrl\":\"" + studentProfileUrl + "\"," +
                "\"className\":\"" + className + "\"," +
                "\"phoneNo\":\"" + phoneNo + "\"," +
                "\"studentLid\":" + studentLid +
                "}";
    }
    
    
}
