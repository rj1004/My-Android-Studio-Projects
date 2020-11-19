package com.rahulcompany.lifelinesappstore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONparsing {
    public static ArrayList parsehomepage(String json) {
        ArrayList<AppDataHome> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray ary = obj.getJSONArray("apps");
            for (int i = 0; i < ary.length(); i++) {
                JSONObject sample = ary.getJSONObject(i);

                AppDataHome ad = new AppDataHome();

                ad.setDownloads(sample.getString("downloads"));
                ad.setIconurl(sample.getString("iconurl"));
                ad.setId(sample.getInt("id"));
                ad.setName(sample.getString("name"));
                ad.setRating(sample.getString("rating"));
                ad.setSize(sample.getString("size"));
                ad.setSizenum(sample.getInt("sizenum"));
                ad.setType(sample.getString("type"));

                ad.setApkurl(sample.getString("apkurl"));
                ad.setReviewsnum(sample.getString("reviewsnum"));
                ad.setSc2url(sample.getString("sc2url"));
                ad.setSc3url(sample.getString("sc3url"));
                ad.setSc4url(sample.getString("sc4url"));
                ad.setSc5url(sample.getString("sc5url"));
                ad.setSc1url(sample.getString("sc1url"));

                ad.setAbout(sample.getString("about"));

                ad.setReviewname1(sample.getString("reviewname1"));
                ad.setReviewname2(sample.getString("reviewname2"));
                ad.setReviewname3(sample.getString("reviewname3"));

                ad.setReviewdetail1(sample.getString("reviewdetail1"));
                ad.setReviewdetail2(sample.getString("reviewdetail2"));
                ad.setReviewdetail3(sample.getString("reviewdetail3"));

                ad.setDevcom(sample.getString("devcom"));
                ad.setDevweb(sample.getString("devweb"));
                ad.setDevemail(sample.getString("devemail"));
                ad.setDevpolicy(sample.getString("devpolicy"));

                list.add(ad);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
