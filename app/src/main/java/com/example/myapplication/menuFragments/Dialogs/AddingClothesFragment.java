package com.example.myapplication.menuFragments.Dialogs;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Logic.workWithClothes.ClothesAdapter;
import com.example.myapplication.Logic.workWithClothes.DBHelper;
import com.example.myapplication.Logic.workWithClothes.WorkClothes;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Clothes;
import com.example.myapplication.fileWork.FileWork;

import java.io.File;
import java.io.FileOutputStream;

import static android.app.Activity.RESULT_OK;

public class AddingClothesFragment extends DialogFragment {

    private static final String TAG = "ADDING CLOTHES | "; // FIXME

    TextView textViewName;
    ImageView imageView; // фото
    Spinner spinner; // спиннер с типами

    Uri selectedImageUri = null; //путь выбранного фото

    String name, type; // имя и тип одежды
    Uri imageUri; // путь до картинки с одеждой

    DBHelper dbHelper = MainActivity.dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adding_clothes, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // задаем диалог окну вид (=фрагмент)
        final View view = inflater.inflate(R.layout.fragment_adding_clothes, null);

        textViewName = (TextView) view.findViewById(R.id.name_for_adding_clothes);

        //делаем спиннер
        spinner = (Spinner) view.findViewById(R.id.clothes_type);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Clothes.getTypes());
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        //------загрузка фото юзером
        imageView = (ImageView) view.findViewById(R.id.image_for_adding_clothes);
        // нажали на картинку (для добавления)
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addPhoto(v);
            }
        });
        //-----------------

        builder.setView(view);
        builder.setTitle("Добавить одежду");

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Закрываем окно
                //dialog.cancel();
            }
        });

        //TODO: убрать positive button, чтобы окошко НЕ ЗАКРЫВАЛОСЬ при ошибке
        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // TODO: ВЕрнуть строки ниже
                // сначала проверим все на корректный ввод
                if (!checkDataFromView(view)){
                    //.,,,,,///
                    return;
                }
                //clothesForAdding = getDataFromView(view); // записываем

                // получаем элемент GridView
                GridView clothesGridView = (GridView) getActivity().findViewById(R.id.clothes_list);

                getDataFromView(view);

                clearDataBase();
                writeToDataBase();
                readFromDataBase();
                // закрываем подключение к БД
                dbHelper.close();

                Toast.makeText(getActivity(), "Добавление успешно", Toast.LENGTH_LONG) //FIXME
                        .show();

                //TODO: к таблице прикрепить слушателей, которые будут обновлять данные

                // отображаем новую таблицу
                clothesGridView.setAdapter(new ClothesAdapter(getActivity(),
                        R.layout.list_item, WorkClothes.getAllClothes()));
            }
        });
        return builder.create();
    }

    public void writeToDataBase(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

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
    }

    public void readFromDataBase(){
        // подключаемся к БД
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // делаем запрос всех данных из таблицы table_clothes, получаем Cursor
        Cursor cursor = database.query(DBHelper.TABLE_CLOTHES, null, null,
                null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (cursor.moveToFirst()){
            // определяем номера столбцов по имени в выборке
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int typeIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE);
            int pictureIndex = cursor.getColumnIndex(DBHelper.KEY_PICTURE);

            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", name = " + cursor.getString(nameIndex) +
                        ", type = " + cursor.getString(typeIndex) +
                        ", picture = " + cursor.getString(pictureIndex));

                WorkClothes.addClothes(new Clothes(
                        cursor.getString(nameIndex),
                        cursor.getString(typeIndex),
                        Uri.parse(cursor.getString(pictureIndex))
                ));

            } while (cursor.moveToNext());
        } else
            Log.d("mLog","0 rows");
    }

    public void clearDataBase(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_CLOTHES, null, null);
    }

    private void getDataFromView(View view){
        // считываем название одежды
        name = textViewName.getText().toString();
        // считываем тип одежды (в спиннере)
        type = spinner.getSelectedItem().toString();

        //imageUri = savePictureAndGetUri(); // путь до картинки
        FileWork fileWork = new FileWork(getContext());

        // Картинка (считываем с imageView, а не берем uri, т.к. сохраняем в Bitmap, который
        // переворачивается (из-за кэша, который я не знаю как удалить))
        imageUri = fileWork.savePictureAndGetUri(
                ( (BitmapDrawable)imageView.getDrawable() ).getBitmap()
        );

        //добавляем в список всех одежд (парсим path to Uri)
        /*WorkClothes.addClothes(new Clothes("test clth" + MainActivity.fileName,
                Uri.parse(path + fileName)));*/

        //Clothes cloth = new Clothes(name, type, imageUri);
        //WorkClothes.addClothes(cloth); // добавляем в общий список
    }

    /**
     *
     * @return Имя файла (картинки)
     */
    private Uri savePictureAndGetUri(){
        String path = getContext().getFilesDir().toString() + "/Clothes/"; // путь к папке с одеждой

        String fileName = MainActivity.fileName + "";

        // TODO: ПОМЕНЯТЬ FILENAME( MainActivity.fileName )
        // Картинка (считываем с imageView, а не берем uri, т.к. сохраняем в Bitmap, который
        // переворачивается (из-за кэша, который я не знаю как удалить))
        Bitmap image = ( (BitmapDrawable)imageView.getDrawable() ).getBitmap();

       // createDirectoryAndSaveFile(image, fileName); // save

        MainActivity.fileName++; // можно сделать имя зависимым от времени + даты // FIXME

        return Uri.parse(path + fileName);
    }


    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName){
        String path = getContext().getFilesDir().toString();

        File direct = new File(getContext().getFilesDir(), "/Clothes");

        if (!direct.exists()) {
            File imageClothesDir = new File(path + "/Clothes/");
            imageClothesDir.mkdirs();
        }

        File file = new File(new File(path + "/Clothes/"), fileName);

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

    /**
     * Проверяем корректность ввода в окошке
     * @param view
     * @return
     */
    private boolean checkDataFromView(View view){
        // сначала проверим все на корректный ввод
        if (selectedImageUri == null) { //если картинка не выбрана
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "Выберите фото!", Toast.LENGTH_SHORT);
            toast.show();
        }

        // считываем название одежды
        TextView textViewName = (TextView) view.findViewById(R.id.name_for_adding_clothes);
        String name = textViewName.getText().toString();
        // проверяем
        if (name.equals("")){
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "Введите название", Toast.LENGTH_SHORT);
            toast.show();
        }

        // TODO: сделать проверку спиннера либо поставить ему дефолтное значение и
        // TODO: удалить "Все"

        return true;
    }


    /**
     * считываем данные с окошка ввода
     * @param view
     * @return объект типа clothes
     */
    private Clothes getDataFromViewToDataBase(View view){
        // считываем название одежды
        TextView textViewName = (TextView) view.findViewById(R.id.name_for_adding_clothes);
        String name = textViewName.getText().toString();

        // считываем тип одежды (в спиннере)
        // TODO: сделать проверку спиннера
        String type = spinner.getSelectedItem().toString();

        Log.i(TAG,"SELECTED IMG " + selectedImageUri);
        Log.i(TAG,"name " + name);
        Log.i(TAG,"type " + type);

        // TODO: ниже отдельно
        //Clothes cloth = new Clothes(name, type, selectedImage);
        Clothes cloth = new Clothes(name, type, selectedImageUri);
        return cloth;
    }


    static final int GALLERY_REQUEST = 1; // для галлереи

    public void addPhoto(View view){ //открывает диалог окно для выбора галереи
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    /**
     * для картинок (возвращает выбранное фото)
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    selectedImageUri = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImageUri);
                }
        }
    }

}