package com.rahulcompany.breakingnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String u1 = "http://newsapi.org/v2/top-headlines?apiKey=1d988c8382a2475c8fbe11f49c1777fa&country=";
    String u2 = "in";
    String u3 = "";
    String u4 ="";
    String nation,cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(MainActivity.this);
        listView = findViewById(R.id.list);
        Button btn = findViewById(R.id.btn);


        final InterstitialAd interstitialAd = new InterstitialAd(MainActivity.this);
        interstitialAd.setAdUnitId("ca-app-pub-5556989253157684/3534472321");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this).setContentText("hi").setContentTitle("hello").setSmallIcon(R.drawable.icon_foreground);

                NotificationManager manager = (NotificationManager) getSystemService(MainActivity.this.NOTIFICATION_SERVICE);
                manager.notify(1, builder.build());
                **/

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }

                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                        final Dialog d = new Dialog(MainActivity.this);
                        d.setContentView(R.layout.dialog_layout);
                        final Spinner country,Category;
                        country = d.findViewById(R.id.country);
                        Category = d.findViewById(R.id.cat);

                        Button set = d.findViewById(R.id.set);

                        set.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                nation=country.getSelectedItem().toString();
                                cat = Category.getSelectedItem().toString();
                                nation = nation.substring(nation.indexOf('(')+1, nation.indexOf(')'));
                                u2 = nation;
                                u3 = "&category=";
                                u4 = cat;

                                Log.d("nation", nation +"  "+ cat);
                                d.dismiss();
                                load();
                            }
                        });
                        d.show();
                        interstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });


            }
        });

        load();

        final SwipeRefreshLayout ref = findViewById(R.id.ref);
        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                ref.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ref.setRefreshing(false);
                    }
                },3000);
            }
        });



    }

    void load() {
        String Resultdata = null;
        Fetching fetching = new Fetching(MainActivity.this, u1 + u2 + u3+ u4, Resultdata,listView);
        fetching.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Context ctx = MainActivity.this;
        ConnectivityManager connectivityManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        AdView banner = findViewById(R.id.banner);

        banner.loadAd(new AdRequest.Builder().build());


        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

        } else {

            Intent i = new Intent(MainActivity.this, NoInternet.class);
            startActivity(i);
        }
    }


}
