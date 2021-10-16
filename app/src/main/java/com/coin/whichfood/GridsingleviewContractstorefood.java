package com.coin.whichfood;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class GridsingleviewContractstorefood extends LinearLayout {

    TextView foodname;
    ImageView wherefunction;


    public GridsingleviewContractstorefood(Context context) {
        super(context);

        init(context);
    }
    public GridsingleviewContractstorefood(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.contractstorefoodsinglegrid, this, true);

        foodname = (TextView)findViewById(R.id.contractfoodname);
        wherefunction = (ImageView)findViewById(R.id.contractfoodpicture);

    }

    public void setfooditem(int where, int kind, int foodnum, String name){
        foodname.setText(name);
    }
}
