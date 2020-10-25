package com.example.myapplication.fileWork;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class FileWork {
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
