package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class ShowStoreAd extends FragmentActivity {

    private FragmentManager fragmentManager;
    private SlideViewFlagment slideViewFlagment;
    private FragmentTransaction fragmentTransaction;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private String baseurl="https://uristory.com/home/ubuntu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.slidepage);
        Fragment fragment = new DemoObjectFragment();

        Bundle adimages = new Bundle();
//홍보 사진 가져오기================================================================================================
        Intent getstoreinfo = getIntent();
        String stotrnum = getstoreinfo.getStringExtra("storenum");
        String foodnum = getstoreinfo.getStringExtra("foodnum");
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
        ArrayList<String> urlArrayList = new ArrayList<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i = 1 ; i < 6; i++) {
                        urlArrayList.add(i, baseurl + "/whichfoodadimages/" + stotrnum + "/" + foodnum + "/"+Integer.toString(i)+".jpg");

                    }
                    adimages.putStringArrayList("adimages",urlArrayList);
                    fragment.setArguments(adimages);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        executorService.execute(runnable);
        executorService.shutdown();


//혼보사진 가져오기 끝 ==============================================================================================
        Log.d(TAG,"슬라이드");
        fragmentManager = getSupportFragmentManager();
        slideViewFlagment = new SlideViewFlagment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.slidehome, slideViewFlagment).commitAllowingStateLoss();
        Log.d(TAG,"슬라이드");



    }


}
