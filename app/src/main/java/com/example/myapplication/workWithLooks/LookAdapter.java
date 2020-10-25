package com.example.myapplication.workWithLooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.myapplication.R;

import java.util.List;

public class LookAdapter extends ArrayAdapter<Look> {
    private LayoutInflater inflater;
    private int layout;
    private List<Look> looks;

    public LookAdapter(Context context, int resourse, List<Look> looks){
        super(context, resourse, looks);
        this.looks = looks;
        this.layout = resourse;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);
        ImageView flagView = (ImageView) view.findViewById(R.id.image_looks);
        Look look = looks.get(position);
        flagView.setImageResource(look.getImage());
        return view;
    }
}
