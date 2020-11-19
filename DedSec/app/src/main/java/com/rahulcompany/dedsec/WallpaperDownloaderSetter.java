package com.rahulcompany.dedsec;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Calendar;

public class WallpaperDownloaderSetter extends AsyncTask<Void,Void,Void> {

    String durl;
    Context ctx;


    String path,name;
    File f,dedsec;
    double size;



    public WallpaperDownloaderSetter(String url, Context ctx) {
        this.durl = url;
        this.ctx = ctx;


    }

    @Override
    protected void onPreExecute() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.dedsecwallpaper";
         dedsec= new File(path);
        if (!dedsec.exists()) {
            dedsec.mkdir();
        }
        name = String.valueOf(Calendar.getInstance().getTime().getTime());

        Log.d("stuff", "Downloading");


        super.onPreExecute();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (f != null) {
            WallpaperManager manager = (WallpaperManager) ctx.getSystemService(Context.WALLPAPER_SERVICE);
            try {
                //WallpaperManager.getInstance(ctx).setStream(new FileInputStream(f),null,true,WallpaperManager.FLAG_LOCK);
                manager.setBitmap(BitmapFactory.decodeFile(f.getPath()));
                f.delete();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Log.d("stuffd", "Download Complete");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(durl);
            URLConnection connection = url.openConnection();
            connection.connect();
            Log.d("stuffd", connection.getContentType());
            final String type = connection.getContentType();
            size = connection.getContentLength();
            size = size / (1024*1024);
            final DecimalFormat df = new DecimalFormat("#.##");
            Log.d("stuffd", String.valueOf(size));

            String exe = type.substring(type.indexOf('/') + 1);
            exe=exe.trim();
            exe = "." + exe;

            name = name + exe;



            f = new File(dedsec, name);
            try {
                f.createNewFile();
            } catch (final IOException e) {

                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(f);
            int c=0;
            int counter=0;
            byte[] buffer = new byte[1024];

            while ((c = is.read(buffer)) != -1) {
                os.write(buffer, 0, c);
                counter++;

                if (counter % 1024 == 0) {
                    final int finalCounter = counter;

                }
            }

            is.close();
            os.close();

        } catch (final MalformedURLException e) {

            e.printStackTrace();
        } catch (final IOException e) {

            e.printStackTrace();
        }
        return null;
    }
}
