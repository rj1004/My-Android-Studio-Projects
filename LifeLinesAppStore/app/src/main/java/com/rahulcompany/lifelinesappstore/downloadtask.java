package com.rahulcompany.lifelinesappstore;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class downloadtask extends AsyncTask<Void,String,Void> {

    int size;
    String urltodownload;
    Context ctx;

    ProgressDialog d;
    TextView data,perc;



    public downloadtask(int size, String urltodownload, Context ctx) {
        this.size = size;
        this.urltodownload = urltodownload;
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        d = new ProgressDialog(ctx, R.style.AppCompatAlertDialogStyle);
        d.setCancelable(false);
        d.setIndeterminate(false);
        d.setMax(size);
        d.setInverseBackgroundForced(true);
        d.setTitle("Downloading");
        d.setMessage("It May take A While..");
        d.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        d.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        d.dismiss();
        File file=new File(Environment.getExternalStorageDirectory().getPath()+"/.tempappstore/app.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(ctx, ctx.getPackageName() + ".provider", file);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ctx.startActivity(Intent.createChooser(intent,"Install With"));
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/.tempappstore";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        File apk = new File(path + "/app.apk");
        if (!apk.exists()) {
            try {
                apk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            apk.delete();
            try {
                apk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        InputStream is;
        OutputStream os;
        try {
            URL url = new URL(urltodownload);
            is=url.openStream();
            os = new FileOutputStream(apk);

            int c;
            int count=0;

            byte[] b = new byte[1024];
            while ((c = is.read(b)) != -1) {
                os.write(b,0,c);
                d.setProgress(count);
                count++;
            }

            is.close();
            os.close();
            Log.d("count", String.valueOf(count));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
