package com.rahulcompany.codersio;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.Loader;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;


public class programFragment extends Fragment {

    ImageButton back;
    TextView path;
    ListView lv;
    FloatingActionButton create;

    String fpath = "/";

    public programFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_program, container, false);

        back = v.findViewById(R.id.fback);
        path = v.findViewById(R.id.fpath);
        lv = v.findViewById(R.id.flv);
        create = v.findViewById(R.id.fabfileadd);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(getContext(), "You Don't Give us Permission to Explore Storage.", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            path.setText("You Don't Give us Permission to Explore Storage.You can Retry When You Do.");
            return v;
        }


        setFiles("/");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fpath.lastIndexOf("/") != 0) {
                    String s = fpath.substring(0, fpath.length() - 1);
                    s = s.substring(0, s.lastIndexOf("/") + 1);
                    fpath = s;
                    setFiles(fpath);
                } else {
                    Toast.makeText(getContext(), "You are on main Diractory", Toast.LENGTH_LONG).show();
                }
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File file = (File) adapterView.getItemAtPosition(i);
                setFiles(fpath+file.getName()+"/");
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                final File f = (File) adapterView.getItemAtPosition(i);
                final Dialog d = new Dialog(getContext());
                d.setContentView(R.layout.dialog_option);
                LinearLayout delete,open;
                TextView rename;
                final EditText namerename;
                namerename = d.findViewById(R.id.namerename);
                rename = d.findViewById(R.id.filerename);
                delete = d.findViewById(R.id.filedelete);
                open = d.findViewById(R.id.fileopen);

                rename.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (namerename.getText().toString().compareToIgnoreCase("") == 0) {
                            namerename.setError("Name Required..");
                            return;
                        }
                        f.renameTo(new File(f.getParentFile().getAbsolutePath() + "/" + namerename.getText().toString()));
                        setFiles(fpath);
                        d.dismiss();
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        f.delete();
                        setFiles(fpath);
                        d.dismiss();
                    }
                });
                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        setFiles(fpath+f.getName()+"/");
                        d.dismiss();
                    }
                });

                d.show();
                return true;
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(getContext());
                d.setContentView(R.layout.dialog_file_create);

                final EditText cname = d.findViewById(R.id.cname);
                Button file=d.findViewById(R.id.cfile);
                Button folder = d.findViewById(R.id.cfolder);
                file.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cname.getText().toString().compareToIgnoreCase("") == 0) {
                            cname.setError("Name is Required");
                            return;
                        }
                        File f = new File(Environment.getExternalStorageDirectory()+fpath + cname.getText().toString().trim());
                        if (!f.exists()) {
                            try {
                                f.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), "File Already Exists With Same Name.", Toast.LENGTH_LONG).show();
                        }
                        d.dismiss();
                        setFiles(fpath);


                    }
                });

                folder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cname.getText().toString().compareToIgnoreCase("") == 0) {
                            cname.setError("Name is Required");
                            return;
                        }
                        File f = new File(Environment.getExternalStorageDirectory()+fpath + cname.getText().toString().trim());
                        if (!f.exists()) {
                            f.mkdir();
                        } else {
                            Toast.makeText(getContext(), "File Already Exists With Same Name.", Toast.LENGTH_LONG).show();
                        }
                        d.dismiss();
                        setFiles(fpath);
                    }
                });
                d.show();
            }
        });



        return v;
    }


    private void setFiles(String path) {
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + path);
        if (!root.isDirectory()) {
            Toast.makeText(getContext(),"Opening File..Please Wait",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(), CodeVIewerActivity.class);
            i.setData(Uri.parse(root.getAbsolutePath()));
            startActivity(i);
            return;
        }
        fpath=path;
        this.path.setText(fpath);
        File[] files = root.listFiles();
        Arrays.sort(files);
        lv.setAdapter(new FileManagerAdapter(files));
        return;
    }

}