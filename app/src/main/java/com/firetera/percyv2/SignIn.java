package com.firetera.percyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignIn extends AppCompatActivity {

    TextView noaccountyet;
    EditText email, password;
    Button signinbtn, googlesigninbtn;
    CheckBox showpassword;
    ProgressBar signin_progressbar;
    FirebaseAuth firebaseAuth;
    Handler setDelay;
    Runnable startDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signinbtn = findViewById(R.id.signinbtn);
        googlesigninbtn = findViewById(R.id.googlesigninbtn);
        noaccountyet = findViewById(R.id.noaccountyetTxtvw);
        signin_progressbar = findViewById(R.id.sign_progressbar);
        showpassword = findViewById(R.id.showpw);
        firebaseAuth = FirebaseAuth.getInstance();

        setDelay = new Handler();


        signin_progressbar.setVisibility(View.GONE);


        noaccountyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signinbtn.setVisibility(View.GONE);
                signin_progressbar.setVisibility(View.VISIBLE);

             if (email.getText().toString().length()==0){
                 email.setError("Enter email");
                 signinbtn.setVisibility(View.VISIBLE);
                 signin_progressbar.setVisibility(View.GONE);
             }
             else if (password.getText().toString().length()==0){
                 password.setError("Enter password");
                 signinbtn.setVisibility(View.VISIBLE);
                 signin_progressbar.setVisibility(View.GONE);
             } else {

                 firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                         .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     Toast.makeText(SignIn.this, "SUCCESFULLY SIGNED IN", Toast.LENGTH_SHORT).show();
                                     signinbtn.setVisibility(View.GONE);
                                     signin_progressbar.setVisibility(View.VISIBLE);
                                     startActivity(new Intent(getApplicationContext(), MainActivity2.class));

                                 } else {
                                     Toast.makeText(SignIn.this, "SIGN IN FAILED", Toast.LENGTH_SHORT).show();
                                     signinbtn.setVisibility(View.VISIBLE);
                                     signin_progressbar.setVisibility(View.GONE);
                                 }
                             }
                         });



                 firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                         .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                             @Override
                             public void onSuccess(AuthResult authResult) {
                                 Intent intent = new Intent(SignIn.this, MainActivity2.class);
                                 startActivity(intent);

                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Log.d("TAG", e.toString());
                             }
                         });
             }




            }


        });










        //calling master




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



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if(firebaseAuth.getCurrentUser() != null){
//            finish();
//            Intent intent = new Intent(SignIn.this, PrecyHomePage.class);
//            startActivity(intent);
//        }
//    }
}






//        signinbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (username.getText().toString().equals("ptc.jomaridanao@gmail.com") && password.getText().toString().equals("password")){
//                    Toast.makeText(SignIn.this, "LOG IN SUCCESFULLY", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SignIn.this, PrecyHomePage.class);
//                    startActivity(intent);
//
//                }
//                else {
//                    Toast.makeText(SignIn.this, "LOG IN FAILED", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });