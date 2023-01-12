package com.firetera.percyv2.LogIn.LogInFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firetera.percyv2.MainActivity2;
import com.firetera.percyv2.R;

import com.firetera.percyv2.Splashscreen.SplashScreenLogIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmailFragment extends Fragment {

    EditText email, password;
    Button signinBtnEmail;
    CheckBox showpassword;
    ProgressBar signin_progressbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_email, container, false);

        email = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        signinBtnEmail = view.findViewById(R.id.signInEmail_Btn);
        signin_progressbar = view.findViewById(R.id.sign_progressbar);
        showpassword = view.findViewById(R.id.showPWEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        signin_progressbar.setVisibility(View.GONE);
        signinBtnEmail.setVisibility(View.VISIBLE);
        password.setTransformationMethod(new PasswordTransformationMethod());

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    password.setTransformationMethod(null);
                }
                else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        signinBtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signinBtnEmail.setVisibility(View.GONE);
                signin_progressbar.setVisibility(View.VISIBLE);

                if (email.getText().toString().length()==0){
                    email.setError("Enter email");
                    signinBtnEmail.setVisibility(View.VISIBLE);
                    signin_progressbar.setVisibility(View.GONE);
                }
                else if (password.getText().toString().length()==0){
                    password.setError("Enter password");
                    signinBtnEmail.setVisibility(View.VISIBLE);
                    signin_progressbar.setVisibility(View.GONE);
                } else {

                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                            signinBtnEmail.setVisibility(View.GONE);
                                            signin_progressbar.setVisibility(View.VISIBLE);
                                            startActivity(new Intent(getContext(), SplashScreenLogIn.class));
                                        }
                                        else {
                                            Toast.makeText(getContext(), "Please verify your Email", Toast.LENGTH_LONG).show();
                                            signinBtnEmail.setVisibility(View.VISIBLE);
                                            signin_progressbar.setVisibility(View.GONE);
                                        }



                                    } else {
                                        Toast.makeText(getContext(), "SIGN IN FAILED", Toast.LENGTH_SHORT).show();
                                        signinBtnEmail.setVisibility(View.VISIBLE);
                                        signin_progressbar.setVisibility(View.GONE);
                                    }
                                }
                            });



//                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
//                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                                @Override
//                                public void onSuccess(AuthResult authResult) {
//                                    Intent intent = new Intent(getContext(), SplashScreenLogIn.class);
//                                    startActivity(intent);
//
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d("TAG", e.toString());
//                                }
//                            });
                }
            }
        });

         return view;
    }
}