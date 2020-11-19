package com.rahulcompany.lifelinesappstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ImageView oneiv,twoiv,threeiv,fouriv,mainanim;
    LinearLayout textanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        oneiv = findViewById(R.id.phones_icon);
        twoiv = findViewById(R.id.news_icon);
        threeiv = findViewById(R.id.take_icon);
        fouriv = findViewById(R.id.insta_icon);
        mainanim = findViewById(R.id.main_icon);
        textanim = findViewById(R.id.text_anim);
        Animation one = AnimationUtils.loadAnimation(MainActivity.this, R.anim.one_anim);
        Animation two = AnimationUtils.loadAnimation(MainActivity.this, R.anim.two_anim);
        Animation three = AnimationUtils.loadAnimation(MainActivity.this, R.anim.three_anim);
        Animation four = AnimationUtils.loadAnimation(MainActivity.this, R.anim.four_anim);
        Animation main = AnimationUtils.loadAnimation(MainActivity.this, R.anim.main_anim);
        oneiv.setAnimation(one);
        twoiv.setAnimation(two);
        threeiv.setAnimation(three);
        fouriv.setAnimation(four);
        mainanim.setAnimation(main);
        textanim.setAnimation(main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        },10000);
    }
}
