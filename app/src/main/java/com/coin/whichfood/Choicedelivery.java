package com.coin.whichfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
            @Override
            public void onClick(View v) {
                if(searchAppPackage(Choicedelivery.this,"com.sampleapp")==true){
                    LaunchApp(Choicedelivery.this, "com.sampleapp");
                }else{
                    GotoInstall(Choicedelivery.this,"com.sampleapp");
                }
            }
        });

    }

    public static boolean searchAppPackage(Context context, String packagename){
        boolean checkapp = false;

        PackageManager PM = context.getPackageManager();
        List<ResolveInfo> applist;
        Intent intentgetapplist = new Intent(Intent.ACTION_MAIN, null);
        intentgetapplist.addCategory(Intent.CATEGORY_LAUNCHER);
        applist = PM.queryIntentActivities(intentgetapplist, 0 );

        try{
            for(int i =0; i< applist.size(); i++){
                if(applist.get(i).activityInfo.packageName.startsWith(packagename)){
                    checkapp = true;
                    break;
                }
            }
        }catch (Exception e){
            checkapp = false;
            e.printStackTrace();
        }

        return checkapp;
    }

    public static void LaunchApp (Context context, String packagename){
        Intent launchapp = context.getPackageManager().getLaunchIntentForPackage(packagename);
        launchapp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launchapp);
    }

    public static void GotoInstall (Context context, String pakagename ){
        String url = "market://details?id="+pakagename;
        Intent gotoplaystore = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        gotoplaystore.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(gotoplaystore);
    }

}
