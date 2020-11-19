package com.example.newsreader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;



public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    ProgressDialog pd;
    TextToSpeech tts;

    String[] news;
    String[] imagelink;
    String welcomemsg;

    Button play,pause,sub;

    Toast toast;
    View layout;
    TextView v,score;

    EditText ans;

    int count=0;
    News n;

    StringData data;

    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new StringData();

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.setTitle("Loading");
        pd.setMessage("Please wait");
        n= new News();
        sp = getSharedPreferences("voicedata", MODE_PRIVATE);

        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        sub = (Button) findViewById(R.id.sub);

        ans = (EditText) findViewById(R.id.ans);
        score = (TextView) findViewById(R.id.score);

        toast = new Toast(this);

        LayoutInflater inflater = getLayoutInflater();
        layout=inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.root));
        v = (TextView) layout.findViewById(R.id.toast_text);
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_LONG);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                news = n.getNews();
                imagelink = n.getImageurl();
                for (int i = 0; i < news.length; i++) {
                    tts.speak(news[i], TextToSpeech.QUEUE_ADD, null);
                }

            v.setText("playing");
            toast.show();
            v.setText("if playing stops in between then restart the app");
            toast.show();

            }
        });



        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setText("press Back to Pause");
                toast.show();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                operate();

            }
        });



    }



    public void Setting(View view) {
        pd.show();
        Intent i = new Intent(this, Setting.class);
        startActivity(i);
        pd.dismiss();
    }



    @Override
    protected void onStart() {
        super.onStart();


        if (!sp.contains("data")) {
            Intent i = new Intent(this, Setting.class);
            startActivity(i);
        } else {

            settts();
            Parsing p = new Parsing(n,MainActivity.this);
            p.execute();
        }


    }


    private void settts() {

        tts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                    Set<String> a=new HashSet<>();
                    Voice male=new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),Voice.QUALITY_VERY_HIGH,Voice.LATENCY_NORMAL,true,a);
                    Voice female=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),Voice.QUALITY_VERY_HIGH,Voice.LATENCY_NORMAL,true,a);

                    int hour = Integer.parseInt(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date()));
                    if (hour>0 && hour<10) {
                        welcomemsg="Good Morning "+sp.getString("name","");
                    } else if (hour >= 10 && hour < 16) {
                        welcomemsg="Good Afternoon "+sp.getString("name","");
                    } else if (hour>=16) {
                        welcomemsg="Good Afternoon "+sp.getString("name","");
                    }



                    if (sp.getString("voice","").equals("1")) {
                        tts.setVoice(male);
                    } else if (sp.getString("voice","").equals("2")) {
                        tts.setVoice(female);
                    }

                    float speed = (float) sp.getInt("speed", 0) / 50;
                    float pitch = (float) sp.getInt("pitch", 0) / 50;

                    tts.setPitch(pitch);
                    tts.setSpeechRate(speed);

                }
            }
        });

    }





    @Override
    protected void onPause() {
        super.onPause();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }



    private void refreshscore() {
        new Headingtask(MainActivity.this, score, index).execute();
    }

    void operate() {
        if (count == 0) {
            score.setVisibility(View.VISIBLE);
            ans.setVisibility(View.VISIBLE);
            new Titles(MainActivity.this, score, data).execute();
            count++;
        } else if (count == 1) {
            ans.setVisibility(View.GONE);
            index= Integer.parseInt(ans.getText().toString());
            new Headingtask(MainActivity.this, score, index).execute();
            count++;

        } else {
            new Headingtask(MainActivity.this, score, index).execute();


        }
    }


}
