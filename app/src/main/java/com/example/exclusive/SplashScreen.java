package com.example.exclusive;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        logo = (ImageView) findViewById(R.id.logo);
        //logoConstraint fly animation object
        Animation logoFly = AnimationUtils.loadAnimation(this, R.anim.fly);
        //start logoConstraint animation
        //logo.setAnimation(logoFly);
       new Handler().postDelayed(new Runnable(){
           @Override
           public void run(){
               Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
               startActivity(intent);
               finish();
           }
       },1499);
    }
}