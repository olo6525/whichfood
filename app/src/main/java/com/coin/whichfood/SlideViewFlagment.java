package com.coin.whichfood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SlideViewFlagment extends Fragment {

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.

    private Bitmap adimage;
    ImageView adimages;
    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();
    TextView storename;
    TextView storeaddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup adview = (ViewGroup) inflater.inflate(R.layout.slidepages, container, false);
        adimages = (ImageView)adview.findViewById(R.id.adimage);
        Bundle args = getArguments();
        Log.d(TAG,"adimagepath  : "+args.getStringArrayList("path").get(0));
        images = args.getStringArrayList("path");
        storename = (TextView)adview.findViewById(R.id.storename);
        storeaddress = (TextView)adview.findViewById(R.id.storeaddress);
        Thread runnablthread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(images.get(args.getInt("page")));
                    Log.d(TAG,"adimage line1");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    Log.d(TAG,"adimage line2");
                    conn.connect();
                    Log.d(TAG, "adimage line3");
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Log.d(TAG, "adimage" + bitmap);
                    bitmapArrayList.add(bitmap);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        runnablthread.start();
        try {
            runnablthread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(args.getInt("page") == 0 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));
            adimages.setImageBitmap(bitmapArrayList.get(0));
            bitmapArrayList.clear();
        }else if (args.getInt("page") == 1 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));
            adimages.setImageBitmap(bitmapArrayList.get(0));
            bitmapArrayList.clear();
        }else if (args.getInt("page") == 2 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));
            adimages.setImageBitmap(bitmapArrayList.get(0));
            bitmapArrayList.clear();
        }else if (args.getInt("page") == 3 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));
            adimages.setImageBitmap(bitmapArrayList.get(0));
            bitmapArrayList.clear();
        }else if (args.getInt("page")== 4 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));
            adimages.setImageBitmap(bitmapArrayList.get(0));
            bitmapArrayList.clear();
        }


        return adview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



    }

//===========================================================================================================================================
//===========================================================================================================================================
//===========================================================================================================================================

    //===========================================================================================================================================
    //===========================================================================================================================================
    //===========================================================================================================================================
}

