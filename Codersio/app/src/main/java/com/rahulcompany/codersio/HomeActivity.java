package com.rahulcompany.codersio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    FragmentManager fm;
    Fragment ide,dev,lang,news,program;
    int Container;
    String t = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fm = getSupportFragmentManager();
        Container = R.id.contain;

        Intent i = getIntent();
        String code = i.getStringExtra("code");
        if (code == null) {
            Log.d("stuff", "code null");
        } else {
            t = code;
        }

        ide = new IDEFragment();
        dev = new devFragment();
        lang = new langFragment();
        news = new newsFragment();
        program = new programFragment();

        Bundle b=new Bundle();
        b.putString("code", t);
        ide.setArguments(b);
        fm.beginTransaction().replace(Container, ide).commit();

        bnv = findViewById(R.id.bnvbar);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dev:
                        fm.beginTransaction().replace(Container, dev).commit();
                        break;
                    case R.id.ide:
                        fm.beginTransaction().replace(Container, ide).commit();
                        break;
                    case R.id.lang:
                        fm.beginTransaction().replace(Container, lang).commit();
                        break;
                    case R.id.news:
                        fm.beginTransaction().replace(Container, news).commit();
                        break;
                    case R.id.program:
                        fm.beginTransaction().replace(Container, program).commit();
                        break;
                }
                return true;
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        checkPerm();

    }
    private void checkPerm() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else {

        }
    }

    /**@Override
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

            }
        }
    }**/

    @Override
    public void onBackPressed() {
        AlertDialog ad=new AlertDialog.Builder(HomeActivity.this)
                .setMessage("Do You Want To Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HomeActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();

        ad.show();
    }
}