package com.rahulcompany.papersin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.util.Size;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    String Papers = "<font color=#25BDD3>Pap</font><font color=#F19F0B>er</font><font color=#E96147>s</font><font color=#E64E38>.in</font>";

    FloatingActionButton cat;
    RecyclerView rv;
    GridView gv;
    ShimmerFrameLayout shim;
    ScrollView forshim;

    int pagenum=1;

    String urlstart="https://pixabay.com/api/?key=16649284-1c88cf6ea0ca1bfc001e2879a&safesearch=true&per_page=72&orientation=vertical";
    String ques = "";
    String category = "";
    String page="";

    String finalurl;



    TextView devcom;
    Chip prev,next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar t = findViewById(R.id.toolbar);
        TextView tv = t.findViewById(R.id.home_app_name);
        tv.setText(Html.fromHtml(Papers));
        setSupportActionBar(t);

        init();






    }

    private void startloading() {
        forshim.setVisibility(View.INVISIBLE);
        shim.setVisibility(View.VISIBLE);
        shim.showShimmer(true);
    }

    private void stoploading() {
        shim.showShimmer(false);
        shim.setVisibility(View.INVISIBLE);
        forshim.setVisibility(View.VISIBLE);
    }

    private void init() {
        cat = findViewById(R.id.home_cat);
        cat.setOnClickListener(this);

        rv = findViewById(R.id.recyclerview);
        rv.setLayoutManager(new GridLayoutManager(HomeActivity.this,3));

        gv = findViewById(R.id.shim_grid);
        gv.setAdapter(new shim_adapter());

        shim = findViewById(R.id.shimmer);
        forshim = findViewById(R.id.for_shim);

        devcom = findViewById(R.id.devcom);
        String com = "<font color=#25BDD3>Ra</font><font color=#F19F0B>hu</font><font color=#E96147>l's &</font><font color=#25BDD3> Co</font><font color=#F19F0B>mp</font><font color=#E96147>any</font>";
        devcom.setText(Html.fromHtml(com));

        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        //pagenum = new Random().nextInt(20 - 1) + 1;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_cat:
                final Dialog d = new Dialog(HomeActivity.this);
                d.setCancelable(true);
                d.setContentView(R.layout.dialog_cat);
                TextView title = d.findViewById(R.id.d_title);
                String cat = "<font color=#25BDD3>Ca</font><font color=#F19F0B>te</font><font color=#E96147>go</font><font color=#E64E38>ry</font>";
                title.setText(Html.fromHtml(cat));

                ChipGroup group = d.findViewById(R.id.group);
                group.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(ChipGroup group, int checkedId) {
                        Chip c = d.findViewById(group.getCheckedChipId());
                        Toast.makeText(HomeActivity.this, c.getText(), Toast.LENGTH_LONG).show();
                    }
                });
                final ArrayList<Chip> chipArrayList = new ArrayList<>();
                chipArrayList.add((Chip) d.findViewById(R.id.c1));
                chipArrayList.add((Chip)d.findViewById(R.id.c2));
                chipArrayList.add((Chip)d.findViewById(R.id.c3));
                chipArrayList.add((Chip)d.findViewById(R.id.c4));
                chipArrayList.add((Chip)d.findViewById(R.id.c5));
                chipArrayList.add((Chip)d.findViewById(R.id.c6));
                chipArrayList.add((Chip)d.findViewById(R.id.c7));
                chipArrayList.add((Chip)d.findViewById(R.id.c8));
                chipArrayList.add((Chip)d.findViewById(R.id.c9));
                chipArrayList.add((Chip)d.findViewById(R.id.c10));
                chipArrayList.add((Chip)d.findViewById(R.id.c11));
                chipArrayList.add((Chip)d.findViewById(R.id.c12));
                chipArrayList.add((Chip)d.findViewById(R.id.c13));
                chipArrayList.add((Chip)d.findViewById(R.id.c14));
                chipArrayList.add((Chip)d.findViewById(R.id.c15));
                chipArrayList.add((Chip)d.findViewById(R.id.c16));
                chipArrayList.add((Chip)d.findViewById(R.id.c17));
                chipArrayList.add((Chip)d.findViewById(R.id.c18));
                chipArrayList.add((Chip)d.findViewById(R.id.c19));
                chipArrayList.add((Chip)d.findViewById(R.id.c20));
                for (int i = 0; i < chipArrayList.size(); i++) {
                    final int finalI = i;
                    chipArrayList.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            reset();
                            category = "&category=" + chipArrayList.get(finalI).getText();
                            checknet();
                            Log.d("chip", String.valueOf(chipArrayList.get(finalI).getText()));
                            d.dismiss();
                        }
                    });
                }
                final TextInputEditText search = d.findViewById(R.id.searchtext);
                search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);


                search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_SEARCH) {
                            InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(search.getWindowToken(), 0);
                            String que = search.getText().toString();
                            if (!que.isEmpty()) {
                                que=que.replace(" ", "+");
                            }
                            reset();
                            ques = "&q="+que;
                            Log.d("que", ques);
                            checknet();
                            d.dismiss();
                            return true;
                        }
                        return false;
                    }
                });


                d.show();
                break;


            case R.id.prev:
                if (pagenum == 1) {
                    Toast.makeText(HomeActivity.this, "This is the First Page.", Toast.LENGTH_SHORT).show();
                    break;
                } else {

                    pagenum--;
                    checknet();
                }


                break;
            case R.id.next:

                pagenum++;
                checknet();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sp = getSharedPreferences("papersinuser", MODE_PRIVATE);
        if (sp.contains("first")) {
            if (sp.getString("first", "").matches("true")) {
                Log.d("sp", "match true");
                preview();
                sp.edit().putString("first", "false").commit();
            }
        } else {
            sp.edit().putString("first", "false").commit();
            Log.d("sp","not contain");
            preview();
        }

        checknet();
        new remotetask(HomeActivity.this).execute();

    }

    private void preview() {
        final Dialog d = new Dialog(HomeActivity.this);
        d.setContentView(R.layout.dialog_preview);
        final TextView title,con;
        title = d.findViewById(R.id.dp_help);
        con = d.findViewById(R.id.dp_con);
        String text = "<font color=#25BDD3>H</font><font color=#F19F0B>e</font><font color=#E96147>l</font><font color=#E64E38>p</font>";
        title.setText(Html.fromHtml(text));


        d.setCancelable(false);
        final Handler h = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(3000);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            con.setText("--Developed By Rahul's & Company");

                        }
                    });

                    Thread.currentThread().sleep(3000);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            con.setText("You Can Use Bottom Right Floating Button as Filter which Helps You to Filter out The Wallpapers as Category wise or Search method.");
                        }
                    });

                    Thread.currentThread().sleep(5000);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            con.setText("You Can use your Finger tap on The image which you want to show or download Wallpaper.By Tapping You can Now See Wallpaper in High Quality with full Screen and You Can Set As Wallpaper or You Can Download And Save it to internal Storage Folder Name as Papers.");
                        }
                    });

                    Thread.currentThread().sleep(7000);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            con.setText("Better Internet Connection Will Help You to Load Images Faster then Usual.");
                        }
                    });

                    Thread.currentThread().sleep(4000);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            con.setTextSize(30);
                            String t = "<font color=#25BDD3>Se</font><font color=#F19F0B>tt</font><font color=#E96147>in</font><font color=#E64E38>g</font>";
                            String t2 = "<font color=#25BDD3>Th</font><font color=#F19F0B>in</font><font color=#E96147>g</font><font color=#E64E38>s</font>";
                            String t3 = "<font color=#25BDD3>.</font><font color=#F19F0B>.</font><font color=#E96147>.</font><font color=#E64E38>.</font>";
                            con.setText(Html.fromHtml(t + " " + t2 + " " + t3));

                        }
                    });

                    Thread.currentThread().sleep(3000);
                    d.dismiss();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        d.show();
    }

    private void checknet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            starttask();
        } else {
            startloading();
            Toast.makeText(HomeActivity.this, "No Internet...\nExit And Try Again", Toast.LENGTH_LONG).show();
        }
    }

    void starttask() {
        page = "&page=" + String.valueOf(pagenum);
        finalurl = urlstart + category + page + ques;
        Log.d("url", finalurl);
        new backtsak(finalurl, forshim, shim, HomeActivity.this,rv).execute();
    }

    void reset() {
        category = "";
        pagenum=1;
        ques = "";
    }

}


