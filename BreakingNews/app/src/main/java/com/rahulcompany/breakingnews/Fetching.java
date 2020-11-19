package com.rahulcompany.breakingnews;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Fetching extends AsyncTask<Void, Void, Void> {
    Context ctx;
    String URLdata, Resultdata;
    ListView listView;

    ProgressDialog pd;

    ArrayList<Data> a;
    InterstitialAd ad;



    public Fetching(Context ctx, String URLdata, String resultdata, ListView listView) {
        this.ctx = ctx;
        this.URLdata = URLdata;
        Resultdata = resultdata;
        this.listView = listView;
        ad=new InterstitialAd(ctx);
        ad.setAdUnitId("ca-app-pub-5556989253157684/3534472321");

    }

    @Override
    protected void onPreExecute() {
        ad.loadAd(new AdRequest.Builder().build());
        pd = new ProgressDialog(ctx);
        pd.setMessage("Loading");
        pd.setIndeterminate(true);
        pd.setCancelable(false);

        pd.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if (a != null) {
            Adapter adapter = new Adapter(a, ctx);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                if (ad.isLoaded()) {
                    ad.show();
                } else {
                    ad.loadAd(new AdRequest.Builder().build());
                    Intent intent = new Intent(ctx, Web.class);
                    intent.putExtra("url", a.get(i).getUrl());
                    ctx.startActivity(intent);
                }

            }
        });

        pd.dismiss();
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {


        try {
            URL url = new URL(URLdata);
            URLConnection conn = url.openConnection();


            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            StringBuilder sb = new StringBuilder();
            int count;
            while ((count = isr.read()) != -1) {
                sb.append((char) count);
            }

            Resultdata = sb.toString();

            a = Parser.parsing(Resultdata);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}


/**
 TextView sample = findViewById(R.id.sample);
 String data=null;
 BTurlToData bt = new BTurlToData(MainActivity.this,"https://rj1004.github.io/jsondata/data.json",data,sample);
 bt.execute();


 user permission is required in manifest----------
 **/