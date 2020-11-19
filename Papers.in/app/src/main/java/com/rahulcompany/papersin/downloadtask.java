package com.rahulcompany.papersin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.chip.Chip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class downloadtask extends AsyncTask<Void, Void, Void> {

    String url;
    int size;
    Context ctx;
    Dialog d;
    TextView progress;

    Handler h;

    File image;

    public downloadtask(String url, int size, Context ctx) {
        this.url = url;
        this.size = size;
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        h = new Handler();
        d = new Dialog(ctx);
        d.setContentView(R.layout.dialog_preview);
        TextView title = d.findViewById(R.id.dp_help);
        String text = "<font color=#25BDD3>Dow</font><font color=#F19F0B>nlo</font><font color=#E96147>adi</font><font color=#E64E38>ng</font>";
        title.setText(Html.fromHtml(text));
        progress = d.findViewById(R.id.dp_con);
        d.setCancelable(false);

        d.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        d.dismiss();
        final Dialog abc = new Dialog(ctx);
        abc.setCancelable(true);
        abc.setContentView(R.layout.dialog_setas);

        TextView textView = abc.findViewById(R.id.sa_title);
        String s = "<font color=#25BDD3>Se</font><font color=#F19F0B>t a</font><font color=#E96147>s.</font><font color=#E64E38>..</font>";

        textView.setText(Html.fromHtml(s));

        Chip no, yes;
        no = abc.findViewById(R.id.sa_no);
        yes = abc.findViewById(R.id.sa_yes);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                abc.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                abc.dismiss();
                WallpaperManager manager = (WallpaperManager) ctx.getSystemService(Context.WALLPAPER_SERVICE);
                try {
                    manager.setBitmap(BitmapFactory.decodeFile(image.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(ctx, "Wallpaper Set.", Toast.LENGTH_LONG).show();
            }
        });
        abc.show();
        Toast.makeText(ctx, "Saved at" + image.getPath(), Toast.LENGTH_LONG).show();
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        InputStream is;
        OutputStream os;
        try {
            URL u = new URL(url);
            is = u.openStream();

            File f = new File(Environment.getExternalStorageDirectory().getPath() + "/Papers");
            if (!f.exists()) {
                f.mkdir();
            }

            Log.d("size", String.valueOf(size));

            image=new File(f, "papers_"+String.valueOf(Calendar.getInstance().getTime().getTime())+".jpg");
            if (!image.exists()) {
                image.createNewFile();
            }

            os=new FileOutputStream(image);
            int c;
            int count=0;
            byte[] b = new byte[1024];
            while ((c = is.read(b)) != -1) {
                count++;
                os.write(b,0,c);


                final int finalCount = count;
                h.post(new Runnable() {
                    @Override
                    public void run() {

                        String s = String.valueOf(finalCount)+" KB";
                        progress.setText(s);
                    }
                });
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
