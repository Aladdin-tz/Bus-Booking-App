package com.example.bookingapp;

public class Bus {
    private  String busName,busType,busFrom,busTo,busTime,imageUrl,key;

    public Bus(String busName, String busType, String busFrom, String busTo, String imageUrl, String key) {
        this.busName = busName;
        this.busType = busType;
        this.busFrom = busFrom;
        this.busTo = busTo;
        this.busTime = busTime;
        this.imageUrl = imageUrl;
        this.key = key;
    }

    public Bus() {
    }

    public String getBusTime() {
        return busTime;
    }

    public void setBusTime(String busTime) {
        this.busTime = busTime;
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
