package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
    Intent startmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startmain = new Intent(this, MainActivity.class);
        final FlagClass flag = (FlagClass)getApplication();
        flag.Init();
        startLoading();
    }
    private void startLoading() {
        Handler hendler = new Handler();
        hendler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(startmain);
            }
        }, 1500);
    }
}
