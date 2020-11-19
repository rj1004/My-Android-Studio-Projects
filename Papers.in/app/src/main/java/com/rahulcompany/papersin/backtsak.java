package com.rahulcompany.papersin;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class backtsak extends AsyncTask<Void,Void,Void> {
    String urlapi;
    ScrollView forshim;
    ShimmerFrameLayout shim;
    Context ctx;
    RecyclerView rv;


    String jsondata;
    ArrayList<Datas> list;

    public backtsak(String url, ScrollView forshim, ShimmerFrameLayout shim, Context ctx,RecyclerView rv) {
        this.urlapi = url;
        this.forshim = forshim;
        this.shim = shim;
        this.ctx = ctx;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        startloading();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (list != null) {
            if (list.size() != 0) {
                Customadapter ca = new Customadapter(list);
                rv.setAdapter(ca);
            } else {
                rv.setAdapter(new Customadapter(new ArrayList<Datas>()));
                Toast.makeText(ctx, "Error Loading", Toast.LENGTH_LONG).show();
            }
        }

        stoploading();
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        InputStream is;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(urlapi);
            URLConnection connection = url.openConnection();
            connection.connect();
            is = url.openStream();
            int c;
            while ((c = is.read()) != -1) {
                sb.append((char) c);
            }

            jsondata = sb.toString();
            list=Jsonparser.fetch(jsondata);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void startloading() {
        forshim.setVisibility(View.INVISIBLE);
        shim.setVisibility(View.VISIBLE);
        shim.showShimmer(true);
    }

    private void stoploading() {
        shim.showShimmer(false);
        shim.setVisibility(View.INVISIBLE);
        forshim.setVisibility(View.VISIBLE);
    }
}
