package com.example.nitinwithin.streetoo.Tables;



public class VENDOR {

    @com.google.gson.annotations.SerializedName("id")
    private String vendorid;
    @com.google.gson.annotations.SerializedName("vendorName")
    private String vendorName;
    @com.google.gson.annotations.SerializedName("vendorAvgCost")
    //"vendorImage": "string",
    private int vendorAvgCost;
    @com.google.gson.annotations.SerializedName("vendorContact")
    private String vendorContact;
    @com.google.gson.annotations.SerializedName("vendorDescription")
    private String vendorDescription;
    @com.google.gson.annotations.SerializedName("vendorOwner")
    private String vendorOwner;
    @com.google.gson.annotations.SerializedName("vendorCuisine")
    private String vendorCuisine;
    @com.google.gson.annotations.SerializedName("avgRating")
    private float vendorAvgRating;

    public float getVendorAvgRating() {
        return vendorAvgRating;
    }

    public void setVendorAvgRating(float vendorAvgRating) {
        this.vendorAvgRating = vendorAvgRating;
    }

    public String getVendorOwner() {
        return vendorOwner;
    }

    public void setVendorOwner(String vendorOwner) {
        this.vendorOwner = vendorOwner;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getVendorAvgCost() {
        return vendorAvgCost;
    }

    public void setVendorAvgCost(int vendorAvgCost) {
        this.vendorAvgCost = vendorAvgCost;
    }

    public String getVendorContact() {
        return vendorContact;
    }

    public void setVendorContact(String vendorContact) {
        this.vendorContact = vendorContact;
    }

    public String getVendorDescription() {
        return vendorDescription;
    }

    public void setVendorDescription(String vendorDescription) {
        this.vendorDescription = vendorDescription;
    }

    public String getVendorCuisine() {
        return vendorCuisine;
    }

    public void setVendorCuisine(String vendorCuisine) {
        this.vendorCuisine = vendorCuisine;
    }
}
