package com.rahulcompany.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webcalc = (WebView) findViewById(R.id.webcalc);
        webcalc.getSettings().setJavaScriptEnabled(true);
        webcalc.loadUrl("file:///android_asset/index.html");
    }
}
