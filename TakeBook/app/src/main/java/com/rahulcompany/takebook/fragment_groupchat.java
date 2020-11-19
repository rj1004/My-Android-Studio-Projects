package com.rahulcompany.takebook;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class fragment_groupchat extends Fragment {
    private DatabaseReference ref;
    ListView lv;
    TextView tv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_groupchat_layout, container, false);
        lv= v.findViewById(R.id.listview_group);
        tv = v.findViewById(R.id.titleGroup);


        final EditText msgbody = v.findViewById(R.id.msgbody_group);
        final ImageButton send = v.findViewById(R.id.send_group);
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

                ref.child(username+current_date).setValue(map);
                msgbody.setText("");

                MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.facebook_message);
                mp.start();
            }
        });




        return v;
    }



    @Override
    public void onStart() {
        ref = FirebaseDatabase.getInstance().getReference("Groups");
        setGroupDetail();
        super.onStart();
    }

    void setGroupDetail() {




        final Dialog d = new Dialog(getActivity());
        d.setContentView(R.layout.dialog_group_detail);
        d.setCancelable(true);

        Button join,cancel;
        cancel = d.findViewById(R.id.create_group);
        join = d.findViewById(R.id.join_group);
        final EditText name = d.findViewById(R.id.gname);


        View.OnClickListener abc=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.create_group:
                        d.dismiss();

                        break;
                    case R.id.join_group:
                        ref = ref.child(name.getText().toString());
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

                                Collections.sort(list, DataModel.c);


                                Log.d("test", String.valueOf(dataSnapshot.getChildrenCount()));

                                CustAdapter ad = new CustAdapter(getContext(), list);
                                lv.setAdapter(ad);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        tv.setText(name.getText().toString());
                        Log.d("test", ref.getKey());
                        joinmsg();
                        d.dismiss();
                        break;

                }
            }
        };



        cancel.setOnClickListener(abc);
        join.setOnClickListener(abc);



        d.show();

    }

    private void joinmsg() {
        SharedPreferences sp = getActivity().getSharedPreferences("userdetail", MODE_PRIVATE);
        String username=sp.getString(FirebaseAuth.getInstance().getCurrentUser().getEmail(),"User");
        Log.d("test", username);
        String msg = username+" is Online.";
        Date date = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy@hh:mm:ss");
        String current_date = format.format(date);

        HashMap<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("date", current_date);
        map.put("msg", msg);

        ref.child(username + current_date).setValue(map);

    }


}
