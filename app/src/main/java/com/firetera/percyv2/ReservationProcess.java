package com.firetera.percyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class ReservationProcess extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    String[] items = {"Birthday Event", "Wedding Event", "Corporate Event", "Event"};
    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoCompleteTextView;
    Button nextBtn, incrementBtn, decrementBtn;
    TextView date, selectDate;
    EditText numofPeopleET, name, phoneNumber;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String reservationID = HomeFragment.reservationID;
    static String chosenEvent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_process);

        selectDate = findViewById(R.id.selectDate_tv);
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        date = findViewById(R.id.date);
        numofPeopleET = findViewById(R.id.numofPeople_RD);
        name = findViewById(R.id.clientName_RD);
        phoneNumber = findViewById(R.id.clientPhoneNumber_RD);
        nextBtn = findViewById(R.id.next_btn);
        incrementBtn = findViewById(R.id.increment_btn);
        decrementBtn = findViewById(R.id.decrement_btn);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        adapterItems = new ArrayAdapter<String>(this, R.layout.events_list_item, items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();

                if (autoCompleteTextView.getText().toString().equals("Birthday Event")) {
                    numofPeopleET.setText("10");

                    int x;
                    x = Integer.parseInt(numofPeopleET.getText().toString());
                    if (x < 10) {

                        numofPeopleET.setError("");
                    }

                }


            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (name.getText().toString().length()==0){
                   name.setError("Enter name");
               }
               else if (autoCompleteTextView.getText().toString().length()==0){
                   autoCompleteTextView.setError("Select Event");
               }
               else if (phoneNumber.getText().toString().length()==0){
                   phoneNumber.setError("Enter phone number");
               }
               else if (numofPeopleET.getText().toString().length()==0){
                   numofPeopleET.setError("This field is required to answer");
               }
               else {




                   HashMap<String, Object> ReservationDetails = new HashMap<>();
                   ReservationDetails.put("Reservation ID", reservationID);
                   ReservationDetails.put("Name", name.getText().toString());
                   ReservationDetails.put("Phone Number", phoneNumber.getText().toString());
                   ReservationDetails.put("ReservationDate", date.getText().toString());
                   ReservationDetails.put("Event", autoCompleteTextView.getText().toString());
                   ReservationDetails.put("Number of People", numofPeopleET.getText().toString());
                   ReservationDetails.put("User ID", firebaseAuth.getUid());
                   ReservationDetails.put("Status", false);

                   firestore.collection("ClientReservationDetails").document(reservationID)
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

                   firestore.collection("Users").document(firebaseAuth.getUid())
                           .collection("My Reservation")
                           .document(reservationID)
                           .set(ReservationDetails);
                   startActivity(new Intent(getApplicationContext(), com.firetera.percyv2.ReservationDetails.class));
               }


            }
        });


        chosenEvent = autoCompleteTextView.getText().toString();



    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        date.setText(currentDateString);


    }







}


