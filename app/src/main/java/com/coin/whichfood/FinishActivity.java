package com.coin.whichfood;

import android.app.Activity;
import android.os.Bundle;

public class FinishActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moveTaskToBack(true);
        finishAndRemoveTask();
    }
}
