package com.rahulcompany.codersio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.github.kbiakov.codeview.CodeView;

public class CodeVIewerActivity extends AppCompatActivity {

    String text;
    CodeView viewer;
    FloatingActionButton fabedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_v_iewer);
        viewer = findViewById(R.id.viewer);
        fabedit = findViewById(R.id.fabedit);

        fabedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CodeVIewerActivity.this, HomeActivity.class);
                intent.putExtra("code", text);
                startActivity(intent);
                finish();
            }
        });
        Intent i = getIntent();
        Uri uri = i.getData();
        if (uri == null) {
            Log.d("stuffuri", "uinull");
        }
        File f = new File(uri.getPath());
        Log.d("stuff", uri.getPath());
        try {
            FileInputStream is = new FileInputStream(f);
            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = is.read()) != -1) {
                sb.append((char) ch);
            }
            text = sb.toString();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("stuffviewr", text);

        viewer.setCode(text);

        getSupportActionBar().setTitle("CodePath - " + f.getName());


    }
}