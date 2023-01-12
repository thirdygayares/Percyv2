package com.firetera.percyv2.RegistrationJavaClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class Register extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText  regfullname, regpassword, regconfirmpassword, regemail, regphonenumber;
    Button regbtn;
    TextView directTologin;
    ProgressBar regprogress_bar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            finish();
        }

        regpassword.setTransformationMethod(new PasswordTransformationMethod());
        regconfirmpassword.setTransformationMethod(new PasswordTransformationMethod());

        directTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LogInExampleUI.class);
                startActivity(intent);

            }
        });

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = regemail.getText().toString();
                final String password = regpassword.getText().toString();
                String confirmPassword = regconfirmpassword.getText().toString();
                String phoneNumber = regphonenumber.getText().toString();
                String fullName = regfullname.getText().toString();


                if (email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                   regemail.setError("Invalid Email");
                    regbtn.setVisibility(View.VISIBLE);
                    regprogress_bar.setVisibility(View.GONE);
                }

                else if (fullName.isEmpty()){
                    regfullname.setError("Full name is required");
                    regbtn.setVisibility(View.VISIBLE);
                    regprogress_bar.setVisibility(View.GONE);
                }

                else if ( password.isEmpty()){
                    regpassword.setError("Password is required");
                    regbtn.setVisibility(View.VISIBLE);
                    regprogress_bar.setVisibility(View.GONE);
                }


                else if (confirmPassword.isEmpty()){
                    regconfirmpassword.setError("Password is required");
                    regbtn.setVisibility(View.VISIBLE);
                    regprogress_bar.setVisibility(View.GONE);
                }

                else if (!password.equals(confirmPassword)){
                    regconfirmpassword.setError("Not match");
                    regpassword.setError("Not Match");
                    regbtn.setVisibility(View.VISIBLE);
                    regprogress_bar.setVisibility(View.GONE);
                }

                else  if (phoneNumber.isEmpty()){
                    regphonenumber.setError("Enter your Mobile Number");
                    regbtn.setVisibility(View.VISIBLE);
                    regprogress_bar.setVisibility(View.GONE);
                }

                else if(phoneNumber.length() != 10){
                    regphonenumber.setError("Invalid phone number");
                }

                else if(phoneNumber.charAt(0) != '0' && phoneNumber.charAt(1) != '9'){
                    regphonenumber.setError("Invalid phone number");
                }

                else {

                    regbtn.setVisibility(View.GONE);
                    regprogress_bar.setVisibility(View.VISIBLE);


                    firebaseAuth.createUserWithEmailAndPassword(regemail.getText().toString(), regconfirmpassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(), "Sucessfully registered, Please verify your email", Toast.LENGTH_LONG).show();
                                                    regfullname.setText(" ");
                                                    regemail.setText(" ");
                                                    regpassword.setText(" ");
                                                    regconfirmpassword.setText(" ");
                                                    regphonenumber.setText(" ");
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });

                                        HashMap <String, String> savedusers = new HashMap<>();
                                        savedusers.put("Fullname", regfullname.getText().toString());
                                        savedusers.put("Email", regemail.getText().toString());
                                        savedusers.put("Phone Number", regphonenumber.getText().toString());

                                        firebaseFirestore.collection("Users").document(firebaseAuth.getUid())
                                                .set(savedusers)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Log.d("TAG", "SUCCESS DATA UPLOAD");
                                                        Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_LONG).show();

                                                        startActivity(new Intent(getApplicationContext(), LogInExampleUI.class));
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("TAG", "data sending" + e);
                                                        regbtn.setVisibility(View.VISIBLE);
                                                        regprogress_bar.setVisibility(View.VISIBLE);
                                                    }
                                                });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                        regbtn.setVisibility(View.VISIBLE);
                                        regprogress_bar.setVisibility(View.GONE);
                                    }

                                }
                            });

                }
            }
        });

    firebaseAuth.signOut();

    }



    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
           startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        }
    }
}









