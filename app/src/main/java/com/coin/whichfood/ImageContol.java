package com.coin.whichfood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ImageContol {


    public Bitmap getImage(String image){
        if(image == null){
            return null;
        }else {
            Bitmap getblob = BitmapFactory.decodeByteArray(image.getBytes(),0,image.getBytes().length);
//
            return getblob;
        }

    }

    public Bitmap getImagetolayout(String image, LinearLayout ll){
        if(image == null){
            return null;
        }else {
            Bitmap getblob = BitmapFactory.decodeByteArray(image.getBytes(),0,image.getBytes().length);
            if(getblob.getWidth() > ll.getWidth()){
                getblob.setHeight(ll.getWidth()*getblob.getHeight()/getblob.getWidth());
                getblob.setWidth(ll.getWidth());
            }
            return getblob;
        }

    }
    public Bitmap getImagebyByte(byte[] image){
        if(image == null){
            return null;
        }else {
            Bitmap getblob = BitmapFactory.decodeByteArray(image,0,image.length);
            return getblob;
        }

    }

    public Bitmap getImagebyBytetolayout(byte[] image, LinearLayout ll){
        if(image == null){
            return null;
        }else {
            Bitmap getblob = BitmapFactory.decodeByteArray(image,0,image.length);
            if(getblob.getWidth() > ll.getWidth()){
                getblob.setHeight(ll.getWidth()*getblob.getHeight()/getblob.getWidth());
                getblob.setWidth(ll.getWidth());
            }
            return getblob;
        }

    }




}
