package com.ama.karate.dto;

public class StudentDto {
    
    private String studentName;
    private String studentBelt;
    private String studentProfileUrl;
    private String student_lid;

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
    public String getStudent_lid() {
        return student_lid;
    }
    public void setStudent_lid(String student_lid) {
        this.student_lid = student_lid;
    }
    
    @Override
    public String toString() {
        return "{" +
                "\"studentName\":\"" + studentName + "\"," +
                "\"studentBelt\":\"" + studentBelt + "\"," +
                "\"studentProfileUrl\":\"" + studentProfileUrl + "\"," +
                "\"student_lid\":" + student_lid +
                "}";
    }

    

    
    
}
