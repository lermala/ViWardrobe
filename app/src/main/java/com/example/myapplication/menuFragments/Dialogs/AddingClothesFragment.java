package com.example.myapplication.menuFragments.Dialogs;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.myapplication.Logic.workWithClothes.WorkClothes;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Clothes;

import java.io.File;
import java.io.FileOutputStream;

import static android.app.Activity.RESULT_OK;

public class AddingClothesFragment extends DialogFragment {

    private static final String TAG = "ADDING CLOTHES | ";

    private ImageView imageView; // фото
    private Spinner spinner; // спиннер с типами

    private Uri selectedImageUri = null; //путь выбранного фото

    private Clothes clothesForAdding; // from view




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

                addPictureToGrid(view);

                // отображаем новую таблицу
                clothesGridView.setAdapter(new ClothesAdapter(getActivity(),
                        R.layout.list_item, WorkClothes.getAllClothes()));
            }
        });
        return builder.create();
    }

//    private String path = getContext().getFilesDir().toString() + "/Clothes/"; // путь к папке с одеждой

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
        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        createDirectoryAndSaveFile(image, fileName); // save

        MainActivity.fileName++; // можно сделать имя зависимым от времени + даты // FIXME

        return Uri.parse(path + fileName);
    }

    private void addPictureToGrid(View view){
        // считываем название одежды
        TextView textViewName = (TextView) view.findViewById(R.id.name_for_adding_clothes);
        String name = textViewName.getText().toString();

        // считываем тип одежды (в спиннере)
        String type = spinner.getSelectedItem().toString();

        Uri imageUri = savePictureAndGetUri(); // путь до картинки

        //добавляем в список всех одежд (парсим path to Uri)
        /*WorkClothes.addClothes(new Clothes("test clth" + MainActivity.fileName,
                Uri.parse(path + fileName)));*/

        Clothes cloth = new Clothes(name, type, imageUri);
        WorkClothes.addClothes(cloth); // добавляем в общий список
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
    private Clothes getDataFromView(View view){
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