package com.example.myapplication.workWithClothes;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;

public class WorkClothes implements LoadClothesInterface{

    private ArrayList<Clothes> clothes = new ArrayList<>();

    private ListView clothesListView;
    private String[] types = Clothes.getTypes();

    public WorkClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }

    public WorkClothes() {
        initClothes();
    }

    /*Добавление одежды в список*/
    public void addClothes(Clothes cloth){
        clothes.add(cloth);
    }

    //добаляем дефолтную одежду
    public void initClothes(){
        clothes.add(new Clothes("одежда1", types[1], R.drawable.c1));
        clothes.add(new Clothes("одежда2", types[1], R.drawable.len1));
        clothes.add(new Clothes("одежда3", types[2], R.drawable.c1));
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
        clothes.add(new Clothes("одежда8", types[7], R.drawable.c1));
    }

    @Override
    public ArrayList<Clothes> getAllClothes() {
        return clothes;
    }
}
