package com.rahulcompany.codersio;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.text.DecimalFormat;

public class FileManagerAdapter extends BaseAdapter {

    File[] files;

    public FileManagerAdapter(File[] files) {
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public Object getItem(int i) {
        return files[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filesample, viewGroup, false);
        ImageView ic = v.findViewById(R.id.ic);
        TextView name = v.findViewById(R.id.fmname);
        TextView size = v.findViewById(R.id.fmsize);


        name.setText(files[i].getName());
        if (files[i].isFile()) {
            double dsize=files[i].length();
            dsize=dsize/1024.00;
            dsize=dsize/1024.00;
            DecimalFormat df = new DecimalFormat("#.##");
            size.setText(df.format(dsize)+" MB");
            if (dsize < 1) {
                dsize=dsize*1024;
                size.setText(df.format(dsize)+" KB");
            }
            if (dsize < 1) {
                dsize = dsize * 1024;
                size.setText(df.format(dsize)+" Bytes");
            }
            ic.setImageDrawable(viewGroup.getContext().getDrawable(R.drawable.ic_baseline_insert_drive_file_24));
        }


        return v;
    }
}
