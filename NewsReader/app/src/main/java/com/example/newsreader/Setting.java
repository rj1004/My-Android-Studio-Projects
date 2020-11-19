package com.example.newsreader;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Setting extends Activity {
    SharedPreferences sp;

    TextToSpeech tts;
    EditText testtext,name;
    Spinner spinner;
    Button test,reset,save;
    RadioButton m,f;

    View layout;
    Toast t;
    TextView v;

    SeekBar pitch,speed;
    Voice male,female;

    ProgressDialog pd;

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.setTitle("Loading");
        pd.setMessage("Please wait");

        pd.show();
        sp = getSharedPreferences("voicedata", MODE_PRIVATE);
        pitch = (SeekBar) findViewById(R.id.pitch);
        speed = (SeekBar) findViewById(R.id.speed);

        t = new Toast(this);

        LayoutInflater inflater = getLayoutInflater();
        layout=inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.root));
        v = (TextView) layout.findViewById(R.id.toast_text);
        t.setView(layout);
        t.setDuration(Toast.LENGTH_LONG);





        testtext = (EditText) findViewById(R.id.testtext);
        name = (EditText) findViewById(R.id.name);
        test = (Button) findViewById(R.id.test);
        reset = (Button) findViewById(R.id.reset);
        save = (Button) findViewById(R.id.save);
        m = (RadioButton) findViewById(R.id.m);
        f = (RadioButton) findViewById(R.id.f);
        pd.dismiss();
    }




    public void testspeak(View view) {
        ttsetting();
    }




    public void reset(View view) {
        pd.show();
        f.setChecked(true);
        pitch.setProgress(50);
        speed.setProgress(50);
        name.setText("");
        testtext.setText("");
        pd.dismiss();
    }



    public void save(View view) {
        pd.show();
        if (name.getText().toString().equals("")) {
            name.setError("Name is Require");
        } else {
            SharedPreferences.Editor editor = sp.edit();
            if (m.isChecked()) {
                editor.putString("voice","1");
            } else if (f.isChecked()) {
                editor.putString("voice","2");
            }

            editor.putInt("pitch",pitch.getProgress());
            editor.putInt("speed",speed.getProgress());
            editor.putString("name",name.getText().toString());
            editor.putString("data", "yes");
            editor.commit();


            v.setText("Saved");
            t.show();
            onBackPressed();
        }
        pd.dismiss();
    }



    @Override
    public void onBackPressed() {
        pd.show();
        super.onBackPressed();
        pd.dismiss();
    }



    @Override
    protected void onStart() {
        super.onStart();

        tts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                    Set<String> a=new HashSet<>();
                    male=new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),400,200,true,a);
                    female=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);


                }
            }
        });

        if (sp.contains("data")) {
            name.setText(sp.getString("name",""));
            if (sp.getString("voice","").equals("1")) {
                m.setChecked(true);
            } else if (sp.getString("voice","").equals("2")) {
                f.setChecked(true);
            }
            pitch.setProgress(sp.getInt("pitch",0));
            speed.setProgress(sp.getInt("speed",0));

            ttsetting();

        }
    }




    private void ttsetting() {
        pd.show();
        String teststring = testtext.getText().toString();
        if (m.isChecked()) {
            tts.setVoice(male);
        } else if (f.isChecked()) {
            tts.setVoice(female);
        }
        float pitchvalue =(float) pitch.getProgress()/50;
        float speedvalue =(float) speed.getProgress()/50;
        //testtext.setText(String.valueOf(pitchvalue));
        tts.setPitch(pitchvalue);
        tts.setSpeechRate(speedvalue);
        tts.speak(teststring, TextToSpeech.QUEUE_FLUSH, null);

        v.setText("Good Testing..");
        t.show();
        pd.dismiss();
    }
}
