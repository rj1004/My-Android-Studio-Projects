package com.rahulcompany.papersin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class remotetask extends AsyncTask<Void,Void,Void> {

    String jsondata=null;
    Context ctx;

    public remotetask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (jsondata != null) {
            try {
                final JSONObject obj = new JSONObject(jsondata);
                if (BuildConfig.VERSION_CODE < obj.getInt("version")) {
                    AlertDialog dialog=new AlertDialog.Builder(ctx)
                            .setCancelable(false)
                            .setTitle("A Newer Version Of Papers.in is Available")
                            .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(obj.getString("apkurl")));
                                        ctx.startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            })
                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    System.exit(0);
                                }
                            })
                            .create();

                    dialog.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        InputStream is;
        URL url = null;
        try {
            url = new URL("https://rj1004.github.io/jsondata/remotePapersin.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            is = url.openStream();
            StringBuilder sb = new StringBuilder();

            int c;
            while ((c = is.read()) != -1) {
                sb.append((char) c);
            }

            is.close();
            jsondata = sb.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
