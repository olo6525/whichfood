package com.coin.whichfood;

public class Griditem {
    String name;
    int foodnum;
    int where;
    int kind;

    public Griditem(int where, int kind, int foodnum, String name){
        this.where = where;
        this.kind = kind;
        this.name = name;
        this.foodnum = foodnum;
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

}
