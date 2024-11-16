package com.ama.karate.dto;


public class ClassesDto {

    private String classesName;
    private String instructorName;
    private String classesAddress;
    private String classesTiming;
    private int totalStudents;
    private Long classesLid;
    private String classesCity;
    private String classesFees;
    private String admissionFees;
    private boolean isMain;

    public String getClassesFees() {
        return classesFees;
    }
    public void setClassesFees(String classesFees) {
        this.classesFees = classesFees;
    }
    public String getAdmissionFees() {
        return admissionFees;
    }
    public void setAdmissionFees(String admissionFees) {
        this.admissionFees = admissionFees;
    }
    public boolean isMain() {
        return isMain;
    }
    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }
    public String getClassesName() {
        return classesName;
    }
    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }
    public String getInstructorName() {
        return instructorName;
    }
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
    public String getClassesAddress() {
        return classesAddress;
    }
    public void setClassesAddress(String classesAddress) {
        this.classesAddress = classesAddress;
    }
    public String getClassesTiming() {
        return classesTiming;
    }
    public void setClassesTiming(String classesTiming) {
        this.classesTiming = classesTiming;
    }
    public int getTotalStudents() {
        return totalStudents;
    }
    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }
    public Long getClassesLid() {
        return classesLid;
    }
    public void setClassesLid(Long classesLid) {
        this.classesLid = classesLid;
    }

    public String getClassesCity() {
        return classesCity;
    }
    public void setClassesCity(String classesCity) {
        this.classesCity = classesCity;
    }
    
    @Override
    public String toString() {
        return "{" +
                "\"classesName\":\"" + (classesName != null ? classesName : "") + "\"," +
                "\"instructorName\":\"" + (instructorName != null ? instructorName : "") + "\"," +
                "\"classesAddress\":\"" + (classesAddress != null ? classesAddress : "") + "\"," +
                "\"classesTiming\":\"" + (classesTiming != null ? classesTiming : "") + "\"," +
                "\"totalStudents\":" + totalStudents + "," +
                "\"classesLid\":" + (classesLid != null ? classesLid : "null") + "," +
                "\"classesCity\":\"" + (classesCity != null ? classesCity : "") + "\"," +
                "\"classesFees\":\"" + (classesFees != null ? classesFees : "") + "\"," +
                "\"admissionFees\":\"" + (admissionFees != null ? admissionFees : "") + "\"," +
                "\"isMain\":" + isMain +
                "}";
    }
    
}
