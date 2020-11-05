package com.example.bookingapp;

public class DetailDb {
    String bName,bType,bTime;

    public DetailDb(String bName, String bType, String bTime) {
        this.bName = bName;
        this.bType = bType;
        this.bTime = bTime;
    }

    public DetailDb() {
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbType() {
        return bType;
    }

    public void setbType(String bType) {
        this.bType = bType;
    }

    public String getbTime() {
        return bTime;
    }

    public void setbTime(String bDate) {
        this.bTime = bDate;
    }
}
