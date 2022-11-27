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

                //firebase

            }
        });



    }
}