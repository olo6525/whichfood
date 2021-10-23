package com.coin.whichfood;

public class GriditemContractstorefood {

    String foodname;
    int foodnum;
    String kindfoodnum;
    int where;
    int kind;
    String storenum;

    public GriditemContractstorefood(int where, int kind, int foodnum, String kindfoodnum, String foodname, String storenum){
        this.where = where;
        this.kind = kind;
        this.foodname = foodname;
        this.foodnum = foodnum;
        this.kindfoodnum = kindfoodnum;
        this.storenum = storenum;
    }
    public String getfoodName(){
        return foodname;
    }
    public int getFoodnum(){
        return foodnum;
    }
    public int getWhere(){
        return where;
    }
    public int getKind(){
        return kind;
    }
    public String getKindfoodnum(){return kindfoodnum;}
    public String getStorenum(){return storenum;}
}
