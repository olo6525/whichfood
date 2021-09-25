package com.coin.whichfood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

import org.json.JSONException;

public class Gridsingleview extends LinearLayout {

    TextView foodname;
    ImageView wherefunction;


    public Gridsingleview(Context context) {
        super(context);

        init(context);
    }
    public Gridsingleview(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singlegridview, this, true);

        foodname = (TextView)findViewById(R.id.foodname1);
        wherefunction = (ImageView)findViewById(R.id.wherefunction);

    }

    public void setfooditem(int where, int kind, int foodnum, String name, String url){
        if(where ==1){
            foodname.setText(name);
            if(kind == 1) {
                wherefunction.setImageResource(R.drawable.ic_recipe);

            }else if (kind == 2){
                wherefunction.setImageResource(R.drawable.ic_recipe);
            }
            Log.d(TAG, "seeview where1");
        }else if (where == 2){
            foodname.setText(name);
            wherefunction.setImageResource(R.drawable.ic_storelocation);
            Log.d(TAG, "seeview where2");
        }else if(where ==3){
            foodname.setText(name);
            wherefunction.setImageResource(R.drawable.ic_delivery);
            Log.d(TAG, "seeview where3");
        }else if(where == 0){
            foodname.setText(name);
            Log.d(TAG, "seeview where0");
        }else{
           Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
        }
    }
}
