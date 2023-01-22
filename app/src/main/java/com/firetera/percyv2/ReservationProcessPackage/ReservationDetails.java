package com.firetera.percyv2.ReservationProcessPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firetera.percyv2.HomeFragment;
import com.firetera.percyv2.LoadingDialog;
import com.firetera.percyv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class ReservationDetails extends AppCompatActivity {

    TextView IDreservation;
    TextView date;
    TextView event;
    TextView name;
    TextView phoneNumber;
    TextView numOfPeople;
    TextView venue;
    TextView companyName;
    TextView packageName;
    TextView email;
    Button changeDetails_Btn, proceed_Btn;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);

        IDreservation = findViewById(R.id.reservationID_txtView);
        changeDetails_Btn = findViewById(R.id.changeDetails_Btn);
        proceed_Btn = findViewById(R.id.proceed_Btn);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        packageName = findViewById(R.id.RD_foodPackage);
        date = findViewById(R.id.RD_date);
        event = findViewById(R.id.RD_event);
        name = findViewById(R.id.RD_clientName);
        phoneNumber = findViewById(R.id.RD_phoneNumber);
        email = findViewById(R.id.RD_email);
        numOfPeople = findViewById(R.id.RD_numofPeople);
        venue = findViewById(R.id.RD_venu);
        companyName = findViewById(R.id.RD_clientCompanyName);
        String reservationID = HomeFragment.reservationID;

        firestore.collection("ClientReservationDetails"). document(reservationID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                               IDreservation.setText( documentSnapshot.getString("Reservation ID"));
                                date.setText( documentSnapshot.getString("ReservationDate"));
                                venue.setText(documentSnapshot.getString("Venue"));
                                companyName.setText(documentSnapshot.getString("CompanyName"));
                                name.setText( documentSnapshot.getString("Name"));
                                event.setText( documentSnapshot.getString("Event"));
                                phoneNumber.setText( "+63" + documentSnapshot.getString("Phone Number"));
                                numOfPeople.setText( documentSnapshot.getString("Number of People"));
                                packageName.setText(documentSnapshot.getString("PackageName"));
                                email.setText(documentSnapshot.getString("Email"));

                            } else{
                                Log.d("TAG", "no such document");
                            }
                        }
                    }
                });

//        firestore.collection("NumberOfPending"). document("NumberOfPendingReservation") .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//
//                if (error != null) {
//                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                }
//                    if (documentSnapshot.exists()) {
//                        numOfPendingReservation = documentSnapshot.get("NumberOfPendingReservation").toString();
//                    }
//                }
//        });

        changeDetails_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        proceed_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                 int  intNumOfPendingReservation = Integer.parseInt(numOfPendingReservation);
//                intNumOfPendingReservation += 1;
//
//                HashMap<String, Object> ReservationDetails = new HashMap<>();
//                ReservationDetails.put("Reservation ID", reservationID);
//                ReservationDetails.put("Name", name.getText().toString());
//                ReservationDetails.put("CompanyName", companyName.getText().toString());
//                ReservationDetails.put("Phone Number", phoneNumber.getText().toString());
//                ReservationDetails.put("ReservationDate", date.getText().toString());
//                ReservationDetails.put("Venue", venue.getText().toString());
//                ReservationDetails.put("Event", event.getText().toString());
//                ReservationDetails.put("Number of People", numOfPeople.getText().toString());
//                ReservationDetails.put("User ID", firebaseAuth.getUid());
//                ReservationDetails.put("Status", false);
//                ReservationDetails.put("Time of Reservation", ReservationProcess.currentTime());
//                ReservationDetails.put("Date of Reservation", ReservationProcess.currentDate());
//
//                firestore.collection("PendingReservation").document(reservationID)
//                        .set(ReservationDetails)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Log.d("TAG", "SUCCESS DATA UPLOAD");
//
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("TAG", "data sending" + e);
//                            }
//                        });
//
//
//                numOfPendingReservation = Integer.toString(intNumOfPendingReservation);
//
//                HashMap<String, String> number = new HashMap<>();
//                number.put("NumberOfPendingReservation", numOfPendingReservation );
//
//                firestore.collection("NumberOfPending").document("NumberOfPendingReservation")
//                        .set(number)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Log.d("TAG", "SUCCESS DATA UPLOAD");
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("TAG", "data sending" + e);
//                            }
//                        });
//
//                HashMap<String, Object> ReservationDetails1 = new HashMap<>();
//                ReservationDetails1.put("Phone Number", phoneNumber.getText().toString());
//
//                firestore.collection("ClientReservationDetails").document(reservationID)
//                        .set(ReservationDetails)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Log.d("TAG", "SUCCESS DATA UPLOAD");
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("TAG", "data sending" + e);
//                            }
//                        });

//                firestore.collection("Users").document(firebaseAuth.getUid())
//                        .collection("My Reservation")
//                        .document(reservationID)
//                        .set(ReservationDetails);


                startActivity(new Intent(ReservationDetails.this, PaymentMethod.class));


            }
        });
    }
}
