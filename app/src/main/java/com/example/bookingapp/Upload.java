package com.example.bookingapp;

public class Upload {
    private  String busName,busType,busFrom,busTo,busTime,imageUrl,key;

    public Upload(String busName, String busType, String busFrom, String busTo,String busTime, String imageUrl, String key) {

        if (busName.trim().equals("")){
            busName = "no bus name Specified";
        }
        if (busType.trim().equals("")){
            busType = "no bus type Specified";
        }
        if (busFrom.trim().equals("")){
            busFrom = "no Location Specified";
        }
        if (busTo.trim().equals("")){
            busTo = "no Location Specified";
        }
        if (busTime.trim().equals("")){
            busTime = "no Time Specified";
        }


        this.busName = busName;
        this.busType = busType;
        this.busFrom = busFrom;
        this.busTo = busTo;
        this.busTime = busTime;
        this.imageUrl = imageUrl;
        this.key = key;
    }

    public Upload() {
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusFrom() {
        return busFrom;
    }

    public void setBusFrom(String busFrom) {
        this.busFrom = busFrom;
    }

    public String getBusTo() {
        return busTo;
    }

    public void setBusTo(String busTo) {
        this.busTo = busTo;
    }

    public String getBusTime() {
        return busTime;
    }

    public void setBusTime(String busTime) {
        this.busTime = busTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
