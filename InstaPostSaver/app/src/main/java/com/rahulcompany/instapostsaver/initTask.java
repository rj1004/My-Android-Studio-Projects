package com.rahulcompany.instapostsaver;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class initTask extends AsyncTask<Void,Void,Void> {

    ProgressDialog pd;

    Context ctx;
    String url;

    Post p;
    FragmentManager fm;

    String image=null,video=null;

    public initTask(Context ctx, String url,Post p,FragmentManager fm) {
        this.ctx = ctx;
        this.url = url;
        this.fm = fm;
        this.p=p;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setTitle("Loading");
        pd.setMessage("Searching For Posts");
        pd.setIndeterminate(true);
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        p.setUrlimage(image);
        p.setUrlVideo(video);
        if (video == null) {
            fm.beginTransaction().replace(R.id.container, new ImageFragment(image)).commit();
        } else {
            Log.d("video", "hi");
            fm.beginTransaction().replace(R.id.container, new VideoFragment(video)).commit();
        }
        pd.dismiss();
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL web = new URL(url);
            Document doc=Jsoup.connect(url).get();
            Elements element = doc.getElementsByTag("meta");
            Element main;
            for (Element e : element
            ) {

                if (e.attr("property").matches("og:image")) {
                    Log.d("test", e.attr("content"));
                    image = e.attr("content");
                }
                if (e.attr("property").matches("og:video")) {
                    Log.d("test", e.attr("content"));
                    video = e.attr("content");
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
