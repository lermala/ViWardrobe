package com.example.myapplication.menuFragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.menuFragments.Dialogs.TagsDialogFragment;

public class FragmentAddingLooks extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // задаем диалог окну вид (=фрагмент)
        View view = inflater.inflate(R.layout.fragment_adding_looks, null);

        builder.setView(view);
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




}