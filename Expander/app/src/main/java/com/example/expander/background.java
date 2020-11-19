package com.example.expander;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class background extends AsyncTask<String,Integer,Void> {

    Double total_size=0d;
    Context ctx;
    TextView dir,total;
    int count=0;
    ProgressDialog pd;
    ArrayList<String> namelist=  new ArrayList<>();


    public background(Context ctx, TextView dir, TextView total_size) {
        this.ctx = ctx;
        this.dir = dir;
        this.total = total_size;
    }

    @Override
    protected Void doInBackground(String... s) {
        File[] filelist = Environment.getExternalStorageDirectory().listFiles();
        func(filelist,s[0]);
        return null;
    }





    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //pd = ProgressDialog.show(ctx, "Loading", "Please Wait", true, false);
        pd = new ProgressDialog(ctx);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setTitle("Loading");
        //pd.setProgress(0);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.setIndeterminate(false);
        //pd.setIndeterminate(true);
        pd.show();

    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < namelist.size(); i++) {
            String path = namelist.get(i);
            String name = path.substring(path.lastIndexOf("/")+1);

            File f = new File(path);

            double size=((double)f.length())/(1024*1024);
            total_size=total_size+size;
            sb.append(name + "\n-------------------------"+new DecimalFormat(".##").format(size) +" MB-----------\n\n\n");
        }

        dir.setText(sb.toString());
        total.setText("Total : "+new DecimalFormat(".##").format(total_size)+" MB");
        pd.dismiss();
        if (namelist.size() == 0) {
            Toast.makeText(ctx,"Files is not available",Toast.LENGTH_LONG).show();
        }

    }

    void func(File[] filelist,String s) {
        for (int i = 0; i < filelist.length; i++) {

            if (filelist[i].isDirectory()) {
                func(filelist[i].listFiles(),s);
            } else {
                if (filelist[i].getName().endsWith(s)) {
                    namelist.add(filelist[i].getAbsolutePath());
                }
            }
            pd.setMax(filelist.length);
            pd.setProgress(i);
        }
    }

}
