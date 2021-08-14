package com.coin.whichfood;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class foodnutrientclass extends Activity {

    private String s_where = "";
    private String s_kind = "";
    private String one_food = "";
    ImageView foodnutrient;
    Bitmap bitimage1;
    GetNutrientImage getNutrientImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodnutrient);
        foodnutrient=(ImageView)findViewById(R.id.foodnutrient);
        final FlagClass flagClass = (FlagClass)getApplication();
        getNutrientImage = new GetNutrientImage();
        if(flagClass.getWhere() == 1){
            s_where = "homefoodnutrient";
            if(flagClass.getKind() == 1){
                s_kind = "meal";
                if(flagClass.getWhat() == 1){
                    one_food = String.valueOf(flagClass.getOne_food1());
                }else{
                    one_food = String.valueOf(flagClass.getOne_food2());
                }
            }else if(flagClass.getKind() ==2){
                s_kind="drink";
                if(flagClass.getWhat() == 1){
                    one_food = String.valueOf(flagClass.getOne_food1());
                }else{
                    one_food = String.valueOf(flagClass.getOne_food2());
                }
            }else{
                s_kind="";
            }
            Log.d(TAG,"datanutrient"+s_where+","+s_kind+","+one_food);
            getNutrientImage.execute(s_where,s_kind,one_food);

        }else if(flagClass.getWhere() == 2){
            s_where = "outfoodnutrient";
            if(flagClass.getKind() == 1){
                s_kind = "meal";
                if(flagClass.getWhat() == 1){
                    one_food = String.valueOf(flagClass.getOne_food1());
                }else{
                    one_food = String.valueOf(flagClass.getOne_food2());
                }
            }else if(flagClass.getKind() ==2){
                s_kind="drink";
                if(flagClass.getWhat() == 1){
                    one_food = String.valueOf(flagClass.getOne_food1());
                }else{
                    one_food = String.valueOf(flagClass.getOne_food2());
                }
            }
            getNutrientImage.execute(s_where,s_kind,one_food);

        }else if(flagClass.getWhere() == 3){
            s_where = "deliverfoodnutrient";
            if(flagClass.getKind() == 1){
                s_kind = "meal";
                if(flagClass.getWhat() == 1){
                    one_food = String.valueOf(flagClass.getOne_food1());
                }else{
                    one_food = String.valueOf(flagClass.getOne_food2());
                }
            }else if(flagClass.getKind() ==2){
                s_kind="drink";
                if(flagClass.getWhat() == 1){
                    one_food = String.valueOf(flagClass.getOne_food1());
                }else{
                    one_food = String.valueOf(flagClass.getOne_food2());
                }
            }
            getNutrientImage.execute(s_where,s_kind,one_food);

        }else{
            s_where="";
        }
    }

    private class GetNutrientImage extends AsyncTask<String , Integer, Bitmap> {




        @Override
        protected Bitmap doInBackground(String... strings) {


            String S_where = strings[0];
            String S_kind = strings[1];
            String S_rand = strings[2];

            try{
                URL foodimage = new URL("https://www.uristory.com/foodimages/" + S_where + "/" + S_kind + "/"+ S_rand+".jpg");
                Log.d(TAG,"datanutrient"+s_where+","+s_kind+","+one_food+","+foodimage);
                HttpURLConnection conn = (HttpURLConnection)foodimage.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bitimage1 = BitmapFactory.decodeStream(is);
            }catch (IOException e){
                e.printStackTrace();
                Log.d(TAG, "이미지 없음1");
            }

            return bitimage1;
        }

        protected void onPostExecute(Bitmap img){
            foodnutrient.setImageBitmap(bitimage1);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
