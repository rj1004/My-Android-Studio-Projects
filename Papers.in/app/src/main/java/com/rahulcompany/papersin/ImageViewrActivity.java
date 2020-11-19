package com.rahulcompany.papersin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class ImageViewrActivity extends AppCompatActivity {

    RelativeLayout rel;
    ShimmerFrameLayout shim;

    ImageView iv;
    FloatingActionButton fab;
    Datas obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewr);


        init();

        showloading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stoploading();

            }
        }, 5000);

    }

    private void showloading() {
        shim.setVisibility(View.VISIBLE);
        rel.setVisibility(View.INVISIBLE);
        shim.showShimmer(true);

    }

    private void stoploading() {
        shim.showShimmer(false);
        shim.setVisibility(View.INVISIBLE);
        rel.setVisibility(View.VISIBLE);
    }

    private void init() {
        Intent i = getIntent();
        obj = (Datas) i.getSerializableExtra("data");
        Log.d("sample", String.valueOf(obj.getSize()));

        rel = findViewById(R.id.i_rel);
        shim = findViewById(R.id.i_shim);
        iv = findViewById(R.id.iv);
        fab = findViewById(R.id.fab);
        Toast.makeText(ImageViewrActivity.this, "Loading HD Wallpaper..\nPlease Wait",Toast.LENGTH_LONG).show();
        Glide.with(ImageViewrActivity.this).load(obj.getOrignalurl()).into(iv);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPerm();
            }
        });
    }

    private void checkPerm() {
        if (ContextCompat.checkSelfPermission(ImageViewrActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(ImageViewrActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ImageViewrActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else {
            download();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            int flag=0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    flag=1;
                }
            }

            if (flag == 1) {
                checkPerm();
            } else {
                download();
            }
        }
    }

    void download() {
        new downloadtask(obj.getOrignalurl(),obj.getSize(), ImageViewrActivity.this).execute();

    }
}
