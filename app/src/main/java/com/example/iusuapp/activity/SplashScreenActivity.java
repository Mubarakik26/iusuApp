package com.example.iusuapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.iusuapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    Animation topAnimation, bottomAnimation;
    ImageView topDesign, logo, bottomDesign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        topDesign=findViewById(R.id.sp_top_design);
        logo=findViewById(R.id.sp_logo);
        bottomDesign=findViewById(R.id.sp_bottom_design);

        topDesign.setAnimation(topAnimation);
        bottomDesign.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }
}