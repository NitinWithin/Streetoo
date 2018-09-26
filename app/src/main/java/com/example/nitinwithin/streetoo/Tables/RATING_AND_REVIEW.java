package com.example.nitinwithin.streetoo.Tables;

import com.microsoft.windowsazure.mobileservices.table.DateTimeOffset;

import java.io.Serializable;
import java.util.Date;


public class RATING_AND_REVIEW implements Serializable
{
    
    @com.google.gson.annotations.SerializedName("id")
    private String rating_id;
    @com.google.gson.annotations.SerializedName("user_id")
    private String user_id;
    @com.google.gson.annotations.SerializedName("vendor_id")
    private String vendor_id;
    @com.google.gson.annotations.SerializedName("rating")
    private float rating;
    @com.google.gson.annotations.SerializedName("review")
    private String reveiw;
    @com.google.gson.annotations.SerializedName("createdAt")
    private Date createDate;
    @com.google.gson.annotations.SerializedName("user_name")
    private String userNameReview;

    public String getUserNameReview() {
        return userNameReview;
    }

    public void setUserNameReview(String userNameReview) {
        this.userNameReview = userNameReview;
    }

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReveiw() {
        return reveiw;
    }

    public void setReveiw(String reveiw) {
        this.reveiw = reveiw;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(DateTimeOffset createDate) {
        this.createDate = createDate;
    }
}
