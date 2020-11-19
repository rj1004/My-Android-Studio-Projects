package com.rahulcompany.lifelinesappstore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class loadapptask extends AsyncTask<Void,Void,Void> {

    Context ctx;
    ListView lv;
    ScrollView sv;
    ShimmerFrameLayout shim;


    ArrayList<AppDataHome> data;
    String jsondata;



    public loadapptask(Context ctx, ListView lv, ScrollView sv, ShimmerFrameLayout shim) {
        this.ctx = ctx;
        this.lv=lv;
        this.sv=sv;
        this.shim=shim;
    }

    @Override
    protected void onPostExecute(Void aVoid) {


        final HomeAdapterAppData adapter=new HomeAdapterAppData(ctx, data);
        lv.setAdapter(adapter);
        stopahimshowdata();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ctx, AppDetailActivity.class);
                AppDataHome obj = (AppDataHome) adapterView.getItemAtPosition(i);

                intent.putExtra("AppDataHome",obj);
                ctx.startActivity(intent);
            }
        });
        // todo: lv item click listner to new appdetailactivity


        try {
            Log.d("smaple", "enter");
            final JSONObject obj = new JSONObject(jsondata);
            if (BuildConfig.VERSION_CODE < obj.getInt("version")) {
                AlertDialog dialog=new AlertDialog.Builder(ctx,R.style.AppCompatAlertDialogStyle)
                        .setCancelable(false)
                        .setTitle("A Newer Version Of AppStore is Available")
                        .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    new downloadtask(obj.getInt("appstoresize"), obj.getString("appstore"), ctx).execute();
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

    @Override
    protected Void doInBackground(Void... voids) {
        data = new ArrayList<>();

        URL url;
        InputStream is;
        StringBuilder sb = new StringBuilder();

        try {
            url = new URL("https://rj1004.github.io/jsondata/data.json");
            URLConnection conn = url.openConnection();
            conn.connect();
            is = url.openStream();

            int c;
            while ((c = is.read()) != -1) {
                sb.append((char) c);
            }

            jsondata = sb.toString();


            Log.d("jsondata", jsondata);

            data = JSONparsing.parsehomepage(jsondata);
            Log.d("listsize", String.valueOf(data.size()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //todo : add to json file, parse it and store it to appdata class


        return null;
    }

    void stopahimshowdata() {
        shim.stopShimmer();
        sv.setVisibility(View.GONE);
        lv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sv.setVisibility(View.VISIBLE);
        lv.setVisibility(View.INVISIBLE);
        shim.startShimmer();
    }
}
