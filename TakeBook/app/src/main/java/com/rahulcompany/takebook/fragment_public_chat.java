package com.rahulcompany.takebook;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class fragment_public_chat extends Fragment {
    ArrayList<DataModel> listmodel;
    FloatingActionButton fab;
    ProgressDialog pd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.public_chat_layout, container, false);


        final ListView lv = v.findViewById(R.id.lv);


        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                ArrayList<DataModel> list = new ArrayList<>();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()
                ) {
                    DataModel dm=snapshot.getValue(DataModel.class);
                    if (dm.getDate() != null && dm.getMsg() != null && dm.getUsername() != null) {
                        list.add(dm);
                    }

                }
                listmodel = list;
                Collections.sort(list, DataModel.c);
                Log.d("test", String.valueOf(dataSnapshot.getChildrenCount()));
                Log.d("test", list.toString());



                CustAdapter ad = new CustAdapter(getContext(), list);
                lv.setAdapter(ad);

                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final EditText msgbody = v.findViewById(R.id.msgbody);
        final ImageButton send = v.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("userdetail", MODE_PRIVATE);
                String username=sp.getString(FirebaseAuth.getInstance().getCurrentUser().getEmail(),"User");
                Log.d("test", username);
                String msg = msgbody.getText().toString();
                Date date = Calendar.getInstance().getTime();
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy@hh:mm:ss");
                String current_date = format.format(date);


                HashMap<String, String> map = new HashMap<>();

                map.put("username", username);
                map.put("date", current_date);
                map.put("msg", msg);

                ref.child(current_date).setValue(map);
                msgbody.setText("");

                MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.facebook_message);
                mp.start();

            }
        });

        /**fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(getContext());
                d.setCancelable(false);
                d.setContentView(R.layout.dialog_fab);
                d.show();


                final EditText msgbody = d.findViewById(R.id.msgbody);
                Button post,cancel;
                post = d.findViewById(R.id.post_btn);
                cancel = d.findViewById(R.id.cancel_btn);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });

                post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sp = getActivity().getSharedPreferences("userdetail", MODE_PRIVATE);
                        String username=sp.getString(FirebaseAuth.getInstance().getCurrentUser().getEmail(),"hi");
                        Log.d("test", username);
                        String msg = msgbody.getText().toString();
                        Date date = Calendar.getInstance().getTime();
                        DateFormat format = new SimpleDateFormat("hh:mm:ss dd-mm-yyyy");
                        String current_date = format.format(date);


                        HashMap<String, String> map = new HashMap<>();

                        map.put("username", username);
                        map.put("date", current_date);
                        map.put("msg", msg);

                        ref.child(username + current_date).setValue(map);
                        d.dismiss();
                    }
                });
            }
        });
         **/


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd=new ProgressDialog(getContext());
        pd.setMessage("Loading");
        pd.setCancelable(false);

        pd.show();
        joinchannelmsg();
    }

    public void joinchannelmsg() {
        SharedPreferences sp = getActivity().getSharedPreferences("userdetail", MODE_PRIVATE);
        String username=sp.getString(FirebaseAuth.getInstance().getCurrentUser().getEmail(),"User");
        Log.d("test", username);
        String msg = username+" Join The Public Chat..";
        Date date = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy@hh:mm:ss");
        String current_date = format.format(date);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Posts");
        HashMap<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("date", current_date);
        map.put("msg", msg);

        Log.d("join problem", map.get("date"));


        ref.child(username+current_date).setValue(map);

    }

    public void leavechannelmsg() {
        SharedPreferences sp = getActivity().getSharedPreferences("userdetail", MODE_PRIVATE);
        String username=sp.getString(FirebaseAuth.getInstance().getCurrentUser().getEmail(),"hi");
        Log.d("test", username);
        String msg = username+" Leave The Public Chat..";
        Date date = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("hh:mm:ss dd-mm-yyyy");
        String current_date = format.format(date);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Posts");
        HashMap<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("date", current_date);
        map.put("msg", msg);

        ref.child(username + current_date).setValue(map);
    }




}
