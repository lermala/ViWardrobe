package com.example.myapplication.Logic.workWithClothes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ClothesAdapter extends ArrayAdapter<Clothes> {

    private static final String TAG = " ADapter clothes | ";

    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Clothes> clothes;

    public ClothesAdapter(Context context, int resourse, ArrayList<Clothes> clothes){
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

     //   flagView.setImageResource(cloth.getImage());
        //Log.i(TAG, "cloth: "  + cloth.toString());
      //  Log.i(TAG, "name: "  + cloth.getName() + " type="
      //  + cloth.getType() + " uri=" + cloth.getImageUri().toString());

        flagView.setImageURI(cloth.getImageUri());


        //flagView.setImageURI(intent);

       // imageView.setImageURI(selectedImageUri);

       // nameView.setText(cloth.getName());
        //capitalView.setText(cloth.getType());

        return view;
    }

}
