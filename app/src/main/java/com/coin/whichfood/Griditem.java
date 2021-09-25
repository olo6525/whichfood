package com.coin.whichfood;

public class Griditem {
    String name;
    int foodnum;
    int where;
    int kind;
    String url;

    public Griditem(int where, int kind, int foodnum, String name, String url){
        this.where = where;
        this.kind = kind;
        this.name = name;
        this.foodnum = foodnum;
        this.url = url;
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
    public String getUrl(){return url;}

}
