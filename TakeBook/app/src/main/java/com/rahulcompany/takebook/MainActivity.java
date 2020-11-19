package com.rahulcompany.takebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView welcome,to,com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome = findViewById(R.id.wel);
        to = findViewById(R.id.to);
        com = findViewById(R.id.com);
        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView gif = findViewById(R.id.gif);
        Glide.with(MainActivity.this).load(R.raw.load).into(gif);

        Animation translate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
        Animation forwel = AnimationUtils.loadAnimation(MainActivity.this, R.anim.wel);
        Animation forto = AnimationUtils.loadAnimation(MainActivity.this, R.anim.to);
        Animation forcom = AnimationUtils.loadAnimation(MainActivity.this, R.anim.com);

        gif.setAnimation(translate);
        welcome.setAnimation(forwel);
        to.setAnimation(forto);
        com.setAnimation(forcom);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                //Intent i = new Intent(MainActivity.this, sample.class);
                //startActivity(i);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        },10000);

    }

    @Override
    protected void onStart() {




        super.onStart();
    }
}
