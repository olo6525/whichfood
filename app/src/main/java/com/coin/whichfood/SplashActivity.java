package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SplashActivity extends Activity {
    Intent startmain;
    VersionCheck versionCheck;
    private static String FoodIP = "https://www.uristory.com/whichfood.php"; //음식 이미지 서버
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startmain = new Intent(this, MainActivity.class);
        final FlagClass flag = (FlagClass)getApplication();
        flag.Init();
        versionCheck = new VersionCheck();
        ArrayList<String> subservers = new ArrayList<>();
        String serverdata;
        try {
            serverdata = new SplashActivity.VersionCheck().execute(FoodIP).get();
            Log.d("TAG", "serverinit" + serverdata);
            JSONObject jsonObject = new JSONObject(serverdata);
            flag.setVersion(jsonObject.getString("version"));
            JSONArray jsonArray = jsonObject.getJSONArray("thenumberoffood");
            JSONObject thenumberoffood = jsonArray.getJSONObject(0);
            flag.setThenumberoffoodhomemeal(Integer.parseInt(thenumberoffood.getString("homemeal")));
            flag.setThenumberoffoodhomedrink(Integer.parseInt(thenumberoffood.getString("homedrink")));
            flag.setThenumberoffoodoutmeal(Integer.parseInt(thenumberoffood.getString("outmeal")));
            flag.setThenumberoffoodoutdrink(Integer.parseInt(thenumberoffood.getString("outdrink")));
            flag.setThenumberoffooddelivermeal(Integer.parseInt(thenumberoffood.getString("delivermeal")));
            flag.setThenumberoffooddeliverdrink(Integer.parseInt(thenumberoffood.getString("deliverdrink")));
            flag.setHowcookpage(jsonObject.getJSONArray("howcook"));
            flag.setHowcookpagedrink(jsonObject.getJSONArray("howcookdrink"));
            flag.setHomefoodmealindex(jsonObject.getJSONArray("homefoodmeal"));
            flag.setHomefooddrinkindex(jsonObject.getJSONArray("homefooddrink"));
            flag.setOutfoodmealindex(jsonObject.getJSONArray("outfoodmeal"));
            flag.setOutfooddrinkindex(jsonObject.getJSONArray("outfooddrink"));
            flag.setDeliveryfoodmealindex(jsonObject.getJSONArray("deliveryfoodmeal"));
            flag.setDeliveryfooddrinkindex(jsonObject.getJSONArray("deliveryfooddrink"));
            for(int i=0; i<5; i++){
                subservers.add(jsonObject.getString("server"+Integer.toString(i+1)));
            }
            flag.setServers(subservers);
            Log.d("TAG", "serverdata"+flag.getThenumberoffoodhomemeal()+ flag.getThenumberoffoodoutdrink() + flag.getOutfoodmealindex().getJSONObject(0).getString("1") + flag.getServers().get(0));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startLoading();
    }
    private void startLoading() {
        Handler hendler = new Handler();
        hendler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(startmain);
                finish();
            }
        }, 500);
    }

    static class VersionCheck extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String IP = strings[0];
            String server_version = new String();

            try{
                URL Version = new URL(IP);
                HttpURLConnection conn = (HttpURLConnection)Version.openConnection();
                conn.setDoInput(true);
                conn.connect();


                int responseStatusCode = conn.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = conn.getInputStream();
                } else {
                    inputStream = conn.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();
                inputStream.close();
                inputStreamReader.close();

                Log.d("TAG","version"+sb.toString());
                return sb.toString();

            }catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

        }

    }
}
