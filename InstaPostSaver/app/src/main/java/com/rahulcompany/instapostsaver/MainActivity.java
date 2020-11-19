package com.rahulcompany.instapostsaver;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Post p;
    EditText userurl;
    FloatingActionButton search;
    Button Download;

    FragmentManager fm;
    String[] perm = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    InterstitialAd inter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Insta Post Saver</font>"));

        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("AdMob", "Imitiliazation Successfull");
            }
        });


        inter = new InterstitialAd(MainActivity.this);
        inter.setAdUnitId("ca-app-pub-5556989253157684/8601722908");
        inter.loadAd(new AdRequest.Builder().build());

        inter.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                inter.loadAd(new AdRequest.Builder().build());
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (inter.isLoaded()) {
                    Log.d("addddd","loaded");
                    inter.show();
                    inter.loadAd(new AdRequest.Builder().build());
                } else if (inter.isLoading()) {
                    Log.d("addddd","ad loading");
                } else {
                    Log.d("addddd","not loaded");
                    inter.loadAd(new AdRequest.Builder().build());
                }
            }
        },7000);



        AdView adView = findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());



        userurl = findViewById(R.id.userurl);
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (manager != null) {
            ClipData data=manager.getPrimaryClip();
            if (data != null) {
                if (data.getItemAt(0) != null) {
                    CharSequence text= manager.getPrimaryClip().getItemAt(0).getText();
                    userurl.setText(text);
                }

            }

        }



        search = findViewById(R.id.search);
        Download = findViewById(R.id.Download);

        search.setOnClickListener(this);
        Download.setOnClickListener(this);
        fm=getSupportFragmentManager();



    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search) {

            if (inter.isLoaded()) {
                Log.d("addddd","loaded");
                inter.show();
                inter.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                       searchwork();
                        inter.loadAd(new AdRequest.Builder().build());
                    }
                });

            } else if (inter.isLoading()) {
                Log.d("addddd","ad loading");
            }
            else {
                Log.d("addddd","not loaded");
                searchwork();
                inter.loadAd(new AdRequest.Builder().build());
            }




        }
        if (view.getId() == R.id.Download) {

            if (inter.isLoaded()) {
                Log.d("addddd","loaded");
                inter.show();
                inter.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                        downloadwork();
                        inter.loadAd(new AdRequest.Builder().build());
                    }
                });
            } else if (inter.isLoading()) {
                Log.d("addddd","ad loading");
            }
            else {
                Log.d("addddd","not loaded");
                downloadwork();
                inter.loadAd(new AdRequest.Builder().build());
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.dev) {
            Dialog dev = new Dialog(MainActivity.this);
            dev.setCancelable(true);
            dev.setContentView(R.layout.dialog_dev);
            FloatingActionButton devcall = (FloatingActionButton) dev.findViewById(R.id.devcall);
            FloatingActionButton devmail = (FloatingActionButton) dev.findViewById(R.id.devmail);

            devcall.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:+91 9157755527"));
                        startActivity(intent);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 200);
                    }
                }
            });

            devmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse("mailto:"));
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"rahulparmar1004@gmail.com"});
                    startActivity(i);
                }
            });
            dev.show();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            Dialog d = new Dialog(MainActivity.this);
            d.setCancelable(true);
            d.setContentView(R.layout.dialog_help);
            d.show();

            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (manager != null) {
                ClipData data=manager.getPrimaryClip();
                if (data != null) {
                    if (data.getItemAt(0) != null) {
                        CharSequence text= manager.getPrimaryClip().getItemAt(0).getText();
                        userurl.setText(text);
                    }

                }

            }
        }
        else {
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("No Internet Connection..")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setCancelable(false).create();

            dialog.show();
        }


        if (ContextCompat.checkSelfPermission(MainActivity.this,perm[0]) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(MainActivity.this,perm[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,perm, 100);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            Log.d("perm", String.valueOf(grantResults.length));
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(MainActivity.this,permissions, 100);
                    break;
                }
            }
        }
        if (requestCode == 200) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(MainActivity.this,permissions, 100);
                    break;
                }
            }
        }
    }


    public void downloadwork() {
        if (p.getUrlimage()!=null) {

            if (p.getUrlVideo() == null) {
                Toast.makeText(MainActivity.this, "Downloading Image", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Downloading Video", Toast.LENGTH_LONG).show();
            }

            p.download(MainActivity.this);
        }
    }

    public void searchwork() {
        if (userurl.getText().toString() != null) {
            p = new Post();
            initTask task = new initTask(MainActivity.this,userurl.getText().toString(),p,fm);
            task.execute();
        }
    }


}

