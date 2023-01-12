package com.firetera.percyv2.RegistrationJavaClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firetera.percyv2.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterUsingMobileNum extends AppCompatActivity {

    EditText mobileNum;
    ProgressBar OTPsend_ProgBar;
    Button sendOTP_Btn;
    PhoneAuthProvider phoneAuthProvider;
    public  static String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_using_mobile_num);

        mobileNum = findViewById(R.id.mobileNum_EditTxt);
        OTPsend_ProgBar = findViewById(R.id.sentOTP_ProgBar);
        sendOTP_Btn = findViewById(R.id.OTPsend_Btn);
        phoneAuthProvider = PhoneAuthProvider.getInstance();



        sendOTP_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileNum.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                mobileNumber = "+63" + mobileNum.getText().toString();
                OTPsend_ProgBar.setVisibility(View.VISIBLE);
                sendOTP_Btn.setVisibility(View.INVISIBLE);

                phoneAuthProvider.verifyPhoneNumber(
                        "+63" +mobileNum.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        RegisterUsingMobileNum.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                OTPsend_ProgBar.setVisibility(View.GONE);
                                sendOTP_Btn.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                OTPsend_ProgBar.setVisibility(View.GONE);
                                sendOTP_Btn.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(), OTPVerification.class);
                                intent.putExtra("phone", mobileNum.getText().toString());
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);


                            }
                        }
                );
            }
        });




    }
}