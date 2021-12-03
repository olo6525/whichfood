package com.coin.whichfood;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Gridsingleviewselfdelivery extends LinearLayout {
    TextView deliverystorename;
    TextView deliverystorenumber;
    TextView deliverystorepage;

    public Gridsingleviewselfdelivery(Context context) {
        super(context);

        init(context);
    }

    public Gridsingleviewselfdelivery(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.selfdeliverysinglegrid, this, true);


        deliverystorename = (TextView) findViewById(R.id.deliverystorename);
        deliverystorenumber =(TextView)findViewById(R.id.deliverystorenumber);
        deliverystorepage = (TextView) findViewById(R.id.deliverystorepage);

    }

    public void setselfdeliveryitem(String storename, String storenumber, String storepage){
        deliverystorepage.setText(storename);
        deliverystorenumber.setText(storenumber);
        deliverystorepage.setText(storepage);
    }
}
