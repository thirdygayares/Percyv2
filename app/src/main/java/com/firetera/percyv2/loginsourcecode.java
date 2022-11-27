package com.firetera.percyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firetera.login_registration_mysql.MainActivity;
import com.firetera.login_registration_mysql.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class loginsourcecode extends AppCompatActivity {

    EditText email,password,username,fullname,confirmpassword;
    Button signup;
    TextView login;

    FirebaseAuth firebaseAuth; //authentication
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        login = findViewById(R.id.login);

        signup = findViewById(R.id.signup);


        firebaseAuth = FirebaseAuth.getInstance(); //initialize auth
        firestore = FirebaseFirestore.getInstance(); // initializa fire



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), confirmpassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Log.d("TAG", "SUCCESS");

                                //save name and other details
                                //document and collection

                                HashMap<String, String> jomasavedusers = new HashMap<>();
                                jomasavedusers.put("Username", username.getText().toString());
                                jomasavedusers.put("Fullname", fullname.getText().toString());
                                jomasavedusers.put("Email", email.getText().toString());



                                firestore.collection("PrecyUsers").document(firebaseAuth.getUid())
                                        .set(jomasavedusers)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("TAG", "SUCCESS DATA UPLOAD");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("TAG", "data sending " + e);
                                            }
                                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", e.toString());
                            }
                        });


            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JRegister.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


    //login

    public class Home extends AppCompatActivity {

        FirebaseFirestore firestore;
        FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_homr);

            Button Logout = findViewById(R.id.logout);
            TextView name = findViewById(R.id.name);

            firestore = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();

            firestore.collection("PrecyUsers").document(firebaseAuth.getUid())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if(documentSnapshot.exists()){
                                    name.setText( documentSnapshot.getString("Fullname"));
                                }else{
                                    Log.d("TAG", "no such document");
                                }

                            }
                        }
                    });


            Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.signOut();
                    Intent intent = new Intent(Home.this, Login.class);
                    startActivity(intent);
                }
            });

        }
    }

}