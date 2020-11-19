package com.rahulcompany.whatsappstatussaver;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class copybackground extends AsyncTask<Void,Void,Void> {

    String path;
    Context ctx;
    File test;

    ProgressDialog pd;
    public copybackground(String path, Context ctx) {
        this.path = path;
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(ctx);
        pd.setIndeterminate(true);
        pd.setTitle("Saving");
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        Toast.makeText(ctx, "Save to "+test.getPath(), Toast.LENGTH_SHORT).show();
        super.onPostExecute(aVoid);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected Void doInBackground(Void... voids) {
        try {
        String destpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Whatsapp statuses";
        File src = new File(path);
        File dir=new File(destpath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        Log.d("file1", String.valueOf(dir.exists()));
        File dest = null;
            if (src.getName().endsWith(".jpg")) {
                dest = new File(destpath + "/" + String.valueOf(Calendar.getInstance().getTime().getTime()) + ".jpg");
            }
            if (src.getName().endsWith(".mp4")) {
                dest = new File(destpath + "/" + String.valueOf(Calendar.getInstance().getTime().getTime()) + ".mp4");
            }

        if (!dest.exists()) {

                dest.createNewFile();

        }
        Log.d("file2", String.valueOf(dest.exists()));

        InputStream is = new FileInputStream(src);
        OutputStream os = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];
        int c;
        while ((c = is.read(buffer)) != -1) {
            os.write(buffer,0,c);
        }

        is.close();
        os.close();


        test=dest;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
