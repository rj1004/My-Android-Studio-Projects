package com.rahulcompany.takebook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class LoginFrag extends Fragment {
    FirebaseAuth fauth;
    EditText email,pass;
    Button login;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.login_frag, container, false);
        email = v.findViewById(R.id.email_login);
        pass = v.findViewById(R.id.pass_login);
        login = v.findViewById(R.id.login_login);
        fauth = FirebaseAuth.getInstance();

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Logging In");
        pd.setMessage("Please Wait..");
        pd.setCancelable(false);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                String mail, pas;
                mail = email.getText().toString();
                pas = pass.getText().toString();

                fauth.signInWithEmailAndPassword(mail, pas).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            Intent i = new Intent(getContext(), DashBoard.class);
                            startActivity(i);
                            getActivity().finish();

                            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return v;
    }
}
