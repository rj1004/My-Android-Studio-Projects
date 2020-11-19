package com.rahulcompany.whatsappstatussaver;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    RecyclerView rview;
    InterstitialAd inter;
    TabLayout tabLayout;
    SwipeRefreshLayout swipe;

    String[] perm = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("AdMob", "Imitiliazation Successfull");
            }
        });

        swipe = findViewById(R.id.swipetorefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                refresh();
                swipe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                },3000);
            }
        });
        AdView banner = findViewById(R.id.adView);
        banner.loadAd(new AdRequest.Builder().build());

        inter = new InterstitialAd(MainActivity.this);
        inter.setAdUnitId("ca-app-pub-5556989253157684/3698672598");
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
                    inter.show();
                    inter.loadAd(new AdRequest.Builder().build());
                } else if (inter.isLoading()) {

                } else {
                    inter.loadAd(new AdRequest.Builder().build());
                }
            }
        },5000);




        rview = findViewById(R.id.rview);
        rview.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
        rview.setHasFixedSize(true);


        loadphoto();

        tabLayout = findViewById(R.id.tablayout_staus);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    loadphoto();
                }
                if (tab.getPosition() == 1) {
                    loadvideo();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_help);


        dialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                .setCancelable(true)
                .setTitle(R.string.app_name)
                .setMessage("Do You Want To Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();
                        overridePendingTransition(R.anim.fade_in,R.anim.enter_left);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog d = builder.create();
        d.show();

    }



    private void loadvideo() {
        ArrayList<File> list = new ArrayList<>();
        String Path = Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/.Statuses";
        File f = new File(Path);

        File[] files = f.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().endsWith(".mp4")) {
                    list.add(files[i]);
                }
            }
        }

        if (list.size() > 0) {
            rview.setAdapter(new adapter(list,MainActivity.this,inter));
        } else {
            Toast.makeText(MainActivity.this, "No Videos Found", Toast.LENGTH_LONG).show();
        }
    }

    private void loadphoto() {
        ArrayList<File> list = new ArrayList<>();
        String Path = Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/.Statuses";
        File f = new File(Path);

        File[] files = f.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().endsWith(".jpg")) {
                    list.add(files[i]);
                }
            }
        }

        if (list.size() > 0) {
            rview.setAdapter(new adapter(list,MainActivity.this,inter));
        } else {
            Toast.makeText(MainActivity.this, "No Photos Found", Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        refresh();
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
            loadphoto();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dev:


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



                break;


            case R.id.loadstatus:
                Intent launch = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                if (launch != null) {
                    startActivity(launch);
                } else {
                    Toast.makeText(MainActivity.this, "Sorry...Whatsapp Not Found.", Toast.LENGTH_LONG).show();
                }
                break;


            case R.id.rate:
                final Dialog d = new Dialog(MainActivity.this);
                d.setCancelable(true);
                d.setContentView(R.layout.dialog_rating);
                final TextView ans = d.findViewById(R.id.ans);
                RatingBar ratingBar = d.findViewById(R.id.rating);
                Button submit = d.findViewById(R.id.submit);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        if (v<=5.0 && v>4.0) {
                            ans.setText("That's Awesome !");
                        }
                        if (v <= 4.0 && v > 3.0) {
                            ans.setText("Good !!");
                        }
                        if (v <= 3.0 && v > 2.0) {
                            ans.setText("Nice work..");
                        }
                        if (v <= 2.0 && v >= 1.0) {
                            ans.setText("It's Okk..");
                        }
                        if (v == 0) {
                            ans.setText("Rating");
                        }
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        Toast.makeText(MainActivity.this, "Thanks for Giving Your Rating About App !...",Toast.LENGTH_LONG).show();
                    }
                });

                d.show();
                break;
            case R.id.share:
                String textmsg = "Whatsapp Status Saver..\n It save photo and video from Whatsapp status..\n Here is a link of developer contact to ask The Apk..\n\n\nhttps://wa.me/919157755527?text=Send%20Whatsapp%20Status%20Saver%20App";
                // TODO : change the message
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, textmsg);
                i.setType("text/plain");
                startActivity(Intent.createChooser(i,"Share App Using "));
                break;

            case R.id.help:
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_help);


                dialog.show();
                break;
            default:

        }

        return true;
    }

    void refresh() {
        if (tabLayout.getSelectedTabPosition() == 0) {
            loadphoto();
        } else if (tabLayout.getSelectedTabPosition() == 1) {
            loadvideo();
        }
    }


}


