package com.example.expander;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class background2 extends AsyncTask<String,Void,Void> {




    Double total_size=0d;
    String name;
    File f;
    Context ctx;
    TextView dir,total;
    int count=0;
    ProgressDialog pd;
    ArrayList<String> namelist=  new ArrayList<>();

    public background2(Context ctx, TextView dir, TextView total_size, String name) {
        this.ctx = ctx;
        this.dir = dir;
        this.total = total_size;
        this.name=name;
        f = new File("/storage/" + name);
    }

    @Override
    protected Void doInBackground(String... s) {
        if(f.exists()){
        File[] filelist = f.listFiles();
        func(filelist,s[0]);
        }
        else {
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ctx);
        pd.setTitle("Loading");
        pd.setCancelable(false);
        pd.setIndeterminate(false);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
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
        pd.dismiss();
        dir.setText(sb.toString());
        total.setText("Total : "+new DecimalFormat(".##").format(total_size)+" MB");
        if (f.exists()) {
            if (namelist.size() == 0) {
                Toast.makeText(ctx, "Files is not available", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ctx, "External Storage is not Found for this name", Toast.LENGTH_LONG).show();

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
