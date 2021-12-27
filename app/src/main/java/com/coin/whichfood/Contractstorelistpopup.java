package com.coin.whichfood;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class Contractstorelistpopup extends Activity {

    FlagClass flagClass;
    JSONObject jsoncontractstorefoodlist;
    Lodingclass lodingclass;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.contractstorefoodlist);
        lodingclass = new Lodingclass(this);
        lodingclass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        flagClass = (FlagClass)getApplication();
        jsoncontractstorefoodlist = new JSONObject();
        GridView contractfoodlist = (GridView)findViewById(R.id.contractfoodlist);
        Gridviewadapter gridviewadaptercontractfoodlist = new Gridviewadapter();
        Intent getinfo = getIntent();
        long userid = getinfo.getLongExtra("userid",0);
        String storenum = getinfo.getStringExtra("storenum");
        String storename = getinfo.getStringExtra("storename");
        Log.d(TAG,"제휴가게받은데이타 :"+userid +","+ storenum +","+ storename);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    String postParameters = "purpose=contractstorefoodlist&userid=" + userid +"&storenum="+storenum;
                    URL url = new URL(flagClass.getServers().get(0) + "whichfoodstorelist.php");
                    Log.d("TAG", "parameter: " +postParameters);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
                    if (responseRusultcode == conn.HTTP_OK) {
                        inputStream = conn.getInputStream();
                    } else {
                        inputStream = conn.getErrorStream();
                    }

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    inputStreamReader.close();

                    Log.d(TAG, "제휴가게데이타 정보 확인 : " + sb.toString());

                    jsoncontractstorefoodlist = new JSONObject(sb.toString());

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
        try {
            Log.d(TAG,"제휴가게음식리스트 : "+jsoncontractstorefoodlist.getJSONArray("foodlist").length());
            if(jsoncontractstorefoodlist.getJSONArray("foodlist").length() > 0) {
                ImageView nocontract = (ImageView)findViewById(R.id.nocontract);
                nocontract.setVisibility(View.GONE);
                for (int i = 0; i < jsoncontractstorefoodlist.getJSONArray("foodlist").length(); i++) {
                    String kindfoodnumlist = jsoncontractstorefoodlist.getJSONArray("foodlist").getJSONObject(i).getString("foodnum");
                    String onlyfoodnum = kindfoodnumlist.replaceAll("[^0-9]", "");
                    int foodnumlist = Integer.parseInt(onlyfoodnum);
                    String contractfoodname = new String();
                    if (kindfoodnumlist.charAt(0) == 'm') {
                        contractfoodname = flagClass.getOutfoodmealindex().getJSONObject(0).getString(onlyfoodnum);
                    } else if (kindfoodnumlist.charAt(0) == 'd') {
                        contractfoodname = flagClass.getOutfooddrinkindex().getJSONObject(0).getString(onlyfoodnum);
                    } else {
                        contractfoodname = "";
                    }
                    Log.d(TAG, "제휴가게데이타음식 인덱스 : " + contractfoodname);
                    //인자설명 -> 위치, 종류, 음식인덱스, 종류임식인덱스, 음식이름
                    gridviewadaptercontractfoodlist.addItem(new GriditemContractstorefood(2, 0, foodnumlist, kindfoodnumlist, contractfoodname, storenum));
                }
            }else{

            }
        }catch (Exception e){
            ImageView nocontract = (ImageView)findViewById(R.id.nocontract);
            nocontract.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
        contractfoodlist.setAdapter(gridviewadaptercontractfoodlist);

        contractfoodlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GriditemContractstorefood item = (GriditemContractstorefood)gridviewadaptercontractfoodlist.getItem(position);

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.myinfo_partner_contractdetail,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.watch:
                                Intent adintent = new Intent(Contractstorelistpopup.this, ShowStoreAd.class);
                                try {
                                    adintent.putExtra("path", flagClass.getServers().get(0) + "whichfoodadimages/" + item.getStorenum() + "/" + item.getKindfoodnum() + "/");
                                    adintent.putExtra("storename", storename);
                                    adintent.putExtra("storeaddress", jsoncontractstorefoodlist.getJSONArray("storeaddress").getJSONObject(0).getString("storeaddress"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                startActivity(adintent);
                                break;
                            case R.id.change:
                                Intent changeregister = new Intent(Contractstorelistpopup.this, Registerpartnerchange.class);
                                changeregister.putExtra("storenum",item.getStorenum());
                                changeregister.putExtra("kindfoodnum",item.getKindfoodnum());
                                changeregister.putExtra("foodnum",item.getFoodnum());
                                changeregister.putExtra("foodname", item.getfoodName());
                                startActivity(changeregister);
                                break;
                            case R.id.delete:
                                AlertDialog.Builder dlg = new AlertDialog.Builder(Contractstorelistpopup.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                                dlg.setMessage("해달 음식에 대한 제휴 및 홍보를 해제 하시겠습니까?")
                                        .setTitle("제휴 및 홍보 해제")
                                        .setPositiveButton("해제", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                lodingclass.show();
                                                lodingclass.setCanceledOnTouchOutside(false);
                                                lodingclass.setCancelable(false);
                                                deletepartnership(storenum, item.getKindfoodnum());
                                                finish();//인텐트 종료
                                                overridePendingTransition(0, 0);//인텐트 효과 없애기
                                                Intent intent = getIntent(); //인텐트
                                                startActivity(intent); //액티비티 열기
                                                overridePendingTransition(0, 0);//인텐트 효과 없애기
                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();

                        }
                        return false;
                    }
                });
                popupMenu.show();
                Log.d(TAG,"click grid");

            }
        });
    }


    class Gridviewadapter extends BaseAdapter {
        ArrayList<GriditemContractstorefood> items = new ArrayList<GriditemContractstorefood>();

        public void addItem(GriditemContractstorefood item){
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

            GridsingleviewContractstorefood view = null;
            if(convertView == null){
                view = new GridsingleviewContractstorefood(getApplicationContext());
            }else{
                view = (GridsingleviewContractstorefood) convertView;
            }

            GriditemContractstorefood griditem = items.get(position);
            view.init(getApplicationContext());
            view.setfooditem(griditem.getWhere(),griditem.getKind(),griditem.getFoodnum() , griditem.getfoodName());
            Log.d(TAG,"seeviewgrid,"+position+"," + griditem.getWhere()+","+griditem.getKind()+","+griditem.getfoodName());
            return view;
        }


    }



    public void deletepartnership(String storenum, String foodnum){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    String parameter = new String();
                    parameter = "purpose=removepartner&userid="+flagClass.getLoginid()+"&storenum="+storenum+"&foodnum="+foodnum;
                    Log.d("TAG", "bii param:" + parameter);
                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();

                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(parameter.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();

                    InputStream inputStream;
                    if(conn.getResponseCode() == conn.HTTP_OK){
                        inputStream = conn.getInputStream();
                    }else{
                        inputStream = conn.getErrorStream();
                    }

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while((line = bufferedReader.readLine()) != null){
                        sb.append(line);
                    }

                    bufferedReader.close();
                    inputStreamReader.close();
                    inputStream.close();

                    Log.d(TAG,"bii 취소삭제 확인 : "+sb.toString());

                    lodingclass.cancel();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
