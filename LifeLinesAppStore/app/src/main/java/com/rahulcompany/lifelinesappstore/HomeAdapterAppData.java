package com.rahulcompany.lifelinesappstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeAdapterAppData extends BaseAdapter {

    ArrayList<AppDataHome> list;
    Context ctx;

    HomeAdapterAppData(Context ctx,ArrayList list) {
        this.ctx=ctx;
        this.list=list;
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
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.list_item, viewGroup, false);
        TextView name = v.findViewById(R.id.name);
        TextView type = v.findViewById(R.id.type);
        TextView rating = v.findViewById(R.id.rating);
        TextView size = v.findViewById(R.id.size);
        TextView down = v.findViewById(R.id.down);
        TextView dev = v.findViewById(R.id.devcom);

        dev.setText(list.get(i).getDevcom());
        name.setText(list.get(i).getName());
        type.setText(list.get(i).getType());
        rating.setText(list.get(i).getRating());
        size.setText(list.get(i).getSize());
        down.setText(list.get(i).getDownloads()+" Downloads");

        ImageView appicon = v.findViewById(R.id.app_icon_iv);
        Glide.with(ctx).load(list.get(i).getIconurl()).into(appicon);
        return v;
    }
}
