package com.example.newsreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Titles extends AsyncTask<Void,Void,Void> {

    StringData data;
    Context ctx;
    TextView score;

    int count=0;

    StringBuilder sb;
    ProgressDialog pd;
    public Titles( Context ctx, TextView score,StringData data) {
        this.data = data;
        this.ctx = ctx;
        this.score = score;
        sb = new StringBuilder();
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.setMessage("Fetching Updates");

        pd.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        score.setText(sb.toString());
        pd.dismiss();
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            /**Document doc = Jsoup.connect("https://www.cricbuzz.com/cricket-match/live-scores").get();
            Elements ele = doc.getElementsByClass("cb-col cb-col-100 cb-lv-main");


            for (Element e:ele
                 ) {
                Elements t=e.getElementsByTag("h2");
                for (Element title:t
                     ) {
                    String tit=title.attr("title");
                    sb.append(count + " -- " + tit + "\n");
                    data.data[count] = tit;
                    count++;
                }

            }**/
            Document doc = Jsoup.connect("https://www.cricbuzz.com/cricket-match/live-scores").get();
            Elements ele = doc.getElementsByClass("cb-lv-scr-mtch-hdr inline-block");

            for (Element e:ele
                 ) {
                String tit=e.text();
                sb.append(count + " -- " + tit + "\n");
                data.data[count] = tit;
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

