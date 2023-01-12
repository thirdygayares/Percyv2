package com.firetera.percyv2.LogIn;

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

import com.firetera.percyv2.LogIn.LogInFragment.MobileFragment;
import com.firetera.percyv2.MainActivity2;
import com.firetera.percyv2.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AskingForEmailLogIn extends AppCompatActivity {

    EditText email;
    Button regBtn;
    ProgressBar progBar;
    String name = AskingForFullNameLogIn.name;
    String mobileNum = MobileFragment.mobileNumber;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_email_log_in);

        email = findViewById(R.id.AFELIemail);
        regBtn = findViewById(R.id.AFELIreg_Btn);
        progBar = findViewById(R.id.AFELIemail_ProgBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

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

                                Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "SUCCESS DATA UPLOAD");
                                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
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