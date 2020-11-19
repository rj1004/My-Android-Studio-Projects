package com.rahulcompany.papersin;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Jsonparser {
    public static ArrayList<Datas> fetch(String jsondata){
        ArrayList<Datas> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsondata);
            JSONArray ary = obj.getJSONArray("hits");
            for (int i = 0; i < ary.length(); i++) {
                Datas datas = new Datas();
                JSONObject o = ary.getJSONObject(i);
                datas.setPreviewurl(o.getString("previewURL"));
                datas.setOrignalurl(o.getString("largeImageURL"));
                datas.setSize(o.getInt("imageSize") / 1024);
                list.add(datas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("total", String.valueOf(list.size()));
        return list;
    }
}
