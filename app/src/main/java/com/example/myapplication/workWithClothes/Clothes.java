package com.example.myapplication.workWithClothes;

public class Clothes {

    private String name;
    private String type;
    private int image;

    private ClothesTags clothesTags;

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
            "Обувь"
    };

    public enum ClothesTags{
        избранное,
        выходной,
        спортивный,
        повседневный,
        нарядный,
        домашний,
        деловой,
        база,
        зима,
        лето,
        демисезон,
        внесезон,
        яркий,
        светлый,
        тёмный,

    };

    public Clothes(String name, String type, int image) {
        this.name = name;
        this.type = type;
        this.image = image;
    }



    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getImage() {
        return image;
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
}
