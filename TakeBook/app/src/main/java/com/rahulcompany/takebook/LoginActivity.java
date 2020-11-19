package com.rahulcompany.takebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rahulcompany.takebook.R;

public class LoginActivity extends AppCompatActivity {

    BottomNavigationView bnv;

    Fragment login_frag,register_frag;

    FragmentManager fm;
    FragmentTransaction ft;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity_layout);
        bnv = findViewById(R.id.bnv);
        login_frag = new LoginFrag();
        register_frag = new RegisterFrag();

        ctx = LoginActivity.this;


        fm = getSupportFragmentManager();
        ft=fm.beginTransaction();
        ft.replace(R.id.frag_container, login_frag).commit();



        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.login:
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frag_container, login_frag).commit();
                        break;
                    case R.id.register:
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frag_container, register_frag).commit();
                        break;
                }
                return true;
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                final Dialog d = new Dialog(ctx);
                d.setCancelable(false);
                d.setContentView(R.layout.help_dialog_layot);
                Button thanks = d.findViewById(R.id.thanks);
                thanks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                d.show();
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        ConnectivityManager connectivityManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {

                Intent i = new Intent(LoginActivity.this, DashBoard.class);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(i);
                finish();
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
            }


        } else {

            final Dialog d = new Dialog(LoginActivity.this);
            d.setCancelable(false);
            d.setContentView(R.layout.nointernet_layout);
            ImageView iv = d.findViewById(R.id.nointernet);
            Glide.with(LoginActivity.this).load(R.raw.net).into(iv);
            Button btn = d.findViewById(R.id.exit);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d.dismiss();
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                }
            });
            d.show();
        }
        super.onStart();
    }



    }

