package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.JsonArray;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class TwoFood extends Activity {

    private final static String appKey = "d70e3fa5fbe6085e8027f285706fefcf";
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    ImageView food_image1;
    ImageView food_image2;
    Bitmap bitimage1; //음식 이미지 비트멥으로 받아올 변수
    Bitmap bitimage2; //음식 이미지 비트멥으로 받아올 변수
    GetFoodImage1 getfoodimage1; //음식 이미지 클래스
    GetFoodImage2 getfoodimage2; //음식 이미지 클래스

    //이미지 리사이징 함수 ------------------------------------------------------------------------------


//이미지 리사이징 함수 끝-----------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twofood);
       //광고 ------------------------------------------------------------------------------------------
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this,
                "ca-app-pub-8231620186256321~9673217647");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8231620186256321/1155279399");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//광고끝-------------------------------------------------------------------------------------------------
        ImageButton Btnno = (ImageButton)findViewById(R.id.btnno);
        ImageButton Btnpleaseselect = (ImageButton)findViewById(R.id.btnpleaseselect);
        ImageButton Btnthank = (ImageButton)findViewById(R.id.btnthank);
        ImageButton Btnend = (ImageButton)findViewById(R.id.btnend);
        ImageButton Btndetail1 = (ImageButton)findViewById(R.id.detail1);
        ImageButton Btndetail2 = (ImageButton)findViewById(R.id.detail2);
        Intent intentBtnno = new Intent(this,TwoFood.class);
        Intent intentBtnpleaseselect = new Intent(this,OneFood.class);
        Intent intentBtnthank = new Intent(this,MainActivity.class);
        Intent intentEnternet = new Intent(Intent.ACTION_VIEW);
        Intent intentEnternet2 = new Intent(Intent.ACTION_VIEW);
        getfoodimage1 = new GetFoodImage1();// 음식 이미지 클레스
        getfoodimage2 = new GetFoodImage2();//음식 이미지 클레스
