package com.ama.karate.dto;

public class BeltDto {
    
    private String colour;
    private String fees;
    private int level;
    private String portion;
    private String description;
    
    public String getColour() {
        return colour;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
    public String getFees() {
        return fees;
    }
    public void setFees(String fees) {
        this.fees = fees;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getPortion() {
        return portion;
    }
    public void setPortion(String portion) {
        this.portion = portion;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "{" +
                "\"colour\":\"" + colour + "\"," +
                "\"fees\":" + fees + "," +
                "\"level\":\"" + level + "\"," +
                "\"portion\":\"" + portion + "\"," +
                "\"description\":\"" + description + "\"" +
                "}";
    }

    

}
