package com.example.myapplication.menuFragments.Dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Calendar;

public class FragmentAddingLooks extends DialogFragment {

    private Button btnDatePicker;
    private TextView textViewDate;

    // делаем переменные даты/времени полями
    private int mYear, mMonth, mDay;

    /**
     * Функция для вызова диалога с выбором даты
     * */
    private void callDatePicker() {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        textViewDate.setText(editTextDateParam);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // задаем диалог окну вид (=фрагмент)
        View view = inflater.inflate(R.layout.fragment_adding_looks, null);

        builder.setView(view);

        ///----------окно для даты TODO: заменитьь view на getActivity() если будешь переделывать
        btnDatePicker = (Button) view.findViewById(R.id.choose_date_button); // кнопка "Выбрать дату"
        textViewDate = (TextView) view.findViewById(R.id.picked_date);// Тест для отображения текущ даты

        // добавляем слушателя для кнопки выбора даты
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callDatePicker(); // вызываем календарь
            }
        });
        ///----------END_окно для даты------------------

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Закрываем окно
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO: здесь будет релазиция добавления, которую я когда-нибудь сделаю
                dialog.cancel();
            }
        });
        return builder.create();
    }


    /**
     * Считывает все введенные юзером данные из компонентов
     * и переводит в нужный формат
     * @param view - вид
     * @return true - ДА, лук на этот день есть
     * @return false - НЕТ, лука на этот день нет
     * */
    private void readData(View view){
        String date = textViewDate.getText().toString();
        ImageView imageView = view.findViewById(R.id.image_for_adding_look);


    }
}