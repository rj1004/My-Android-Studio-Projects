package com.rahulcompany.phones;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Data> a;
    ArrayList<Data> fil;
    ArrayList<Recent> rec;
    EditText search;
    String[] perm;
    FloatingActionButton addcontact,refresh,dial,recent;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button devbtn = (Button) findViewById(R.id.devbtn);
        devbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dev = new Dialog(MainActivity.this);
                dev.setCancelable(true);
                dev.setContentView(R.layout.dialog_dev);
                FloatingActionButton devcall = (FloatingActionButton) dev.findViewById(R.id.devcall);
                FloatingActionButton devmail = (FloatingActionButton) dev.findViewById(R.id.devmail);

                devcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:+91 9157755527"));
                            startActivity(intent);
                        } else {
                            requestPermissions(perm, 100);
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
            }
        });


        lv = findViewById(R.id.lv);
        search = (EditText) findViewById(R.id.search);
        addcontact = (FloatingActionButton) findViewById(R.id.addcontact);
        refresh = (FloatingActionButton) findViewById(R.id.refresh);
        dial = (FloatingActionButton) findViewById(R.id.dial);
        recent = (FloatingActionButton)findViewById(R.id.recent);

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

                    rec = new ArrayList<>();
                    Dialog d = new Dialog(MainActivity.this);
                    d.setCancelable(true);
                    d.setContentView(R.layout.recent_layout);
                    Cursor managedcursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
                    int number = managedcursor.getColumnIndex(CallLog.Calls.NUMBER);
                    int type = managedcursor.getColumnIndex(CallLog.Calls.TYPE);
                    int date = managedcursor.getColumnIndex(CallLog.Calls.DATE);
                    int duration = managedcursor.getColumnIndex(CallLog.Calls.DURATION);

                    while (managedcursor.moveToNext()) {
                        String no = managedcursor.getString(number);
                        String ty = managedcursor.getString(type);
                        String dat = managedcursor.getString(date);
                        String dur = managedcursor.getString(duration);
                        String dialtype = null;
                        int dircode = Integer.parseInt(ty);
                        switch (dircode) {
                            case CallLog.Calls.OUTGOING_TYPE:
                                dialtype = "out";
                                break;
                            case CallLog.Calls.INCOMING_TYPE:
                                dialtype = "in";
                                break;
                            case CallLog.Calls.MISSED_TYPE:
                                dialtype = "miss";
                                break;

                        }

                        rec.add(new Recent(findname(no), no, dur, dialtype, dat));
                    }
                    managedcursor.close();
                    Collections.sort(rec,Recent.c);
                    Recentadapter rc = new Recentadapter(rec, MainActivity.this);
                    ListView l = d.findViewById(R.id.recentlist);
                    l.setAdapter(rc);
                    l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Recent r = (Recent) adapterView.getItemAtPosition(i);
                            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" +r.getNo()));
                                startActivity(intent);
                            } else {
                                requestPermissions(perm, 100);
                            }

                        }
                    });
                    d.show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, 100);
                }
            }
        });
        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    /**Intent i = new Intent(ContactsContract.Intents.Insert.ACTION);
                    i.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    i.putExtra(ContactsContract.Intents.Insert.NAME, "hiiii");
                    i.putExtra(ContactsContract.Intents.Insert.PHONE, "123");
                    startActivity(i);**/

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_addcontact);
                final EditText dname = (EditText) dialog.findViewById(R.id.dcname);
                final EditText dno = (EditText) dialog.findViewById(R.id.dcno);
                FloatingActionButton save = (FloatingActionButton) dialog.findViewById(R.id.dcsaveqwe);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dname.getText().toString().equals("")) {
                            dname.setError("Name Field is Empty !");
                            return;
                        }
                        if (dno.getText().toString().equals("")) {
                            dno.setError("Phone No. Field is Empty !");
                            return;
                        }
                        if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) ==
                                PackageManager.PERMISSION_GRANTED
                        ) {
                            Intent i = new Intent(ContactsContract.Intents.Insert.ACTION);
                            i.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                            i.putExtra(ContactsContract.Intents.Insert.NAME, dname.getText().toString());
                            i.putExtra(ContactsContract.Intents.Insert.PHONE, dno.getText().toString());
                            startActivity(i);
                        } else {
                            requestPermissions(perm, 100);
                        }

                    }
                });
                dialog.show();

            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast t = new Toast(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View v=inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.root), false);
                t.setView(v);
                t.setDuration(Toast.LENGTH_LONG);
                t.show();
                initializearray();
            }
        });

        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dia = new Dialog(MainActivity.this);
                dia.setCancelable(true);
                dia.setContentView(R.layout.dialog_dialpad);
                final EditText no = (EditText) dia.findViewById(R.id.dialno);
                final TextView contacttext = (TextView) dia.findViewById(R.id.contacttext);
                no.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String no=filterbyno(charSequence);
                        contacttext.setText(no);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                contacttext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] a = contacttext.getText().toString().split("\n");
                        String callno = a[a.length - 1];
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" +callno));
                            startActivity(intent);
                        } else {
                            requestPermissions(perm, 100);
                        }

                    }
                });
                FloatingActionButton dialcall = (FloatingActionButton) dia.findViewById(R.id.dialcall);
                dialcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (no.getText().toString().equals("")) {
                            no.setError("Number field is empty !");
                            return;
                        }
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + no.getText().toString()));
                            startActivity(intent);
                        } else {
                            requestPermissions(perm, 100);
                        }
                    }
                });

                dia.show();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initializearray();
    }

    private String findname(String no) {
        for (int i = 0; i < this.a.size(); i++) {
            if (no.contains(a.get(i).getNo().replace(" ",""))) {
                return a.get(i).getName();
            }
        }
        return "NO_NAME";

    }

    private void filter(CharSequence charSequence) {
        fil=new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                fil.add(a.get(i));
            }
        }
        Adapter a = new Adapter(fil, getApplicationContext());
        lv.setAdapter(a);
    }

    private String filterbyno(CharSequence charSequence) {
        int flag=0;
        if (charSequence.toString().equals("")) {
            return "";
        }
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getNo().contains(charSequence.toString())) {
                flag = 1;
                return a.get(i).getName()+"\n"+a.get(i).getNo();
            }
        }
        return "No match found";
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializearray() {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
        } else {


            a = new ArrayList<>();
            Cursor cursor;
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            while (cursor.moveToNext()) {

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                String phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                a.add(new Data(name, phonenumber));
            }

            cursor.close();
            Collections.sort(a, Data.c);
            Adapter adapter = new Adapter(this.a, MainActivity.this);
            lv.setAdapter(adapter);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final Data data = (Data) adapterView.getItemAtPosition(i);
                    /**if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                     Intent intent = new Intent(Intent.ACTION_CALL);
                     intent.setData(Uri.parse("tel:" + data.getNo()));
                     startActivity(intent);
                     } else {
                     requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);

                     }**/

                    /**AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                     ad.setTitle("Call");
                     ad.setMessage(data.getName() + "\n" + data.getNo());
                     ad.setCancelable(true);

                     AlertDialog alertDialog = ad.create();
                     alertDialog.show();**/

                    Dialog d = new Dialog(MainActivity.this);
                    d.setCancelable(true);
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);


                    d.setContentView(R.layout.dialog);
                    TextView tname = (TextView) d.findViewById(R.id.dname);
                    TextView tno = (TextView) d.findViewById(R.id.dno);
                    FloatingActionButton dcall = (FloatingActionButton) d.findViewById(R.id.dcall);
                    tname.setText(data.getName());
                    tno.setText(data.getNo());

                    dcall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + data.getNo()));
                                startActivity(intent);
                            } else {
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);

                            }
                        }
                    });
                    d.show();
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        perm= new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE,Manifest.permission.WRITE_CONTACTS};
        check(perm);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void check(String[] perm) {
        for (int i = 0; i < perm.length; i++) {
            if (checkSelfPermission(perm[i]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perm, 100);
            } else {

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(permissions, 100);
                } else {
                    initializearray();
                }
            }
        }
    }

}

