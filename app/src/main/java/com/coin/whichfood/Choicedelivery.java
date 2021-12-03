package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class Choicedelivery extends Activity {

    private int where =0;
    private int kind = 0;
    private int foodnum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choicedelivery);
        Intent getfoodinfo = getIntent();
        where = getfoodinfo.getIntExtra("where",0);
        kind = getfoodinfo.getIntExtra("kind",0);
        foodnum = getfoodinfo.getIntExtra("foodnum",0);

        ImageButton selfdelivery = (ImageButton) findViewById(R.id.selfdelivery);
        ImageButton flatformdelivery = (ImageButton) findViewById(R.id.flatformdelivery);

        selfdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goselfdeliverylist = new Intent(Choicedelivery.this, Selfdeliverylist.class);
                goselfdeliverylist.putExtra("where",where);
                goselfdeliverylist.putExtra("kind",kind);
                goselfdeliverylist.putExtra("foodnum",foodnum);
                startActivity(goselfdeliverylist);
                finish();
            }
        });

    }


}
