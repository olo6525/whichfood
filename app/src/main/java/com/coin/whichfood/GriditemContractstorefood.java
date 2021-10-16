package com.coin.whichfood;

public class GriditemContractstorefood {

    String name;
    int foodnum;
    String kindfoodnum;
    int where;
    int kind;
    String storenum;

    public GriditemContractstorefood(int where, int kind, int foodnum, String kindfoodnum, String name, String storenum){
        this.where = where;
        this.kind = kind;
        this.name = name;
        this.foodnum = foodnum;
        this.kindfoodnum = kindfoodnum;
        this.storenum = storenum;
    }
    public String getName(){
        return name;
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
