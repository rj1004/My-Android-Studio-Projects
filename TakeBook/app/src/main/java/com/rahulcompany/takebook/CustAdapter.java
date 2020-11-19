package com.rahulcompany.takebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class CustAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<DataModel> list;

    public CustAdapter(Context ctx, ArrayList<DataModel> list) {
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
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v=inflater.inflate(R.layout.list_item, viewGroup, false);

        TextView username,date,msg;
        username = v.findViewById(R.id.username_list);
        date = v.findViewById(R.id.date_item);
        msg = v.findViewById(R.id.msg_list);

        username.setText(list.get(i).getUsername());
        date.setText(list.get(i).getDate());
        msg.setText("Message : "+list.get(i).getMsg());

        return v;
    }
}
