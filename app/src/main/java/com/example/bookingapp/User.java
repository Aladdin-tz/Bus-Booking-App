package com.example.bookingapp;

public class User {
    String emailRegister, pswRegister, usernameRegister, phoneRegister, role;

    public User() {
    }

    public User(String emailRegister, String pswRegister, String usernameRegister, String phoneRegister, String role) {
        this.emailRegister = emailRegister;
        this.pswRegister = pswRegister;
        this.usernameRegister = usernameRegister;
        this.phoneRegister = phoneRegister;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmailRegister() {
        return emailRegister;
    }

    public void setEmailRegister(String emailRegister) {
        this.emailRegister = emailRegister;
    }

    public String getPswRegister() {
        return pswRegister;
    }

    public void setPswRegister(String pswRegister) {
        this.pswRegister = pswRegister;
    }

    public String getUsernameRegister() {
        return usernameRegister;
    }

    public void setUsernameRegister(String usernameRegister) {
        this.usernameRegister = usernameRegister;
    }

    public String getPhoneRegister() {
        return phoneRegister;
    }

    public void setPhoneRegister(String phoneRegister) {
        this.phoneRegister = phoneRegister;
    }
}
