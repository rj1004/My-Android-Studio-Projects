package com.rahulcompany.breakingnews;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

public class NoInternet extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet_layout);
        ImageView gif = findViewById(R.id.gif);

        Glide.with(this).load(R.drawable.net).into(gif);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(NoInternet.this,"Connect to the Internet and Restart The app",Toast.LENGTH_LONG).show();
    }
}
