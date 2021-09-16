package com.coin.whichfood;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class Gridsingleview extends LinearLayout {

    TextView foodname;
    ImageButton wherefunction;
    Intent wherefunctionintent;

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
        wherefunction = (ImageButton)findViewById(R.id.wherefunction);
    }

    public void setfooditem(int where, int kind, int foodnum, String name){
        if(where ==1){
            foodname.setText(name);
            wherefunction.setImageResource(R.drawable.ic_recipe);
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
