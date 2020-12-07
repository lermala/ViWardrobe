package com.example.myapplication.Logic.workWithClothes;

import android.net.Uri;

public class Clothes{

    private String name;
    private String type;
    private Uri imageUri;

    public Uri getImageUri() {
        return imageUri;
    }

    private static final String[] types = {
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
            "Сумки",
            "Обувь"
    };

    public Clothes(String name, String type, Uri imageUri) {
        this.name = name;
        this.type = type;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String[] getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", imageUri=" + imageUri +
                '}';
    }
}
