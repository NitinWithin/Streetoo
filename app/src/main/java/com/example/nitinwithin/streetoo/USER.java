package com.example.nitinwithin.streetoo;


public class USER
{
    public USER()
    {

    }
    @com.google.gson.annotations.SerializedName("id")
    private String user_id;
    @com.google.gson.annotations.SerializedName("user_name")
    private String user_name;
    @com.google.gson.annotations.SerializedName("user_mob_no")
    private String user_mob_no;
    @com.google.gson.annotations.SerializedName("user_lat")
    private float user_lat;
    @com.google.gson.annotations.SerializedName("user_long")
    private float user_long;
    @com.google.gson.annotations.SerializedName("user_password")
    private String user_password;
    @com.google.gson.annotations.SerializedName("user_mail")
    private String user_mail;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mob_no() {
        return user_mob_no;
    }

    public void setUser_mob_no(String user_mob_no) {
        this.user_mob_no = user_mob_no;
    }

    public float getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(float user_lat) {
        this.user_lat = user_lat;
    }

    public float getUser_long() {
        return user_long;
    }

    public void setUser_long(float user_long) {
        this.user_long = user_long;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }
   /* @Override
    public boolean equals(Object o) {
        return o instanceof USER && ((USER) o).user_id == user_id;
    }
*/

}
