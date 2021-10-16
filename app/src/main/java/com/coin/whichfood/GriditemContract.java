package com.coin.whichfood;

public class GriditemContract {

    String storenum;
    String storename;

    public GriditemContract(String storenum, String storename){
        this.storename = storename;
        this.storenum = storenum;
    }

    public String getStorenum() { return storenum;}
    public String getStorename(){return storename;}
}
