package com.example.myapplication.menuFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Logic.UsersPack.User;
import com.example.myapplication.Logic.workWithClothes.Clothes;
import com.example.myapplication.Logic.workWithLooks.LookAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import static com.example.myapplication.MainActivity.APP_PREFERENCES_NAME;

public class LoginFragment extends Fragment{

    EditText txtName, txtMail, txtDate;
    Button btnOk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Введите данные");
        txtMail = (EditText) getActivity().findViewById(R.id.login_mail);
        txtName = (EditText) getActivity().findViewById(R.id.login_name);
        txtDate = (EditText) getActivity() .findViewById(R.id.login_date);
        btnOk = (Button) getActivity().findViewById(R.id.login_btn);

        if(MainActivity.Settings.contains(APP_PREFERENCES_NAME)){ // если редактируем
            getActivity().setTitle("Редактирование профиля");
            txtMail.setText(MainActivity.user.getMail());
            txtName.setText(MainActivity.user.getName());
            txtDate.setText(MainActivity.user.getDate());
        }
    }

    public User getUserData(){
        String name = txtName.getText().toString();
        String mail = txtMail.getText().toString();
        String date = txtDate.getText().toString();

        if (name.equals("") || mail.equals("")){
            return null;
        }
        else
            return new User(name, mail, date);
    }
}