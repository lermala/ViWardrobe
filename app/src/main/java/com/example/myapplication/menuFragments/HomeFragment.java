package com.example.myapplication.menuFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.menuFragments.Dialogs.ClothesDialogFragment;
import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Clothes;
import com.example.myapplication.Logic.workWithClothes.ClothesAdapter;
import com.example.myapplication.Logic.workWithClothes.LoadClothesInterface;
import com.example.myapplication.Logic.workWithClothes.WorkClothes;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private LoadClothesInterface loadClothesInterface = new WorkClothes();
    private List<Clothes> clothes = loadClothesInterface.getAllClothes();
    private GridView clothesGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Мой гардероб");

        // получаем элемент GridView
        clothesGridView = (GridView) getActivity().findViewById(R.id.clothes_list);
        // создаем адаптер
        //ClothesAdapter clothesAdapter = new ClothesAdapter(getActivity(),
        //        R.layout.list_item, clothes);
        // устанавливаем адаптер
        //clothesGridView.setAdapter(clothesAdapter);
        // слушатель выбора в списке

        //СЛУШАТЕЛЬ ДЛЯ НАЖАТИЙ НА ЭЛЕМЕНТ ТАБЛИЦЫ
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // получаем выбранный пункт
                Clothes selectedCloth = (Clothes) parent.getItemAtPosition(position);
                // Создание диалогового окна
                FragmentManager manager = getFragmentManager();
                ClothesDialogFragment myDialogFragment = new ClothesDialogFragment(selectedCloth);
                myDialogFragment.show(manager, "dialog");
            }
        };
        // подключаем слушатель к итемам
        clothesGridView.setOnItemClickListener(itemListener);


        //------------------------СПИНЕР---------------------------
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.home_filter);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Clothes.getTypes());
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);// Применяем адаптер к элементу spinner
        spinner.setSelection(0); // ставим значение по умолчанию

        // получаем выбранное значение в спиннере
        //добавляем к спиннеру слушатель выбранного итема
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                       int selectedItemPosition, long selectedId){
                String[] choose = Clothes.getTypes();

                if (choose[selectedItemPosition].equals("Все")){
                    clothesGridView.setAdapter(new ClothesAdapter(getActivity(), // отображаем все значения
                            R.layout.list_item, clothes));
                    return;
                }

                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Ваш выбор: " +
                        choose[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();

                // АЛГОРИТМ ВЫГРУЗКИ ФОТО С ВЫБРАННЫМ ТИПОМ
                ArrayList<Clothes> selectedTypeClothes = new ArrayList<>(); // новый список одежды (выбранного типа!)
                for (Clothes el: //проходим по каждому элементу списка одежды
                     clothes) {
                    if (el.getType().equals(choose[selectedItemPosition])) // если тип одежды этого элемента совпадает с выбранным
                        selectedTypeClothes.add(el); // добалвяем
                }
                clothesGridView.setAdapter(new ClothesAdapter(getActivity(), // отображаем новую таблицу
                        R.layout.list_item, selectedTypeClothes));
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //------------------------END_СПИНЕР---------------------------
    }



}