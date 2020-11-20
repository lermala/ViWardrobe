package com.example.myapplication.Logic.workWithClothes;

import android.net.Uri;
import android.widget.ListView;

import com.example.myapplication.R;

import java.net.URI;
import java.util.ArrayList;

public class WorkClothes{

    // TODO: сделать НЕ создание нового списка, а загрузку старого
    private static ArrayList<Clothes> clothes = new ArrayList<>();

    private ListView clothesListView;
    private String[] types = Clothes.getTypes();

    public WorkClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }

    /*Добавление одежды в список*/
    public static void addClothes(Clothes cloth){
        clothes.add(cloth);
    }

    //добаляем дефолтную одежду
    public void initClothes(){
        //clothes.add(new Clothes("одежда1", types[1], R.drawable.c1));
       // clothes.add(new Clothes("одежда2", types[1], R.drawable.len1));
        //clothes.add(new Clothes("одежда5", types[4], Uri.parse("content://com.miui.gallery.open/raw/%2Fstorage%2Femulated%2F0%2FDCIM%2FCamera%2FIMG_20201108_013133.jpg")));
        //clothes.add(new Clothes("одежда5", types[4], "content://com.miui.gallery.open/raw/%2Fstorage%2Femulated%2F0%2FDCIM%2FCamera%2FIMG_20201108_013133.jpg"));


        /*clothes.add(new Clothes("одежда3", types[2], R.drawable.c1));
        clothes.add(new Clothes("одежда4", types[3], R.drawable.c3));
        clothes.add(new Clothes("одежда5", types[4], R.drawable.c5));
        clothes.add(new Clothes("одежда6", types[5], R.drawable.c8));
        clothes.add(new Clothes("одежда7", types[6], R.drawable.len1));
        clothes.add(new Clothes("одежда8", types[7], R.drawable.c1));
        clothes.add(new Clothes("одежда1", types[1], R.drawable.c1));
        clothes.add(new Clothes("одежда2", types[1], R.drawable.len1));
        clothes.add(new Clothes("одежда3", types[2], R.drawable.c1));
        clothes.add(new Clothes("одежда4", types[3], R.drawable.c3));
        clothes.add(new Clothes("одежда5", types[4], R.drawable.c5));
        clothes.add(new Clothes("одежда6", types[5], R.drawable.c8));
        clothes.add(new Clothes("одежда7", types[6], R.drawable.len1));
        clothes.add(new Clothes("одежда8", types[7], R.drawable.c1));*/
    }

    public static ArrayList<Clothes> getAllClothes() {
        return clothes;
    }
}
