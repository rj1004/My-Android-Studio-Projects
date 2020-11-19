package com.rahulcompany.instapostsaver;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

public class DownloadTask extends AsyncTask<Void, Void, Void> {
    String urlimage,urlvideo;
    Context ctx;
    String path;

    ProgressDialog pd;
    public DownloadTask(String urlimage, String urlVideo, Context ctx) {
        this.urlimage=urlimage;
        this.urlvideo = urlVideo;
        this.ctx = ctx;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        URL url = null;
        File file = null;
        path= Environment.getExternalStorageDirectory().getPath() + "/Insta Post Saver";
        if (!new File(path).exists()) {
            new File(path).mkdir();
        }
        if (urlvideo != null) {
            try {
                url = new URL(urlvideo);
                path = path + "/" + Calendar.getInstance().getTime().getTime() + ".mp4";
                file = new File(path);
                file.createNewFile();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                url = new URL(urlimage);
                path = path + "/" + Calendar.getInstance().getTime().getTime() + ".jpg";
                file = new File(path);
                file.createNewFile();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(file);

            pd.setMax(is.available());

            int c = 0;
            while ((c = is.read()) != -1) {
                os.write(c);
            }


            is.close();
            os.close();
            Log.d("download", "Success");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(ctx);
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.setTitle("Downloading");
        pd.setMessage("It will take Some Amount Of time..");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        Toast.makeText(ctx, path, Toast.LENGTH_LONG).show();
        super.onPostExecute(aVoid);
    }
}
