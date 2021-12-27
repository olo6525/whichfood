package com.coin.whichfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.IncompleteAnnotationException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;


import static android.content.ContentValues.TAG;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Myinfo extends Activity {

    FlagClass flagClass;
    ImageButton openpartnerinfo;
    ImageButton opencookinfo;
    ImageButton openoutinfo;
    ImageButton opendeliveryinfo;
    ImageButton closepartnerinfo;
    ImageButton closecookinfo;
    ImageButton closeoutinfo;
    ImageButton closedeliveryinfo;
    TextView myinfowelcome;
    TextView myemail;
    TextView partnerinfo;
    TextView cookinfo;
    TextView outinfo;
    TextView deliveryinfo;
    LinearLayout partnerlistlayout;
    LinearLayout cookfoodlistlayout;
    LinearLayout outfoodlistlayout;
    LinearLayout deliveryfoodlistlayout;
    GridView partnerlist;
    GridView cookfoodlist1;
    GridView outfoodlist1;
    GridView deliveryfoodlist1;
    GridView cookfoodlist2;
    GridView outfoodlist2;
    GridView deliveryfoodlist2;
    JSONObject myinfodata;

    private AdView mAdView; //광고 변수 선언
    private InterstitialAd mInterstitialAd; //광고변수

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        //광고-----------------------------------------------------------------------------------------------------
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //종료시 전면광고-----------------------------------------------------------------------------
        MobileAds.initialize(this,
                "ca-app-pub-8231620186256321~9673217647");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8231620186256321/1155279399");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //광고 끝---------------------------------------------------------------------------------------

        Intent getchangeinfo = getIntent();
        //수정됫는지 안됬는지 확인===================================
        if(getchangeinfo.getIntExtra("Success",0) == 1){
            Toast.makeText(getApplicationContext(), "제휴 및 홍보 정보 수정이 적용되었습니다.",Toast.LENGTH_SHORT).show();
        }
        if(getchangeinfo.getIntExtra("Fail",0) == 1){
            Toast.makeText(getApplicationContext(), "수정에 실패하였습니다. 정확히 요구되는 정보를 입력해주시길 바랍니다.",Toast.LENGTH_SHORT).show();
        }

        //수정된는지 안됬는지 확인 끝 =================================

        flagClass = (FlagClass)getApplication();
        openpartnerinfo = (ImageButton)findViewById(R.id.openpartnerinfo);
        opencookinfo = (ImageButton)findViewById(R.id.opencookinfo);
        openoutinfo = (ImageButton)findViewById(R.id.openoutinfo);
        opendeliveryinfo = (ImageButton)findViewById(R.id.opendeliveryinfo);
        closepartnerinfo = (ImageButton)findViewById(R.id.closepartnerinfo);
        closecookinfo =(ImageButton)findViewById(R.id.closecookinfo);
        closeoutinfo = (ImageButton)findViewById(R.id.closeoutinfo);
        closedeliveryinfo = (ImageButton)findViewById(R.id.closedeliveryinfo);
        myinfowelcome = (TextView)findViewById(R.id.myinfowelcome);
        myemail = (TextView)findViewById(R.id.myemail);
        partnerinfo = (TextView)findViewById(R.id.partnerinfo);
        cookinfo = (TextView)findViewById(R.id.cookinfo);
        outinfo = (TextView)findViewById(R.id.outinfo);
        deliveryinfo = (TextView)findViewById(R.id.deliveryinfo);
        partnerlistlayout = (LinearLayout)findViewById(R.id.partnerlistlayout);
        cookfoodlistlayout = (LinearLayout)findViewById(R.id.cookfoodlistlayout);
        outfoodlistlayout = (LinearLayout)findViewById(R.id.outfoodlistlayout);
        deliveryfoodlistlayout = (LinearLayout)findViewById(R.id.deliveryfoodlistlayout);
        partnerlist = (GridView)findViewById(R.id.partnerlist);
        cookfoodlist1= (GridView)findViewById(R.id.cookfoodlist1);
        outfoodlist1 = (GridView)findViewById(R.id.outfoodlist1);
        deliveryfoodlist1 = (GridView)findViewById(R.id.deliveryfoodlist1);
        cookfoodlist2= (GridView)findViewById(R.id.cookfoodlist2);
        outfoodlist2 = (GridView)findViewById(R.id.outfoodlist2);
        deliveryfoodlist2 = (GridView)findViewById(R.id.deliveryfoodlist2);
        LinearLayout nocontractimagelayout = (LinearLayout) findViewById(R.id.nocontractimagelayout);
        myinfodata = new JSONObject();
        Gridviewadapter2 gridviewadaptercontractstore;
        Gridviewadapter gridviewadapterpartnermeal;
        Gridviewadapter gridviewadapterpartnerdrink;
        Gridviewadapter gridviewadapterhomemeal;
        Gridviewadapter gridviewadapterhomedrink;
        Gridviewadapter gridviewadapteroutmeal;
        Gridviewadapter gridviewadapteroutdrink;
        Gridviewadapter gridviewadapterdeliverymeal;
        Gridviewadapter gridviewadapterdeliverydrink;

        //서버데이터 가져오기=====================================================================
        Thread datathread = new Thread(){
            @Override
            public void run(){
                try {
                    String postParameters = "purpose=getmyinfo&userid="+Long.toString(flagClass.getLoginid());
                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                    Log.d("TAG","myinfo"+url.toString());
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();

                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(postParameters.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();

                    int responseRusultcode = conn.getResponseCode();
                    Log.d(TAG, "POST response code - " + responseRusultcode);

                    InputStream inputStream;
                    if(responseRusultcode == conn.HTTP_OK){
                        inputStream = conn.getInputStream();
                    }else{
                        inputStream = conn.getErrorStream();
                    }

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while((line = bufferedReader.readLine())!= null){
                        sb.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    inputStreamReader.close();

                    Log.d(TAG,"로그인정보확인 : "+sb.toString());

                    myinfodata = new JSONObject(sb.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        datathread.start();
        try {
            datathread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        try {
            myinfowelcome.setText("'"+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("id")+"' 님의 '이거먹자' 페이지 입니다. \nID : "+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("id"));
            myemail.setText(myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("email"));
            partnerinfo.setText("제휴 및 홍보 매장");
            cookinfo.setText("관심요리음식 : "+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("cookfood")+" 가지");
            outinfo.setText("관심외식음식 : "+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("outfood")+" 가지");
            deliveryinfo.setText("관심배달음식 : "+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("deliveryfood")+" 가지");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //서버데이터가져오기 끝===================================================================================================================
//관심음식 열고닫기=================================================================================
        if(flagClass.getMyinforenew() == 1){
            openpartnerinfo.setVisibility(View.GONE);
            partnerlistlayout.setVisibility(View.VISIBLE);
            closepartnerinfo.setVisibility(View.VISIBLE);
        }else if(flagClass.getMyinforenew() ==2){
            opencookinfo.setVisibility(View.GONE);
            cookfoodlistlayout.setVisibility(View.VISIBLE);
            closecookinfo.setVisibility(View.VISIBLE);
        }else if(flagClass.getMyinforenew() ==3){
            openoutinfo.setVisibility(View.GONE);
            outfoodlistlayout.setVisibility(View.VISIBLE);
            closeoutinfo.setVisibility(View.VISIBLE);
        }else if(flagClass.getMyinforenew() ==4){
            opendeliveryinfo.setVisibility(View.GONE);
            deliveryfoodlistlayout.setVisibility(View.VISIBLE);
            closedeliveryinfo.setVisibility(View.VISIBLE);
        }
        flagClass.setMyinforenew(0);
        openpartnerinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(myinfodata.getJSONArray("mycontractinfo").length()>0){
                        openpartnerinfo.setVisibility(View.GONE);
                        partnerlistlayout.setVisibility(View.VISIBLE);
                        closepartnerinfo.setVisibility(View.VISIBLE);
                    }
                    else{
                        nocontractimagelayout.setVisibility(View.VISIBLE);
                        openpartnerinfo.setVisibility(View.GONE);
                        closepartnerinfo.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        opencookinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencookinfo.setVisibility(View.GONE);
                cookfoodlistlayout.setVisibility(View.VISIBLE);
                closecookinfo.setVisibility(View.VISIBLE);
            }
        });
        openoutinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openoutinfo.setVisibility(View.GONE);
                outfoodlistlayout.setVisibility(View.VISIBLE);
                closeoutinfo.setVisibility(View.VISIBLE);
            }
        });
        opendeliveryinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendeliveryinfo.setVisibility(View.GONE);
                deliveryfoodlistlayout.setVisibility(View.VISIBLE);
                closedeliveryinfo.setVisibility(View.VISIBLE);
            }
        });

        closepartnerinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nocontractimagelayout.setVisibility(View.GONE);
                closepartnerinfo.setVisibility(View.GONE);
                partnerlistlayout.setVisibility(View.GONE);
                openpartnerinfo.setVisibility(View.VISIBLE);
            }
        });
        closecookinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closecookinfo.setVisibility(View.GONE);
                cookfoodlistlayout.setVisibility(View.GONE);
                opencookinfo.setVisibility(View.VISIBLE);
            }
        });
        closeoutinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeoutinfo.setVisibility(View.GONE);
                outfoodlistlayout.setVisibility(View.GONE);
                openoutinfo.setVisibility(View.VISIBLE);
            }
        });
        closedeliveryinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closedeliveryinfo.setVisibility(View.GONE);
                deliveryfoodlistlayout.setVisibility(View.GONE);
                opendeliveryinfo.setVisibility(View.VISIBLE);
            }
        });
