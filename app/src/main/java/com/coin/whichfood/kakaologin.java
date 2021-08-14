package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.se.omapi.Session;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.model.ApprovalType;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.attribute.UserPrincipal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

import static android.content.ContentValues.TAG;

public class kakaologin extends Activity {
    private View loginbtn, logoutbtn;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private int logoutflag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_kakaologin);
        //앱 종료, 다시시작시 자동 로그아웃
        FlagClass loginflag = (FlagClass)getApplication();
        if(loginflag.getLoginflag() == 0) {
            logoutflag = loginflag.getLoginflag();
        }else{
            logoutflag = loginflag.getLoginflag();
        }


        getHashKey();
        MainActivity MA = (MainActivity)MainActivity.activity;
        loginbtn = findViewById(R.id.logintn);
        logoutbtn = findViewById(R.id.logoutbtn);
        Intent mainactivitylayout = new Intent(this, MainActivity.class);
        updatekakaoLoginUi();


            Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null){
                    Log.d("로그인성공", "로그인 성공");
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {
                            if (user != null) {
                                JSONObject jsonObject;

                                startActivity(mainactivitylayout);
                                Log.d(TAG,"로그인정보 : "+user);

                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        try{
                                            String postParameters;
                                            URL url = new URL("https://uristory.com/whichfoodstorelist.php");
                                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                                            httpURLConnection.setReadTimeout(5000);
                                            httpURLConnection.setConnectTimeout(5000);
                                            httpURLConnection.setRequestMethod("POST");
                                            httpURLConnection.connect();

                                            postParameters = "purpose=logininfo&id="+user.getId()+"&nickname="+user.getProperties().get("nickname")+"&email="+user.getKakaoAccount().getEmail();
                                            OutputStream outputStream = httpURLConnection.getOutputStream();
                                            outputStream.write(postParameters.getBytes("UTF-8"));
                                            outputStream.flush();
                                            outputStream.close();


                                            int responseStatusCode = httpURLConnection.getResponseCode();
                                            Log.d(TAG, "POST response code - " + responseStatusCode);

                                            InputStream inputStream;
                                            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                                                inputStream = httpURLConnection.getInputStream();
                                            }
                                            else{
                                                inputStream = httpURLConnection.getErrorStream();
                                            }

                                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                            StringBuilder sb = new StringBuilder();
                                            String line = null;
                                            while((line = bufferedReader.readLine()) != null){
                                                sb.append(line);
                                            }
                                            bufferedReader.close();

                                            Log.d(TAG,"로그인정보저장에러 : "+sb.toString());

                                        }catch (Exception e){
                                            Log.d(TAG, "로그인정보확인에러: ", e);
                                        }

                                    }

                                };
                                executorService.execute(runnable);
                                executorService.shutdown();

                                finish();
                                loginflag.setLoginflag(1);
                                MA.finish();
                            }
                            return null;
                        }
                    });
                }
                if(throwable != null){
                    Log.d("로그인에러",throwable.getLocalizedMessage());
                }
                updatekakaoLoginUi();
                return null;
            }
        };

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(kakaologin.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(kakaologin.this,callback);
                }else{
                    UserApiClient.getInstance().loginWithKakaoAccount(kakaologin.this,callback);
                }
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        finish();
                        loginflag.setLoginflag(0);
                        MA.finish();
                        startActivity(mainactivitylayout);
                        return null;
                    }
                });
            }
        });
    }
    private void updatekakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {

            @Override
            public Unit invoke(User user, Throwable throwable) {
                Log.d(TAG,"로그인 아웃 플래크 : "+logoutflag);
                if(logoutflag == 0){
                        user = null;
                    }
                if(user != null){
                    loginbtn.setVisibility(View.GONE);
                    logoutbtn.setVisibility(View.VISIBLE);
                }else {
                    loginbtn.setVisibility(View.VISIBLE);
                    logoutbtn.setVisibility(View.GONE);
                }

                return null;
            }
        });


    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


}
