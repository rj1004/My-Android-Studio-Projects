package com.rahulcompany.phones;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class Adapter extends BaseAdapter {
    ArrayList<Data> a;
    Context ctx;
    LayoutInflater inflater;

    public Adapter(ArrayList<Data> a, Context ctx) {
        this.a = a;
        this.ctx = ctx;
        inflater=LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return a.size();
    }

    @Override
    public Object getItem(int i) {
        return a.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item, viewGroup, false);
        TextView name = (TextView) view.findViewById(R.id.name);
        final TextView no = (TextView) view.findViewById(R.id.no);
        name.setText(a.get(i).getName());
        no.setText(a.get(i).getNo());
        Button call = (Button) view.findViewById(R.id.call);

        return view;

    }



}
