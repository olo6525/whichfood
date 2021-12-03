package com.coin.whichfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Aikind extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aikind);

        ImageButton weight = (ImageButton) findViewById(R.id.weightfood);
        ImageButton diet = (ImageButton) findViewById(R.id.dietfood);
        ImageButton healty = (ImageButton) findViewById(R.id.healthyfood);

        Intent weightintent = new Intent(this,Weight_choice.class);
        Intent dietintent = new Intent(this,MainActivity.class);
        Intent healtyintent = new Intent(this, Healthy_choice.class);

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(weightintent);
            }
        });
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(dietintent);
            }
        });
        healty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(healtyintent);
            }
        });



    }

}
