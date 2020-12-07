package com.example.myapplication.menuFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Clothes;
import com.example.myapplication.Logic.workWithLooks.Look;
import com.example.myapplication.Logic.workWithLooks.LookAdapter;

import java.util.ArrayList;
import java.util.List;

public class LooksFragment extends Fragment {

    private List<Look> looks = new ArrayList<>();
    private GridView looksGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Мои луки");
        return inflater.inflate(R.layout.fragment_looks, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // получаем элемент GridView
        looksGridView = (GridView) getActivity().findViewById(R.id.clothes_list);
        // создаем адаптер
        LookAdapter lookAdapter = new LookAdapter(getActivity(),
                R.layout.list_look_item, looks);
        // устанавливаем адаптер
        looksGridView.setAdapter(lookAdapter);
        // слушатель выбора в списке
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                Clothes selectedCloth = (Clothes) parent.getItemAtPosition(position);
                Toast.makeText(getActivity().getApplicationContext(), "Был выбран пункт " + selectedCloth.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        looksGridView.setOnItemClickListener(itemListener);
    }


}