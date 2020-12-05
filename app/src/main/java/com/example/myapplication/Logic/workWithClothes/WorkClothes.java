package com.example.myapplication.Logic.workWithClothes;

import android.widget.ListView;

import com.example.myapplication.MainActivity;

import java.util.ArrayList;

public class WorkClothes{

    private static ArrayList<Clothes> clothes = MainActivity.dbHelper.readAllFromDataBase();

    private ListView clothesListView;
    private String[] types = Clothes.getTypes();

    public WorkClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }

    /*Добавление одежды в список*/
    public static void addClothes(Clothes cloth){
        clothes.add(cloth);
    }

    public static void deleteCloth(int positionForDeleting){
        clothes.remove(positionForDeleting);
    }

    /**
     * Функция фильтрации одежды
     * @param type - тип для фильтрации
     * @return отфильтрованный список (только одежда с указанным типом)
     */
    public static ArrayList<Clothes> filter(String type){
        ArrayList<Clothes> filteredList = new ArrayList<>();
        for (Clothes el:
             clothes) {
            if (el.getType().equals(type))
                filteredList.add(el);
        }
        return filteredList;
    }

    public static void update(){
        clothes = MainActivity.dbHelper.readAllFromDataBase();
    }


    public static Clothes getClothes(int position) {
        return clothes.get(position);
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
