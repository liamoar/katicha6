package com.example.welcome.katicha6;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    private ViewPager mviewPager;
    private LinearLayout mdotlayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;

    Button loginbtn,signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        loginbtn= findViewById(R.id.start_btn_login);
        signupbtn=findViewById(R.id.start_btn_signup);

        mviewPager =findViewById(R.id.slide_viewpager);
        mdotlayout = findViewById(R.id.dots);

        sliderAdapter = new SliderAdapter(this);
        mviewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mviewPager.addOnPageChangeListener(viewListner);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

    }

    //for creating dots
    public void addDotsIndicator(int position){
        mDots =new TextView[3];
        mdotlayout.removeAllViews();

        for(int i=0;i<mDots.length;i++){

            mDots[i] =new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.transwhite));
            mdotlayout.addView(mDots[i]);
        }
        if(mDots.length>0){
             mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    //for dots indication of each page
    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
           addDotsIndicator(i);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}