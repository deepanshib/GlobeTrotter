package com.deepanshi.globetrotter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
ImageButton restro,currency,tourist,wea,hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restro=(ImageButton)findViewById(R.id.bt2);
        restro.setOnClickListener(this);
        hotel=(ImageButton)findViewById(R.id.bt3);
        hotel.setOnClickListener(this);
       currency=(ImageButton)findViewById(R.id.bt6);
       currency.setOnClickListener(this);
        tourist=(ImageButton)findViewById(R.id.bt1);
        tourist.setOnClickListener(this);
        wea=(ImageButton)findViewById(R.id.bt5);
        wea.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    }
    public void onClick(View v){
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearLayout);

        Animation animm = AnimationUtils.loadAnimation(this, R.anim.slideleft);
        linearLayout.startAnimation(animm);
        LinearLayout linearLayout2=(LinearLayout)findViewById(R.id.linearLayout2);
        Animation animm2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideright);
        linearLayout2.startAnimation(animm2);

        switch(v.getId()){
            case R.id.bt1:
                startActivity(new Intent(this,interest.class));
                break;
            case R.id.bt2 :
                startActivity(new Intent(this,restaurants.class));
                break;
            case R.id.bt3 :
                startActivity(new Intent(this,Hotels.class));
                break;
            case R.id.bt5 :
                startActivity(new Intent(this,weather.class));
                break;
            case R.id.bt6:
                startActivity(new Intent(this,currencyconvertr.class));

        }
    }

}
