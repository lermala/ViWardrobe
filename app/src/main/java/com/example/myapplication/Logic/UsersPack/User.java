package com.example.myapplication.Logic.UsersPack;

import android.net.Uri;

public class User {
    private String name;
   // private String city;
    private Uri photoUri;
    private String mail;
    private String date;
   // private String password;

    public User(String name, String mail, String date) {
        this.name = name;
        this.mail = mail;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
