package com.example.myapplication.Logic.workWithLooks;

import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Clothes;

import java.util.ArrayList;

public class Look {

    private  ArrayList<Look> looks; //TODO: УДАЛИТЬ

    private int image;
    private ArrayList<Clothes> clothes;


    public Look(ArrayList<Clothes> clothes) {
        this.clothes = clothes; //в этом случае придется создавать картинку, состоящую из картинок каждого из предметов
    }

    public Look() {
        initLooks();//TODO: УДАЛИТЬ
    }

    public Look(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList<Clothes> getClothes() {
        return clothes;
    }

    public void setClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }

    public void initLooks(){//TODO: УДАЛИТЬ
        looks.add(new Look(R.drawable.look1));
        looks.add(new Look(R.drawable.look2));
        looks.add(new Look(R.drawable.look3));
        looks.add(new Look(R.drawable.look4));
        looks.add(new Look(R.drawable.look1));
        looks.add(new Look(R.drawable.look2));
        looks.add(new Look(R.drawable.look3));
        looks.add(new Look(R.drawable.look4));
    }
}
