package com.rahulcompany.vtubedownloader.task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.rahulcompany.vtubedownloader.R;
import com.rahulcompany.vtubedownloader.Vplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class downloadvideo extends AsyncTask<String,Void,Void> {

    Context ctx;

    ProgressDialog pd;

    File output;
    public downloadvideo(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setTitle("Downloading");
        pd.setMessage("Please Wait");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setIndeterminate(false);
        pd.show();

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        final AlertDialog dialog;

        if (output == null) {
            Toast.makeText(ctx, "Something Went Wrong", Toast.LENGTH_LONG).show();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("Saved at---" + output.getPath());
            builder.setPositiveButton("Play", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("listen", "play");
                    Intent intent = new Intent(ctx, Vplayer.class);
                    intent.putExtra("path", output.getPath());
                    ctx.startActivity(intent);

                }

            });
            builder.setNegativeButton("Share", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("listen", "share");
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("video/*");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(output.getPath()));
                    ctx.startActivity(Intent.createChooser(intent, "Share Video Using"));
                }
            });

            dialog = builder.create();
            dialog.show();
        }
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            URLConnection conn = url.openConnection();
            int size = conn.getContentLength();
            int orignal = size / 1024;
            pd.setMax(orignal);

            String Path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Vtube Downloads";

            String name;
            if (strings[1] == "true") {
                 name= "vtube_" + Calendar.getInstance().getTime().getTime() + ".mp4";

            } else {
                name = "vtube_" + Calendar.getInstance().getTime().getTime() + ".mp3";
            }
            File file = new File(Path);
            if (!file.exists()) {
                file.mkdir();
            }
            output= new File(Path, name);
            if (!output.exists()) {
                try {
                    output.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(output);

            int c=0;
            int counter=0;
            byte[] buffer = new byte[1024];

            while ((c = is.read(buffer)) != -1) {
                os.write(buffer, 0, c);
                counter++;
                pd.setProgress(counter);
            }

            is.close();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
