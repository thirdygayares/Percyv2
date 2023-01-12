package com.firetera.percyv2.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firetera.percyv2.LogIn.LogInFragment.MobileFragment;
import com.firetera.percyv2.MainActivity2;
import com.firetera.percyv2.R;
import com.firetera.percyv2.Splashscreen.SplashScreenLogIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class LogInOTPVerification extends AppCompatActivity {

    EditText input1, input2, input3, input4, input5, input6;
    TextView sent;
    Button enter_Btn;
    ProgressBar enter_ProgBar;
    public  String verificationId;
    public  String code;
    String sentTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_otpverification);

        input1 = findViewById(R.id.signIn_OTPinput1);
        input2 = findViewById(R.id.signIn_OTPinput2);
        input3 = findViewById(R.id.signIn_OTPinput3);
        input4 = findViewById(R.id.signIn_OTPinput4);
        input5 = findViewById(R.id.signIn_OTPinput5);
        input6 = findViewById(R.id.signIn_OTPinput6);
        sent = findViewById(R.id.sentTo_TxtView);
        enter_Btn = findViewById(R.id.signIn_OTPenter_Btn);
        enter_ProgBar = findViewById(R.id.signIn_enterOTP_ProgBar);
        sentTo = MobileFragment.mobileNumber;

        sent.setText("OTP sent to " + sentTo);

        verificationId = getIntent().getStringExtra("verificationIdSignIn");

        setupInput();

        enter_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input1.getText().toString().trim().isEmpty()
                        || input2.getText().toString().trim().isEmpty()
                        || input3.getText().toString().trim().isEmpty()
                        || input4.getText().toString().trim().isEmpty()
                        || input5.getText().toString().trim().isEmpty()
                        || input6.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter Valid Code", Toast.LENGTH_LONG).show();
                    return;
                }

                code =
                        input1.getText().toString()
                                + input2.getText().toString()
                                + input3.getText().toString()
                                + input4.getText().toString()
                                + input5.getText().toString()
                                + input6.getText().toString();

                if (verificationId !=null){
                    enter_ProgBar.setVisibility(View.VISIBLE);
                    enter_Btn.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthProvider = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthProvider)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    enter_ProgBar.setVisibility(View.GONE);
                                    enter_Btn.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(), SplashScreenLogIn.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "The verification code entered is invalid", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setupInput() {

        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input1.getText().toString().length()==1){
                    input2.requestFocus();
                }




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input2.getText().toString().isEmpty()){
                    input1.requestFocus();
                }
                if (input2.getText().toString().length()==1){
                    input3.requestFocus();
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input3.getText().toString().isEmpty()){
                    input2.requestFocus();
                }
                if (input3.getText().toString().length()==1){
                    input4.requestFocus();
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input4.getText().toString().isEmpty()){
                    input3.requestFocus();
                }
                if (input4.getText().toString().length()==1){
                    input5.requestFocus();
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input5.getText().toString().isEmpty()){
                    input4.requestFocus();
                }if (input5.getText().toString().length()==1){
                    input6.requestFocus();
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (input6.getText().toString().isEmpty()){
                    input5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}