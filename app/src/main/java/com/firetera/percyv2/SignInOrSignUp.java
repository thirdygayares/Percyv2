package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firetera.percyv2.LogIn.LogInExampleUI;
import com.firetera.percyv2.RegistrationJavaClass.Register;
import com.firetera.percyv2.RegistrationJavaClass.RegistrationMethod;
import com.google.firebase.auth.FirebaseAuth;

public class SignInOrSignUp extends AppCompatActivity {

    Button signupbtn;
    Button signinbtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_or_sign_up);

        signupbtn = findViewById(R.id.SIOSUsignupbtn);
        signinbtn = findViewById(R.id.SIOSUsigninbtn);
        firebaseAuth = FirebaseAuth.getInstance();


        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LogInExampleUI.class));

            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationMethod.class));
            }
        });



    }

    @Override
    protected void onStart(){
        super.onStart();

        if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(SignInOrSignUp.this , MainActivity2.class));
        }


    }





}
