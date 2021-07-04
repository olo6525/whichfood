package com.coin.whichfood;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.model.ApprovalType;


import org.json.JSONArray;
import org.json.JSONObject;

public class FlagClass extends Application {

    private static int where;
    private static int kind;
    private static int what;
    private static String one_where;
    private static String one_kind;
    private static int one_food1;
    private static int one_food2;
    private static int findstore;
    private static int thenumberoffoodhomemeal;
    private static int thenumberoffoodhomedrink;
    private static int thenumberoffoodoutmeal;
    private static int thenumberoffoodoutdrink;
    private static int thenumberoffooddelivermeal;
    private static int thenumberoffooddeliverdrink;
    private JSONArray howcookpage;
    private JSONArray howcookpagedrink;


    //카카오톡로그인연동
    private static FlagClass instance;
    private static int loginflag;

    public static FlagClass getFalgClassContext() throws IllegalAccessException {
        if(instance == null){
            throw new IllegalAccessException("This Application does not inherit com.kakao");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        where = 0;
        kind = 0;
        what = 0;
        one_where="";
        one_kind="";
        one_food1 = 0;
        one_food2 = 0;
        findstore = 0;
        thenumberoffoodhomemeal=0;
        thenumberoffoodhomedrink=0;
        thenumberoffoodoutmeal=0;
        thenumberoffoodoutdrink=0;
        thenumberoffooddelivermeal=0;
        thenumberoffooddeliverdrink=0;
        howcookpage = new JSONArray();
        howcookpagedrink = new JSONArray();
//카카오톡 로그인 연동
        instance = this;
        loginflag = 0;
        KakaoSdk.init(this, "675ccfa4872a4eb6e1be8a61059dc307");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

    public void Init() {
        where = 0;
        kind = 0;
        what = 0;
        one_where="";
        one_kind="";
        one_food1 = 0;
        one_food2 = 0;
        findstore = 0;
        thenumberoffoodhomemeal=0;
        thenumberoffoodhomedrink=0;
        thenumberoffoodoutmeal=0;
        thenumberoffoodoutdrink=0;
        thenumberoffooddelivermeal=0;
        thenumberoffooddeliverdrink=0;
        howcookpage = new JSONArray();
        howcookpagedrink = new JSONArray();
        loginflag = 0;
    }

    public void setWhere(int flag){this.where = flag;}
    public void setKind(int flag) {this.kind = flag;}
    public void setWhat(int flag) {this.what = flag;}
    public void setOne_where(String flag) {this.one_where = flag;}
    public void setOne_kind(String flag) {this.one_kind = flag;}
    public void setOne_food1(int flag) {this.one_food1 = flag;}
    public void setOne_food2(int flag) {this.one_food2 =flag;}
    public void setFindstore(int flag) {this.findstore=flag;}
    public void setThenumberoffoodhomemeal(int flag) {this.thenumberoffoodhomemeal = flag;}
    public void setThenumberoffoodhomedrink(int flag) {this.thenumberoffoodhomedrink = flag;}
    public void setThenumberoffoodoutmeal(int flag) {this.thenumberoffoodoutmeal = flag;}
    public void setThenumberoffoodoutdrink(int flag) {this.thenumberoffoodoutdrink = flag;}
    public void setThenumberoffooddelivermeal(int flag) {this.thenumberoffooddelivermeal = flag;}
    public void setThenumberoffooddeliverdrink(int flag) {this.thenumberoffooddeliverdrink = flag;}
    public void setHowcookpage(JSONArray flag) {this.howcookpage = flag;}
    public void setHowcookpagedrink(JSONArray flag) {this.howcookpagedrink = flag;}
    public void setLoginflag(int flag) { this.loginflag = flag;}

    public int getWhere() {return where;}
    public int getKind() {return kind;}
    public int getWhat() {return what;}
    public String getOne_where() {return one_where;}
    public String getOne_kind() {return one_kind;}
    public int getOne_food1() { return one_food1;}
    public int getOne_food2() {return one_food2;}
    public int getFindstore(){return findstore;}
    public int getThenumberoffoodhomemeal(){return thenumberoffoodhomemeal;}
    public int getThenumberoffoodhomedrink(){return thenumberoffoodhomedrink;}
    public int getThenumberoffoodoutmeal(){return thenumberoffoodoutmeal;}
    public int getThenumberoffoodoutdrink(){return thenumberoffoodoutdrink;}
    public int getThenumberoffooddelivermeal(){return thenumberoffooddelivermeal;}
    public int getThenumberoffooddeliverdrink(){return thenumberoffooddeliverdrink;}
    public JSONArray getHowcookpage(){ return howcookpage;}
    public JSONArray getHowcookpagedrink(){ return howcookpagedrink;}
    public int getLoginflag(){return loginflag;}





}
