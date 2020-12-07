package com.example.myapplication.Logic.UsersPack;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * В РАЗРАБОТКЕ
 */
public class UserDBHelper extends SQLiteOpenHelper {

    //TODO: ЭТО ПОКА НЕ НУЖНО

    // Данные базы данных и таблиц
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "usersDb";
    public static final String TABLE_USERS = "users";

    // Название столбцов
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CITY = "city";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PICTURE = "picture";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_CITY + " TEXT, " +
                KEY_MAIL + " TEXT, " +
                KEY_PASSWORD + " TEXT, " +
                KEY_PICTURE + " TEXT); ";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS);

        onCreate(db);
    }

    // Метод добавления строки в БД
    public void writeToDataBase(String name, String city, String mail, String password, String imageUri){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // подготовим данные для вставки в виде пар: наименование столбца - значение
        contentValues.put(UserDBHelper.KEY_NAME, name);
        contentValues.put(UserDBHelper.KEY_CITY, city);
        contentValues.put(UserDBHelper.KEY_MAIL, mail);
        contentValues.put(UserDBHelper.KEY_PASSWORD, password);
        contentValues.put(UserDBHelper.KEY_PICTURE, imageUri);

        // вставляем запись
        database.insert(UserDBHelper.TABLE_USERS, null, contentValues);

        database.close();
    }

}
