package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Logic.workWithClothes.Clothes;
import com.example.myapplication.Logic.workWithClothes.WorkClothes;
import com.example.myapplication.menuFragments.Dialogs.AddingClothesFragment;
import com.example.myapplication.menuFragments.CalendarFragment;
import com.example.myapplication.menuFragments.Dialogs.FragmentAddingLooks;
import com.example.myapplication.menuFragments.Dialogs.TagsDialogFragment;
import com.example.myapplication.menuFragments.HomeFragment;
import com.example.myapplication.menuFragments.LooksFragment;
import com.example.myapplication.menuFragments.ProfileFragment;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN ACTIVITY | "; // 4 debugging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addFirstFragment();

    }


    /*загрузка 1-го фрагмента*/
    private void addFirstFragment(){
        // получаем экземпляр FragmentTransaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // добавляем фрагмент
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.container, homeFragment);
        fragmentTransaction.commit();
    }


    // открытие диалогового окна для добавления ЛУКОВ
    public void openDialogLook(View view){
        FragmentManager manager = getSupportFragmentManager();
        FragmentAddingLooks fragmentAddingLooks = new FragmentAddingLooks();
        fragmentAddingLooks.show(manager, "dialog");
    }


    // открытие диалогового окна для тэгов
    public void openDialogTags(View view){
        FragmentManager manager = getSupportFragmentManager();
        TagsDialogFragment tagsDialogFragment = new TagsDialogFragment();
        tagsDialogFragment.show(manager, "dialog");
    }

    //событие для кнопки "Добавить" (одежду)
    public void AddClothes(View view){
        FragmentManager manager = getSupportFragmentManager();
        AddingClothesFragment myDialogFragment = new AddingClothesFragment();
        myDialogFragment.show(manager, "dialog");
    }

    /*Метод для изменения наполнения контента фрагментов/ измена текущ фрагмента*/
    public void Change (View view) {
        Fragment fragment = null;

        switch (view.getId()){
            case R.id.menu_calendar:
                fragment = new CalendarFragment();
                break;
            case  R.id.menu_looks:
                fragment = new LooksFragment();
                break;
            case R.id.menu_wardrobe:
                fragment = new HomeFragment();
                break;
            case R.id.menu_profile:
                fragment = new ProfileFragment();
                break;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }



}