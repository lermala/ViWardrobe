package com.example.myapplication.menuFragments.Dialogs;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Clothes;

public class AddingClothesFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adding_clothes, container, false);
    }

  /*  @Override
    public void onStart(){
        super.onStart();
        getActivity().setTitle("Добавить");

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.clothes_type);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Clothes.getTypes());
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // задаем диалог окну вид (=фрагмент)
        View view = inflater.inflate(R.layout.fragment_adding_clothes, null);

        //делаем спиннер
        Spinner spinner = (Spinner) view.findViewById(R.id.clothes_type);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Clothes.getTypes());
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        builder.setView(view);
        builder.setTitle("Добавить одежду");
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Закрываем окно
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO: здесь будет релазиция добавления, которую я когда-нибудь сделаю

                // Закрываем окно
                dialog.cancel();
            }
        });
        return builder.create();
    }
}