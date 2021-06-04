package com.coin.whichfood;

import android.app.Application;

public class FlagClass extends Application {

    private static int where;
    private static int kind;
    private static int what;
    private static String one_where;
    private static String one_kind;
    private static int one_food1;
    private static int one_food2;
    private static int findstore;

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
    }

    @Override
    public void onTerminate() {super.onTerminate();}

    public void Init() {
        where = 0;
        kind = 0;
        what = 0;
        one_where="";
        one_kind="";
        one_food1 = 0;
        one_food2 = 0;
        findstore = 0;
    }

    public void setWhere(int flag){this.where = flag;}
    public void setKind(int flag) {this.kind = flag;}
    public void setWhat(int flag) {this.what = flag;}
    public void setOne_where(String flag) {this.one_where = flag;}
    public void setOne_kind(String flag) {this.one_kind = flag;}
    public void setOne_food1(int flag) {this.one_food1 = flag;}
    public void setOne_food2(int flag) {this.one_food2 =flag;}
    public void setFindstore(int flag) {this.findstore=flag;}

    public int getWhere() {return where;}
    public int getKind() {return kind;}
    public int getWhat() {return what;}
    public String getOne_where() {return one_where;}
    public String getOne_kind() {return one_kind;}
    public int getOne_food1() { return one_food1;}
    public int getOne_food2() {return one_food2;}
    public int getFindstore(){return findstore;}

}
