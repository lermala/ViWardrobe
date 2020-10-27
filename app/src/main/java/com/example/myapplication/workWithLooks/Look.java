package com.example.myapplication.workWithLooks;

import com.example.myapplication.R;
import com.example.myapplication.workWithClothes.Clothes;

import java.util.ArrayList;

public class Look {

    private  ArrayList<Look> looks;
    private int image;
    private ArrayList<Clothes> clothes;

    private LookTags lookTags = null;

   public enum LookTags{
       выходной,
       спортивный,
       повседневный,
       нарядный,
       домашний,
       деловой,
       другое,
       зима,
       лето,
       демисезон,
       внесезон,
       избранное
    };

    public Look(ArrayList<Clothes> clothes) {
        this.clothes = clothes; //в этом случае придется создавать картинку, состоящую из картинок каждого из предметов
    }

    public Look() {
        initLooks();
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


    public void initLooks(){
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
