package com.rahulcompany.codersio;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class IDEFragment extends Fragment {

    FloatingActionButton fabmore, fabsave, fabexe;

    EditText Codeedit,fname;
    TextView resultcode;
    Spinner spinner;

    Handler h;

    Dialog d;
    Button dclose;

    String error = "Some Error Occured..";



    public IDEFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_i_d_e, container, false);


        fabexe = v.findViewById(R.id.fabexe);
        fabmore = v.findViewById(R.id.fabmore);
        fabsave = v.findViewById(R.id.fabsave);
        Codeedit = v.findViewById(R.id.codeedit);
        fname = v.findViewById(R.id.fname);

        Codeedit.setText(getArguments().getString("code"));

        spinner = v.findViewById(R.id.spinner);

        h = new Handler();

        d = new Dialog(container.getContext());



        d.setCancelable(false);
        d.setContentView(R.layout.dialog_output);
        resultcode = d.findViewById(R.id.resultCode);
        dclose = d.findViewById(R.id.dclose);
        dclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        viewanimations.init(fabsave);
        viewanimations.init(fabexe);

        fabmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewanimations.rotate(fabmore);
                if (fabexe.getVisibility() == View.VISIBLE) {
                    viewanimations.showOut(fabexe);
                    viewanimations.showOut(fabsave);
                } else {
                    viewanimations.showIn(fabexe);
                    viewanimations.showIn(fabsave);
                }
            }
        });

        fabexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewanimations.showOut(fabexe);
                viewanimations.showOut(fabsave);
                viewanimations.rotate(fabmore);
                d.show();
                resultcode.setText("Executing.. Please Wait..");
                codeexecute();

            }
        });

        fabsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewanimations.showOut(fabexe);
                viewanimations.showOut(fabsave);
                viewanimations.rotate(fabmore);
                Log.d("stuff", "save function");
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getContext(), "You Don't Give us Permission to Save File.", Toast.LENGTH_LONG).show();

                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
                } else {
                    if (fname.getText().toString().compareTo("") == 0) {
                        fname.setError("Enter File Name..");
                    } else {
                        save();
                    }
                }
            }
        });

        return v;
    }

    private void save() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CodePath");
            if (!file.exists()) {
                file.mkdir();
            }
            String Extensions;
            switch (spinner.getSelectedItem().toString()) {
                case "Java":
                    Extensions = ".java";
                    break;
                case "C":
                    Extensions = ".c";
                    break;
                case "Cpp":
                    Extensions = ".cpp";
                    break;
                case "Cpp14":
                    Extensions = ".cpp";
                    break;
                case "Csharp":
                    Extensions = ".cs";
                    break;
                case "Perl":
                    Extensions = ".pl";
                    break;
                case "Php":
                    Extensions = ".php";
                    break;
                case "Python":
                    Extensions = ".py";
                    break;
                case "Python3":
                    Extensions = ".py";
                    break;
                case "Scala":
                    Extensions = ".scala";
                    break;
                default:
                    Extensions = ".txt";
            }

            final File src = new File(file, fname.getText().toString().trim() + Extensions);
            if (src.exists()) {
                AlertDialog ad = new AlertDialog.Builder(getContext())
                        .setTitle("File Already Exists With Same Name")
                        .setMessage("Do You Want To Replace a File ? ")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("stuffad", "No");
                                Toast.makeText(getContext(), "Change File Name", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    Log.d("stuffad", "Yes");
                                    FileOutputStream os = new FileOutputStream(src);
                                    String data = Codeedit.getText().toString();
                                    byte[] bytes = data.getBytes("UTF-8");
                                    os.write(bytes);
                                    os.close();
                                    Toast.makeText(getContext(), "Saved at : " + src.getAbsolutePath(), Toast.LENGTH_LONG).show();

                                } catch (Exception e) {
                                }
                            }
                        })
                        .create();
                ad.show();


            }
            else {
                src.createNewFile();
                FileOutputStream os = new FileOutputStream(src);
                String data = Codeedit.getText().toString();
                byte[] bytes = data.getBytes("UTF-8");
                os.write(bytes);
                os.close();
                Toast.makeText(getContext(), "Saved at : " + src.getAbsolutePath(), Toast.LENGTH_LONG).show();

            }



        } catch (Exception e) {
            Log.d("stuffe", e.toString());
        }





    }


    private void codeexecute() {
        String code = Codeedit.getText().toString();
        Log.d("stuff", code);
        String lang = spinner.getSelectedItem().toString();
        Log.d("stuff", lang);

        final OkHttpClient client = new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("lang",lang)
                .add("code",code)
                .add("save","false")
                .build();
        Request request = new Request.Builder()
                .url("https://ide.geeksforgeeks.org/main.php")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                if (e.toString().contains("host")) {
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            resultcode.setText("No Internet Connection.....");
                        }
                    });
                }
                Log.d("stuffapi", e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().string();
                try {
                    final JSONObject obj = new JSONObject(res);
                    String status = obj.getString("status");

                    if (status.compareToIgnoreCase("SUCCESS") == 0) {
                        final String sid = obj.getString("sid");
                        Log.d("stuffapi", sid);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("stuff", "thread is running");


                                RequestBody rb=new FormBody.Builder()
                                        .add("sid",sid)
                                        .add("requestType","fetchResults")
                                        .build();

                                Request r=new Request.Builder()
                                        .url("https://ide.geeksforgeeks.org/submissionResult.php")
                                        .post(rb)
                                        .build();


                                final String[] s = new String[1];
                                s[0]="sd";

                                while (s[0].compareToIgnoreCase("SUCCESS") != 0) {
                                    try {
                                        Thread.currentThread().sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    client.newCall(r).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                            call.cancel();

                                            Log.d("stuffapin",e.toString());
                                        }

                                        @Override
                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                            String mainres = response.body().string();
                                            try {
                                                final JSONObject object = new JSONObject(mainres);
                                                s[0] = object.getString("status");
                                                if (s[0].compareToIgnoreCase("SUCCESS") == 0) {
                                                    Log.d("stuffapiin", "output recieved");
                                                    if (object.getString("compResult").compareToIgnoreCase("F") == 0) {
                                                        h.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    resultcode.setText(object.getString("cmpError"));
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        });
                                                        Log.d("stuffapiin", object.getString("cmpError"));
                                                    } else {
                                                        h.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    resultcode.setText(object.getString("output"));
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        });
                                                        Log.d("stuffapiin", object.getString("output"));
                                                    }
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });


                                }



                            }
                        }).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


}