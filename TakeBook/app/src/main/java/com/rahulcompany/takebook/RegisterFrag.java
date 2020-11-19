package com.rahulcompany.takebook;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterFrag extends Fragment {
    EditText login_reg,password_reg, repass_reg,user;
    Button register;

    ProgressDialog pd;

    FirebaseAuth fauth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_frag_layout, container, false);
        login_reg = v.findViewById(R.id.email_reg);
        password_reg = v.findViewById(R.id.pass_reg);
        repass_reg= v.findViewById(R.id.repass_reg);
        user = v.findViewById(R.id.username_reg);

        pd = new ProgressDialog(getContext());
        pd.setCancelable(false);
        pd.setMessage("Please wait..");
        pd.setTitle("Registering");

        fauth = FirebaseAuth.getInstance();

        register = v.findViewById(R.id.reg);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {







                pd.show();
                final String email = login_reg.getText().toString();
                String pass = password_reg.getText().toString();
                String repass = repass_reg.getText().toString();
                final String username = user.getText().toString();
                int ERROR_CODE=0;

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    login_reg.setError("Enter Valid Email");
                    ERROR_CODE = 1;
                }

                if (pass.length() < 6) {
                    password_reg.setError("Password Length must be 6 or more");
                    ERROR_CODE = 1;
                }
                if (!pass.matches(repass)) {
                    repass_reg.setError("Password must be match");
                    ERROR_CODE = 1;
                }
                if (username.length() < 4 || username.contains("@")) {
                    user.setError("Username length must be 4 or more without containing @,#,$,%,&,!,* etc..");
                    ERROR_CODE = 1;
                }

                if (ERROR_CODE == 0) {
                    fauth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "User Created", Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), "Login Now", Toast.LENGTH_LONG).show();
                                SharedPreferences sp = getActivity().getSharedPreferences("userdetail",Context.MODE_PRIVATE);
                                sp.edit().putString(email, username).commit();

                            } else {

                                Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
                            }
                        }

                    });
                } else {
                    pd.dismiss();
                }

            }
        });

        return v;
    }
}
