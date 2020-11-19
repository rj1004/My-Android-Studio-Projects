package com.rahulcompany.dedsec;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.EditText;

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

public class Downloader extends AsyncTask<Void,Void,Void> {

    String durl;
    Context ctx;
    EditText Codeedit;

    String path,name;
    File f,dedsec;
    double size;
    Handler h;


    public Downloader(String url, Context ctx, EditText codeedit) {
        this.durl = url;
        this.ctx = ctx;
        Codeedit = codeedit;
    }

    @Override
    protected void onPreExecute() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DedSec";
         dedsec= new File(path);
        if (!dedsec.exists()) {
            dedsec.mkdir();
        }
        name = String.valueOf(Calendar.getInstance().getTime().getTime());


        super.onPreExecute();
        Codeedit.append("\nDownloading...Please Wait..");
        h = new Handler();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (f != null) {
            Codeedit.append("\nDownload Complete-" + f.getPath());
        }
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
            if (f.exists()) {
                f.delete();
            }
            try {
                f.createNewFile();
            } catch (final IOException e) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Codeedit.append("\n" + e.toString());
                    }
                });

                e.printStackTrace();
            }

            h.post(new Runnable() {
                @Override
                public void run() {
                    Codeedit.append("\nFile-size-" + df.format(size) + " MB --.."+type);
                }
            });
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(f);
            int c=0;
            int counter=0;
            byte[] buffer = new byte[1024];

            while ((c = is.read(buffer)) != -1) {
                os.write(buffer, 0, c);
                counter++;
                Log.d("stuffd", String.valueOf(counter));
                if (counter % 1024 == 0) {
                    final int finalCounter = counter;
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Codeedit.append("\n" + finalCounter / 1024 + " MB/" + df.format(size) + " MB");
                        }
                    });
                }
            }

            is.close();
            os.close();

        } catch (final MalformedURLException e) {
            h.post(new Runnable() {
                @Override
                public void run() {
                    Codeedit.append("\n" + e.toString());
                }
            });
            e.printStackTrace();
        } catch (final IOException e) {
            h.post(new Runnable() {
                @Override
                public void run() {
                    Codeedit.append("\n" + e.toString());
                }
            });
            e.printStackTrace();
        }
        return null;
    }
}
