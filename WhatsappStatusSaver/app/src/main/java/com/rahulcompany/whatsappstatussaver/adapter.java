package com.rahulcompany.whatsappstatussaver;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {
    ArrayList<File> list;
    Context ctx;
    InterstitialAd ad;

    public adapter(ArrayList<File> list, Context ctx,InterstitialAd ad) {
        this.list = list;
        this.ctx = ctx;
        this.ad=ad;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (list.get(position).getName().endsWith(".jpg")) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            holder.iv.setImageBitmap(BitmapFactory.decodeFile(list.get(position).getPath(),options));
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (ad.isLoaded()) {
                        ad.show();
                    } else if (ad.isLoading()) {
                        Intent i = new Intent(ctx, ImageViewActivity.class);
                        i.putExtra("photofilepath", list.get(position).getPath());
                        ctx.startActivity(i);
                    } else {
                        ad.loadAd(new AdRequest.Builder().build());
                        Intent i = new Intent(ctx, ImageViewActivity.class);
                        i.putExtra("photofilepath", list.get(position).getPath());
                        ctx.startActivity(i);
                    }


                    ad.setAdListener(new AdListener(){
                        @Override
                        public void onAdClosed() {
                            Intent i = new Intent(ctx, ImageViewActivity.class);
                            i.putExtra("photofilepath", list.get(position).getPath());
                            ctx.startActivity(i);
                            ad.loadAd(new AdRequest.Builder().build());
                        }
                    });






                }
            });
        } else if (list.get(position).getName().endsWith(".mp4")) {
            holder.iv.setImageBitmap(ThumbnailUtils.createVideoThumbnail(list.get(position).getPath(), 0));
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (ad.isLoaded()) {
                        ad.show();
                    } else if (ad.isLoading()) {
                        Intent i = new Intent(ctx, VideoViewActivity.class);
                        i.putExtra("videopath", list.get(position).getPath());
                        ctx.startActivity(i);
                    } else {
                        ad.loadAd(new AdRequest.Builder().build());
                        Intent i = new Intent(ctx, VideoViewActivity.class);
                        i.putExtra("videopath", list.get(position).getPath());
                        ctx.startActivity(i);
                    }


                    ad.setAdListener(new AdListener(){
                        @Override
                        public void onAdClosed() {
                            Intent i = new Intent(ctx, VideoViewActivity.class);
                            i.putExtra("videopath", list.get(position).getPath());
                            ctx.startActivity(i);
                            ad.loadAd(new AdRequest.Builder().build());
                        }
                    });

                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.imageView);
        }
    }
}
