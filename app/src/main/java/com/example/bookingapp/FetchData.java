package com.example.bookingapp;

public class FetchData {
    String nameFn,typeFn,fromFn,toFn,timeFn,psName,psEmail, psPhone;

    public FetchData(String nameFn, String typeFn, String fromFn, String toFn, String timeFn, String psName, String psEmail, String psPhone) {
        this.nameFn = nameFn;
        this.typeFn = typeFn;
        this.fromFn = fromFn;
        this.toFn = toFn;
        this.timeFn = timeFn;
        this.psName = psName;
        this.psEmail = psEmail;
        this.psPhone = psPhone;
    }

    public FetchData() {
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

    public String getToFn() {
        return toFn;
    }

    public void setToFn(String toFn) {
        this.toFn = toFn;
    }

    public String getTimeFn() {
        return timeFn;
    }

    public void setTimeFn(String timeFn) {
        this.timeFn = timeFn;
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
}
