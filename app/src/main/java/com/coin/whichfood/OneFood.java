package com.coin.whichfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;


public class OneFood extends Activity {

    Bitmap bitmap_one;
    ImageButton food_image_one;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private int mapint = 0;
    private int onefood = 0;
    ImageButton menu1;
    ImageButton menu2;
    ImageButton menu3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onefood);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Intent intent_thank = new Intent(this, MainActivity.class);
        Intent intentEnternet = new Intent(Intent.ACTION_VIEW);
        Intent nutrient = new Intent(this, foodnutrientclass.class);
        ImageButton imbtn_thank = (ImageButton) findViewById(R.id.imbtn_thank);
        ImageButton imbtn_end = (ImageButton) findViewById(R.id.imbtn_end);
        menu1 = (ImageButton) findViewById(R.id.menu1);
        menu2 = (ImageButton) findViewById(R.id.menu2);
        menu3 = (ImageButton) findViewById(R.id.menu3);


        //종료시 전면광고-----------------------------------------------------------------------------
        MobileAds.initialize(this,
                "ca-app-pub-8231620186256321~9673217647");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8231620186256321/1155279399");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //광고 끝---------------------------------------------------------------------------------------
//음식 한개 보여지기 -----------------------------------------------------------------------------------------------
        final FlagClass flag = (FlagClass) getApplication();
        int where = flag.getWhere();
        int kind = flag.getKind();
        Log.d(TAG, "data" + flag.getKind() + "," + where);
        GetFoodImageOne getFoodImageOne = new GetFoodImageOne();
        if (flag.getOne_food1() > flag.getOne_food2()) {
            getFoodImageOne.execute(flag.getOne_where(), flag.getOne_kind(), Integer.toString(flag.getOne_food1()));
            onefood = flag.getOne_food1();
            mapint = 1;
            Log.d(TAG, "data" + onefood + " ," + mapint);
        } else {
            getFoodImageOne.execute(flag.getOne_where(), flag.getOne_kind(), Integer.toString(flag.getOne_food2()));
            onefood = flag.getOne_food2();
            mapint = 2;
            Log.d(TAG, "data" + onefood + " ," + mapint);
        }

        if (where == 1) {
            Log.d(TAG, "data" + onefood + " ," + mapint + "," + kind);
            food_image_one = (ImageButton) findViewById(R.id.foodimageone);
            if (kind == 1) {

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(mapint);
                        startActivity(nutrient);
                    }
                });

                menu2.setImageResource(R.drawable.ic_recipe);
                menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = new String();
                        try {
                            url = flag.getHowcookpage().getJSONObject(0).getString(String.valueOf(onefood));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intentEnternet.setData(Uri.parse(url));
                        startActivity(intentEnternet);
                    }
                });
                menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심요리식사 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Thread getdata = new Thread(){
                                        @Override
                                        public void run(){
                                            try {
                                                String postParameters = "purpose=getdata&userid="+flag.getLoginid()+"&where="+where+"&kind="+kind+"&food=meal"+onefood;
                                                Log.d(TAG,"getdata post : "+postParameters);
                                                URL url = new URL(flag.getServers().get(0)+"whichfoodstorelist.php");
                                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                conn.setDoInput(true);
                                                conn.setDoOutput(true);
                                                conn.connect();

                                                OutputStream outputStream = conn.getOutputStream();
                                                outputStream.write(postParameters.getBytes("UTF-8"));
                                                outputStream.flush();
                                                outputStream.close();

                                                int response = conn.getResponseCode();
                                                if(response == conn.HTTP_OK){
                                                    Log.d(TAG,"getdata connected to myinfo");
                                                }else{
                                                    Log.d(TAG,"getdata disconnected to myinfo");
                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    getdata.start();
                                    Toast.makeText(getApplicationContext(),"관심음식으로 등록이 되었습니다.",Toast.LENGTH_LONG).show();
                                    try {
                                        getdata.join();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(OneFood.this, kakaologin.class);
                                    startActivity(longinlayout);
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        }
                    }
                });

            } else if (kind == 2) {

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(mapint);
                        startActivity(nutrient);
                    }
                });

                menu2.setImageResource(R.drawable.ic_recipe);
                menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = new String();
                        try {
                            url = flag.getHowcookpage().getJSONObject(0).getString(String.valueOf(onefood));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intentEnternet.setData(Uri.parse(url));
                        startActivity(intentEnternet);
                    }
                });
                menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심요리안주 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Thread getdata = new Thread(){
                                        @Override
                                        public void run(){
                                            try {
                                                String postParameters = "purpose=getdata&userid="+flag.getLoginid()+"&where="+where+"&kind="+kind+"&food=drink"+onefood;
                                                Log.d(TAG,"getdata post : "+postParameters);
                                                URL url = new URL(flag.getServers().get(0)+"whichfoodstorelist.php");
                                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                conn.setDoInput(true);
                                                conn.setDoOutput(true);
                                                conn.connect();

                                                OutputStream outputStream = conn.getOutputStream();
                                                outputStream.write(postParameters.getBytes("UTF-8"));
                                                outputStream.flush();
                                                outputStream.close();

                                                int response = conn.getResponseCode();
                                                if(response == conn.HTTP_OK){
                                                    Log.d(TAG,"getdata connected to myinfo");
                                                }else{
                                                    Log.d(TAG,"getdata disconnected to myinfo");
                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    getdata.start();
                                    Toast.makeText(getApplicationContext(),"관심음식으로 등록이 되었습니다.",Toast.LENGTH_LONG).show();
                                    try {
                                        getdata.join();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(OneFood.this, kakaologin.class);
                                    startActivity(longinlayout);
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        }
                    }
                });


            } else {
                Log.d(TAG, "data" + onefood + " ," + mapint);
            }
        } else if (where == 2) {
            food_image_one = (ImageButton) findViewById(R.id.foodimageone);
            Intent intentmap = new Intent(this, mapofstore.class);
            if (kind == 1) {

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(mapint);
                        startActivity(nutrient);
                    }
                });

                menu2.setImageResource(R.drawable.ic_storelocation);
                menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setFindstore(mapint);
                        intentmap.putExtra("scope",1);
                        startActivity(intentmap);
                    }
                });
                menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심외식식사 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Thread getdata = new Thread(){
                                        @Override
                                        public void run(){
                                            try {
                                                String postParameters = "purpose=getdata&userid="+flag.getLoginid()+"&where="+where+"&kind="+kind+"&food=meal"+onefood;
                                                Log.d(TAG,"getdata post : "+postParameters);
                                                URL url = new URL(flag.getServers().get(0)+"whichfoodstorelist.php");
                                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                conn.setDoInput(true);
                                                conn.setDoOutput(true);
                                                conn.connect();

                                                OutputStream outputStream = conn.getOutputStream();
                                                outputStream.write(postParameters.getBytes("UTF-8"));
                                                outputStream.flush();
                                                outputStream.close();

                                                int response = conn.getResponseCode();
                                                if(response == conn.HTTP_OK){
                                                    Log.d(TAG,"getdata connected to myinfo");
                                                }else{
                                                    Log.d(TAG,"getdata disconnected to myinfo");
                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    getdata.start();
                                    Toast.makeText(getApplicationContext(),"관심음식으로 등록이 되었습니다.",Toast.LENGTH_LONG).show();
                                    try {
                                        getdata.join();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(OneFood.this, kakaologin.class);
                                    startActivity(longinlayout);
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        }
                    }
                });

            } else if (kind == 2) {

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(mapint);
                        startActivity(nutrient);
                    }
                });

                menu2.setImageResource(R.drawable.ic_storelocation);
                menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setFindstore(mapint);
                        intentmap.putExtra("scope",1);
                        startActivity(intentmap);
                    }
                });
                menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심외식안주 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Thread getdata = new Thread(){
                                        @Override
                                        public void run(){
                                            try {
                                                String postParameters = "purpose=getdata&userid="+flag.getLoginid()+"&where="+where+"&kind="+kind+"&food=drink"+onefood;
                                                Log.d(TAG,"getdata post : "+postParameters);
                                                URL url = new URL(flag.getServers().get(0)+"whichfoodstorelist.php");
                                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                conn.setDoInput(true);
                                                conn.setDoOutput(true);
                                                conn.connect();

                                                OutputStream outputStream = conn.getOutputStream();
                                                outputStream.write(postParameters.getBytes("UTF-8"));
                                                outputStream.flush();
                                                outputStream.close();

                                                int response = conn.getResponseCode();
                                                if(response == conn.HTTP_OK){
                                                    Log.d(TAG,"getdata connected to myinfo");
                                                }else{
                                                    Log.d(TAG,"getdata disconnected to myinfo");
                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    getdata.start();
                                    Toast.makeText(getApplicationContext(),"관심음식으로 등록이 되었습니다.",Toast.LENGTH_LONG).show();
                                    try {
                                        getdata.join();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(OneFood.this, kakaologin.class);
                                    startActivity(longinlayout);
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        }
                    }
                });

            } else {

            }
        } else if (where == 3) {
            food_image_one = (ImageButton) findViewById(R.id.foodimageone);
            if (kind == 1) {

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(mapint);
                        startActivity(nutrient);
                    }
                });

                menu2.setImageResource(R.drawable.ic_delivery);
                menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심배달식사 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Thread getdata = new Thread(){
                                        @Override
                                        public void run(){
                                            try {
                                                String postParameters = "purpose=getdata&userid="+flag.getLoginid()+"&where="+where+"&kind="+kind+"&food=meal"+onefood;
                                                Log.d(TAG,"getdata post : "+postParameters);
                                                URL url = new URL(flag.getServers().get(0)+"whichfoodstorelist.php");
                                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                conn.setDoInput(true);
                                                conn.setDoOutput(true);
                                                conn.connect();

                                                OutputStream outputStream = conn.getOutputStream();
                                                outputStream.write(postParameters.getBytes("UTF-8"));
                                                outputStream.flush();
                                                outputStream.close();

                                                int response = conn.getResponseCode();
                                                if(response == conn.HTTP_OK){
                                                    Log.d(TAG,"getdata connected to myinfo");
                                                }else{
                                                    Log.d(TAG,"getdata disconnected to myinfo");
                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    getdata.start();
                                    Toast.makeText(getApplicationContext(),"관심음식으로 등록이 되었습니다.",Toast.LENGTH_LONG).show();
                                    try {
                                        getdata.join();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(OneFood.this, kakaologin.class);
                                    startActivity(longinlayout);
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        }
                    }
                });

            } else if (kind == 2) {

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(mapint);
                        startActivity(nutrient);
                    }
                });

                menu2.setImageResource(R.drawable.ic_delivery);
                menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심배달안주 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Thread getdata = new Thread(){
                                        @Override
                                        public void run(){
                                            try {
                                                String postParameters = "purpose=getdata&userid="+flag.getLoginid()+"&where="+where+"&kind="+kind+"&food=drink"+onefood;
                                                Log.d(TAG,"getdata post : "+postParameters);
                                                URL url = new URL(flag.getServers().get(0)+"whichfoodstorelist.php");
                                                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                                                conn.setDoInput(true);
                                                conn.setDoOutput(true);
                                                conn.connect();

                                                OutputStream outputStream = conn.getOutputStream();
                                                outputStream.write(postParameters.getBytes("UTF-8"));
                                                outputStream.flush();
                                                outputStream.close();

                                                int response = conn.getResponseCode();
                                                if(response == conn.HTTP_OK){
                                                    Log.d(TAG,"getdata connected to myinfo");
                                                }else{
                                                    Log.d(TAG,"getdata disconnected to myinfo");
                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    getdata.start();
                                    Toast.makeText(getApplicationContext(),"관심음식으로 등록이 되었습니다.",Toast.LENGTH_LONG).show();
                                    try {
                                        getdata.join();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(OneFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(OneFood.this, kakaologin.class);
                                    startActivity(longinlayout);
                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        }
                    }
                });

            } else {

            }

        } else {
        }
//음식한개 보여지기 끝-------------------------------------------------------------------------------------------------
        imbtn_thank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_thank);
            }
        });
        imbtn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                ActivityCompat.finishAffinity(OneFood.this);
            }
        });


    }


    //음식 이미지 가져오기 테스크-----------------------------------------------------------------------------------
    private class GetFoodImageOne extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {


            String S_where = strings[0];
            String S_kind = strings[1];
            String S_rand = strings[2];

            try {
                URL foodimage = new URL("https://www.uristory.com/foodimages/" + S_where + "/" + S_kind + "/" + S_rand + ".jpg");
                HttpURLConnection conn = (HttpURLConnection) foodimage.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bitmap_one = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "이미지 없음1");
            }

            return bitmap_one;
        }

        protected void onPostExecute(Bitmap img) {
            food_image_one.setImageBitmap(bitmap_one);
        }
    }
//음식 이지미 가져오기 테스크 끝-----------------------------------------------------------------------------------------------------

    //뒤로가기 종료==================================================================================
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    //뒤로가기 종료 끝-----------------------------------------------------------------------------
}