//관심음식 열고닫기 끝 ================================================================================

//파트너, 관심음식 그리드 표현 ========================================================================================================
        //그리드용 전역 변수 =======================================
        int count = 0;
        JSONArray getindex = new JSONArray();
        //그리드용 전역 변수 끝=======================================
//파트너 음식 정보================================================================
        gridviewadaptercontractstore = new Gridviewadapter2();

        try {
            if(myinfodata.getJSONArray("mycontractinfo").length()>0) {
                for (int i = 0; i < myinfodata.getJSONArray("mycontractinfo").length(); i++) {
                    gridviewadaptercontractstore.addItem(new GriditemContract(myinfodata.getJSONArray("mycontractinfo").getJSONObject(i).getString("membersstorenum")
                            , myinfodata.getJSONArray("mycontractinfo").getJSONObject(i).getString("storename")));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "error for getdata mycontractinfo");
        }
        gridviewadapterpartnermeal = new Gridviewadapter();
        count = flagClass.getThenumberoffoodoutmeal();
        getindex = flagClass.getOutfoodmealindex();
        for(int i = 1; i < count+1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("partnermeal"+i)+getindex.getJSONObject(0).getString(Integer.toString(i))+"count"+gridviewadapterpartnermeal.getCount());
                int check = Integer.parseInt(myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("partnermeal"+i));
                if(check == 1){
                    String url = new String();
                    url = flagClass.getHowcookpage().getJSONObject(0).getString(String.valueOf(i));
                    Log.d(TAG, "lists partner1");
                    gridviewadapterpartnermeal.addItem(new Griditem(0,1,i,getindex.getJSONObject(0).getString(Integer.toString(i)),url));
                    Log.d(TAG, "lists partner1");
                }else{
                    Log.d(TAG, "lists if no!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error get fooddatas");
            }
        }
        gridviewadapterpartnerdrink = new Gridviewadapter();
        count = flagClass.getThenumberoffoodoutdrink();
        getindex = flagClass.getOutfooddrinkindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("partnerdrink"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("partnerdrink"+i));
                if(check == 1){
                    gridviewadapterpartnerdrink.addItem(new Griditem(0,2,i,getindex.getJSONObject(0).getString(Integer.toString(i)),""));
                    Log.d(TAG, "lists partner2");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error get fooddatas");
            }
        }
        //파트너 음식 정보 ================================================================
        gridviewadapterhomemeal = new Gridviewadapter();


        count = flagClass.getThenumberoffoodhomemeal();
        getindex = flagClass.getHomefoodmealindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("1meal"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("1meal"+i));
                if(check == 1){
                    gridviewadapterhomemeal.addItem(new Griditem(1,1,i,getindex.getJSONObject(0).getString(Integer.toString(i)),""));
                    Log.d(TAG, "lists homemeal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error get fooddatas");
            }
        }
        gridviewadapterhomedrink = new Gridviewadapter();
        count = flagClass.getThenumberoffoodhomedrink();
        getindex = flagClass.getHomefooddrinkindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("1drink"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("1drink"+i));
                if(check == 1){
                    gridviewadapterhomedrink.addItem(new Griditem(1,2,i,getindex.getJSONObject(0).getString(Integer.toString(i)),""));
                    Log.d(TAG, "lists homedrink");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error get fooddatas");
            }
        }
        gridviewadapteroutmeal = new Gridviewadapter();
        count = flagClass.getThenumberoffoodoutmeal();
        getindex = flagClass.getOutfoodmealindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("2meal"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("2meal"+i));
                if(check == 1){
                    gridviewadapteroutmeal.addItem(new Griditem(2,1,i,getindex.getJSONObject(0).getString(Integer.toString(i)),""));
                    Log.d(TAG, "lists outmeal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error get fooddatas");
            }
        }
        gridviewadapteroutdrink = new Gridviewadapter();
        count = flagClass.getThenumberoffoodoutdrink();
        getindex = flagClass.getOutfooddrinkindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("2drink"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("2drink"+i));
                if(check == 1){
                    gridviewadapteroutdrink.addItem(new Griditem(2,2,i,getindex.getJSONObject(0).getString(Integer.toString(i)),""));
                    Log.d(TAG, "lists outmeal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error get fooddatas");
            }
        }
        gridviewadapterdeliverymeal = new Gridviewadapter();
        count = flagClass.getThenumberoffooddelivermeal();
        getindex = flagClass.getDeliveryfoodmealindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("3meal"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("3meal"+i));
                if(check == 1){
                    gridviewadapterdeliverymeal.addItem(new Griditem(3,1,i,getindex.getJSONObject(0).getString(Integer.toString(i)),""));
                    Log.d(TAG, "lists deliverymeal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error get fooddatas");
            }
        }
        gridviewadapterdeliverydrink = new Gridviewadapter();
        count = flagClass.getThenumberoffooddeliverdrink();
        getindex = flagClass.getDeliveryfooddrinkindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("3drink"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONArray("myinfo").getJSONObject(0).getString("3drink"+i));
                if(check == 1){
                    gridviewadapterdeliverydrink.addItem(new Griditem(3,2,i,getindex.getJSONObject(0).getString(Integer.toString(i)),""));
                    Log.d(TAG, "lists deliverydrink");

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error get fooddatas");
            }
        }
        partnerlist.setAdapter(gridviewadaptercontractstore);
        cookfoodlist1.setAdapter(gridviewadapterhomemeal);
        cookfoodlist2.setAdapter(gridviewadapterhomedrink);
        outfoodlist1.setAdapter(gridviewadapteroutmeal);
        outfoodlist2.setAdapter(gridviewadapteroutdrink);
        deliveryfoodlist1.setAdapter(gridviewadapterdeliverymeal);
        deliveryfoodlist2.setAdapter(gridviewadapterdeliverydrink);

        partnerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GriditemContract fooditem = (GriditemContract) gridviewadaptercontractstore.getItem(position);
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.myinfo_partner_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.detail:
                                Intent contractstorelist = new Intent(Myinfo.this, Contractstorelistpopup.class);
                                contractstorelist.putExtra("userid", flagClass.getLoginid());
                                contractstorelist.putExtra("storenum", fooditem.getStorenum());
                                contractstorelist.putExtra("storename", fooditem.getStorename());
                                startActivity(contractstorelist);
                                break;
                            case R.id.break_contract:
                                Toast.makeText(getApplication(),"정기 구독(자동결제) 해제 시 후 다음 결제가 미승인시 자동 헤제됩니다.",Toast.LENGTH_LONG).show();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
                Log.d(TAG,"click grid");
            }
        });

        cookfoodlist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Griditem fooditem = (Griditem)gridviewadapterhomemeal.getItem(position);
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.myinfo_cook_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.gorecipe:
                                Intent intent_recipe = new Intent(Intent.ACTION_VIEW);
                                String url = new String();
                                try {
                                    url = flagClass.getHowcookpage().getJSONObject(0).getString(String.valueOf(fooditem.getFoodnum()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                intent_recipe.setData(Uri.parse(url));
                                startActivity(intent_recipe);
                                break;
                            case R.id.delete_interest:
                                AlertDialog.Builder deletecheck = new AlertDialog.Builder(Myinfo.this);
                                deletecheck.setIcon(R.drawable.ic_choicefood);
                                deletecheck.setTitle("식사요리 관심 음식");
                                deletecheck.setMessage("관심음식 목록에서 제거하시겠습니까?");
                                deletecheck.setPositiveButton("제거", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Thread thread = new Thread(){
                                            @Override
                                            public void run(){
                                                try {
                                                    String postParameters = "purpose=deletedata&userid="+flagClass.getLoginid()+"&where="+fooditem.getWhere()+"&kind="+fooditem.getKind()+"&food="+fooditem.getWhere()+"meal"+fooditem.getFoodnum();
                                                    Log.d(TAG,"getdata post : "+postParameters);
                                                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                                                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                    conn.setDoInput(true);
                                                    conn.setDoOutput(true);
                                                    conn.connect();

                                                    OutputStream outputStream = conn.getOutputStream();
                                                    outputStream.write(postParameters.getBytes("UTF-8"));
                                                    outputStream.flush();
                                                    outputStream.close();

                                                    int response = conn.getResponseCode();
                                                    if(response == conn.HTTP_OK){
                                                        Log.d(TAG,"deletedata connected to myinfo");
                                                    }else{
                                                        Log.d(TAG,"deletedata disconnected to myinfo");
                                                    }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        thread.start();
                                        try {
                                            thread.join();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        flagClass.setMyinforenew(2);
                                        Intent intentrenew = getIntent();
                                        finish();
                                        startActivity(intentrenew);

                                    }
                                });
                                deletecheck.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                deletecheck.show();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
        cookfoodlist2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Griditem fooditem = (Griditem)gridviewadapterhomedrink.getItem(position);
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.myinfo_cook_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.gorecipe:
                                Intent intent_recipe = new Intent(Intent.ACTION_VIEW);
                                String url = new String();
                                try {
                                    url = flagClass.getHowcookpagedrink().getJSONObject(0).getString(String.valueOf(fooditem.getFoodnum()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                intent_recipe.setData(Uri.parse(url));
                                startActivity(intent_recipe);
                                break;
                            case R.id.delete_interest:
                                AlertDialog.Builder deletecheck = new AlertDialog.Builder(Myinfo.this);
                                deletecheck.setIcon(R.drawable.ic_choicefood);
                                deletecheck.setTitle("안주요리 관심 음식");
                                deletecheck.setMessage("관심음식 목록에서 제거하시겠습니까?");
                                deletecheck.setPositiveButton("제거", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Thread thread = new Thread(){
                                            @Override
                                            public void run(){
                                                try {
                                                    String postParameters = "purpose=deletedata&userid="+flagClass.getLoginid()+"&where="+fooditem.getWhere()+"&kind="+fooditem.getKind()+"&food="+fooditem.getWhere()+"drink"+fooditem.getFoodnum();
                                                    Log.d(TAG,"getdata post : "+postParameters);
                                                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                                                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                    conn.setDoInput(true);
                                                    conn.setDoOutput(true);
                                                    conn.connect();

                                                    OutputStream outputStream = conn.getOutputStream();
                                                    outputStream.write(postParameters.getBytes("UTF-8"));
                                                    outputStream.flush();
                                                    outputStream.close();

                                                    int response = conn.getResponseCode();
                                                    if(response == conn.HTTP_OK){
                                                        Log.d(TAG,"deletedata connected to myinfo");
                                                    }else{
                                                        Log.d(TAG,"deletedata disconnected to myinfo");
                                                    }


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        thread.start();
                                        try {
                                            thread.join();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        flagClass.setMyinforenew(2);
                                        Intent intentrenew = getIntent();
                                        finish();
                                        startActivity(intentrenew);
                                    }
                                });
                                deletecheck.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                deletecheck.show();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
        outfoodlist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Griditem fooditem = (Griditem)gridviewadapteroutmeal.getItem(position);
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.myinfo_out_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.gomap:
                                flagClass.setOne_food1(fooditem.getFoodnum());
                                flagClass.setKind(1);
                                flagClass.setFindstore(1);
                                Intent intentmap = new Intent(Myinfo.this, mapofstore.class);
                                intentmap.putExtra("scope",1);
                                startActivity(intentmap);
                                break;
                            case R.id.delete_interest:
                                AlertDialog.Builder deletecheck = new AlertDialog.Builder(Myinfo.this);
                                deletecheck.setIcon(R.drawable.ic_choicefood);
                                deletecheck.setTitle("식사외식 관심 음식");
                                deletecheck.setMessage("관심음식 목록에서 제거하시겠습니까?");
                                deletecheck.setPositiveButton("제거", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Thread thread = new Thread(){
                                            @Override
                                            public void run(){
                                                try {
                                                    String postParameters = "purpose=deletedata&userid="+flagClass.getLoginid()+"&where="+fooditem.getWhere()+"&kind="+fooditem.getKind()+"&food="+fooditem.getWhere()+"meal"+fooditem.getFoodnum();
                                                    Log.d(TAG,"getdata post : "+postParameters);
                                                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                                                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                    conn.setDoInput(true);
                                                    conn.setDoOutput(true);
                                                    conn.connect();

                                                    OutputStream outputStream = conn.getOutputStream();
                                                    outputStream.write(postParameters.getBytes("UTF-8"));
                                                    outputStream.flush();
                                                    outputStream.close();

                                                    int response = conn.getResponseCode();
                                                    if(response == conn.HTTP_OK){
                                                        Log.d(TAG,"deletedata connected to myinfo");
                                                    }else{
                                                        Log.d(TAG,"deletedata disconnected to myinfo");
                                                    }


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        thread.start();
                                        try {
                                            thread.join();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        flagClass.setMyinforenew(3);
                                        Intent intentrenew = getIntent();
                                        finish();
                                        startActivity(intentrenew);
                                    }
                                });
                                deletecheck.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                deletecheck.show();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        outfoodlist2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Griditem fooditem = (Griditem)gridviewadapteroutdrink.getItem(position);
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.myinfo_out_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.gomap:
                                flagClass.setOne_food1(fooditem.getFoodnum());
                                flagClass.setKind(2);
                                flagClass.setFindstore(1);
                                Intent intentmap = new Intent(Myinfo.this, mapofstore.class);
                                intentmap.putExtra("scope",1);
                                startActivity(intentmap);
                                break;
                            case R.id.delete_interest:
                                AlertDialog.Builder deletecheck = new AlertDialog.Builder(Myinfo.this);
                                deletecheck.setIcon(R.drawable.ic_choicefood);
                                deletecheck.setTitle("안주외식 관심 음식");
                                deletecheck.setMessage("관심음식 목록에서 제거하시겠습니까?");
                                deletecheck.setPositiveButton("제거", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Thread thread = new Thread(){
                                            @Override
                                            public void run(){
                                                try {
                                                    String postParameters = "purpose=deletedata&userid="+flagClass.getLoginid()+"&where="+fooditem.getWhere()+"&kind="+fooditem.getKind()+"&food="+fooditem.getWhere()+"drink"+fooditem.getFoodnum();
                                                    Log.d(TAG,"getdata post : "+postParameters);
                                                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                                                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                    conn.setDoInput(true);
                                                    conn.setDoOutput(true);
                                                    conn.connect();

                                                    OutputStream outputStream = conn.getOutputStream();
                                                    outputStream.write(postParameters.getBytes("UTF-8"));
                                                    outputStream.flush();
                                                    outputStream.close();

                                                    int response = conn.getResponseCode();
                                                    if(response == conn.HTTP_OK){
                                                        Log.d(TAG,"deletedata connected to myinfo");
                                                    }else{
                                                        Log.d(TAG,"deletedata disconnected to myinfo");
                                                    }


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        thread.start();
                                        try {
                                            thread.join();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        flagClass.setMyinforenew(3);
                                        Intent intentrenew = getIntent();
                                        finish();
                                        startActivity(intentrenew);
                                    }
                                });
                                deletecheck.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                deletecheck.show();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        deliveryfoodlist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Griditem fooditem = (Griditem)gridviewadapterdeliverymeal.getItem(position);
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.myinfo_delivery_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.godelivery:
                                Toast.makeText(getApplication(),"서비스 준비 중 입니다.",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.delete_interest:
                                AlertDialog.Builder deletecheck = new AlertDialog.Builder(Myinfo.this);
                                deletecheck.setIcon(R.drawable.ic_choicefood);
                                deletecheck.setTitle("식사배달 관심 음식");
                                deletecheck.setMessage("관심음식 목록에서 제거하시겠습니까?");
                                deletecheck.setPositiveButton("제거", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Thread thread = new Thread(){
                                            @Override
                                            public void run(){
                                                try {
                                                    String postParameters = "purpose=deletedata&userid="+flagClass.getLoginid()+"&where="+fooditem.getWhere()+"&kind="+fooditem.getKind()+"&food="+fooditem.getWhere()+"meal"+fooditem.getFoodnum();
                                                    Log.d(TAG,"getdata post : "+postParameters);
                                                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                                                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                    conn.setDoInput(true);
                                                    conn.setDoOutput(true);
                                                    conn.connect();

                                                    OutputStream outputStream = conn.getOutputStream();
                                                    outputStream.write(postParameters.getBytes("UTF-8"));
                                                    outputStream.flush();
                                                    outputStream.close();

                                                    int response = conn.getResponseCode();
                                                    if(response == conn.HTTP_OK){
                                                        Log.d(TAG,"deletedata connected to myinfo");
                                                    }else{
                                                        Log.d(TAG,"deletedata disconnected to myinfo");
                                                    }


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        thread.start();
                                        try {
                                            thread.join();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        flagClass.setMyinforenew(4);
                                        Intent intentrenew = getIntent();
                                        finish();
                                        startActivity(intentrenew);
                                    }
                                });
                                deletecheck.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                deletecheck.show();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        deliveryfoodlist2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Griditem fooditem = (Griditem)gridviewadapterdeliverydrink.getItem(position);
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.myinfo_delivery_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.godelivery:
                                Toast.makeText(getApplication(),"서비스 준비 중 입니다.",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.delete_interest:
                                AlertDialog.Builder deletecheck = new AlertDialog.Builder(Myinfo.this);
                                deletecheck.setIcon(R.drawable.ic_choicefood);
                                deletecheck.setTitle("안주배달 관심 음식");
                                deletecheck.setMessage("관심음식 목록에서 제거하시겠습니까?");
                                deletecheck.setPositiveButton("제거", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Thread thread = new Thread(){
                                            @Override
                                            public void run(){
                                                try {
                                                    String postParameters = "purpose=deletedata&userid="+flagClass.getLoginid()+"&where="+fooditem.getWhere()+"&kind="+fooditem.getKind()+"&food="+fooditem.getWhere()+"drink"+fooditem.getFoodnum();
                                                    Log.d(TAG,"getdata post : "+postParameters);
                                                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                                                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                    conn.setDoInput(true);
                                                    conn.setDoOutput(true);
                                                    conn.connect();

                                                    OutputStream outputStream = conn.getOutputStream();
                                                    outputStream.write(postParameters.getBytes("UTF-8"));
                                                    outputStream.flush();
                                                    outputStream.close();

                                                    int response = conn.getResponseCode();
                                                    if(response == conn.HTTP_OK){
                                                        Log.d(TAG,"deletedata connected to myinfo");
                                                    }else{
                                                        Log.d(TAG,"deletedata disconnected to myinfo");
                                                    }


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        thread.start();
                                        try {
                                            thread.join();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        flagClass.setMyinforenew(4);
                                        Intent intentrenew = getIntent();
                                        finish();
                                        startActivity(intentrenew);
                                    }
                                });
                                deletecheck.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                deletecheck.show();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    class Gridviewadapter extends BaseAdapter {
        ArrayList<Griditem> items = new ArrayList<Griditem>();

        public void addItem(Griditem item){
            items.add(item);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Gridsingleview view = null;
            if(convertView == null){
                view = new Gridsingleview(getApplicationContext());
            }else{
                view = (Gridsingleview)convertView;
            }

            Griditem griditem = items.get(position);
            view.init(getApplicationContext());
            view.setfooditem(griditem.getWhere(),griditem.getKind(),griditem.getFoodnum() , griditem.getName(),griditem.getUrl());
            Log.d(TAG,"seeviewgrid,"+position+"," + griditem.getWhere()+","+griditem.getKind()+","+griditem.getName());
            return view;
        }
    }

    class Gridviewadapter2 extends BaseAdapter {
        ArrayList<GriditemContract> items = new ArrayList<GriditemContract>();

        public void addItem(GriditemContract item){
            items.add(item);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            GridsingleviewContract view = null;
            if(convertView == null){
                view = new GridsingleviewContract(getApplicationContext());
            }else{
                view = (GridsingleviewContract) convertView;
            }

            GriditemContract griditem = items.get(position);
            view.init(getApplicationContext());
            view.setstoreitem(griditem.getStorenum(),griditem.getStorename());
            Log.d(TAG,"seeviewgrid,"+position+"," + griditem.getStorename()+","+griditem.getStorenum());
            return view;
        }
    }


}
