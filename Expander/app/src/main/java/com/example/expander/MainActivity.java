package com.example.expander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView dir,total_size;
    private Button btn;
    private EditText type,sdcard;
    private RadioButton i,x;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dir = (TextView) findViewById(R.id.dir);
        total_size = (TextView) findViewById(R.id.total_size);
        btn = (Button) findViewById(R.id.btn);
        type = (EditText) findViewById(R.id.type);
        sdcard = (EditText) findViewById(R.id.sdcard);

        sp = getSharedPreferences("Data", getApplicationContext().MODE_PRIVATE);
        i = (RadioButton) findViewById(R.id.internal);
        x = (RadioButton) findViewById(R.id.external);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = type.getText().toString();
                if (i.isChecked()) {
                    background b = new background(MainActivity.this, dir,total_size);
                    b.execute(s);

                } else if (x.isChecked()) {
                    SharedPreferences.Editor editor = sp.edit();

                    String name=sdcard.getText().toString();
                    editor.putString("Data", name);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"External Storage name-" + name +"-Remember",Toast.LENGTH_LONG).show();
                    background2 b2 = new background2(MainActivity.this, dir,total_size,name);
                    b2.execute(s);
                }


            }
        });
    }


    public void show(View view) {
        sdcard.setVisibility(View.VISIBLE);
    }

    public void dismis(View view) {
        sdcard.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String[] permissions=new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        checkPermission(permissions);
        if (sp.contains("Data")) {
            sdcard.setText(sp.getString("Data",""));
        }
    }

    private void checkPermission(String [] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permissions[i]) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 200);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==200) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(MainActivity.this,permissions,200);
                }
            }
        }
    }
}

