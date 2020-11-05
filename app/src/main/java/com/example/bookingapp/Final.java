package com.example.bookingapp;

public class Final {
    String nameFn,typeFn,fromFn,toFn,timeFn,psName,psEmail, psPhone,key;


    public Final (String nameFn, String typeFn, String fromFn, String timeFn,String psName,String psEmail,String psPhone,String toFn,String key) {
        this.nameFn = nameFn;
        this.typeFn = typeFn;
        this.fromFn = fromFn;
        this.timeFn = timeFn;
        this.psName = psName;
        this.psEmail = psEmail;
        this.psPhone = psPhone;
        this.toFn = toFn;
        this.key = key;
    }

    public void Final (String nameFn, String typeFn, String fromFn, String timeFn, String psName, String psEmail, String psPhone,String toFn, String key) {
    }

    public String getToFn() {
        return toFn;
    }

    public void setToFn(String toFn) {
        this.toFn = toFn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
    }

    public String getPsEmail() {
        return psEmail;
    }

    public void setPsEmail(String psEmail) {
        this.psEmail = psEmail;
    }

    public String getPsPhone() {
        return psPhone;
    }

    public void setPsPhone(String psPhone) {
        this.psPhone = psPhone;
    }

    public String getNameFn() {
        return nameFn;
    }

    public void setNameFn(String nameFn) {
        this.nameFn = nameFn;
    }

    public String getTypeFn() {
        return typeFn;
    }

    public void setTypeFn(String typeFn) {
        this.typeFn = typeFn;
    }

    public String getFromFn() {
        return fromFn;
    }

    public void setFromFn(String fromFn) {
        this.fromFn = fromFn;
    }

    public String getTimeFn() {
        return timeFn;
    }

    public void setTimeFn(String timeFn) {
        this.timeFn = timeFn;
    }
}
