package com.rahulcompany.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class ImageViewActivity extends AppCompatActivity implements View.OnClickListener {

    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button close,download,share;
        close = findViewById(R.id.photoclose);
        download=findViewById(R.id.photodownload);
        share = findViewById(R.id.photoshare);

        close.setOnClickListener(this);

        download.setOnClickListener(this);
        share.setOnClickListener(this);

        ImageView iv = findViewById(R.id.photoview);
        path =getIntent().getStringExtra("photofilepath");
        iv.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photoclose:
                finish();
                break;
            case R.id.photodownload:
                try {
                    copyfile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.photoshare:
                Uri uri = Uri.parse(path);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_STREAM, uri);
                i.setType("image/*");
                startActivity(Intent.createChooser(i, "Share Image Using"));
                break;
        }
    }

    private void copyfile() throws IOException {

        new copybackground(path, ImageViewActivity.this).execute();
    }

}
