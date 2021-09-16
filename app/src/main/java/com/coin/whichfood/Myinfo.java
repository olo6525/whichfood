package com.coin.whichfood;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

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

public class Myinfo extends Activity {

    FlagClass flagClass;
    ImageButton openpartnerinfo;
    ImageButton opencookinfo;
    ImageButton openoutinfo;
    ImageButton opendeliveryinfo;
    TextView myinfowelcome;
    TextView myemail;
    TextView partnerinfo;
    TextView cookinfo;
    TextView outinfo;
    TextView deliveryinfo;
    GridView partnerlist1;
    GridView cookfoodlist1;
    GridView outfoodlist1;
    GridView deliveryfoodlist1;
    GridView partnerlist2;
    GridView cookfoodlist2;
    GridView outfoodlist2;
    GridView deliveryfoodlist2;
    JSONArray myinfodata;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        flagClass = (FlagClass)getApplication();
        openpartnerinfo = (ImageButton)findViewById(R.id.openpartnerinfo);
        opencookinfo = (ImageButton)findViewById(R.id.opencookinfo);
        openoutinfo = (ImageButton)findViewById(R.id.openoutinfo);
        opendeliveryinfo = (ImageButton)findViewById(R.id.opendeliveryinfo);
        myinfowelcome = (TextView)findViewById(R.id.myinfowelcome);
        myemail = (TextView)findViewById(R.id.myemail);
        partnerinfo = (TextView)findViewById(R.id.partnerinfo);
        cookinfo = (TextView)findViewById(R.id.cookinfo);
        outinfo = (TextView)findViewById(R.id.outinfo);
        deliveryinfo = (TextView)findViewById(R.id.deliveryinfo);
        partnerlist1 = (GridView)findViewById(R.id.partnerlist1);
        cookfoodlist1= (GridView)findViewById(R.id.cookfoodlist1);
        outfoodlist1 = (GridView)findViewById(R.id.outfoodlist1);
        deliveryfoodlist1 = (GridView)findViewById(R.id.deliveryfoodlist1);
        partnerlist2 = (GridView)findViewById(R.id.partnerlist2);
        cookfoodlist2= (GridView)findViewById(R.id.cookfoodlist2);
        outfoodlist2 = (GridView)findViewById(R.id.outfoodlist2);
        deliveryfoodlist2 = (GridView)findViewById(R.id.deliveryfoodlist2);
        myinfodata = new JSONArray();
        Gridviewadapter gridviewadapterpartnermeal;
        Gridviewadapter gridviewadapterpartnerdrink;
        Gridviewadapter gridviewadapterhomemeal;
        Gridviewadapter gridviewadapterhomedrink;
        Gridviewadapter gridviewadapteroutmeal;
        Gridviewadapter gridviewadapteroutdrink;
        Gridviewadapter gridviewadapterdeliverymeal;
        Gridviewadapter gridviewadapterdeliverydrink;
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

                    myinfodata = new JSONArray(sb.toString());

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
            myinfowelcome.setText("'"+myinfodata.getJSONObject(0).getString("nickname")+"' 님의 '이거먹자' 페이지 입니다. \nID : "+myinfodata.getJSONObject(0).getString("id"));
            myemail.setText(myinfodata.getJSONObject(0).getString("email"));
            partnerinfo.setText("제휴 및 홍보 음식 : "+myinfodata.getJSONObject(0).getString("partnerships")+" 가지");
            cookinfo.setText("관심요리음식 : "+myinfodata.getJSONObject(0).getString("cookfood")+" 가지");
            outinfo.setText("관심외식음식 : "+myinfodata.getJSONObject(0).getString("outfood")+" 가지");
            deliveryinfo.setText("관심배달음식 : "+myinfodata.getJSONObject(0).getString("deliveryfood")+" 가지");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//파트너, 관심음식 그리드 표현 ========================================================================================================
        //그리드용 전역 변수 =======================================
        int count = 0;
        JSONArray getindex = new JSONArray();
        //그리드용 전역 변수 끝=======================================

