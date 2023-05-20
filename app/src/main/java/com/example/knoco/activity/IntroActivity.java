package com.example.knoco.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.knoco.R;
import com.example.knoco.adapter.IntroViewPagerAdapter;
import com.example.knoco.model.IntroData;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager2 screenItem ;
    public IntroViewPagerAdapter introAdapter  ;
    private LinearLayout indicator ;
    private Button next ;
    private Button pre ;
    private TextView skiptxt ;
    int key ;

    public void BindingData(){
        indicator = findViewById(R.id.indicator);
        screenItem = findViewById(R.id.intro);
        introAdapter = new IntroViewPagerAdapter(ListData());
        screenItem.setAdapter(introAdapter);
        next = findViewById(R.id.nextBtn);
        pre = findViewById(R.id.preBtn);
        skiptxt = findViewById(R.id.Skiptxt);
    }

    public List<IntroData> ListData(){
        List<IntroData> mlist = new ArrayList<>();

        mlist.add(new IntroData("About us","Dear readers,\n" +
                "\n" +
                " We likewise move in immense accumulation of Investments, references books proposed by various foundations as scheduled the nation over",R.drawable.foucalut));
        mlist.add(new IntroData("Knowledge","Learning gives creativity, Creativity leads to thinking, Thinking provides knowledge, and Knowledge makes you alive",R.drawable.job));
        mlist.add(new IntroData("Communication","Communis is a Latin word, which means common. The common component in all kinds of interactions and communication is understanding. ",R.drawable.lenin));
        mlist.add(new IntroData("let's get started","Life is a journey, and if you fall in love with the journey, you will be in love forever.",R.drawable.socrates));


        return  mlist;
    }
    public  void BindingAction(){

        setUpIndicator();
        screenItem.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position==3){
                    key = 1;
                }
                else if(position==0){
                    key = 0;
                }
                else {

                    key=3 ;
                }
                setCurrentIndicator(position);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key==1){
                    Intent myIntent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(myIntent);
                }
                else{
                    screenItem.setCurrentItem(screenItem.getCurrentItem()+1);
                }
            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key==0){
                   // Intent myIntent = new Intent(IntroActivity.this, login.class);
                  //  startActivity(myIntent);
                }
                else{
                    screenItem.setCurrentItem(screenItem.getCurrentItem()-1);
                }
            }
        });
        skiptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        BindingData();
        BindingAction();

    }

    private void setUpIndicator(){
        ImageView[] indi = new ImageView[4];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i = 0 ; i<indi.length;i++){
            indi[i] = new ImageView(getApplicationContext());
            indi[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.ic_baseline_circle_12
            ));
            indi[i].setLayoutParams(layoutParams);
            indicator.addView(indi[i]);
        }
    }

    private void setCurrentIndicator(int index){
        int childCount = indicator.getChildCount();

        for(int i = 0; i<childCount;i++){
            ImageView imageView = (ImageView) indicator.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_circle_12_choose)
                );
            }
            else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_circle_12)
                );
            }
        }
    }
}