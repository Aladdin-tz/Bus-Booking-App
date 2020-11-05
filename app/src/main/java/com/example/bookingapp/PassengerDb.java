package com.example.bookingapp;

public class PassengerDb {
    String pName,pEmail,pPhone;

    public PassengerDb(String pName, String pEmail, String pPhone) {
        this.pName = pName;
        this.pEmail = pEmail;
        this.pPhone = pPhone;
    }

    public PassengerDb() {
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getpPhone() {
        return pPhone;
    }

    public void setpPhone(String pPhone) {
        this.pPhone = pPhone;
    }
}
