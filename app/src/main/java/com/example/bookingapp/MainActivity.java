package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static int SPALASH_SCREEN = 5000;
    Animation topAnimation,bottomAnimation;
    ImageView img_anim;
    TextView  txt_bg,txt_sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        img_anim = findViewById(R.id.land_pc);
        txt_bg = findViewById(R.id.txt_landing);
        txt_sm = findViewById(R.id.txt_land_small);

        img_anim.setAnimation(topAnimation);
        txt_sm.setAnimation(bottomAnimation);
        txt_bg.setAnimation(bottomAnimation);

       new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){
           @Override
           public void run() {
               Intent intent = new Intent(MainActivity.this,LoginActivity.class);
               Pair[] pairs =new Pair[2];
               pairs[0] = new Pair<View,String>(img_anim,"img_trs");
               pairs[1] = new Pair<View,String>(txt_bg,"txt_trs");

               ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
               startActivity(intent,options.toBundle());
               finish();

           }
       },SPALASH_SCREEN);
    }
}