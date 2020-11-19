package com.rahulcompany.papersin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

public class Customadapter extends RecyclerView.Adapter<Customadapter.MyHolder> {
    ArrayList<Datas> list;
    Context ctx;
    public Customadapter(ArrayList<Datas> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Customadapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Customadapter.MyHolder holder, final int position) {
        //todo : iv click listner and download set as wall share
        Glide.with(ctx).load(list.get(position).getPreviewurl()).into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ImageViewrActivity.class);
                intent.putExtra("data", list.get(position));
                ctx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.image_prev);
        }
    }
}
