package com.rahulcompany.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {

    VideoView videoView;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        path = getIntent().getStringExtra("videopath");
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoView = findViewById(R.id.videoview);
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);

        MediaController c = new MediaController(VideoViewActivity.this);
        c.setAnchorView(videoView);

        videoView.setMediaController(c);

        videoView.start();

        Button close, save, share;
        close = findViewById(R.id.videoclose);
        save = findViewById(R.id.videodownload);
        share = findViewById(R.id.videoshare);

        close.setOnClickListener(this);
        save.setOnClickListener(this);
        share.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.videoclose:
                finish();
                break;
            case R.id.videodownload:
                copyfile();
                break;
            case R.id.videoshare:
                Uri uri = Uri.parse(path);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_STREAM, uri);
                i.setType("video/*");
                startActivity(Intent.createChooser(i, "Share Video Using"));
                break;
        }
    }

    private void copyfile() {
        new copybackground(path, VideoViewActivity.this).execute();

    }
}
