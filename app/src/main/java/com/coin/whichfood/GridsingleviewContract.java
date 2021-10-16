package com.coin.whichfood;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class GridsingleviewContract extends LinearLayout {
    
    TextView storename;
    TextView storenum;

    public GridsingleviewContract(Context context) {
        super(context);

        init(context);
    }
    public GridsingleviewContract(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singlegridviewcontract, this, true);

        storename = (TextView)findViewById(R.id.storename);
        storenum = (TextView)findViewById(R.id.storenum);

    }

    public void setstoreitem(String getstorenum, String getstorename){
        storenum.setText(getstorenum);
        storename.setText(getstorename);
    }
}
