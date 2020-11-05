package com.example.bookingapp;

public class Detail {
    private String busTypeDetail,acDetail,chargeDetail,biteDetail,amountDetail;
    private int busImg;

    public Detail(String busTypeDetail, String acDetail, String chargeDetail, String biteDetail, String amountDetail, int busImg) {
        this.busTypeDetail = busTypeDetail;
        this.acDetail = acDetail;
        this.chargeDetail = chargeDetail;
        this.biteDetail = biteDetail;
        this.amountDetail = amountDetail;
        this.busImg = busImg;
    }

    public String getBusTypeDetail() {
        return busTypeDetail;
    }

    public void setBusTypeDetail(String busTypeDetail) {
        this.busTypeDetail = busTypeDetail;
    }

    public String getAcDetail() {
        return acDetail;
    }

    public void setAcDetail(String acDetail) {
        this.acDetail = acDetail;
    }

    public String getChargeDetail() {
        return chargeDetail;
    }

    public void setChargeDetail(String chargeDetail) {
        this.chargeDetail = chargeDetail;
    }

    public String getBiteDetail() {
        return biteDetail;
    }

    public void setBiteDetail(String biteDetail) {
        this.biteDetail = biteDetail;
    }

    public String getAmountDetail() {
        return amountDetail;
    }

    public void setAmountDetail(String amountDetail) {
        this.amountDetail = amountDetail;
    }

    public int getBusImg() {
        return busImg;
    }

    public void setBusImg(int busImg) {
        this.busImg = busImg;
    }
}
