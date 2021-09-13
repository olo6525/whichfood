package com.coin.whichfood;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;


import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.model.ApprovalType;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FlagClass extends Application {

    private static String version;
    private static int where;
    private static int kind;
    private static int what;
    private static String one_where;
    private static String one_kind;
    private static int one_food1;
    private static int one_food1_before;
    private static int one_food2;
    private static int one_food2_before;
    private static int findstore;
    private static int thenumberoffoodhomemeal;
    private static int thenumberoffoodhomedrink;
    private static int thenumberoffoodoutmeal;
    private static int thenumberoffoodoutdrink;
    private static int thenumberoffooddelivermeal;
    private static int thenumberoffooddeliverdrink;
    private JSONArray howcookpage;
    private JSONArray howcookpagedrink;
    private JSONArray homefoodmealindex;
    private JSONArray homefooddrinkindex;
    private JSONArray outfoodmealindex;
    private JSONArray outfooddrinkindex;
    private JSONArray deliveryfoodmealindex;
    private JSONArray deliveryfooddrinkindex;
    private static Bitmap adimage1;
    private static Bitmap adimage2;
    private static Bitmap adimage3;
    private static Bitmap adimage4;
    private static Bitmap adimage5;
    private static ArrayList<String> servers;


    //카카오톡로그인연동
    private static FlagClass instance;
    private static int loginflag;
    private static long loginid;

    public static FlagClass getFalgClassContext() throws IllegalAccessException {
        if(instance == null){
            throw new IllegalAccessException("This Application does not inherit com.kakao");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        version = "";
        where = 0;
        kind = 0;
        what = 0;
        one_where="";
        one_kind="";
        one_food1 = 0;
        one_food1_before =0;
        one_food2 = 0;
        one_food2_before =0;
        findstore = 0;
        thenumberoffoodhomemeal=0;
        thenumberoffoodhomedrink=0;
        thenumberoffoodoutmeal=0;
        thenumberoffoodoutdrink=0;
        thenumberoffooddelivermeal=0;
        thenumberoffooddeliverdrink=0;
        howcookpage = new JSONArray();
        howcookpagedrink = new JSONArray();
        homefoodmealindex = new JSONArray();
        homefooddrinkindex = new JSONArray();
        outfoodmealindex = new JSONArray();
        outfooddrinkindex = new JSONArray();
        deliveryfoodmealindex = new JSONArray();
        deliveryfooddrinkindex = new JSONArray();
        adimage1 = null;
        adimage2 = null;
        adimage3 = null;
        adimage4 = null;
        adimage5 = null;
        servers = new ArrayList<>();
//카카오톡 로그인 연동
        instance = this;
        loginflag = 0;
        KakaoSdk.init(this, "675ccfa4872a4eb6e1be8a61059dc307");
        long loginid =0;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

    public void Init() {
        version = "";
        where = 0;
        kind = 0;
        what = 0;
        one_where="";
        one_kind="";
        one_food1 = 0;
        one_food1_before =0;
        one_food2 = 0;
        one_food2_before =0;
        findstore = 0;
        thenumberoffoodhomemeal=0;
        thenumberoffoodhomedrink=0;
        thenumberoffoodoutmeal=0;
        thenumberoffoodoutdrink=0;
        thenumberoffooddelivermeal=0;
        thenumberoffooddeliverdrink=0;
        howcookpage = new JSONArray();
        howcookpagedrink = new JSONArray();
        homefoodmealindex = new JSONArray();
        homefooddrinkindex = new JSONArray();
        outfoodmealindex = new JSONArray();
        outfooddrinkindex = new JSONArray();
        deliveryfoodmealindex = new JSONArray();
        deliveryfooddrinkindex = new JSONArray();
        adimage1 = null;
        adimage2 = null;
        adimage3 = null;
        adimage4 = null;
        adimage5 = null;
        servers = new ArrayList<>();

        //카카오톡 로그인
        loginflag = 0;
        long loginid =0;

    }

    public void setVersion(String flag){this.version = flag;}
    public void setWhere(int flag){this.where = flag;}
    public void setKind(int flag) {this.kind = flag;}
    public void setWhat(int flag) {this.what = flag;}
    public void setOne_where(String flag) {this.one_where = flag;}
    public void setOne_kind(String flag) {this.one_kind = flag;}
    public void setOne_food1(int flag) {this.one_food1 = flag;}
    public void setOne_food1_before(int flag) {this.one_food1_before = flag;}
    public void setOne_food2(int flag) {this.one_food2 =flag;}
    public void setOne_food2_before(int flag) {this.one_food2_before = flag;}
    public void setFindstore(int flag) {this.findstore=flag;}
    public void setThenumberoffoodhomemeal(int flag) {this.thenumberoffoodhomemeal = flag;}
    public void setThenumberoffoodhomedrink(int flag) {this.thenumberoffoodhomedrink = flag;}
    public void setThenumberoffoodoutmeal(int flag) {this.thenumberoffoodoutmeal = flag;}
    public void setThenumberoffoodoutdrink(int flag) {this.thenumberoffoodoutdrink = flag;}
    public void setThenumberoffooddelivermeal(int flag) {this.thenumberoffooddelivermeal = flag;}
    public void setThenumberoffooddeliverdrink(int flag) {this.thenumberoffooddeliverdrink = flag;}
    public void setHowcookpage(JSONArray flag) {this.howcookpage = flag;}
    public void setHowcookpagedrink(JSONArray flag) {this.howcookpagedrink = flag;}
    public void setHomefoodmealindex(JSONArray flag) {this.homefoodmealindex = flag;}
    public void setHomefooddrinkindex(JSONArray flag) {this.homefooddrinkindex = flag;}
    public void setOutfoodmealindex(JSONArray flag) {this.outfoodmealindex = flag;}
    public void setOutfooddrinkindex(JSONArray flag) {this.outfooddrinkindex = flag;}
    public void setDeliveryfoodmealindex(JSONArray flag) {this.deliveryfoodmealindex = flag;}
    public void setDeliveryfooddrinkindex(JSONArray flag) {this.deliveryfooddrinkindex = flag;}
    public void setLoginflag(int flag) { this.loginflag = flag;}
    public void setAdimage1(Bitmap flag){this.adimage1 = flag;}
    public void setAdimage2(Bitmap flag){this.adimage2 = flag;}
    public void setAdimage3(Bitmap flag){this.adimage3 = flag;}
    public void setAdimage4(Bitmap flag){this.adimage4 = flag;}
    public void setAdimage5(Bitmap flag){this.adimage5 = flag;}
    public void setServers(ArrayList<String> flag){this.servers = flag;}
    public void setLoginid(long flag){this.loginid = flag;}

    public String getVersion(){return version;}
    public int getWhere() {return where;}
    public int getKind() {return kind;}
    public int getWhat() {return what;}
    public String getOne_where() {return one_where;}
    public String getOne_kind() {return one_kind;}
    public int getOne_food1() { return one_food1;}
    public int getOne_food1_before() { return one_food1_before;}
    public int getOne_food2() {return one_food2;}
    public int getOne_food2_before() { return one_food2_before;}
    public int getFindstore(){return findstore;}
    public int getThenumberoffoodhomemeal(){return thenumberoffoodhomemeal;}
    public int getThenumberoffoodhomedrink(){return thenumberoffoodhomedrink;}
    public int getThenumberoffoodoutmeal(){return thenumberoffoodoutmeal;}
    public int getThenumberoffoodoutdrink(){return thenumberoffoodoutdrink;}
    public int getThenumberoffooddelivermeal(){return thenumberoffooddelivermeal;}
    public int getThenumberoffooddeliverdrink(){return thenumberoffooddeliverdrink;}
    public JSONArray getHowcookpage(){ return howcookpage;}
    public JSONArray getHowcookpagedrink(){ return howcookpagedrink;}
    public JSONArray getHomefoodmealindex(){return homefoodmealindex;}
    public JSONArray getHomefooddrinkindex(){return homefooddrinkindex;}
    public JSONArray getOutfoodmealindex(){return outfoodmealindex;}
    public JSONArray getOutfooddrinkindex(){return outfooddrinkindex;}
    public JSONArray getDeliveryfoodmealindex(){return deliveryfoodmealindex;}
    public JSONArray getDeliveryfooddrinkindex(){return deliveryfooddrinkindex;}
    public int getLoginflag(){return loginflag;}
    public Bitmap getAdimage1(){return adimage1;}
    public Bitmap getAdimage2(){return adimage2;}
    public Bitmap getAdimage3(){return adimage3;}
    public Bitmap getAdimage4(){return adimage4;}
    public Bitmap getAdimage5(){return adimage5;}
    public ArrayList<String> getServers(){return servers;}
    public long getLoginid(){return loginid;}





}
