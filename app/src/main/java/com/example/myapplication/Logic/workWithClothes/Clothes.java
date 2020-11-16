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

    //--------ТЭГИ---------- (необязат)
    private Tags.Season season; //сезон
    private Tags.Use use; // для чего (повседневный...)
    private Tags.Brightness brightness; //яркость
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

    //конструктор без тэгов
    public Clothes(String name, String type, int image) {
        this.name = name;
        this.type = type;
        this.image = image;
    }

    //-------КОНСТРУКТОРЫ С ТЭГАМИ------------


    public Clothes(String name, String type, Uri imageUri) {
        this.name = name;
        this.type = type;
        this.imageUri = imageUri;
    }

    public Clothes(String name, String type, String imageUriString) {
        this.name = name;
        this.type = type;
        this.imageUriString = imageUriString;
    }

    public Clothes(String name, String type, int image, Tags.Season season, Tags.Use use, Tags.Brightness brightness) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.season = season;
        this.use = use;
        this.brightness = brightness;
    }

    public Clothes(String name, String type, int image, Tags.Season season) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.season = season;
    }

    public Clothes(String name, String type, int image, Tags.Use use) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.use = use;
    }

    public Clothes(String name, String type, int image, Tags.Brightness brightness) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.brightness = brightness;
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
