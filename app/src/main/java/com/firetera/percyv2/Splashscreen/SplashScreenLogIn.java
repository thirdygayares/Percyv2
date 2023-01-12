package com.firetera.percyv2.Splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.firetera.percyv2.HomeFragment;
import com.firetera.percyv2.LogIn.AskingForFullNameLogIn;
import com.firetera.percyv2.LogIn.LogInFragment.MobileFragment;
import com.firetera.percyv2.MainActivity2;
import com.firetera.percyv2.R;
import com.firetera.percyv2.RegistrationJavaClass.AskingForEmail;
import com.firetera.percyv2.RegistrationJavaClass.AskingForFullname;
import com.firetera.percyv2.SignInOrSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SplashScreenLogIn extends AppCompatActivity {


    public String name = null;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    public  Boolean myBoolean = false;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_log_in);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                firestore.collection("Users"). document(firebaseAuth.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if(documentSnapshot.exists()){
                                        Toast.makeText(getApplicationContext(), "SUCCESFULLY SIGNED IN", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity2.class));

                                    } else{
                                        Log.d("TAG", "no such document");
                                        startActivity(new Intent(getApplicationContext(), AskingForFullNameLogIn.class));
                                    }
                                }
                            }
                        });
            }
        }, 3000);
    }
}
