package com.example.bookingapp;

public class Passenger {
    String fullName,passengerFrom,passengerTo,date,time,passengerPhone;

    public Passenger(String fullName, String passengerFrom, String passengerTo, String date, String time, String passengerPhone) {
        this.fullName = fullName;
        this.passengerFrom = passengerFrom;
        this.passengerTo = passengerTo;
        this.date = date;
        this.time = time;
        this.passengerPhone = passengerPhone;
    }

    public Passenger() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassengerFrom() {
        return passengerFrom;
    }

    public void setPassengerFrom(String passengerFrom) {
        this.passengerFrom = passengerFrom;
    }

    public String getPassengerTo() {
        return passengerTo;
    }

    public void setPassengerTo(String passengerTo) {
        this.passengerTo = passengerTo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }
}
