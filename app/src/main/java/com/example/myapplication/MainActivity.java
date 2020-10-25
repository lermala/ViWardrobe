package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.myapplication.menuFragments.AddingClothesFragment;
import com.example.myapplication.menuFragments.CalendarFragment;
import com.example.myapplication.menuFragments.HomeFragment;
import com.example.myapplication.menuFragments.LooksFragment;
import com.example.myapplication.menuFragments.ProfileFragment;

import java.net.DatagramPacket;

public class MainActivity extends AppCompatActivity {

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


    /*для добавления картинок*/
    public static final int PICK_IMAGE = 1;
    /*считывание картинки в внес-е во фрагмент*/
    private Uri mSelectedImageUri;

    public void addPhoto(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        //image_for_adding
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