package com.firetera.percyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText regusername, regfullname, regpassword, regconfirmpassword, regemail, regphonenumber;
    Button regbtn;
    TextView directTologin;
    ProgressBar regprogress_bar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regusername = findViewById(R.id.username);
        regfullname = findViewById(R.id.fullname);
        regpassword = findViewById(R.id.password);
        regconfirmpassword = findViewById(R.id.confirmpassword);
        regemail = findViewById(R.id.email);
        regphonenumber = findViewById(R.id.phonenumber);
        regbtn = findViewById(R.id.registerbtn);
        directTologin = findViewById(R.id.haveaccount);
        regprogress_bar = findViewById(R.id.regprogress_bar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), HomeFragment.class));
            finish();
        }

        regpassword.setTransformationMethod(new PasswordTransformationMethod());
        regconfirmpassword.setTransformationMethod(new PasswordTransformationMethod());

        directTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, SignIn.class);
                startActivity(intent);

            }
        });



        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = regemail.getText().toString();
                final String password = regpassword.getText().toString();
                String confirmpassword = regconfirmpassword.getText().toString();
                String phonenumber = regphonenumber.getText().toString();

                regbtn.setVisibility(View.GONE);
                regprogress_bar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email, confirmpassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account Succesfully Created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, SignIn.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(Register.this, "Register Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    regbtn.setVisibility(View.VISIBLE);
                                    regprogress_bar.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                firebaseAuth.createUserWithEmailAndPassword(regemail.getText().toString(), regconfirmpassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {



                                Log.d("TAG", "SUCCESS");

                                //save name and other details
                                //document and collection

                                HashMap <String, String> savedusers = new HashMap<>();
                                savedusers.put("Username", regusername.getText().toString());
                                savedusers.put("Fullname", regfullname.getText().toString());
                                savedusers.put("Email", regemail.getText().toString());
                                savedusers.put("Phone Number", regphonenumber.getText().toString());

                                firebaseFirestore.collection("Users").document(firebaseAuth.getUid())
                                        .set(savedusers)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("TAG", "SUCCESS DATA UPLOAD");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("TAG", "data sending" + e);
                                            }
                                        });

                            }
                        });
                if (email.isEmpty() || confirmpassword.isEmpty()) {
                    Toast.makeText(Register.this, "Email and Password are required", Toast.LENGTH_SHORT).show();
                    regbtn.setVisibility(View.VISIBLE);
                    regprogress_bar.setVisibility(View.GONE);


                }
            }
        });



    }



    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            Intent intent = new Intent(Register.this, HomeFragment.class);
            startActivity(intent);
        }
    }
}







//                String confirmpassword = regconfirmpassword.getText().toString().trim();
//                final String email =regemail.getText().toString().trim();
//                String password = regpassword.getText().toString().trim();
//                final String fullname = regfullname.getText().toString();
//                final  String phonenumber = regphonenumber.getText().toString();

//
//                if(TextUtils.isEmpty(fullname)){
//                    regfullname.setError("Full name is required");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    regpassword.setError("Password is Required");
//                    return;
//
//                }




//    private void signinmethod(String email, String password){
//
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
//                            Toast.makeText(Register.this, "Account Created Succesfully", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Register.this, SignIn.class);
//                            startActivity(intent);
//                        }
//
//                        else {
//                            Toast.makeText(Register.this, "Register Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            regbtn.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });


