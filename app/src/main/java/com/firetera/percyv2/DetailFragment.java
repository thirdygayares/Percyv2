package com.firetera.percyv2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firetera.percyv2.LogIn.LogInExampleUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class DetailFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    Button logoutbtn, editInfo_Btn, contactnum_btn, location_btn;
    TextView menu_fullname, client_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_detail_fragment, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        logoutbtn = view.findViewById(R.id.logoutbtn);
        contactnum_btn =view.findViewById(R.id.contactnumber_btn);
        location_btn = view.findViewById(R.id.location_btn);
        menu_fullname = view.findViewById(R.id.menu_fullname);
        client_email = view.findViewById(R.id.client_email);
        editInfo_Btn = view.findViewById(R.id.editInfo_Btn);



        firestore.collection("Users"). document(firebaseAuth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                menu_fullname.setText( documentSnapshot.getString("Fullname"));
                            } else{
                                Log.d("TAG", "no such document");
                            }
                        }
                    }
                });

        firestore.collection("Users"). document(firebaseAuth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                client_email.setText( documentSnapshot.getString("Email"));
                            } else{
                                Log.d("TAG", "no such document");
                            }
                        }
                    }
                });





        editInfo_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), EditInfo.class));
            }
        });




        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goLink("https://goo.gl/maps/Kp33W8MQFaHNC2Pw8");
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                Toast.makeText(getContext(), "Successfully Logged Out", Toast.LENGTH_SHORT ).show();

                Intent intent = new Intent(getContext(), LogInExampleUI.class);
                startActivity(intent);

            }
        });






    return view;
    }


    private void goLink(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));


    }

}