//날씨 API불러오기---------------------------------------------------------------------------------------------------
//       Call<ApiModel> call = RetrofitClient.apiService().getWeather("London",appKey);
//        call.enqueue(new Callback<ApiModel>(){
//
//            @Override
//            public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {
//                Log.d(TAG, "POST 순서테스트 백그라운드");
//            }
//
//            @Override
//            public void onFailure(Call<ApiModel> call, Throwable t) {
//                Log.d(TAG, "POST 순서테스트 백그라운드 실패 !!");
//            }
//        });
//날씨 API불러오기 끝!!-------------------------------------------------------------------------------------------

        //음식 이미지 불러오기----------------------------------------------------------------------------------------
        final FlagClass flag = (FlagClass)getApplication();
        int where = flag.getWhere();
        String s_where = new String();
        int kind = flag.getKind();
        String s_kind = new String();
        Random random = new Random();
        int i_rand1 = random.nextInt(30)+1;
        int i_rand2 = random.nextInt(30)+1;

        if(where == 1) { s_where = "homefood";
            flag.setOne_where(s_where);
            Btndetail1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kind == 1) {
                        if (i_rand1 == 1) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222122568573"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 2) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222122622800"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 3) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222123585343"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 4) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139902023"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 5) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139903941"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 6) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139906357"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 7) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222128364229"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 8) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139910399"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 9) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222129549767"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 10) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139918242"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 11) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139921034"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 12) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139928448"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 13) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139925551"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 14) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222127255137"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 15) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222127271305"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 16) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139933231"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 17) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222126227093"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 18) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139945375"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 19) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139948699"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 20) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222126249726"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 21) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139967058"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 22) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139970030"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 23) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139975492"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 24) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139979888"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 25) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139994723"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 26) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139999744"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 27) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222140007479"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 28) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222140025103"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 29) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222140031197"));
                            startActivity(intentEnternet);
                        } else if (i_rand1 == 30) {
                            intentEnternet.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222140034420"));
                            startActivity(intentEnternet);
                        }
                    } else if (kind == 2) {
                        if (i_rand1 == 1) {
                        } else if (i_rand1 == 2) {
                        } else if (i_rand1 == 3) {
                        } else if (i_rand1 == 4) {
                        } else if (i_rand1 == 5) {
                        } else if (i_rand1 == 6) {
                        } else if (i_rand1 == 7) {
                        } else if (i_rand1 == 8) {
                        } else if (i_rand1 == 9) {
                        } else if (i_rand1 == 10) {
                        } else if (i_rand1 == 11) {
                        } else if (i_rand1 == 12) {
                        } else if (i_rand1 == 13) {
                        } else if (i_rand1 == 14) {
                        } else if (i_rand1 == 15) {
                        } else if (i_rand1 == 16) {
                        } else if (i_rand1 == 17) {
                        } else if (i_rand1 == 18) {
                        } else if (i_rand1 == 19) {
                        } else if (i_rand1 == 20) {
                        } else if (i_rand1 == 21) {
                        } else if (i_rand1 == 22) {
                        } else if (i_rand1 == 23) {
                        } else if (i_rand1 == 24) {
                        } else if (i_rand1 == 25) {
                        } else if (i_rand1 == 26) {
                        } else if (i_rand1 == 27) {
                        } else if (i_rand1 == 28) {
                        } else if (i_rand1 == 29) {
                        } else if (i_rand1 == 30) {
                        }
                    }
                }
            });
            Btndetail2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                        if (kind == 1) {
                            if (i_rand2 == 1) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222122568573"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 2) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222122622800"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 3) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222123585343"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 4) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139902023"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 5) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139903941"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 6) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139906357"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 7) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222128364229"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 8) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139910399"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 9) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222129549767"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 10) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139918242"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 11) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139921034"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 12) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139928448"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 13) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139925551"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 14) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222127255137"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 15) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222127271305"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 16) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139933231"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 17) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222126227093"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 18) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139945375"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 19) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139948699"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 20) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222126249726"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 21) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139967058"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 22) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139970030"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 23) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139975492"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 24) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139979888"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 25) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139994723"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 26) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222139999744"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 27) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222140007479"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 28) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222140025103"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 29) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222140031197"));
                                startActivity(intentEnternet2);
                            } else if (i_rand2 == 30) {
                                intentEnternet2.setData(Uri.parse("https://m.blog.naver.com/oh___ya/222140034420"));
                                startActivity(intentEnternet2);
                            }
                        }else if(kind==2) {

                        }

                    }
            });
        }
        else if (where == 2) {
            s_where = "outfood";
            flag.setOne_where(s_where);
            Btndetail1.setImageResource(R.drawable.whichfoodfindcafeteria1);
            Btndetail2.setImageResource(R.drawable.whichfoodfindcafeteria2);
            Intent intentmap = new Intent(this,mapofstore.class);
            Btndetail1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag.setFindstore(1);
                    if (kind == 1) {
                        startActivity(intentmap);
                    } else if (kind == 2) {
                        startActivity(intentmap);
                    }
                }
            });
            Btndetail2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag.setFindstore(2);
                    if (kind == 1) {
                        startActivity(intentmap);
                    } else if (kind == 2) {
                        startActivity(intentmap);
                    }
                }
            });
        }
        else if(where == 3) {s_where = "deliverfood";
            flag.setOne_where(s_where);
            Btndetail1.setImageResource(R.drawable.whichfooddelivery1);
            Btndetail2.setImageResource(R.drawable.whichfooddelivery2);
            Btndetail1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kind == 1) {

                    } else if (kind == 2) {

                    }
                }
            });
            Btndetail2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kind == 1) {

                    } else if (kind == 2) {

                    }
                }
            });
        } else {s_where = "error";}
        if(kind ==1){s_kind = "meal";
            flag.setOne_kind(s_kind);}
        else if (kind ==2){s_kind ="drink";
            flag.setOne_kind(s_kind);}
        else {s_kind = "error";}
        String s_rand1 = Integer.toString(i_rand1);
        flag.setOne_food1(i_rand1);
        String s_rand2 = Integer.toString(i_rand2);
        flag.setOne_food2(i_rand2);


        if(where == 3)
        {
            s_rand1 = Integer.toString(i_rand1%16);
            flag.setOne_food1(i_rand1%16);
            s_rand2 = Integer.toString(i_rand2%16);
            flag.setOne_food2(i_rand2%16);
            getfoodimage1.execute(s_where, s_kind, s_rand1);
            getfoodimage2.execute(s_where, s_kind, s_rand2);
        }
        else if (where ==1 || where == 2)
        {
            getfoodimage1.execute(s_where, s_kind, s_rand1);
            getfoodimage2.execute(s_where, s_kind, s_rand2);
        }
        else
        {

        }

        //음식 이미지 불러오기 끝 --------------------------------------------------------------------------------------

        Btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(intentBtnno);


           }
        });
        Btnpleaseselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                startActivity(intentBtnpleaseselect);


          }
        });
        Btnthank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){


                startActivity(intentBtnthank);


            }
        });
        Btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                ActivityCompat.finishAffinity(TwoFood.this);


            }
        });





//음식 이미지 불러오기 끝 -------------------------------------------------------------------------------------

    }


   private class GetFoodImage1 extends AsyncTask<String , Integer, Bitmap>{




        @Override
        protected Bitmap doInBackground(String... strings) {


            String S_where = strings[0];
            String S_kind = strings[1];
            String S_rand = strings[2];

            try{
                URL foodimage = new URL("https://www.uristory.com/foodimages/" + S_where + "/" + S_kind + "/"+ S_rand+".jpg");
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
            food_image1=(ImageView)findViewById(R.id.result_image1);
            food_image1.setImageBitmap(bitimage1);
        }
    }

    private class GetFoodImage2 extends AsyncTask<String , Integer, Bitmap>{




        @Override
        protected Bitmap doInBackground(String... strings) {


            String S_where = strings[0];
            String S_kind = strings[1];
            String S_rand = strings[2];

            try{
                URL foodimage = new URL("https://www.uristory.com/foodimages/" + S_where + "/" + S_kind + "/"+ S_rand+".jpg");
                HttpURLConnection conn = (HttpURLConnection)foodimage.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bitimage2 = BitmapFactory.decodeStream(is);
            }catch (IOException e){
                e.printStackTrace();
                Log.d(TAG, "이미지 없음1");
            }

            return bitimage2;
        }

        protected void onPostExecute(Bitmap img){
            food_image2=(ImageView)findViewById(R.id.result_image2);
            food_image2.setImageBitmap(bitimage2);
        }
    }

    //뒤로가기 종료==================================================================================
    private long time= 0;
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    //뒤로가기 종료 끝-----------------------------------------------------------------------------
}




