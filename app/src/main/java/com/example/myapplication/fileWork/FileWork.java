package com.example.myapplication.fileWork;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class FileWork {
    private static final String TAG = "FILE WORK | "; // FIXME

    private Context context;
    final File PATH_ALL_FILES; // путь до внутренней папки
    final File PATH_CLOTHES; // путь до папки с картинками одежды

    public FileWork(Context context) {
        this.context = context;
        PATH_ALL_FILES = context.getFilesDir();
        PATH_CLOTHES = new File(PATH_ALL_FILES, "/Clothes/");

        if (!PATH_CLOTHES.exists()) {
            File imageClothesDir = new File(PATH_ALL_FILES + "/Clothes/");
            imageClothesDir.mkdirs();
        }

        Log.i(TAG,"PATH_CLOTHES  " + PATH_CLOTHES.toString());
    }

    /**
     *
     * @return Имя файла (картинки)
     */
  /*  public Uri savePictureAndGetUri(Bitmap image){
        String fileName = "/" + MainActivity.fileName + "";

        String uniqueString = UUID.randomUUID().toString();

        createDirectoryAndSaveFile(image, uniqueString); // save

        MainActivity.fileName++; // можно сделать имя зависимым от времени + даты

        Log.i(TAG,"Uri.parse PATH_CLOTHES  " + PATH_CLOTHES.toString() + fileName);

        return Uri.parse(PATH_CLOTHES.toString() + fileName);
    }*/

    public Uri savePictureAndGetUri(Bitmap image){
        String uniqueString = UUID.randomUUID().toString(); // генерируем имя файла
        String fileName = "/" + uniqueString + "";

        createDirectoryAndSaveFile(image, uniqueString); // save

       // MainActivity.fileName++; // можно сделать имя зависимым от времени + даты // FIXME

        Log.i(TAG,"Uri.parse PATH_CLOTHES  " + PATH_CLOTHES.toString() + fileName);

        return Uri.parse(PATH_CLOTHES.toString() + fileName);
    }

    public void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName){
        File file = new File(new File(PATH_CLOTHES.toString()), fileName);
        //File file = new File(new File(path + "/Clothes/"), fileName);

        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllImagesClothes(){
        File[] files = PATH_CLOTHES.listFiles();

        for (int i = 0; i < files.length; i++)
            files[i].delete();

        logCheck();
    }

    public void logCheck(){
        File[] files = PATH_CLOTHES.listFiles();
        Log.d("Files", "Size BEFORE: "+ files.length);

        for (int i = 0; i < files.length; i++)
            Log.d("Files", "FileName:" + files[i].getName());
    }

    public void deleteImage(Uri imgUri){
        logCheck();

        // получаем путь до картинки этого элемента
       // Uri imgUri = WorkClothes.getClothes(idForDel).getImageUri();
        File file = new File(String.valueOf(imgUri));

        // Удаляем фото из папки с картинками
        if (file.delete()){
            Log.i(TAG,"del OK");
        }
        else Log.i(TAG,"del is not OK");

        logCheck();
    }


    /*метод для получения имен файлов в подпапке assets*/
    String[] displayFiles(AssetManager mgr, String path) {
        String list[] = null;
        try {
            list = mgr.list(path);
            if (list != null)
                for (int i = 0; i < list.length; ++i) {
                    Log.v("Assets:", path + "/" + list[i]);
                    displayFiles(mgr, path + "/" + list[i]);
                }
        } catch (IOException e) {
            Log.v("List error:", "can't list" + path);
        }
        String res = null;
        for (String el:
                list) {
            res += el + "\n";
        }
        return list;
    }
    /*метод для получения имен файлов в подпапке assets*/
    public ArrayList<String> displayFilesToTheList(AssetManager mgr, String path) {
        String list[] = null;
        try {
            list = mgr.list(path);
            if (list != null)
                for (int i = 0; i < list.length; ++i) {
                    Log.v("Assets:", path + "/" + list[i]);
                    displayFiles(mgr, path + "/" + list[i]);
                }
        } catch (IOException e) {
            Log.v("List error:", "can't list" + path);
        }

        ArrayList<String> res = null;
        for (String el:
                list) {
            res.add(el);
        }
        return res;
    }
}
