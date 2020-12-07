package com.example.myapplication.menuFragments.Dialogs;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.DBHelper;
import com.example.myapplication.Logic.workWithClothes.WorkClothes;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Clothes;
import com.example.myapplication.fileWork.FileWork;
import com.example.myapplication.menuFragments.HomeFragment;

import static android.app.Activity.RESULT_OK;

/**
 * Фрагмент добавления/редактирования предмета одежды
 */
public class AddingClothesFragment extends DialogFragment {

    private String title;
    private int idForEdit = -1;

    private static final String TAG = "ADDING CLOTHES | "; // FIXME

    static final int GALLERY_REQUEST = 1; // для галлереи

    private TextView textViewName;
    private ImageView imageView; // фото
    private Spinner spinner; // спиннер с типами

    private Uri selectedImageUri = null; //путь выбранного фото пользователем

    String name, type; // имя и тип одежды
    Uri imageUri; // путь до картинки с одеждой

    private DBHelper dbHelper = MainActivity.dbHelper;

    private View dialogView; //текущий диалог фрагмент

    public AddingClothesFragment(String title) {
        this.title = title;
    }

    // конструктор для редактирования
    public AddingClothesFragment(String title, int idForEdit) {
        this.title = title;
        this.idForEdit = idForEdit;
    }

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
        dialogView = inflater.inflate(R.layout.fragment_adding_clothes, null);

        textViewName = (TextView) dialogView.findViewById(R.id.name_for_adding_clothes);
        imageView = (ImageView) dialogView.findViewById(R.id.image_for_adding_clothes);
        spinner = (Spinner) dialogView.findViewById(R.id.clothes_type);

        initSpinner();

        if (idForEdit != -1){ // если редактирование
            setCloth(WorkClothes.getClothes(idForEdit));
        }

        // нажали на картинку (для добавления)
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addPhoto(v);
            }
        });

        builder.setView(dialogView);
        builder.setTitle(title);
        builder.setNegativeButton("Отмена", null);
        builder.setPositiveButton("Добавить", null);

        return builder.create();
    }

    /**
     * Для переопределения лисенера для positive btn
     */
    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog d = (AlertDialog) getDialog();

        if(d != null){
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE); // считываем pos btn
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // добавляем к нему слушателя
                    // сначала проверим все на корректный ввод
                    String txtError = checkDataFromView(dialogView);

                    // если некорректный ввод
                    if (!txtError.equals(""))
                        Toast.makeText(getActivity(), txtError, Toast.LENGTH_LONG)
                                .show();

                    // Если ввод корректен
                    else {
                        GridView clothesGridView = (GridView) getActivity().findViewById(R.id.clothes_list);

                        getDataFromView(dialogView); // обновили поля
                        Clothes newCloth = new Clothes(name, type, imageUri);

                        String message; // текст сообщения ошибки

                        // если редактирование
                        if (idForEdit != -1) {
                            // редактируем запись в БД
                            dbHelper.updateCloth(newCloth, idForEdit + 1);

                            // удаляем и вставляем на это место новый объект одежды в адаптере
                            HomeFragment.clothesAdapter.remove(WorkClothes.getClothes(idForEdit));
                            HomeFragment.clothesAdapter.insert(newCloth, idForEdit);

                            message = "Запись отредактирована";
                        }
                        // если добавление
                        else {
                            // записываем в БД
                            dbHelper.writeToDataBase(newCloth.getName(), newCloth.getType(),
                                    newCloth.getImageUri().toString());

                            // добавляем в адаптер
                            HomeFragment.clothesAdapter.add(newCloth);

                            message = "Добавление успешно";
                        }

                        WorkClothes.update();   // обновили
                        //HomeFragment.clothesAdapter.notifyDataSetChanged();
                        clothesGridView.invalidateViews(); // обновляем gridView

                        // выводим сообщение
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG)
                                .show();

                        d.dismiss(); //закрыли
                    }
                }
            });
        }
    }

    /**
     * Заполняем спиннер (типы одежды)
     */
    public void initSpinner(){
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Clothes.getTypes());

        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(1); // ставим значение по умолчанию
    }

    /**
     * Метод для получения данных. Обновляет поля name, type, imageUri
     */
    private void getDataFromView(View view){
        // считываем название одежды
        name = textViewName.getText().toString();
        // считываем тип одежды (в спиннере)
        type = spinner.getSelectedItem().toString();

        //imageUri = savePictureAndGetUri(); // путь до картинки
        FileWork fileWork = new FileWork(getContext());

        if (idForEdit != -1) // если редактирование
            fileWork.deleteImage(imageUri);

        // Картинка (считываем с imageView, а не берем uri, т.к. сохраняем в Bitmap, который
        // переворачивается (из-за кэша, который я не знаю как удалить))
        imageUri = fileWork.savePictureAndGetUri(
                ( (BitmapDrawable)imageView.getDrawable() ).getBitmap()
        );
    }

    /**
     * Устанавливает данные одежды для редактирования
     * @param cloth - одежда для редактирования
     */
    public void setCloth(Clothes cloth){
        textViewName.setText(cloth.getName());

        int typeId = 0;
        for (String el:
                Clothes.getTypes()) {
            if (el.equals(cloth.getType()))
                break;
            typeId ++;
        }
        spinner.setSelection(typeId);

        imageUri = cloth.getImageUri();
        imageView.setImageURI(imageUri);
    }

    /**
     * Проверяем корректность ввода
     * @return message - текст ошибки. Если message пуст, то ввод корректен
     */
    private String checkDataFromView(View view){
        String message = "";
        if (selectedImageUri == null) //если картинка не выбрана
            message = "Выберите фото!";

        String name = textViewName.getText().toString();
        if (name.equals(""))
            message = "Введите название";

        return message;
    }

    /**
     * открывает диалог окно для выбора галлереи
     */
    public void addPhoto(View view){
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