package com.coin.whichfood;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.content.ContentValues.TAG;

public class Partnershippay extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partnership_choice);
        checkpermission();
        Button vippay = (Button)findViewById(R.id.vippay);


        vippay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Partnershippay.this, Registerpartner.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "카메라 앨업 접근 권한 허용 - " + permissions[i]);
                }
            }
        }

    }

    public void checkpermission(){
        String permission = "";

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permission += Manifest.permission.READ_EXTERNAL_STORAGE + "  ";
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permission += Manifest.permission.WRITE_EXTERNAL_STORAGE+ "  ";
        }

        if(TextUtils.isEmpty(permission)==false){
            ActivityCompat.requestPermissions(this, permission.trim().split("  "),1);
        }else{
            Toast.makeText(this,"모든 카메라, 앨범 접근권한 승인 확인",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
