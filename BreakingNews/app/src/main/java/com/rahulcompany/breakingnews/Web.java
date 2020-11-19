package com.rahulcompany.breakingnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Web extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        AdView banner = findViewById(R.id.banner_ad);
        banner.loadAd(new AdRequest.Builder().build());


        WebView web = findViewById(R.id.web);
        final TextView loading = findViewById(R.id.loading);

        loading.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);
            }
        },10000);
        Intent i = getIntent();
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        String url=i.getStringExtra("url");
        web.loadUrl(url);

    }
}
