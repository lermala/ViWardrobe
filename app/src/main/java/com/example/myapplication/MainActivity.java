package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.menuFragments.Dialogs.AddingClothesFragment;
import com.example.myapplication.menuFragments.CalendarFragment;
import com.example.myapplication.menuFragments.Dialogs.FragmentAddingLooks;
import com.example.myapplication.menuFragments.Dialogs.TagsDialogFragment;
import com.example.myapplication.menuFragments.HomeFragment;
import com.example.myapplication.menuFragments.LooksFragment;
import com.example.myapplication.menuFragments.ProfileFragment;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ---------------Запрос на доступ к файлам------------
        if (hasPermissions()){
            // our app has permissions.
            makeFolder();

            setContentView(R.layout.activity_main);
            addFirstFragment();
        }
        else {
            //our app doesn't have permissions, So i m requesting permissions.
            requestPermissionWithRationale();
        }



        // ---------------Запрос на доступ к файлам--------------

//        setContentView(R.layout.activity_main);
  //      addFirstFragment();
    }

    private void makeFolder(){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"fandroid");

        if (!file.exists()){
            Boolean ff = file.mkdir();
            if (ff){
                Toast.makeText(MainActivity.this, "Folder created successfully", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Failed to create folder", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(MainActivity.this, "Folder already exist", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * с помощью метода checkCallingOrSelfPermission в цикле проверяет
     * предоставленные приложению разрешения и сравнивает их с тем, которое нам необходимо.
     * @return При отсутствии разрешения метод будет возвращать false,
     * а при наличии разрешения — true.
     */
    private boolean hasPermissions(){
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    /**
     * создаем массив permissions , который содержит ссылки на
     * константы класса Manifest с кодами разрешений.
     * Поскольку используется массив, то одновременно можно запрашивать несколько разрешений.
     */
    private void requestPerms(){
        String[] permissions = new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //После проверки версии устройства
            // Запрос разрешения выполняет метод requestPermissions,
            // которому мы передаем массив нужных нам разрешений и
            // константу PERMISSION_REQUEST_CODE
            //
            // После вызова этого метода пользователю отображается диалоговое окно с запросом разрешения.
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }


    /**
     * Ответ пользователя приходит в метод onRequestPermissionsResult.
     * Параметры requestCode и permissions содержат данные, которые вы передавали при
     * запросе разрешений. Основные данные здесь несет массив grantResults, в котором
     * находится информация о том, получены разрешения или нет. Каждому i-му элементу
     * permissions соответствует i-ый элемент из grantResults.
     *
     * Здесь мы обрабатываем ответ — проверяем, если разрешение предоставлено пользователем,
     * о чем будет свидетельствовать ответ PERMISSION_GRANTED, то вызываем метод makeFolder,
     * а если нет, то проверяем версию устройства,
     *
     * и если она равна Андроид 6.0 или выше — проверяем, отказывался ли ранее пользователь
     * предоставлять это разрешение. В таком случае метод с длинным названием
     * shouldShowRequestPermissionRationale вернет нам true.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMISSION_REQUEST_CODE:

                for (int res : grantResults){
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed){
            //user granted all permissions we can perform our task.
            makeFolder();
        }
        else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Storage Permissions denied.", Toast.LENGTH_SHORT).show();

                } else {
                    showNoStoragePermissionSnackbar();
                }
            }
        }

    }

    public void showNoStoragePermissionSnackbar() {
        Snackbar.make(MainActivity.this.findViewById(R.id.activity_main), "Storage permission isn't granted" , Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openApplicationSettings();

                        Toast.makeText(getApplicationContext(),
                                "Open Permissions and grant the Storage permission",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .show();
    }

    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            makeFolder();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            final String message = "Storage permission is needed to show files count";

            Snackbar.make(MainActivity.this.findViewById(R.id.activity_main), message, Snackbar.LENGTH_LONG)
                    .setAction("GRANT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPerms();
                        }
                    })
                    .show();
        } else {
            requestPerms();
        }
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

    //----------------add pic--------------
    /*static final int GALLERY_REQUEST = 1;
    public void addPhoto(View view){ //открывает диалог окно для выбора галереи
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }*/
    //----------------add pic--------------

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