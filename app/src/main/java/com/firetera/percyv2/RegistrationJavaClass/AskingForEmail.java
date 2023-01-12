package com.firetera.percyv2.RegistrationJavaClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firetera.percyv2.LogIn.LogInExampleUI;
import com.firetera.percyv2.MainActivity2;
import com.firetera.percyv2.R;
import com.firetera.percyv2.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AskingForEmail extends AppCompatActivity {

    EditText email;
    Button regBtn;
    ProgressBar progBar;
    String name = AskingForFullname.name;
    String code = OTPVerification.code;
    String mobileNum = RegisterUsingMobileNum.mobileNumber;
    public static String userEmail;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_email);

        email = findViewById(R.id.AFEemail);
        regBtn = findViewById(R.id.AFEreg_Btn);
        progBar = findViewById(R.id.email_ProgBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String verificationId = getIntent().getStringExtra("verificationId");

        userEmail = email.getText().toString();


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progBar.setVisibility(View.VISIBLE);
                regBtn.setVisibility(View.INVISIBLE);

                if (email.getText().toString().isEmpty()) {
                    email.setError("Enter email");
                    progBar.setVisibility(View.GONE);
                    regBtn.setVisibility(View.VISIBLE);
                }

                HashMap<String, String> savedusers = new HashMap<>();
                savedusers.put("Fullname", name);
                savedusers.put("Email", email.getText().toString());
                savedusers.put("Phone Number", mobileNum);

                firebaseFirestore.collection("Users").document(firebaseAuth.getUid())
                        .set(savedusers)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "SUCCESS DATA UPLOAD");

                                Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                                firebaseAuth.signOut();
                                Intent intent = new Intent(getApplicationContext(), LogInExampleUI.class);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "data sending" + e);
                                regBtn.setVisibility(View.VISIBLE);
                                progBar.setVisibility(View.VISIBLE);
                            }
                        });
            }
        });
    }
}