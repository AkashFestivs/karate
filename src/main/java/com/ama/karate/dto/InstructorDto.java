package com.ama.karate.dto;

public class InstructorDto {
    
    private String instructorName;
    private String instructorBelt;
    private String instructorProfileUrl;
    private String instructorlid;
    
    public String getInstructorName() {
        return instructorName;
    }
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
    public String getInstructorBelt() {
        return instructorBelt;
    }
    public void setInstructorBelt(String instructorBelt) {
        this.instructorBelt = instructorBelt;
    }
    public String getInstructorProfileUrl() {
        return instructorProfileUrl;
    }
    public void setInstructorProfileUrl(String instructorProfileUrl) {
        this.instructorProfileUrl = instructorProfileUrl;
    }
    public String getInstructorlid() {
        return instructorlid;
    }
    public void setInstructorlid(String instructorlid) {
        this.instructorlid = instructorlid;
    }
    
    @Override
    public String toString() {
        return "{" +
                "\"instructorName\":\"" + instructorName + "\"," +
                "\"instructorBelt\":\"" + instructorBelt + "\"," +
                "\"instructorProfileUrl\":\"" + instructorProfileUrl + "\"," +
                "\"instructorlid\":" + instructorlid +
                "}";
    }

    
}
