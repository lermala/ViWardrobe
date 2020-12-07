package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Logic.UsersPack.User;
import com.example.myapplication.db.DBHelper;
import com.example.myapplication.fileWork.FileWork;
import com.example.myapplication.menuFragments.Dialogs.AddingClothesFragment;
import com.example.myapplication.menuFragments.CalendarFragment;
import com.example.myapplication.menuFragments.Dialogs.FragmentAddingLooks;
import com.example.myapplication.menuFragments.Dialogs.TagsDialogFragment;
import com.example.myapplication.menuFragments.HomeFragment;
import com.example.myapplication.menuFragments.LoginFragment;
import com.example.myapplication.menuFragments.LooksFragment;
import com.example.myapplication.menuFragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    // TODO: оптимизировать фильтрацию

    private static final String TAG = "MAIN ACTIVITY | "; // 4 debugging

    LoginFragment loginFragment;

    public static DBHelper dbHelper;
    public static User user;

    // это будет именем файла настроек
    public static final String APP_PREFERENCES = "Settings";
    public static final String APP_PREFERENCES_NAME = "Name";
    public static final String APP_PREFERENCES_MAIL = "Mail";
    public static final String APP_PREFERENCES_DATE = "Date";

    public static SharedPreferences Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if(!Settings.contains(APP_PREFERENCES_NAME)) { // не авторизован
            loginFragment = new LoginFragment();
            openFragment(loginFragment);
            return;
        }

        // если авторизован:
        user = new User(
                Settings.getString(MainActivity.APP_PREFERENCES_NAME, ""),
                Settings.getString(MainActivity.APP_PREFERENCES_MAIL, ""),
                Settings.getString(MainActivity.APP_PREFERENCES_DATE, "")
        );

        dbHelper = new DBHelper(this);
        dbHelper.readAllFromDataBase();
        //dbHelper.deleteAll();

        FileWork fileWork = new FileWork(this);
        fileWork.logCheck();
        //fileWork.deleteAllImagesClothes();

        //addFirstFragment();
        openFragment(new HomeFragment());
    }

    private void openFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();
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
        AddingClothesFragment myDialogFragment = new AddingClothesFragment("Добавить одежду");
        myDialogFragment.show(manager, "dialog");
        myDialogFragment.setCancelable(false); // запрет выключения на клик вне окна
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

        /*FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();*/
        openFragment(fragment);
    }

    public void onClickLogin(View view){
        user = loginFragment.getUserData();
        if (user == null){ // некорректный ввод
            showToast("Введите данные");
        } else if (!Settings.contains(APP_PREFERENCES_NAME)){// все ок (первый вход)
            writeSettings();
            dbHelper = new DBHelper(this);
            openFragment(new HomeFragment());
        } else {
            // меняем настройки
            writeSettings();
            // выводим сообщение
            showToast("Данные изменены");
            // открываем фрагмент "профиль"
            openFragment(new ProfileFragment());
        }
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void writeSettings(){
        SharedPreferences.Editor editor = Settings.edit();
        editor.putString(APP_PREFERENCES_NAME, user.getName());
        editor.putString(APP_PREFERENCES_MAIL, user.getMail());
        editor.putString(APP_PREFERENCES_DATE, user.getDate());
        editor.apply();
    }

    public void onClickEditProfile(View view){
        loginFragment = new LoginFragment();
        openFragment(loginFragment);
    }

}