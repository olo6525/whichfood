package com.coin.whichfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.List;

public class Choicedelivery extends Activity {

    private int where =0;
    private int kind = 0;
    private int foodnum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choicedelivery);
        Intent getfoodinfo = getIntent();
        where = getfoodinfo.getIntExtra("where",0);
        kind = getfoodinfo.getIntExtra("kind",0);
        foodnum = getfoodinfo.getIntExtra("foodnum",0);

        ImageButton selfdelivery = (ImageButton) findViewById(R.id.selfdelivery);
        ImageButton flatformdelivery = (ImageButton) findViewById(R.id.flatformdelivery);

        selfdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goselfdeliverylist = new Intent(Choicedelivery.this, Selfdeliverylist.class);
                goselfdeliverylist.putExtra("where",where);
                goselfdeliverylist.putExtra("kind",kind);
                goselfdeliverylist.putExtra("foodnum",foodnum);
                startActivity(goselfdeliverylist);
                finish();
            }
        });
        flatformdelivery.setOnClickListener(new View.OnClickListener() {
            String packagename = "com.sampleapp";
            @Override
            public void onClick(View v) {
              try {
                  LaunchApp(Choicedelivery.this, packagename);
                  Log.d("TAG", "Choicedelivery App is on the phone");
              }catch (Exception e) {
                  GotoInstall(Choicedelivery.this, packagename);
                  Log.d("TAG", "Choicedelivery App is not on the phone");
              }

                Log.d("TAG","Choicedelivery App "+searchAppPackage(Choicedelivery.this,packagename) );
            }
        });

    }

    public static int searchAppPackage(Context context, String packagename){
        int checkapp = 0;

        PackageManager PM = context.getPackageManager();
        List<PackageInfo> applist;
        Intent intentgetapplist = new Intent(Intent.ACTION_MAIN);
        intentgetapplist.addCategory(Intent.CATEGORY_LAUNCHER);
        applist = PM.getInstalledPackages(0);

        try{
            for(int i =0; i< applist.size(); i++){
                if(applist.get(i).packageName.startsWith(packagename)){
                    checkapp = 1;
                    Log.d("TAG","Choicedelivery Check for");
                    break;
                }else{
                    Log.d("TAG","Choicedelivery Check for: " + applist.get(i) + "numver: " + i);
                }
            }
        }catch (Exception e){
            checkapp = 2;
            Log.d("TAG","Choicedelivery error");
            e.printStackTrace();
        }

        return 1;
    }

    public static void LaunchApp (Context context, String packagename){
        Intent launchapp = context.getPackageManager().getLaunchIntentForPackage(packagename);
        launchapp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(launchapp);
    }

    public static void GotoInstall (Context context, String pakagename ){
        String url = "market://details?id="+pakagename;
        Intent gotoplaystore = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        gotoplaystore.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(gotoplaystore);
    }

}
