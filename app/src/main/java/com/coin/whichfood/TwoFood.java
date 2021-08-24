package com.coin.whichfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class TwoFood extends Activity {

    private final static String appKey = "d70e3fa5fbe6085e8027f285706fefcf";
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    ImageButton food_image1;
    ImageButton food_image2;
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


// 음식 이미지 불러오기----------------------------------------------------------------------------------------
        ImageButton Btnno = (ImageButton) findViewById(R.id.btnno);
        ImageButton Btnpleaseselect = (ImageButton) findViewById(R.id.btnpleaseselect);
        ImageButton Btnthank = (ImageButton) findViewById(R.id.btnthank);
        ImageButton Btnend = (ImageButton) findViewById(R.id.btnend);
        ImageButton left_menu1 = (ImageButton) findViewById(R.id.left_menu1);
        ImageButton left_menu2 = (ImageButton) findViewById(R.id.left_menu2);
        ImageButton left_menu3 = (ImageButton) findViewById(R.id.left_menu3);
        ImageButton right_menu1 = (ImageButton) findViewById(R.id.right_menu1);
        ImageButton right_menu2 = (ImageButton) findViewById(R.id.right_menu2);
        ImageButton right_menu3 = (ImageButton) findViewById(R.id.right_menu3);
        Intent intentBtnno = new Intent(this, TwoFood.class);
        Intent intentBtnpleaseselect = new Intent(this, OneFood.class);
        Intent intentBtnthank = new Intent(this, MainActivity.class);
        Intent intentEnternet = new Intent(Intent.ACTION_VIEW);
        Intent intentEnternet2 = new Intent(Intent.ACTION_VIEW);
        Intent shownutrient = new Intent(this, foodnutrientclass.class);
        getfoodimage1 = new GetFoodImage1();// 음식 이미지 클레스
        getfoodimage2 = new GetFoodImage2();//음식 이미지 클레스

        final FlagClass flag = (FlagClass) getApplication();
        int where = flag.getWhere();
        String s_where = new String();
        int kind = flag.getKind();
        String s_kind = new String();
        Random random = new Random();


        if (kind == 1) {
            s_kind = "meal";
            flag.setOne_kind(s_kind);
        } else if (kind == 2) {
            s_kind = "drink";
            flag.setOne_kind(s_kind);
        } else {
            s_kind = "error";
        }
        int i_rand1 = 0;
        int i_rand2 = 0;

        if (where == 1) {
            food_image1 = (ImageButton) findViewById(R.id.result_image1);
            food_image2 = (ImageButton) findViewById(R.id.result_image2);

            String s_rand1 = new String();
            String s_rand2 = new String();
            s_where = "homefood";
            flag.setOne_where(s_where);
            if (kind == 1) {
                while (i_rand1 == i_rand2 || i_rand1 == flag.getOne_food1_before() || i_rand1 == flag.getOne_food2_before() || i_rand2 == flag.getOne_food1_before() || i_rand2 == flag.getOne_food2_before()) {
                    i_rand1 = random.nextInt(flag.getThenumberoffoodhomemeal()) + 1;
                    i_rand2 = random.nextInt(flag.getThenumberoffoodhomemeal()) + 1;
                }
                flag.setOne_food1_before(i_rand1);
                flag.setOne_food2_before(i_rand2);
                Log.d(TAG, "whatfood?:" + i_rand1 + "," + i_rand2);
                int finalI_rand1 = i_rand1;
                int finalI_rand2 = i_rand2;

                left_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(1);
                        startActivity(shownutrient);
                    }
                });

                left_menu2.setImageResource(R.drawable.ic_recipe);
                left_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(1);
                        String url = new String();
                        try {
                            url = flag.getHowcookpage().getJSONObject(0).getString(String.valueOf(finalI_rand1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intentEnternet.setData(Uri.parse(url));
                        startActivity(intentEnternet);
                    }
                });

                left_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(1);
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심요리식사 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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


                right_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(2);
                        startActivity(shownutrient);
                    }
                });

                right_menu2.setImageResource(R.drawable.ic_recipe);
                right_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(2);
                        String url = new String();
                        try {
                            url = flag.getHowcookpage().getJSONObject(0).getString(String.valueOf(finalI_rand2));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intentEnternet.setData(Uri.parse(url));
                        startActivity(intentEnternet);
                    }
                });
                right_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(2);
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심요리식사 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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
                while (i_rand1 == i_rand2 || i_rand1 == flag.getOne_food1_before() || i_rand1 == flag.getOne_food2_before() || i_rand2 == flag.getOne_food1_before() || i_rand2 == flag.getOne_food2_before()) {
                    i_rand1 = random.nextInt(flag.getThenumberoffoodhomedrink()) + 1;
                    i_rand2 = random.nextInt(flag.getThenumberoffoodhomedrink()) + 1;
                }
                flag.setOne_food1_before(i_rand1);
                flag.setOne_food2_before(i_rand2);
                int finalI_rand1 = i_rand1;
                int finalI_rand2 = i_rand2;

                left_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(1);
                        startActivity(shownutrient);
                    }
                });

                left_menu2.setImageResource(R.drawable.ic_recipe);
                left_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(1);
                        String url = new String();
                        try {
                            url = flag.getHowcookpagedrink().getJSONObject(0).getString(String.valueOf(finalI_rand1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intentEnternet.setData(Uri.parse(url));
                        startActivity(intentEnternet);
                    }
                });

                left_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(1);
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심요리안주 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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


                right_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(2);
                        startActivity(shownutrient);
                    }
                });

                right_menu2.setImageResource(R.drawable.ic_recipe);
                right_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(2);
                        String url = new String();
                        try {
                            url = flag.getHowcookpagedrink().getJSONObject(0).getString(String.valueOf(finalI_rand2));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intentEnternet.setData(Uri.parse(url));
                        startActivity(intentEnternet);
                    }
                });

                right_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag.getLoginflag() == 1) {
                            flag.setWhat(2);
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심요리안주 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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
                i_rand1 = 0;
                i_rand2 = 0;
            }

            s_rand1 = Integer.toString(i_rand1);
            flag.setOne_food1(i_rand1);
            s_rand2 = Integer.toString(i_rand2);
            flag.setOne_food2(i_rand2);
            getfoodimage1.execute(s_where, s_kind, s_rand1);
            getfoodimage2.execute(s_where, s_kind, s_rand2);

        } else if (where == 2) {

            food_image1 = (ImageButton) findViewById(R.id.result_image1);
            food_image2 = (ImageButton) findViewById(R.id.result_image2);
            String s_rand1 = new String();
            String s_rand2 = new String();
            Intent intentmap = new Intent(this, mapofstore.class);

            s_where = "outfood";
            flag.setOne_where(s_where);
            if (kind == 1) {
                while (i_rand1 == i_rand2 || i_rand1 == flag.getOne_food1_before() || i_rand1 == flag.getOne_food2_before() || i_rand2 == flag.getOne_food1_before() || i_rand2 == flag.getOne_food2_before()) {
                    i_rand1 = random.nextInt(flag.getThenumberoffoodoutmeal()) + 1;
                    i_rand2 = random.nextInt(flag.getThenumberoffoodoutmeal()) + 1;
                }
                flag.setOne_food1_before(i_rand1);
                flag.setOne_food2_before(i_rand2);
                Log.d(TAG, "랜덤숫자" + i_rand1 + "," + i_rand2);
                int finalI_rand1 = i_rand1;
                int finalI_rand2 = i_rand2;


                left_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(1);
                        startActivity(shownutrient);
                    }
                });

                left_menu2.setImageResource(R.drawable.ic_storelocation);
                left_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(1);
                        flag.setFindstore(1);
                        intentmap.putExtra("scope",1);
                        startActivity(intentmap);
                    }
                });

                left_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag.getLoginflag() == 1) {
                            flag.setWhat(1);
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심외식식사 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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


                right_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(2);
                        startActivity(shownutrient);
                    }
                });

                right_menu2.setImageResource(R.drawable.ic_storelocation);
                right_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(2);
                        flag.setFindstore(2);
                        intentmap.putExtra("scope",1);
                        startActivity(intentmap);
                    }
                });

                right_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(2);
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심외식식사 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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
                while (i_rand1 == i_rand2 || i_rand1 == flag.getOne_food1_before() || i_rand1 == flag.getOne_food2_before() || i_rand2 == flag.getOne_food1_before() || i_rand2 == flag.getOne_food2_before()) {
                    i_rand1 = random.nextInt(flag.getThenumberoffoodoutdrink()) + 1;
                    i_rand2 = random.nextInt(flag.getThenumberoffoodoutdrink()) + 1;
                }
                flag.setOne_food1_before(i_rand1);
                flag.setOne_food2_before(i_rand2);


                left_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(1);
                        startActivity(shownutrient);
                    }
                });

                left_menu2.setImageResource(R.drawable.ic_storelocation);
                left_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(1);
                        flag.setFindstore(1);
                        intentmap.putExtra("scope",1);
                        startActivity(intentmap);
                    }
                });

                left_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(1);
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심외식안주 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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


                right_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(2);
                        startActivity(shownutrient);
                    }
                });

                right_menu2.setImageResource(R.drawable.ic_storelocation);
                right_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(2);
                        flag.setFindstore(2);
                        intentmap.putExtra("scope",1);
                        startActivity(intentmap);
                    }
                });

                right_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(2);
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심외식안주 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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
                i_rand1 = 0;
                i_rand2 = 0;
            }
            s_rand1 = Integer.toString(i_rand1);
            flag.setOne_food1(i_rand1);
            s_rand2 = Integer.toString(i_rand2);
            flag.setOne_food2(i_rand2);
            getfoodimage1.execute(s_where, s_kind, s_rand1);
            getfoodimage2.execute(s_where, s_kind, s_rand2);

        } else if (where == 3) {
            food_image1 = (ImageButton) findViewById(R.id.result_image1);
            food_image2 = (ImageButton) findViewById(R.id.result_image2);
            String s_rand1 = new String();
            String s_rand2 = new String();
            s_where = "deliverfood";
            flag.setOne_where(s_where);
            if (kind == 1) {
                while (i_rand1 == i_rand2 || i_rand1 == flag.getOne_food1_before() || i_rand1 == flag.getOne_food2_before() || i_rand2 == flag.getOne_food1_before() || i_rand2 == flag.getOne_food2_before()) {
                    i_rand1 = random.nextInt(flag.getThenumberoffooddelivermeal()) + 1;
                    i_rand2 = random.nextInt(flag.getThenumberoffooddelivermeal()) + 1;
                }
                flag.setOne_food1_before(i_rand1);
                flag.setOne_food2_before(i_rand2);


                left_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(1);
                        startActivity(shownutrient);
                    }
                });

                left_menu2.setImageResource(R.drawable.ic_delivery);
                left_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(getApplicationContext(),"서비스 준비중입니다.",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
                        toast.show();
                    }
                });

                left_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(1);
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심배달식사 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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


                right_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(2);
                        startActivity(shownutrient);
                    }
                });

                right_menu2.setImageResource(R.drawable.ic_delivery);
                right_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(getApplicationContext(),"서비스 준비중입니다.",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
                        toast.show();
                    }
                });

                right_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(2);
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심배달식사 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("회원 전용 서비스입니다.");
                            dlg.setMessage("로그인 후 이용가능한 서비스 입니다. \n로그인 화면으로 이동하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent longinlayout = new Intent(TwoFood.this, kakaologin.class);
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
                while (i_rand1 == i_rand2 || i_rand1 == flag.getOne_food1_before() || i_rand1 == flag.getOne_food2_before() || i_rand2 == flag.getOne_food1_before() || i_rand2 == flag.getOne_food2_before()) {
                    i_rand1 = random.nextInt(flag.getThenumberoffooddeliverdrink()) + 1;
                    i_rand2 = random.nextInt(flag.getThenumberoffooddeliverdrink()) + 1;
                }
                flag.setOne_food1_before(i_rand1);
                flag.setOne_food2_before(i_rand2);


                left_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(1);
                        startActivity(shownutrient);
                    }
                });

                left_menu2.setImageResource(R.drawable.ic_delivery);
                left_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(getApplicationContext(),"서비스 준비중입니다.",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
                        toast.show();
                    }
                });

                left_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag.getLoginflag() == 1) {
                            flag.setWhat(1);
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심배달안주 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 후 이용가능한 서비스 입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                right_menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        flag.setWhat(2);
                        startActivity(shownutrient);
                    }
                });

                right_menu2.setImageResource(R.drawable.ic_delivery);
                right_menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(getApplicationContext(),"서비스 준비중입니다.",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
                        toast.show();
                    }
                });

                right_menu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag.setWhat(2);
                        if (flag.getLoginflag() == 1) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(TwoFood.this);
                            dlg.setTitle("관심음식으로 선택");
                            dlg.setMessage("선택하신 음식을 관심배달안주 목록에 추가하시겠습니까?");
                            dlg.setIcon(R.drawable.ic_storeimage);
                            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dlg.show();
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 후 이용가능한 서비스 입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                i_rand1 = 0;
                i_rand2 = 0;
            }
            s_rand1 = Integer.toString(i_rand1);
            flag.setOne_food1(i_rand1);
            s_rand2 = Integer.toString(i_rand2);
            flag.setOne_food2(i_rand2);
            getfoodimage1.execute(s_where, s_kind, s_rand1);
            getfoodimage2.execute(s_where, s_kind, s_rand2);
        }


        //음식 이미지 불러오기 끝 --------------------------------------------------------------------------------------

        Btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intentBtnno);


            }
        });
        Btnpleaseselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intentBtnpleaseselect);


            }
        });
        Btnthank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(intentBtnthank);


            }
        });
        Btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    private class GetFoodImage1 extends AsyncTask<String, Integer, Bitmap> {


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
                bitimage1 = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "이미지 없음1");
            }

            return bitimage1;
        }

        protected void onPostExecute(Bitmap img) {
            food_image1.setImageBitmap(bitimage1);
        }
    }

    private class GetFoodImage2 extends AsyncTask<String, Integer, Bitmap> {


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
                bitimage2 = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "이미지 없음1");
            }

            return bitimage2;
        }

        protected void onPostExecute(Bitmap img) {
            food_image2.setImageBitmap(bitimage2);
        }
    }

    //뒤로가기 종료==================================================================================
    private long time = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //뒤로가기 종료 끝-----------------------------------------------------------------------------
}




