package com.rahulcompany.instapostsaver;

import android.content.Context;

import java.io.File;

public class Post {
    String urlimage;
    String urlVideo;

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getUrlimage() {
        return urlimage;
    }

    public String getUrlVideo() {
        return urlVideo;
    }


    public void download(Context ctx) {
        new DownloadTask(urlimage,urlVideo,ctx).execute();
    }
}
