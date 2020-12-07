package com.example.myapplication.Logic.workWithClothes;
import com.example.myapplication.MainActivity;

import java.util.ArrayList;

/**
 * класс, который хранит объекты одежды из бд
 */
public class WorkClothes{

    private static ArrayList<Clothes> clothes = MainActivity.dbHelper.readAllFromDataBase();

    public WorkClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }

    public static void update(){
        clothes = MainActivity.dbHelper.readAllFromDataBase();
    }

    public static Clothes getClothes(int position) {
        return clothes.get(position);
    }

    public static ArrayList<Clothes> getAllClothes() {
        return clothes;
    }
}
