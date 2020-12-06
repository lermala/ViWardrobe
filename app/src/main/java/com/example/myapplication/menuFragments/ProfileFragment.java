package com.example.myapplication.menuFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Data.WorkClothes;

public class ProfileFragment extends Fragment {

    TextView txtName, txtMail, txtDate;
    Button btnEdit;

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
        txtMail = getActivity().findViewById(R.id.profile_mail);
        txtMail.setText(MainActivity.user.getMail());

        txtName = getActivity().findViewById(R.id.profile_name);
        txtName.setText(MainActivity.user.getName());

        txtDate = getActivity().findViewById(R.id.profile_date);
        txtDate.setText(MainActivity.user.getDate());

        btnEdit = getActivity().findViewById(R.id.profile_btn_edit);

        TextView textView = getActivity().findViewById(R.id.count_of_clothes);
        textView.setText("Всего предметов одежды: " + WorkClothes.getAllClothes().size());
    }

}