        gridviewadapterpartnermeal = new Gridviewadapter();
        count = flagClass.getThenumberoffoodoutmeal();
        getindex = flagClass.getOutfoodmealindex();
        for(int i = 1; i < count+1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONObject(0).getString("partnermeal"+i)+getindex.getJSONObject(0).getString(Integer.toString(i))+"count"+gridviewadapterpartnermeal.getCount());
                int check = Integer.parseInt(myinfodata.getJSONObject(0).getString("partnermeal"+i));
                if(check == 1){
                    Log.d(TAG, "lists partner1");
                    gridviewadapterpartnermeal.addItem(new Griditem(0,1,i,getindex.getJSONObject(0).getString(Integer.toString(i))));
                    Log.d(TAG, "lists partner1");
                }else{
                    Log.d(TAG, "lists if no!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error");
            }
        }
        gridviewadapterpartnerdrink = new Gridviewadapter();
        count = flagClass.getThenumberoffoodoutdrink();
        getindex = flagClass.getOutfooddrinkindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONObject(0).getString("partnerdrink"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONObject(0).getString("partnerdrink"+i));
                if(check == 1){
                    gridviewadapterpartnerdrink.addItem(new Griditem(0,2,i,getindex.getJSONObject(0).getString(Integer.toString(i))));
                    Log.d(TAG, "lists partner2");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error");
            }
        }
        gridviewadapterhomemeal = new Gridviewadapter();
        count = flagClass.getThenumberoffoodhomemeal();
        getindex = flagClass.getHomefoodmealindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONObject(0).getString("1meal"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONObject(0).getString("1meal"+i));
                if(check == 1){
                    gridviewadapterhomemeal.addItem(new Griditem(1,1,i,getindex.getJSONObject(0).getString(Integer.toString(i))));
                    Log.d(TAG, "lists homemeal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error");
            }
        }
        gridviewadapterhomedrink = new Gridviewadapter();
        count = flagClass.getThenumberoffoodhomedrink();
        getindex = flagClass.getHomefooddrinkindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONObject(0).getString("1drink"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONObject(0).getString("1drink"+i));
                if(check == 1){
                    gridviewadapterhomedrink.addItem(new Griditem(1,2,i,getindex.getJSONObject(0).getString(Integer.toString(i))));
                    Log.d(TAG, "lists homedrink");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error");
            }
        }
        gridviewadapteroutmeal = new Gridviewadapter();
        count = flagClass.getThenumberoffoodoutmeal();
        getindex = flagClass.getOutfoodmealindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONObject(0).getString("2meal"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONObject(0).getString("2meal"+i));
                if(check == 1){
                    gridviewadapteroutmeal.addItem(new Griditem(2,1,i,getindex.getJSONObject(0).getString(Integer.toString(i))));
                    Log.d(TAG, "lists outmeal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error");
            }
        }
        gridviewadapteroutdrink = new Gridviewadapter();
        count = flagClass.getThenumberoffoodoutdrink();
        getindex = flagClass.getOutfooddrinkindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONObject(0).getString("2drink"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONObject(0).getString("2drink"+i));
                if(check == 1){
                    gridviewadapteroutdrink.addItem(new Griditem(2,2,i,getindex.getJSONObject(0).getString(Integer.toString(i))));
                    Log.d(TAG, "lists outmeal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error");
            }
        }
        gridviewadapterdeliverymeal = new Gridviewadapter();
        count = flagClass.getThenumberoffooddelivermeal();
        getindex = flagClass.getDeliveryfoodmealindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONObject(0).getString("3meal"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONObject(0).getString("3meal"+i));
                if(check == 1){
                    gridviewadapterdeliverymeal.addItem(new Griditem(3,1,i,getindex.getJSONObject(0).getString(Integer.toString(i))));
                    Log.d(TAG, "lists deliverymeal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error");
            }
        }
        gridviewadapterdeliverydrink = new Gridviewadapter();
        count = flagClass.getThenumberoffooddeliverdrink();
        getindex = flagClass.getDeliveryfooddrinkindex();
        for(int i = 1; i< count + 1; i++){
            try {
                Log.d(TAG, "lists"+myinfodata.getJSONObject(0).getString("3drink"+i)+getindex.getJSONObject(0).getString(Integer.toString(i)));
                int check = Integer.parseInt(myinfodata.getJSONObject(0).getString("3drink"+i));
                if(check == 1){
                    gridviewadapterdeliverydrink.addItem(new Griditem(3,2,i,getindex.getJSONObject(0).getString(Integer.toString(i))));
                    Log.d(TAG, "lists deliverydrink");

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error");
            }
        }
        partnerlist1.setAdapter(gridviewadapterpartnermeal);
        partnerlist2.setAdapter(gridviewadapterpartnerdrink);
        cookfoodlist1.setAdapter(gridviewadapterhomemeal);
        cookfoodlist2.setAdapter(gridviewadapterhomedrink);
        outfoodlist1.setAdapter(gridviewadapteroutmeal);
        outfoodlist2.setAdapter(gridviewadapteroutdrink);
        deliveryfoodlist1.setAdapter(gridviewadapterdeliverymeal);
        deliveryfoodlist2.setAdapter(gridviewadapterdeliverydrink);
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
            view.setfooditem(griditem.getWhere(),griditem.getKind(),griditem.getFoodnum() , griditem.getName());
            Log.d(TAG,"seeviewgrid,"+position+"," + griditem.getWhere()+","+griditem.getKind()+","+griditem.getName());
            return view;
        }
    }

}
