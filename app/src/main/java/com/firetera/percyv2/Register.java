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

    FirebaseFirestore firestore;
    String userID;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);
        EditText conpassword = (EditText) findViewById(R.id.confirmpassword);
        EditText regusername = (EditText) findViewById(R.id.username);
        EditText regfullname = (EditText) findViewById(R.id.fullname);
        EditText regpassword = (EditText) findViewById(R.id.password);
        EditText regemail = (EditText) findViewById(R.id.email);
        EditText regphonenumber = (EditText) findViewById(R.id.phonenumber);
        Button regbtn = (Button) findViewById(R.id.registerbtn);
        TextView loginbtn = (TextView) findViewById(R.id.haveaccount);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        regpassword.setTransformationMethod(new PasswordTransformationMethod());
        conpassword.setTransformationMethod(new PasswordTransformationMethod());

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, SignIn.class);
                startActivity(intent);

            }
        });

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmpassword = conpassword.getText().toString().trim();
                final String email =regemail.getText().toString().trim();
                String password = regpassword.getText().toString().trim();
                final String fullname = regfullname.getText().toString();
                final  String phonenumber = regphonenumber.getText().toString();

                if(TextUtils.isEmpty(fullname)){
                    regfullname.setError("Full name is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    regpassword.setError("Password is Required");
                    return;

                }

                if (TextUtils.isEmpty(confirmpassword)) {
                    conpassword.setError("Password is Required");
                    return;
                }


                if(password.length() < 6){
                    regpassword.setError("Password must not exceed to 6 characters");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    regemail.setError("Email is Required");
                    return;

                }

                if(TextUtils.isEmpty(phonenumber)){
                    regphonenumber.setError("Phone number is required");
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser fuser = firebaseAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Register Succesful", Toast.LENGTH_SHORT);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Onfailure: Email not sent" + e.getMessage());

                                }
                            });

                            Toast.makeText(getApplicationContext(),"User Created", Toast.LENGTH_SHORT).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("user").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName", fullname);
                            user.put("email", email);
                            user.put("phone", phonenumber);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "Onsuccess: User profile is created for" + userID);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure:" + e.toString());

                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else {
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });



    }
}