package com.firetera.percyv2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class DetailFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    Button logoutbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_detail_fragment, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        logoutbtn = view.findViewById(R.id.logoutbtn);



        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                Toast.makeText(getContext(), "Successfully Logged Out", Toast.LENGTH_SHORT ).show();

                Intent intent = new Intent(getContext(), SignIn.class);
                startActivity(intent);

            }
        });
    return view;
    }
}