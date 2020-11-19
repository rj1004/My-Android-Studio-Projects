package com.example.newsreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.icu.util.RangeValueIterator;
import android.os.AsyncTask;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Headingtask extends AsyncTask<Void,Void,Void> {

    Context ctx;
    TextView score;
    int index;

    StringBuilder sb;
    ProgressDialog pd;


    public Headingtask(Context ctx, TextView score, int index) {
        this.ctx = ctx;
        this.score = score;
        this.index = index;
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
            Document doc1 = Jsoup.connect("https://www.cricbuzz.com/cricket-match/live-scores").get();
            Elements ele1 = doc1.getElementsByClass("cb-col cb-col-100 cb-lv-main");
            Document doc = Jsoup.connect("https://www.cricbuzz.com/cricket-match/live-scores").get();
            Elements ele = doc.getElementsByClass("cb-lv-scr-mtch-hdr inline-block");



           Element e=ele1.get(index);
            if (e != null) {

                sb.append(e.text());
            }
           /**
            Elements e1 = e.getElementsByClass("cb-mtch-lst cb-col cb-col-100 cb-tms-itm");
            Elements scores = e.getElementsByClass("cb-col");
            sb.append(scores.get(0).text());**/

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
