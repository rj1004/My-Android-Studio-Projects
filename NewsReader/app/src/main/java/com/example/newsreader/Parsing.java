package com.example.newsreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Parsing extends AsyncTask<Void,Void,Void>{

    String[] news;
    String[] imagelink;
    Context ctx;

    ProgressDialog pd;

    News n;
    String jsonData;
    public Parsing(News n, Context ctx) {
        this.n=n;
        this.ctx = ctx;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        ConnectivityManager connectivityManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            try {
                URL url = new URL("https://newsapi.org/v2/top-headlines?country=in&apiKey=1d988c8382a2475c8fbe11f49c1777fa");
                URLConnection conn = url.openConnection();


                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                StringBuilder sb = new StringBuilder();
                int count;
                while ((count = isr.read()) != -1) {
                    sb.append((char) count);
                }

                jsonData = sb.toString();

                int length;
                JSONObject obj = new JSONObject(jsonData);
                if (obj.getString("status").equals("ok")) {

                    length = obj.getInt("totalResults");
                    JSONArray articals = obj.getJSONArray("articles");
                    news = new String[articals.length()];
                    imagelink = new String[articals.length()];

                    pd.setMax(length);


                    for (int i = 0; i < articals.length(); i++) {
                        JSONObject det = articals.getJSONObject(i);
                        news[i] = det.getString("title");
                        imagelink[i] = det.getString("urlToImage");
                        pd.setProgress(i);
                    }
                    n.setNews(news);
                    n.setImageurl(imagelink);

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            n.setNews(new String[]{"Please Connect to Internet and Restart the app"});
            n.setImageurl(new String[]{null});
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ctx);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);
        pd.setTitle("Fetching News");
        pd.setIndeterminate(false);
        pd.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        pd.dismiss();
    }



}
