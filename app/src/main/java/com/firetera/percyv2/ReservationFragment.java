package com.firetera.percyv2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firetera.percyv2.Adapter.ReservationHistoryAdapter;
import com.firetera.percyv2.Model.ReservationHistoryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class ReservationFragment extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    ReservationHistoryAdapter myAdapter;
    ArrayList<ReservationHistoryModel> list;
    String ID;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_reservation_fragment, container, false);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firestore.collection("Users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .collection("My Reservation")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
                else{
                    list.clear();

                    for (QueryDocumentSnapshot document :value) {
                        if (document.exists()) {
                            list.add(new ReservationHistoryModel(document.get("Reservation ID").toString(), document.get("Name").toString(), document.get("Phone Number").toString(), document.get("Event").toString() , document.get("ReservationDate").toString(), document.get("Number of People").toString(), Boolean.parseBoolean(document.get("Status").toString()), document.get("CompanyName").toString(), document.get("Venue").toString()));
                            myAdapter.notifyDataSetChanged();

                        }
                    }
                }

            }
        });


        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.reservationHistory_RecyclerView);

        firestore = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() ));

        myAdapter = new ReservationHistoryAdapter(getContext(), list);
        recyclerView.setAdapter(myAdapter);


//        firestore.collection("ClientReservationDetails").whereEqualTo(" User ID", firebaseAuth.getUid())
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot s) {
//                        if(!s.isEmpty()){
//                            Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
//
//                            for(DocumentSnapshot value: s){
//                                list.add(new ReservationHistoryModel(value.get("Reservation ID").toString(), value.get("Name").toString(), value.get("Phone Number").toString(), value.get("Event").toString() , value.get("ReservationDate").toString(), value.get("Number of People").toString(), Boolean.parseBoolean(value.get("Status").toString())));
//                                myAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });


        return view;
    }
}