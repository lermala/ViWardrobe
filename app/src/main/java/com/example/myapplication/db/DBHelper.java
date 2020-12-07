package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.myapplication.Logic.workWithClothes.Clothes;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Данные базы данных и таблиц
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "clothesDb";
    public static final String TABLE_CLOTHES = "clothes";

    // Название столбцов
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PICTURE = "picture";

    // Номера столбцов
    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_TYPE = 2;
    private static final int NUM_COLUMN_PICTURE = 3;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CLOTHES + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_TYPE + " TEXT, " +
                KEY_PICTURE + " TEXT); ";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CLOTHES);

        onCreate(db);
    }

    // Метод добавления строки в БД
    public void writeToDataBase(String name, String type, String imageUri){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // подготовим данные для вставки в виде пар: наименование столбца - значение
        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelper.KEY_TYPE, type);
        contentValues.put(DBHelper.KEY_PICTURE, imageUri.toString());

        Log.d( "mLog", "name = " + name +
                ", type = " + type +
                ", picture = " + imageUri.toString());

        // вставляем запись
        database.insert(DBHelper.TABLE_CLOTHES, null, contentValues);

        Log.d("mLog", "ROW INSERTED " +
                "name = " + contentValues.get(DBHelper.KEY_NAME) +
                ", type = " + contentValues.get(DBHelper.KEY_TYPE) +
                ", picture = " + contentValues.get(DBHelper.KEY_PICTURE)
        );

        database.close();
    }

    public void writeToDataBase(String name, String type, String imageUri, int id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // подготовим данные для вставки в виде пар: наименование столбца - значение
        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelper.KEY_TYPE, type);
        contentValues.put(DBHelper.KEY_PICTURE, imageUri.toString());

        Log.d( "mLog", "name = " + name +
                ", type = " + type +
                ", picture = " + imageUri.toString());

        // вставляем запись
        database.insert(DBHelper.TABLE_CLOTHES, null, contentValues);

        Log.d("mLog", "ROW INSERTED " +
                "name = " + contentValues.get(DBHelper.KEY_NAME) +
                ", type = " + contentValues.get(DBHelper.KEY_TYPE) +
                ", picture = " + contentValues.get(DBHelper.KEY_PICTURE)
        );

        database.close();
    }

    public ArrayList<Clothes> readAllFromDataBase(){
        ArrayList<Clothes> clothesList = new ArrayList<Clothes>();
        // подключаемся к БД
        SQLiteDatabase database = this.getWritableDatabase();

        // делаем запрос всех данных из таблицы table_clothes, получаем Cursor
        Cursor cursor = database.query(DBHelper.TABLE_CLOTHES, null, null,
                null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (cursor.moveToFirst()){
            do {
                Clothes cloth = new Clothes(
                        cursor.getString(NUM_COLUMN_NAME),
                        cursor.getString(NUM_COLUMN_TYPE),
                        Uri.parse(cursor.getString(NUM_COLUMN_PICTURE))
                );

                Log.d("DBHELPER |   ", "READING ALL:\nID = " + cursor.getInt(0) +
                        ", name = " + cursor.getString(1) +
                        ", type = " + cursor.getString(2) +
                        ", picture = " + cursor.getString(3));

                clothesList.add(cloth);
            } while (cursor.moveToNext());
        } else
            Log.d("mLog","0 rows");

        return clothesList;
    }



    public Clothes getCloth(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLOTHES, new String[] { KEY_ID,
                        KEY_NAME, KEY_TYPE, KEY_PICTURE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Clothes cloth = new Clothes(
                cursor.getString(NUM_COLUMN_NAME),
                cursor.getString(NUM_COLUMN_TYPE),
                Uri.parse(cursor.getString(NUM_COLUMN_PICTURE))
        );

        return cloth;
    }

    // Метод редактирования строки в БД
    public void updateCloth(Clothes newCloth, int id){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NAME, newCloth.getName());
        contentValues.put(DBHelper.KEY_TYPE, newCloth.getType());
        contentValues.put(DBHelper.KEY_PICTURE, newCloth.getImageUri().toString());

        int updCount = db.update(TABLE_CLOTHES, contentValues, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });

        Log.d("mLog", "updates rows count = " + updCount);
    }

    public void deleteCloth(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLOTHES, KEY_ID + " = ?", new String[]
                { String.valueOf(id) });
        db.close();
    }

    // Метод удаления всех записей из БД
    public void deleteAll(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DBHelper.TABLE_CLOTHES, null, null);
        database.close();
    }

    public int getClothesCount(){
        String countQuery = "SELECT  * FROM " + TABLE_CLOTHES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}
