package com.firetera.percyv2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {

    CardView foodbutton, schedulebutton, eventthemebutton;
    TextView username, password, clientname;
    Button signinbtn, googlesigninbtn, logoutbtn;
    CheckBox showpassword;
    ImageView cateringinfo_arrow;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        //XML
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        signinbtn = view.findViewById(R.id.signinbtn);
        showpassword = view.findViewById(R.id.showpw);
        clientname = view.findViewById(R.id.clientname);
        logoutbtn = view.findViewById(R.id.logoutbtn);
        cateringinfo_arrow = view.findViewById(R.id.cateringinfoarrow);


        //FIREBASE
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        firestore.collection("Users"). document(firebaseAuth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                clientname.setText( documentSnapshot.getString("Fullname"));
                            } else{
                                Log.d("TAG", "no such document");
                            }
                        }
                    }
                });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), SignIn.class);
                startActivity(intent);

            }
        });

        return view;
    }

    private void cateringinfo(){

        cateringinfo_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CateringInfo.class));
            }
        });
    }

    private void eventthemephase() {
        eventthemebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventThemeOption.class);
                startActivity(intent);
            }
        });
    }

    private void schedulephasemethod() {
        schedulebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScheduleOption.class);
                startActivity(intent);
            }
        });
    }


    private void foodphasemethod() {
        foodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Foodoption.class);
                startActivity(intent);
            }
        });
    }

    }




