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
    TextView deliverystoreaddress;
    TextView deliverystoredistance;
    TextView deliverystoreinfo;

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
        deliverystoreaddress = (TextView)findViewById(R.id.deliverystoreaddress);
        deliverystoredistance = (TextView)findViewById(R.id.deliverystoredistance);
        deliverystoreinfo = (TextView)findViewById(R.id.deliverystoreinfo);

    }

    public void setselfdeliveryitem(String storename, String storeaddress, String storenumber, String storepage, String storedistance, String storeinfo){
        deliverystorename.setText       ("매장 상호     : " + storename);
        deliverystorenumber.setText     ("매장 번호     : " + storenumber);
        deliverystorepage.setText       ("홈페이지      : " + storepage);
        deliverystoreaddress.setText    ("매장 주소     : " + storeaddress);
        deliverystoredistance.setText   ("매장 거리     : " + storedistance + "km");
        deliverystoreinfo.setText       (storeinfo);
    }
}
