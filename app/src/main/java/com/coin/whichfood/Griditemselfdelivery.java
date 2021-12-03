package com.coin.whichfood;

public class Griditemselfdelivery {

    String storename;
    String storenumber;
    String storepage;

    public Griditemselfdelivery(String storename, String storenumber, String storepage){
        this.storename = storename;
        this.storenumber = storenumber;
        this.storepage = storepage;
    }

    public String getStorename(){return storename;}
    public String getStorenumber(){return storenumber;}
    public String getStorepage(){return storepage;}
}
