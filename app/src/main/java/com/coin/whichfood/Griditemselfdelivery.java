package com.coin.whichfood;

public class Griditemselfdelivery {

    String storename;
    String storenumber;
    String storepage;
    String storeaddress;
    String distance;
    String introduce;

    public Griditemselfdelivery(String storename,String storeaddress, String storenumber, String storepage, String distance, String introduce){
        this.storename = storename;
        this.storeaddress = storeaddress;
        this.storenumber = storenumber;
        this.storepage = storepage;
        this.distance = distance;
        this.introduce = introduce;
    }

    public String getStorename(){return storename;}
    public String getStorenumber(){return storenumber;}
    public String getStorepage(){return storepage;}
    public String getStoreaddress(){return storeaddress;}
    public String getDistance(){return distance; }
    public String getIntroduce(){return introduce;}
}
