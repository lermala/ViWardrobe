package com.example.myapplication.Logic.UsersPack;

import android.net.Uri;

public class User {
    private String name;
    private String city;
    private Uri photoUri;
    private String mail;
    private String password;

    public User(String name, String city, Uri photoUri, String mail, String password) {
        this.name = name;
        this.city = city;
        this.photoUri = photoUri;
        this.mail = mail;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
