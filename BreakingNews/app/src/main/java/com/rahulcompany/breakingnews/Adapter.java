package com.rahulcompany.breakingnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    ArrayList<Data> list;
    Context ctx;

    public Adapter(ArrayList<Data> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v=inflater.inflate(R.layout.list_item, viewGroup, false);


        Data d = (Data)list.get(i);
        TextView tv = v.findViewById(R.id.title_data);
        TextView time = v.findViewById(R.id.time);
        TextView desc = v.findViewById(R.id.desc);
        TextView auther = v.findViewById(R.id.author);


        tv.setText(d.getTitle());
        time.setText(d.getTime());
        desc.setText(d.getDesc());
        auther.setText(d.getAuther());


        return v;
    }
}


/**

 change to your Layout------
 and elements-----
 arraylist and context has constructor-----------
 **/


/**
 ListView sample = findViewById(R.id.sample);

 ArrayList<String> list = new ArrayList<>();
 list.add("ht");
 list.add("ht");
 list.add("ht");
 list.add("ht");
 list.add("ht");

 Adapter ad=new Adapter(list,MainActivity.this);

 sample.setAdapter(ad);

 **/