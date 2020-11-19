package com.rahulcompany.dedsec;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.LoginFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Editor extends AppCompatActivity {

    String code;
    EditText Codeedit;
    String url="https://us-central1-mymarket-83c33.cloudfunctions.net/helloWorld";
    Executers ex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Codeedit = findViewById(R.id.codeedit);
        Handler handler = new Handler();
        ex = new Executers(Editor.this,Codeedit,handler);



        Codeedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    String whole_text = charSequence.toString();
                    char a = whole_text.charAt(whole_text.length() - 1);
                    if (a == '\\') {
                        int $ = whole_text.lastIndexOf('$');
                        int last = whole_text.length() - 1;
                        if ($ > -1 && last > -1 && $+1 > -1) {
                            Log.d("stuff", String.valueOf($));
                            code = whole_text.substring($+1, last);
                            code = code.trim();
                            Log.d("stuff", code);
                            //TODO : make post request
                            try {

                                execute(code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }



    private void execute(String code) throws JSONException {
        Log.d("stuff", "in execute-" + code);
        //todo:code bypass here
        code = code.trim();
        ex.bypass(code);

    }



    private void post(String code) throws JSONException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        object.put("code", code);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, object.toString());
        Request request = new Request.Builder()
                .url(url+"/a")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                call.cancel();
                Log.d("stuff", e.getMessage());
                Editor.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Codeedit.append("\n"+e.getMessage());
                        Codeedit.setSelection(Codeedit.getText().toString().length());

                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("stuff", response.body().string());
                Editor.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Codeedit.append("\nSuccess with error code 0");
                        Codeedit.setSelection(Codeedit.getText().toString().length());
                    }
                });
            }
        });
    }

    public void codelist(View view) {
        Dialog d = new Dialog(Editor.this);
        d.setCancelable(true);
        d.setContentView(R.layout.codelist);
        d.show();
    }
}