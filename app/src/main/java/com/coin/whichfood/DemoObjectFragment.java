package com.coin.whichfood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

// Instances of this class are fragments representing a single
// object in our collection.
public class DemoObjectFragment extends Fragment {

    private Bitmap adimage;
    ImageView adimages;
    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup adview = (ViewGroup)inflater.inflate(R.layout.slidepages, container,false);
        Bundle args = getArguments();
        Log.d(TAG,"adimagepath  : "+args.getString("path"));
        Thread runnablthread = new Thread(){

            @Override
            public void run() {

                try{
                    for(int i = 0 ; i < 5; i++) {
                        URL url = new URL(args.getString("path"));
                        Log.d(TAG,"adimage line1");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        Log.d(TAG,"adimage line2");
                        conn.connect();
                        Log.d(TAG,"adimage line3");
                        InputStream is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        Log.d(TAG,"adimage"+bitmap);
                        bitmapArrayList.add(bitmap);

                    }


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


        if(args.getInt("page") == 0 ){
            adimages.setImageBitmap(bitmapArrayList.get(0));
        }else if (args.getInt("page") == 1){
            adimages.setImageBitmap(bitmapArrayList.get(1));
        }else if (args.getInt("page") == 2){
            adimages.setImageBitmap(bitmapArrayList.get(2));
        }else if (args.getInt("page") == 3){
            adimages.setImageBitmap(bitmapArrayList.get(3));
        }else if (args.getInt("page")==4){
            adimages.setImageBitmap(bitmapArrayList.get(4));
        }






        return adview;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {





    }



}
