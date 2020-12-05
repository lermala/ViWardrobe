package com.example.myapplication.Logic.workWithClothes;

import android.net.Uri;

import com.example.myapplication.Logic.Tags;

public class Clothes{

    //----ОСНОВНЫЕ ПОЛЯ-----
    private String name;
    private String type;
    private int image;
   // private String imageUriString;
    private String imageUriString;

    private Uri imageUri;
    //----------------------


    public Uri getImageUri() {
        return imageUri;
    }

    private static final String[] types = {
            //TODO: убрать "Все" и возможно переделать из стринг в enum
            "Все",
            "Верхняя одежда",
            "Джемперы и кардиганы",
            "Футболки и майки",
            "Блузки, рубашки, водолазки",
            "Костюмы - комбинезоны",
            "Платья - сарафаны",
            "Брюки - джинсы",
            "Толстовки - худи",
            "Жакеты - пиджаки",
            "Юбки - шорты",
            "Аксессуары",
            "Обувь"
    };


    public Clothes(String name, String type, Uri imageUri) {
        this.name = name;
        this.type = type;
        this.imageUri = imageUri;
    }





    //--------------------------------



    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getImage() {
        return image;
    }

    public String getImageUriString() {
        return imageUriString;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static String[] getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' + imageUriString +
                '}';
    }

}
