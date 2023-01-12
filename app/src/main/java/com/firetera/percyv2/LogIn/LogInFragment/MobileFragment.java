package com.firetera.percyv2.LogIn.LogInFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firetera.percyv2.LogIn.LogInExampleUI;
import com.firetera.percyv2.LogIn.LogInOTPVerification;
import com.firetera.percyv2.R;
import com.firetera.percyv2.RegistrationJavaClass.AskingForEmail;
import com.firetera.percyv2.RegistrationJavaClass.AskingForFullname;
import com.firetera.percyv2.RegistrationJavaClass.OTPVerification;
import com.firetera.percyv2.RegistrationJavaClass.RegisterUsingMobileNum;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class
MobileFragment extends Fragment {

    Button signinBtn;
    ProgressBar progBar;
    EditText mobileNum;
    PhoneAuthProvider phoneAuthProvider;
    public  static String mobileNumber;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile, container, false);

        signinBtn = view.findViewById(R.id.signinbtn_mobile);
        progBar = view.findViewById(R.id.sign_progressbar_mobile);
        mobileNum = view.findViewById(R.id.mobileNum_EditTxt_mobile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        phoneAuthProvider = PhoneAuthProvider.getInstance();
        progBar.setVisibility(View.GONE);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileNum.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }



                mobileNumber = "+63" + mobileNum.getText().toString();
                progBar.setVisibility(View.VISIBLE);
                signinBtn.setVisibility(View.INVISIBLE);

                phoneAuthProvider.verifyPhoneNumber(
                        "+63" +mobileNum.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        (Activity) getContext(),
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progBar.setVisibility(View.GONE);
                                signinBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationIdSignIn, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progBar.setVisibility(View.GONE);
                                signinBtn.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getContext(), com.firetera.percyv2.LogIn.LogInOTPVerification.class);
                                intent.putExtra("phone", mobileNum.getText().toString());
                                intent.putExtra("verificationIdSignIn", verificationIdSignIn);
                                startActivity(intent);

                            }
                        }
                );
            }
        });

    return  view;
    }
}