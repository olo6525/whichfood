package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

public class PopUp extends Activity {

    FlagClass flagClass;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        TextView notify = (TextView)findViewById(R.id.notify);
        Button btn_update = (Button)findViewById(R.id.button_update);
        Button btn_no = (Button)findViewById(R.id.button_no);
        flagClass = (FlagClass)getApplication();
        notify.setText(flagClass.getServers().get(4));
        //업데이트 버튼 클릭 시 업데이트 페이지로.
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.coin.whichfood"));
                startActivity(intent);
                ActivityCompat.finishAffinity(PopUp.this);
            }
        });
        //취소 버튼 클릭시 그냥 이용
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


//바깥 레이아웃 클릭 못하기-------------------------------------------------------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
//뒤로가기로 종료 금지 -------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
