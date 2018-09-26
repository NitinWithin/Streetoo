package com.example.nitinwithin.streetoo.Tables;

import java.io.Serializable;

public class Live_Location implements Serializable{



    @com.google.gson.annotations.SerializedName("id")
    private String id;
    @com.google.gson.annotations.SerializedName("vendor_id")
    private String vendor_id;
    @com.google.gson.annotations.SerializedName("status")
    private boolean status;
    @com.google.gson.annotations.SerializedName("latitude")
    private float latitude;
    @com.google.gson.annotations.SerializedName("longitude")
    private float longitude;

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
