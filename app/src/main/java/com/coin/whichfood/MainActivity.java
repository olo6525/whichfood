package com.coin.whichfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView; //광고 변수 선언
    VersionCheck versionCheck;
    private static String FoodIP = "https://www.uristory.com/whichfood.php"; //음식 이미지 서버
    private InterstitialAd mInterstitialAd;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final String[] REQUESTED_PERMISSION = {ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION};
    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public static Activity activity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;

//권한 신청 --------------------------------------------------
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ){
            Toast.makeText(getApplicationContext(),"위치기반 권한 허용 상태입니다.",Toast.LENGTH_SHORT).show();

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("'이거먹자' 위치기반 서비스 이용안내");
            builder.setMessage("위치기반 권한 허용을 하시면 사용자의 현재위치를 기반으로 주변 식당들의 위치를 파악하실 수 있습니다. \n " +
                    "미 허용시 위치기반 서비스를이용하실 수 없습니다.");
            builder.setIcon(android.R.drawable.ic_menu_save);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (!hasPermissions(MainActivity.this, REQUESTED_PERMISSION)) {
                        ActivityCompat.requestPermissions(MainActivity.this, REQUESTED_PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

                    }

                }
            });
            builder.create();
            builder.show();
        } else {
            Toast.makeText(getApplicationContext(),"권한 허용을 하지 않아 위치기반 서비스 이용이 불가능합니다. \n앱 옵션에서 위치기반 권한 허용을 해주시길 바랍니다.",Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("'이거먹자' 위치기반 서비스 이용안내");
            builder.setMessage("위치기반 권한 허용을 하시면 사용자의 현재위치를 기반으로 주변 식당들의 위치를 파악하실 수 있습니다. \n " +
                    "미 허용시 위치기반 서비스를이용하실 수 없습니다.");
            builder.setIcon(android.R.drawable.ic_menu_save);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (!hasPermissions(MainActivity.this, REQUESTED_PERMISSION)) {
                        ActivityCompat.requestPermissions(MainActivity.this, REQUESTED_PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

                    }

                }
            });
            builder.create();
            builder.show();

        }


//광고-----------------------------------------------------------------------------------------------------
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //종료시 전면광고-----------------------------------------------------------------------------
        MobileAds.initialize(this,
                "ca-app-pub-8231620186256321~9673217647");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8231620186256321/1155279399");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //광고 끝---------------------------------------------------------------------------------------
//광고끝 -----------------------------------------------------------------------------------------------------
        //로그인============================================================================================
        final FlagClass flag = (FlagClass)getApplication();
        ImageButton loginbutton = (ImageButton)findViewById(R.id.login);
        ImageButton myinfo = (ImageButton)findViewById(R.id.myinfo);
        ImageButton logout = (ImageButton)findViewById(R.id.logout);
        Intent loginlayout = new Intent(this,kakaologin.class);
        if(flag.getLoginflag() == 0) {
            myinfo.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            loginbutton.setVisibility(View.VISIBLE);
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(loginlayout);
                }
            });
        }else if (flag.getLoginflag() == 1){
            loginbutton.setVisibility(View.GONE);
            myinfo.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            myinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(loginlayout);
                }
            });
        }
        final Intent intent_whatkindfood = new Intent(this, WhatKindFood.class);
        versionCheck = new VersionCheck();
        ArrayAdapter sexAdapter = ArrayAdapter.createFromResource(this, R.array.city_weather, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Intent update_pop = new Intent(this, PopUp.class);
        String serverdata;
        try {
            serverdata = new VersionCheck().execute(FoodIP).get();
            Log.d("TAG", "serverdata" + serverdata);
            JSONObject jsonObject = new JSONObject(serverdata);
            String version = jsonObject.getString("version");
            JSONArray jsonArray = jsonObject.getJSONArray("thenumberoffood");
            JSONObject thenumberoffood = jsonArray.getJSONObject(0);
            flag.setThenumberoffoodhomemeal(Integer.parseInt(thenumberoffood.getString("homemeal")));
            flag.setThenumberoffoodhomedrink(Integer.parseInt(thenumberoffood.getString("homedrink")));
            flag.setThenumberoffoodoutmeal(Integer.parseInt(thenumberoffood.getString("outmeal")));
            flag.setThenumberoffoodoutdrink(Integer.parseInt(thenumberoffood.getString("outdrink")));
            flag.setThenumberoffooddelivermeal(Integer.parseInt(thenumberoffood.getString("delivermeal")));
            flag.setThenumberoffooddeliverdrink(Integer.parseInt(thenumberoffood.getString("deliverdrink")));
            flag.setHowcookpage(jsonObject.getJSONArray("howcook"));
            flag.setHowcookpagedrink(jsonObject.getJSONArray("howcookdrink"));
            Log.d("TAG", "serverdata" + version+","+flag.getThenumberoffoodhomemeal()+ flag.getThenumberoffoodoutdrink()+ flag.getHowcookpage().getJSONObject(0).getString("1"));
            if(!version.equals("4"))
            {
                Log.d("TAG", "The interstitial wasn't loaded yet."+ version);
                startActivity(update_pop);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ImageButton btn_cook = (ImageButton)findViewById(R.id.btncook);
        ImageButton btn_out = (ImageButton)findViewById(R.id.btnout);
        ImageButton btn_deliver = (ImageButton)findViewById(R.id.btndeliver);

        btn_cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                flag.setWhere(1);
                startActivity(intent_whatkindfood);
            }
        });
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                flag.setWhere(2);
                startActivity(intent_whatkindfood);
            }
        });
        btn_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                flag.setWhere(3);
                startActivity(intent_whatkindfood);
            }
        });

    }

    //뒤로가기 종료==================================================================================
    private long time= 0;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            ActivityCompat.finishAffinity(MainActivity.this);
        }
    }

    //뒤로가기 종료 끝-----------------------------------------------------------------------------

//버전체크 서버 접속-------------------------------------------------------------------------------
    private class VersionCheck extends AsyncTask <String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String IP = strings[0];
            String server_version = new String();

            try{
                URL Version = new URL(IP);
                HttpURLConnection conn = (HttpURLConnection)Version.openConnection();
                conn.setDoInput(true);
                conn.connect();


                int responseStatusCode = conn.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = conn.getInputStream();
                } else {
                    inputStream = conn.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();

                Log.d("TAG","version"+sb.toString());
                return sb.toString();

            }catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

        }

    }
    //버전체크 서버접속 끝!!----------------------------------------------------------------------------------


}
