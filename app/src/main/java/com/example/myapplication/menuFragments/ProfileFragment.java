package com.example.myapplication.menuFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.workWithClothes.LoadClothesInterface;
import com.example.myapplication.workWithClothes.WorkClothes;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Профиль");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView textView = getActivity().findViewById(R.id.count_of_clothes);
        LoadClothesInterface loadClothesInterface = new WorkClothes();
        textView.setText("Всего предметов одежды: " + loadClothesInterface.getAllClothes().size());
    }

}