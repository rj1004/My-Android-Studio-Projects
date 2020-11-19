package com.rahulcompany.instapostsaver;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    String url;
    VideoView videoView;

    public VideoFragment(String video) {
        url=video;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_video, container, false);
        videoView = v.findViewById(R.id.videoview);
        MediaController controller = new MediaController(getContext());
        controller.setAnchorView(videoView);
        final ProgressBar pb = v.findViewById(R.id.pdloading);

        videoView.setMediaController(controller);
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
        Log.d("video", "enter");
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                pb.setVisibility(View.GONE);
            }
        });
        return v;
    }

}
