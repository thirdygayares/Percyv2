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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ReservationDetails extends AppCompatActivity {

    TextView IDreservation, date, event, name, phoneNumber, numOfPeople;
    Button changeDetails_Btn, proceed_Btn;
    FirebaseFirestore firestore;


    String idNum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);


        IDreservation = findViewById(R.id.reservationID_txtView);
        changeDetails_Btn = findViewById(R.id.changeDetails_Btn);
        proceed_Btn = findViewById(R.id.proceed_Btn);
        firestore = FirebaseFirestore.getInstance();
        date = findViewById(R.id.RD_date);
        event = findViewById(R.id.RD_event);
        name = findViewById(R.id.RD_clientName);
        phoneNumber = findViewById(R.id.RD_phoneNumber);
        numOfPeople = findViewById(R.id.RD_numofPeople);
        String reservationID = HomeFragment.reservationID;
        final String[] chosenEvent = new String[1];

        firestore.collection("ClientReservationDetails"). document(reservationID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                               IDreservation.setText( documentSnapshot.getString("Reservation ID"));
                                date.setText( documentSnapshot.getString("ReservationDate"));
                                name.setText( documentSnapshot.getString("Name"));
                                event.setText( documentSnapshot.getString("Event"));
                                chosenEvent[0] = (documentSnapshot.getString("Event"));
                                phoneNumber.setText( documentSnapshot.getString("Phone Number"));
                                numOfPeople.setText( documentSnapshot.getString("Number of People"));

                            } else{
                                Log.d("TAG", "no such document");
                            }
                        }
                    }
                });

        changeDetails_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        proceed_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> ReservationDetails = new HashMap<>();
                ReservationDetails.put("Reservation ID", IDreservation.getText().toString());
                ReservationDetails.put("Name", name.getText().toString());
                ReservationDetails.put("Phone Number", phoneNumber.getText().toString());
                ReservationDetails.put("ReservationDate", date.getText().toString());
                ReservationDetails.put("Event", chosenEvent[0]);
                ReservationDetails.put("Number of People", numOfPeople.getText().toString());

                firestore.collection("PendingReservation").document(reservationID)
                        .set(ReservationDetails)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "SUCCESS DATA UPLOAD");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "data sending" + e);
                            }
                        });

                startActivity(new Intent(ReservationDetails.this, ConfirmationOfReservation.class));
            }
        });












//        firestore.collection("ClientReservationDetails"). document()
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()){
//                            DocumentSnapshot documentSnapshot = task.getResult();
//                            if(documentSnapshot.exists()){
//                                IDreservation.setText( documentSnapshot.getString("Reservation ID"));
//                            } else{
//                                Log.d("TAG", "no such document");
//                            }
//                        }
//                    }
//                });
    }
}