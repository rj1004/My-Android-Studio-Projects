package com.rahulcompany.breakingnews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser {
    public static ArrayList<Data> parsing(String data) throws JSONException {
        ArrayList<Data> a = new ArrayList<>();
        JSONObject obj = new JSONObject(data);
        JSONArray art = obj.getJSONArray("articles");
        for (int i = 0; i < art.length(); i++) {
            JSONObject o = art.getJSONObject(i);
            String auther = o.getString("author");
            String title = o.getString("title");
            String desc = o.getString("description");
            String url = o.getString("url");
            String time = o.getString("publishedAt");

            Data d = new Data(title, desc, time, url, auther);

            a.add(d);
        }
        return a;
    }
}
