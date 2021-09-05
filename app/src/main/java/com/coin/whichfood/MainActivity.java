package com.coin.whichfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.LinearLayout;
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

    LinearLayout fragmentlayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        final FlagClass flag = (FlagClass)getApplication();
        Intent get_registervalue = getIntent();
        fragmentlayout = (LinearLayout)findViewById(R.id.fragmantlayout);
        if(get_registervalue.getIntExtra("Success", 0)==1){
            Toast.makeText(getApplicationContext(),"입력하신 정보로 제휴 등록이 완료되셨습니다.",Toast.LENGTH_LONG).show();
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("제휴 서비스 등록 완료"); //제목
            dlg.setMessage("입력하신 정보로 제휴 등록이 완료되셨습니다.");
            dlg.setIcon(R.drawable.ic_storeimage);
            dlg.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dlg.show();
        }else if(get_registervalue.getIntExtra("Fail", 0)==1){
            Toast.makeText(getApplicationContext(),"제휴 등록에 실패하였습니다. 네트워크, 입력하신 정보를 다시 한번 확인바랍니다.",Toast.LENGTH_LONG).show();
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("제휴 서비스 등록 실패"); //제목
            dlg.setMessage("제휴 등록에 실패하였습니다. \n네트워크, 입력하신 정보를 다시 한번 확인바랍니다.");
            dlg.setIcon(R.drawable.ic_storeimage);
            dlg.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dlg.show();
        }else{

        }
//테스트 기능 버튼 =============================================================================================
//        Button testbutton1 = (Button)findViewById(R.id.testbutton1);
//        Button testbutton2 = (Button)findViewById(R.id.testbutton2);
//        testbutton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ShowStoreAd.class);
//                intent.putExtra("path","https://uristory.com/whichfoodadimages/");
//                startActivity(intent);
//
//            }
//        });
//테스트 기능 버튼 =============================================================================================
//버전체크=========================================================
        if(!flag.getVersion().equals("10")){
            Intent popup = new Intent(this,PopUp.class);
            startActivity(popup);
        }
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
        ImageButton loginbutton = (ImageButton)findViewById(R.id.login);
        ImageButton myinfo = (ImageButton)findViewById(R.id.myinfo);
        ImageButton logout = (ImageButton)findViewById(R.id.logout);
        ImageButton partnershipfake = (ImageButton)findViewById(R.id.partnershipfake);
        ImageButton partnershiplogin = (ImageButton)findViewById(R.id.partnershiplogin);
        Intent partnershipregister = new Intent(this,Partnershippay.class);
        Intent loginlayout = new Intent(this,kakaologin.class);
        Intent gomyinfo = new Intent(this, Myinfo.class);
        if(flag.getLoginflag() == 0) {
            myinfo.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            partnershipfake.setVisibility(View.VISIBLE);
            loginbutton.setVisibility(View.VISIBLE);
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(loginlayout);
                }
            });
            partnershipfake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"로그인 후 이용가능한 서비스 입니다.",Toast.LENGTH_LONG).show();
                }
            });
        }else if (flag.getLoginflag() == 1){
            loginbutton.setVisibility(View.GONE);
            partnershipfake.setVisibility(View.GONE);
            myinfo.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            partnershiplogin.setVisibility(View.VISIBLE);
            myinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(gomyinfo);
                }
            });
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(loginlayout);
                }
            });
            partnershiplogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(partnershipregister);
                }
            });
        }
        final Intent intent_whatkindfood = new Intent(this, WhatKindFood.class);



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
            fragmentlayout = (LinearLayout)findViewById(R.id.fragmantlayout);
            fragmentlayout.setVisibility(View.GONE);
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




}
