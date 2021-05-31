package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

public class WhatKindFood extends Activity {

    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatkindfood);
        final Intent intent_twofood = new Intent(this, TwoFood.class);
        final FlagClass flag = (FlagClass)getApplication();

        //종료시 전면광고-----------------------------------------------------------------------------
        MobileAds.initialize(this,
                "ca-app-pub-8231620186256321~9673217647");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8231620186256321/1155279399");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //광고 끝---------------------------------------------------------------------------------------

        ImageButton btn_meal = (ImageButton)findViewById(R.id.btnmeal);
        ImageButton btn_drink = (ImageButton)findViewById(R.id.btndrink);

        btn_meal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                flag.setKind(1);
                startActivity(intent_twofood);
            }
        });
        btn_drink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                flag.setKind(2);
                startActivity(intent_twofood);
            }
        });
    }

    //뒤로가기 종료==================================================================================
    private long time= 0;
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    //뒤로가기 종료 끝-----------------------------------------------------------------------------
}
