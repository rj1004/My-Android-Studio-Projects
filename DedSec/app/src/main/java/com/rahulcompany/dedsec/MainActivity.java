package com.rahulcompany.dedsec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private  int pass=0;
    String[] perm = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        final ImageView mainload=findViewById(R.id.mainload);
        Glide.with(MainActivity.this).load(R.raw.mainload).into(mainload);

        Button hacker, looser;
        hacker = findViewById(R.id.hacker);
        looser = findViewById(R.id.looser);
        hacker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass == 1) {
                                Intent i = new Intent(MainActivity.this, Editor.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
        looser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass == 1) {
                    Intent i = new Intent(MainActivity.this, CodeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(MainActivity.this, perm, 100);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            int flag = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    flag=1;
                }
            }

            if (flag == 1) {
                ActivityCompat.requestPermissions(MainActivity.this, perm, 100);
            }
            else{
                pass=1;
            }



        }
    }
}