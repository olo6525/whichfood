package com.coin.whichfood;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singlegridview, this, true);

        foodname = (TextView)findViewById(R.id.foodname);
        wherefunction = (ImageButton)findViewById(R.id.wherefunction);
    }

    public void setfooditem(int where, int kind, int foodnum, String name){
        if(where ==1){
            foodname.setText(name);
            wherefunction.setImageResource(R.drawable.ic_recipe);
        }else if (where == 2){
            foodname.setText(name);
            wherefunction.setImageResource(R.drawable.ic_storelocation);
        }else if(where ==3){
            foodname.setText(name);
            wherefunction.setImageResource(R.drawable.ic_delivery);
        }else if(where == 0){
            foodname.setText(name);
        }else{
            Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
        }
    }
}
