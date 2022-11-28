package com.firetera.percyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signinbtn = findViewById(R.id.signinbtn);
        googlesigninbtn = findViewById(R.id.googlesigninbtn);
        noaccountyet = findViewById(R.id.noaccountyetTxtvw);
        showpassword = findViewById(R.id.showpw);
        firebaseAuth = FirebaseAuth.getInstance();


        noaccountyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, Register.class);
                startActivity(intent);

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignIn.this, "SUCCESFULLY SIGNED IN", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignIn.this, PrecyHomePage.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignIn.this, "SIGN IN FAILED", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent intent = new Intent(SignIn.this, PrecyHomePage.class);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", e.toString());
                            }
                        });
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

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            Intent intent = new Intent(SignIn.this, PrecyHomePage.class);
            startActivity(intent);
        }
    }
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