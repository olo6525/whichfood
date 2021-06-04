package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;


public class OneFood extends Activity {

    Bitmap bitmap_one;
    ImageView food_image_one;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onefood);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Intent intent_thank = new Intent(this,MainActivity.class);
        Intent intentEnternet = new Intent(Intent.ACTION_VIEW);
        ImageButton imbtn_thank = (ImageButton)findViewById(R.id.imbtn_thank);
        ImageButton imbtn_end = (ImageButton)findViewById(R.id.imbtn_end);
        ImageButton imbtn_detail = (ImageButton)findViewById(R.id.detail);
        int onefood;



        //종료시 전면광고-----------------------------------------------------------------------------
        MobileAds.initialize(this,
                "ca-app-pub-8231620186256321~9673217647");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8231620186256321/1155279399");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //광고 끝---------------------------------------------------------------------------------------
//음식 한개 보여지기 -----------------------------------------------------------------------------------------------
        final FlagClass flag = (FlagClass)getApplication();
        int where = flag.getWhere();
        if(where == 1) {  }
        else if (where == 2) {
            imbtn_detail.setImageResource(R.drawable.whichfoodfindcafeteria1);
            }
        else if(where == 3) {
            imbtn_detail.setImageResource(R.drawable.whichfooddelivery1);
           }
        else {}
        GetFoodImageOne getFoodImageOne=new GetFoodImageOne();
        if(flag.getOne_food1() > flag.getOne_food2())
        {
            getFoodImageOne.execute(flag.getOne_where(),flag.getOne_kind(),Integer.toString(flag.getOne_food1()));
            onefood=flag.getOne_food1();
        }
        else
        {
            getFoodImageOne.execute(flag.getOne_where(),flag.getOne_kind(),Integer.toString(flag.getOne_food1()));
            onefood=flag.getOne_food2();
        }
//음식한개 보여지기 끝-------------------------------------------------------------------------------------------------
        imbtn_thank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent_thank);
            }
        });
        imbtn_end.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                ActivityCompat.finishAffinity(OneFood.this);
            }
        });
        imbtn_detail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



            }
        });

    }



//음식 이미지 가져오기 테스크-----------------------------------------------------------------------------------
    private class GetFoodImageOne extends AsyncTask<String , Integer, Bitmap> {




        @Override
        protected Bitmap doInBackground(String... strings) {


            String S_where = strings[0];
            String S_kind = strings[1];
            String S_rand = strings[2];

            try{
                URL foodimage = new URL("https://www.uristory.com/foodimages/" + S_where + "/" + S_kind + "/"+ S_rand+".jpg");
                HttpURLConnection conn = (HttpURLConnection)foodimage.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bitmap_one = BitmapFactory.decodeStream(is);
            }catch (IOException e){
                e.printStackTrace();
                Log.d(TAG, "이미지 없음1");
            }

            return bitmap_one;
        }

        protected void onPostExecute(Bitmap img){
            food_image_one=(ImageView)findViewById(R.id.foodimageone);
            food_image_one.setImageBitmap(bitmap_one);
        }
    }
//음식 이지미 가져오기 테스크 끝-----------------------------------------------------------------------------------------------------

    //뒤로가기 종료==================================================================================
    private long time= 0;
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    //뒤로가기 종료 끝-----------------------------------------------------------------------------
}