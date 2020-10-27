package com.example.myapplication.menuFragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Logic.workWithClothes.Clothes;
import com.example.myapplication.R;

// Фрагмент для просмотра одежды
public class ClothesDialogFragment extends DialogFragment {
    private Clothes clothes; // selected item

    public ClothesDialogFragment(Clothes clothes) {
        this.clothes = clothes;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // задаем диалог окну вид (=фрагмент)
        View view = inflater.inflate(R.layout.fragment_dialog_clothes, null);

        // вписываем все значения кликнутого итема в диалогове окно:
        ImageView imageView = view.findViewById(R.id.dialog_clothes_image); //фото
        imageView.setImageResource(clothes.getImage());

        TextView nameTextView = view.findViewById(R.id.dialog_clothes_name);//название
        nameTextView.setText("Название: " + clothes.getName());

        TextView typeTextView = view.findViewById(R.id.dialog_clothes_type);//тип
        typeTextView.setText("Тип: " + clothes.getType());

        builder.setView(view);
        builder.setTitle("Просмотр"); // можно заменить на название предмета одежды clothes.getName()
        builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Закрываем окно
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
