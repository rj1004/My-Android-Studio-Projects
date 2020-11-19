package com.rahulcompany.takebook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Queue;

public class DashBoard extends AppCompatActivity {


    BottomNavigationView bnv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        bnv = findViewById(R.id.bnv_main);
        final FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_con, new fragment_public_chat()).commit();

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.post:
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.main_con, new fragment_public_chat()).commit();
                        break;

                    case R.id.group:
                        FragmentTransaction ft1 = fm.beginTransaction();
                        fragment_groupchat fgc = new fragment_groupchat();
                        ft1.replace(R.id.main_con, fgc).commit();
                        break;

                }
                return true;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.help_dash:

                final Dialog d = new Dialog(DashBoard.this);
                d.setCancelable(false);
                d.setContentView(R.layout.help_dashboard_layout);
                Button btn = d.findViewById(R.id.thanks_dash);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                d.show();

                break;
            case R.id.out:
                final Dialog dialog = new Dialog(DashBoard.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_signout);
                Button cancel,signout;
                cancel = dialog.findViewById(R.id.cancel_out);
                signout = dialog.findViewById(R.id.out_out);

                View.OnClickListener listener=new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        switch (view.getId()) {
                            case R.id.out_out:

                                FirebaseAuth.getInstance().signOut();

                                Toast.makeText(DashBoard.this, "LogOut Successfully", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(DashBoard.this, LoginActivity.class);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                startActivity(i);
                                finish();



                                break;
                            case R.id.cancel_out:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                cancel.setOnClickListener(listener);
                signout.setOnClickListener(listener);

                dialog.show();

                break;
        }
        return true;
    }


}
