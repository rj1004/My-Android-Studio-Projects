package com.rahulcompany.dedsec;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Executers {
    Context ctx;
    EditText Codeedit;
    TextToSpeech textToSpeech;
    Handler handler;

    public Executers(Context ctx, EditText Codeedit, android.os.Handler handler) {
        this.ctx = ctx;
        this.Codeedit=Codeedit;
        this.handler = handler;
        startservices();


    }

    public void bypass(String code) {
        Codeedit.append("\nexecuting...");

        if (code.startsWith("do -toast")) {
            code = code.replace("do -toast", "");
            code = code.trim();
            Log.d("stuff", "actual string-" + code);
            Toast.makeText(ctx, code, Toast.LENGTH_LONG).show();

        } else if (code.startsWith("do -speak")) {
            code = code.replace("do -speak", "");
            code = code.trim();
            textToSpeech.speak(code, TextToSpeech.QUEUE_FLUSH, null);

        } else if (code.startsWith("do -timer")) {
            code = code.replace("do -timer", "");
            code = code.trim();
            try {
                int time = Integer.parseInt(code);
                time = time * 1000;
                Log.d("stuff", String.valueOf(time));
                starttimer(time);
            } catch (NumberFormatException e) {
                Log.d("stufferror", e.getMessage());
                Log.d("stufferror", e.toString());
                Codeedit.append("\n" + e.toString());
            }
        } else if (code.startsWith("do -open -web")) {
            code = code.replace("do -open -web", "");
            code = code.trim();
            if (code.startsWith("-google")) {
                code = code.replace("-google", "");
                code = code.trim();
                code = "https://www.google.com/search?q=" + code;
            }
            if (code.startsWith("http")) {
                Codeedit.append("\nOpening --" + code);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(code));
                ctx.startActivity(i);
            } else {
                Codeedit.append("\nBad URL-");
            }
        } else if (code.startsWith("do -open -app")) {

            code = code.replace("do -open -app", "");
            code = code.trim();
            Codeedit.append("\nOpening app "+code);
            Intent intent = ctx.getPackageManager().getLaunchIntentForPackage(code);
            if (intent == null) {
                // Bring user to the market or let them choose an app?
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+code+"&hl=en_IN&gl=US"));
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        } else if (code.startsWith("do -download")) {
            code = code.replace("do -download", "");
            code = code.trim();
            if (code.startsWith("-ytb")) {
                code = code.replace("-ytb", "");
                code = code.trim();
                youtubedownloader(code);
            } else {
                new Downloader(code, ctx, Codeedit).execute();
            }

        } else if (code.startsWith("do -list -itag")) {
            String itags = "\n360p mp4--18 " + "\n720p mp4--22"+"\n1080p mp4--37"+"\n3072 mp4--38"+"\n48kbps mp3--139"+"\n128kbps mp3--140"+"\n256kbps mp3--141";
            Codeedit.append(itags);
        }  else if (code.startsWith("do -glance")) {
            code = code.replace("do -glance", "");
            code = code.trim();

            try {
                int time = Integer.parseInt(code);
                Log.d("stuffglance", String.valueOf(time));
                glance(time*1000);
            } catch (NumberFormatException e) {
                Codeedit.append("\n" + e.toString());
            }
        }




    }

    private void glance(final int time) {
        OkHttpClient client = new OkHttpClient();
        Request request=new Request.Builder()
                .url("https://pixabay.com/api/?key=16649284-1c88cf6ea0ca1bfc001e2879a&safesearch=true&per_page=200&orientation=vertical")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("stuffwallerror", e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String res = response.body().string();
                if (res != null) {
                    JSONObject obj = null;
                    JSONArray hits=null;
                    try {
                        obj = new JSONObject(res);
                        hits= obj.getJSONArray("hits");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final JSONArray finalHits = hits;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                while (true) {
                                    int start = (int) (Math.random() * 199);
                                    new WallpaperDownloaderSetter(finalHits.getJSONObject(start).getString("largeImageURL"), ctx).execute();
                                    Thread.currentThread().sleep(time);
                                }
                            } catch (JSONException | InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();

                }
            }
        });
    }

    private void youtubedownloader(String code) {
        try {
            code = code.trim();
            String url = code.substring(0, code.lastIndexOf("-"));
            String itag = code.substring(code.lastIndexOf("-") + 1);
            url = url.trim();
            final String itagmain = itag.trim();
            Log.d("stuffytb", url + itag);
            int itagno = Integer.parseInt(itagmain);
            new YouTubeUriExtractor(ctx) {
                @Override
                public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                    if (ytFiles != null) {
                        int itag = Integer.parseInt(itagmain);
                        YtFile f = ytFiles.get(itag);
                        if (f != null) {
                            String url = ytFiles.get(itag).getUrl();
                            if (url != null) {
                                new Downloader(url, ctx, Codeedit).execute();
                            }
                        } else {
                            Codeedit.append("\nNo Content Found..Change itag");
                        }

                    } else {
                        Codeedit.append("\nNo Youtube files found");
                    }
                }
            }.extract(url, true, true);
        } catch (NumberFormatException e) {
            Codeedit.append("\n" + e.toString());
        }

    }


    private void startservices() {
        Codeedit.append("\nstarting all services...");
        textToSpeech= new TextToSpeech(ctx, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    Codeedit.append("\nTTS initialization success..");
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Codeedit.append("\nTTS Language Setting Failed..");
                    } else {
                        Codeedit.append("\nTTS Language Setup Success..");
                        //Disable the button if any.
                    }
                    textToSpeech.setPitch(1f);
                    textToSpeech.setSpeechRate(0.3f);

                }
                else {
                    Codeedit.append("\n TTS init Failed..");
                }
            }
        });






        checkinternet();
    }

    private void checkinternet() {
        ConnectivityManager conMgr = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {

            Codeedit.append("\nNetwork Connection Success..\n");

        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Codeedit.append("\nNetwork Connection Failed.Try Connecting with wifi or mobile data then try reopening app again..\n");
        }
    }

    private void starttimer(final int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(time);
                    textToSpeech.speak("Timer end.Timer end.Timer end.", TextToSpeech.QUEUE_FLUSH, null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
