package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Errorpopup extends Activity {
    private static final String TAG = "TAG";

    TextView errormassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.errorpopup);
        errormassage = (TextView)findViewById(R.id.errormassage);
        Button checkbutton = (Button)findViewById(R.id.checkbutton);

        String errormassagetext = "";
        Intent geterrormassage = getIntent();
        errormassagetext = geterrormassage.getStringExtra("errormassage");
        Log.d(TAG, "imagelocation errormassage : "+errormassagetext);

        errormassage.setText(errormassagetext);

        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
