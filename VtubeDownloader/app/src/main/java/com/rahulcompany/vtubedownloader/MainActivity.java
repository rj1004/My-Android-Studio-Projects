package com.rahulcompany.vtubedownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rahulcompany.vtubedownloader.task.*;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.urltext);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    downloadtask();
                }
                return true;
            }
        });

        sp = findViewById(R.id.spin);


        Intent i = getIntent();
        if (i.getAction() == Intent.ACTION_SEND) {
            editText.setText(i.getStringExtra(Intent.EXTRA_TEXT));
            Toast.makeText(MainActivity.this, "Select Quality and tap download", Toast.LENGTH_LONG).show();
        }
    }

    public void download(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        downloadtask();
    }



    private  void downloadtask() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }

        if (editText.getText().toString().isEmpty()) {
            editText.setError("Empty!!");
            return;
        }

        if (checknet()) {
            geturl();
        }

    }

    private void geturl() {
        Toast.makeText(MainActivity.this, "Downloading...Please Wait", Toast.LENGTH_LONG).show();

        Log.d("sp", String.valueOf(sp.getSelectedItemPosition()));

        new YouTubeUriExtractor(MainActivity.this) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if (ytFiles != null) {
                    int itag = 22;
                    String ismp4 = "true";
                    switch (sp.getSelectedItemPosition()) {
                        case 0:
                            itag = 18;
                            break;
                        case 1:
                            itag = 22;
                            break;
                        case 2:
                            itag = 37;
                            break;
                        case 3:
                            itag = 38;
                            break;
                        case 4:
                            itag = 139;
                            ismp4 = "false";
                            break;
                        case 5:
                            itag = 140;
                            ismp4 = "false";
                            break;
                        case 6:
                            itag = 141;
                            ismp4 = "false";
                            break;

                    }
                    YtFile f = ytFiles.get(itag);
                    if (f != null) {
                        String url = ytFiles.get(itag).getUrl();
                        if (url != null) {
                            new downloadvideo(MainActivity.this).execute(new String[]{url, ismp4});
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No Content Found..Change Resolution", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "No Youtube files found", Toast.LENGTH_LONG).show();
                }
            }
        }.extract(editText.getText().toString(), true, true);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            int flag = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    flag=1;
                }
            }

            if (flag == 1) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                downloadtask();
            }
        }

        if (requestCode == 200) {
            int flag = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    flag=1;
                }
            }

            if (flag == 1) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Confirm Exit ?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();

        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        checknet();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dev:
                Dialog dev = new Dialog(MainActivity.this);
                dev.setCancelable(true);
                dev.setContentView(R.layout.dialog_dev);
                FloatingActionButton devcall = (FloatingActionButton) dev.findViewById(R.id.devcall);
                FloatingActionButton devmail = (FloatingActionButton) dev.findViewById(R.id.devmail);

                devcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:+91 9157755527"));
                            startActivity(intent);
                        } else {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 200);
                        }
                    }
                });

                devmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_SENDTO);
                        i.setData(Uri.parse("mailto:"));
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"rahulparmar1004@gmail.com"});
                        startActivity(i);
                    }
                });
                dev.show();


                break;

        }
        return true;
    }

    private boolean checknet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }else {
            AlertDialog ac = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("No Internet!")
                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            checknet();

                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);

                        }
                    })
                    .create();

            ac.setCancelable(false);

            ac.show();
        }
        return false;
    }



    //todo : help dialog
    //todo : ads

}
