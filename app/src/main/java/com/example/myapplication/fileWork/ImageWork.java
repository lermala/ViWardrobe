package com.example.myapplication.fileWork;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.myapplication.R;

import java.io.IOException;
import java.io.InputStream;

public class ImageWork {




   /* public void LoadThePicture(){
        ImageView imageView = (ImageView) findViewById(R.id.image);
        String filename = "clothes/";
        InputStream inputStream = null;
        try{
            inputStream = getApplicationContext().getAssets().open(filename);
            Drawable d = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(d);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }*/


   /*        //LoadThePicture();
        //TextView textView = findViewById(R.id.fuck);

        //final AssetManager mgr = getAssets();
        //textView.setText(displayFiles(mgr, "clothes")); // содержимое подпапки /assets/cats

        final AssetManager mgr = getAssets();

        String[] clothes = displayFiles(mgr, "clothes");

        ListView clothesList = (ListView) findViewById(R.id.clothes_list);



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, clothes);

        clothesList.setAdapter(adapter);*/
}
