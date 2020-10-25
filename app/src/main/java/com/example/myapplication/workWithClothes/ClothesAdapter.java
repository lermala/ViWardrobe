package com.example.myapplication.workWithClothes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.workWithClothes.Clothes;

import java.util.List;

public class ClothesAdapter extends ArrayAdapter<Clothes> {

    private LayoutInflater inflater;
    private int layout;
    private List<Clothes> clothes;

    public ClothesAdapter(Context context, int resourse, List<Clothes> clothes){
        super(context, resourse, clothes);
        this.clothes = clothes;
        this.layout = resourse;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * ВЫГРУЖАЕТ ВСЕ В СПИСОК (В ХОМЕ)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        ImageView flagView = (ImageView) view.findViewById(R.id.image_clothes);
        //TextView nameView = (TextView) view.findViewById(R.id.name);
        //TextView capitalView = (TextView) view.findViewById(R.id.type);

        Clothes cloth = clothes.get(position);

        flagView.setImageResource(cloth.getImage());
       // nameView.setText(cloth.getName());
        //capitalView.setText(cloth.getType());

        return view;
    }

}
