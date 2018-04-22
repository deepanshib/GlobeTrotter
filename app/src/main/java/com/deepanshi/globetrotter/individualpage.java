package com.deepanshi.globetrotter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class individualpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individualpage);

        final String name= getIntent().getStringExtra("name");
        final String address= getIntent().getStringExtra("address");
        final String description= getIntent().getStringExtra("rating");
        final String description2= getIntent().getStringExtra("review");
        final String photo= getIntent().getStringExtra("photo");

        final String cuisine= getIntent().getStringExtra("cuisine");
        final String cost= getIntent().getStringExtra("cost");
        final String currency= getIntent().getStringExtra("currency");
       ImageView imageheader=(ImageView) findViewById(R.id.image);
       if(photo.equals("")){
           imageheader.setVisibility(View.GONE);
       }
       else {
           Picasso.with(imageheader.getContext())
                   .load(photo)
                   .into(imageheader);
       }
        TextView namee=(TextView)findViewById(R.id.name);
        TextView addresss=(TextView)findViewById(R.id.address);
        TextView rating=(TextView)findViewById(R.id.rating);
        TextView review=(TextView)findViewById(R.id.review);
        TextView costtt=(TextView)findViewById(R.id.cost);
        TextView cuisin=(TextView)findViewById(R.id.cuisin);

        namee.setText(name);
        addresss.setText(address);
        rating.setText(description+"/5");
        review.setText("Avg. Review: "+description2);
costtt.setText("Cost for Two :  "+currency+" "+cost);
cuisin.setText("Cuisines Available :\n "+ cuisine);
    }
}
