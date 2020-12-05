package com.example.myapplication.menuFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.myapplication.Logic.workWithClothes.WorkClothes;

import java.io.File;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "Home Fragment | "; //FIXME

    private ArrayList<Clothes> clothes = WorkClothes.getAllClothes();
    private GridView clothesGridView;

    //context menu items:
    public static final int IDM_A = 101; //deleting
    public static final int IDM_B = 102;

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

        //////new

        //AdapterView.OnCreateContextMenuListener itemContextMenuListener
        registerForContextMenu(clothesGridView);

        //-----------

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

    private int contextMenuGridPosition; //номер элемента в gridview. появляется при длительном нажатии

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_A, Menu.NONE, "Удалить");

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contextMenuGridPosition = info.position; //номер элемента в gridview
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case IDM_A:
                // TODO: добавить "Точно ли хотите удалить?" да/нет

                // ДЛЯ ПРОВЕРКИ
                String path = getContext().getFilesDir().toString();
                File directory = new File(path + "/Clothes");
                File[] files = directory.listFiles();
                Log.d("Files", "Size BEFORE: "+ files.length);
                for (int i = 0; i < files.length; i++)
                {
                    Log.d("Files", "FileName:" + files[i].getName());
                }


                // получаем путь до картинки этого элемента
                Uri imgUri = WorkClothes.getClothes(contextMenuGridPosition).getImageUri();
                File file = new File(String.valueOf(imgUri));

                // Удаляем фото из папки с картинками
                if (file.delete()){
                    Log.i(TAG,"del OK");
                }
                else Log.i(TAG,"del is not OK");

                WorkClothes.deleteCloth(contextMenuGridPosition); //удаляем из списка

                // ДЛЯ ПРОВЕРКИ
                files = directory.listFiles();
                Log.d("Files", "Size AFTER: "+ files.length);
                for (int i = 0; i < files.length; i++)
                {
                    Log.d("Files", "FileName:" + files[i].getName());
                }

                // отображаем новую таблицу
                clothesGridView.setAdapter(new ClothesAdapter(getActivity(),
                        R.layout.list_item, WorkClothes.getAllClothes()));

                Toast.makeText(getActivity(), "Удаление завершено", Toast.LENGTH_LONG)
                        .show();

                return true;
        }
        return super.onContextItemSelected(item);
    }


}