package com.example.myapplication.menuFragments.Dialogs;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.os.Environment;
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
import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Clothes;

import java.io.File;
import java.util.ArrayList;

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

                // сначала проверим все на корректный ввод
                if (!checkDataFromView(view)){
                    //.,,,,,///
                    return;
                }

                clothesForAdding = getDataFromView(view); // записываем

                // получаем элемент GridView
                GridView clothesGridView = (GridView) getActivity().findViewById(R.id.clothes_list);

                WorkClothes.addClothes(clothesForAdding); //добавили в общий список

               // File file = new File(getContext().getFilesDir(), "LERA_imagename.png");



                // отображаем новую таблицу
                clothesGridView.setAdapter(new ClothesAdapter(getActivity(),
                        R.layout.list_item, WorkClothes.getAllClothes()));
            }
        });
        return builder.create();
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
                    "Загрузите фото!", Toast.LENGTH_SHORT);
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

    static final int GALLERY_REQUEST = 1; // для галереи

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