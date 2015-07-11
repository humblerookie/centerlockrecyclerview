package com.demo;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity{
    RecyclerView listView;
    ArrayList<SampleBean> mData = new ArrayList<SampleBean>();
    View header,footer,btngroup,plusbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        header=findViewById(R.id.appbar);
        footer=findViewById(R.id.footer);
        btngroup=findViewById(R.id.buttongroup);
        plusbutton=findViewById(R.id.plusbutton);
        plusbutton.setOnClickListener(new View.OnClickListener() {
            int from=45; boolean rev=false;
            @Override
            public void onClick(View v) {

                plusbutton.animate().rotation(-from).setDuration(300);
                btngroup.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,rev?R.anim.animout:R.anim.animoutset));
                if(rev){
                    btngroup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btngroup.setVisibility(View.GONE);
                        }
                    },299);
                }
                else btngroup.setVisibility(View.VISIBLE);
                rev=!rev;
                from+=45;
                from%=360;
            }
        });
        setSupportActionBar((android.support.v7.widget.Toolbar) header);
        ((Toolbar)header).setTitle("Random Stuff 1 Does");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Dummy Data
        for(int i=0;i<50;i++){
            mData.add(new SampleBean().setTitle(i+"").setNormal(i%6!=0 || i==0));

        }
        listView= (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager lm=new LinearLayoutManager(this);
        listView.setLayoutManager(lm);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        listView.setAdapter(new MainAdapter(mData, MainActivity.this, width));
        header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            boolean once=true;
            @Override
            public void onGlobalLayout() {
                if(once){
                    once=false;
                    listView.setOnScrollListener(new ScrollCallbacks() {
                        @Override
                        public void onHide() {
                            header.animate().translationY(-header.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) footer.getLayoutParams();
                            int fabBottomMargin = lp.bottomMargin;
                            footer.animate().translationY(footer.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
                        }

                        @Override
                        public void onShow() {
                            header.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                            footer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                        }
                    }.setThreshold(header.getHeight()));
                }

            }
        });


    }




}
