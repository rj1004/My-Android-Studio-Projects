package com.rahulcompany.removeunwantedapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<App> list;

    public CustomAdapter(Context ctx, ArrayList<App> list) {
        this.ctx = ctx;
        this.list = list;
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
        View v = LayoutInflater.from(ctx).inflate(R.layout.item, viewGroup, false);
        TextView name = v.findViewById(R.id.name);
        ImageView icon = v.findViewById(R.id.icon);
        icon.setImageDrawable(list.get(i).getIcon());
        name.setText(list.get(i).getLabel());

        return v;
    }
}
