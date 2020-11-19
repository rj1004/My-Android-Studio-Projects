package com.rahulcompany.phones;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Recentadapter extends BaseAdapter {
    ArrayList<Recent> a;
    Context ctx;

    public Recentadapter(ArrayList<Recent> a, Context ctx) {
        this.a = a;
        this.ctx = ctx;
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
        LayoutInflater lv=LayoutInflater.from(ctx);
        View v=lv.inflate(R.layout.recent_list_item, viewGroup, false);
        ImageView iv = v.findViewById(R.id.img);
        TextView log=v.findViewById(R.id.logtext);

        if (a.get(i).type.equals("out")) {
            iv.setImageResource(R.drawable.outgoing);
        } else if (a.get(i).type.equals("in")) {
            iv.setImageResource(R.drawable.income);
        } else if (a.get(i).type.equals("miss")) {
            iv.setImageResource(R.drawable.missed);
        }
        Date d = new Date(Long.parseLong(a.get(i).getCalltime()));
        log.setText(a.get(i).getName() + "\n" + a.get(i).getNo() + "\n" + d.toLocaleString() + "\n" + "duration : " + a.get(i).getDuration()+" sec");
        return v;
    }
}
