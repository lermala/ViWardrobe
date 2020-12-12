package com.example.myapplication.menuFragments;

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

import com.example.myapplication.MainActivity;
import com.example.myapplication.fileWork.FileWork;
import com.example.myapplication.menuFragments.Dialogs.AddingClothesFragment;
import com.example.myapplication.menuFragments.Dialogs.ClothesDialogFragment;
import com.example.myapplication.R;
import com.example.myapplication.Logic.workWithClothes.Clothes;
import com.example.myapplication.Logic.workWithClothes.ClothesAdapter;
import com.example.myapplication.Logic.workWithClothes.WorkClothes;

import java.util.ArrayList;

/**
 * Фрагмент со всей одеждой "Гардероб"
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "Home Fragment | "; //FIXME

    private ArrayList<Clothes> clothes = WorkClothes.getAllClothes();
    private GridView clothesGridView;
    private Spinner spinner;

    private int contextMenuGridPosition; // номер элемента в gridview. появляется при длительном нажатии

    //context menu items:
    public static final int IDM_A = 101; //deleting
    public static final int IDM_B = 102; // редактирование

    public static ClothesAdapter clothesAdapter;

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
        spinner = (Spinner) getActivity().findViewById(R.id.home_filter);

        clothesAdapter = new ClothesAdapter(getActivity(), R.layout.list_item, clothes);

        initGridView();
        initSpinner();
    }

    public void initGridView(){
        clothesGridView.setAdapter(clothesAdapter); // подключаем адаптер со списком значений

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

        registerForContextMenu(clothesGridView); // подключаем контекстное меню к таблице

        // подключаем слушатель к итемам
        clothesGridView.setOnItemClickListener(itemListener);
    }

    public void initSpinner(){
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Clothes.getTypes());

        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        // ставим значение по умолчанию
        spinner.setSelection(0);

        //добавляем к спиннеру слушатель выбранного итема
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                       int selectedItemPosition, long selectedId){
                String[] choose = Clothes.getTypes();

                // Если выбрано "Всё"
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

                // проходим по каждому элементу списка одежды
                for (Clothes el:
                        clothes) {
                    // если тип одежды этого элемента совпадает с выбранным
                    if (el.getType().equals(choose[selectedItemPosition]))
                        selectedTypeClothes.add(el); // добалвяем
                }

                // отображаем новую таблицу
                clothesGridView.setAdapter(new ClothesAdapter(getActivity(),
                        R.layout.list_item, selectedTypeClothes));
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Контекстное меню, которое вызывается при зажатии на элементе одежды.
     * Содержит удаление и редактирование
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_B, Menu.NONE, "Редактировать");
        menu.add(Menu.NONE, IDM_A, Menu.NONE, "Удалить");

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contextMenuGridPosition = info.position; //номер элемента в gridview
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case IDM_A:
                // TODO: добавить "Точно ли хотите удалить?" да/нет

                Clothes clothForDel = WorkClothes.getClothes(contextMenuGridPosition);

                FileWork fileWork = new FileWork(getContext());
                fileWork.deleteImage(clothForDel.getImageUri()); // удаляем картинку из папки

                Log.d("Files", "contextMenuGridPosition=" + contextMenuGridPosition); // FIXME delete

                Log.d("CHECK ", " | before "); // FIXME delete
                for (Clothes el:
                        clothesAdapter.getClothes()) {
                    Log.d("CHECK ", el.toString() + " | "); // FIXME delete
                }

                MainActivity.dbHelper.deleteCloth(contextMenuGridPosition + 1); // удаляем из БД
                //HomeFragment.clothesAdapter.remove(clothForDel);
                clothesAdapter.remove(clothesAdapter.getItem(contextMenuGridPosition));
                WorkClothes.update(); // обновляем список
                clothesAdapter.notifyDataSetChanged(); // оповещаем об изменениях данных

                //this.clothesAdapter.notifyDataSetChanged(); // оповещаем об изменениях данных

                Log.d("CHECK ", " | after "); // FIXME delete
                for (Clothes el:
                     clothesAdapter.getClothes()) {
                    Log.d("CHECK ", el.toString() + " | "); // FIXME delete
                }

                clothesGridView.invalidateViews(); // обновляем gridView

                Toast.makeText(getActivity(), "Удаление завершено", Toast.LENGTH_LONG)
                        .show();

                return true;

            case IDM_B: // редактировать
                AddingClothesFragment clothesFragment = new AddingClothesFragment(
                        "Редактировать одежду", contextMenuGridPosition);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                clothesFragment.show(manager, "dialog");

                return true;
        }
        return super.onContextItemSelected(item);
    }


}