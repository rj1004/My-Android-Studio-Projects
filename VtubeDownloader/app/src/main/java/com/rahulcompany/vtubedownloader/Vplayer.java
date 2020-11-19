package com.rahulcompany.vtubedownloader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class Vplayer extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vplayer);
        Intent i = getIntent();
        String path = i.getStringExtra("path");
        VideoView player = findViewById(R.id.player);
        MediaController controller = new MediaController(Vplayer.this);
        controller.setAnchorView(player);
        player.setMediaController(controller);
        player.setVideoURI(Uri.fromFile(new File(path)));
        player.start();
    }
}
