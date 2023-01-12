package com.firetera.percyv2.reservationProcess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
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

import com.firetera.percyv2.DatePickerFragment;
import com.firetera.percyv2.HomeFragment;
import com.firetera.percyv2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ReservationProcess extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    String[] items = {"Birthday Event", "Wedding Event", "Corporate Event", "Party Event", "Other"};
    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoCompleteTextView;
    Button nextBtn, incrementBtn, decrementBtn, backArrow;
    TextView date, selectDate;
    TextInputLayout eventTxtInputLayout, otherTxtInputLayout;
    EditText numofPeopleET, name, phoneNumber,companyName, other, where;
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
        eventTxtInputLayout = findViewById(R.id.editText_event);
        otherTxtInputLayout = findViewById(R.id.other_EditText);
        date = findViewById(R.id.date);

        numofPeopleET = findViewById(R.id.numofPeople_RD);
        name = findViewById(R.id.clientName_RD);
        phoneNumber = findViewById(R.id.clientPhoneNumber_RD);
        where = findViewById(R.id.where_RD);
        other = findViewById(R.id.other_RD);
        companyName = findViewById(R.id.clientCompanyName_RD);
        nextBtn = findViewById(R.id.next_btn);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        backArrow = findViewById(R.id.reservation_backArrow);

        date.setText(currentDate());

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        adapterItems = new ArrayAdapter<String>(this, R.layout.events_list_item, items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();

                if (autoCompleteTextView.getText().toString().equals("Birthday Event") ||
                        autoCompleteTextView.getText().toString().equals("Wedding Event") ||
                        autoCompleteTextView.getText().toString().equals("Corporate Event") ||
                        autoCompleteTextView.getText().toString().equals("Party Event")) {
                    numofPeopleET.setText("50");
                    other.setVisibility(View.GONE);
                    otherTxtInputLayout.setVisibility(View.GONE);



                }
                else if (autoCompleteTextView.getText().toString().equals("Other")){

                    autoCompleteTextView.setVisibility(View.GONE);
                    eventTxtInputLayout.setVisibility(View.GONE);
                    otherTxtInputLayout.setVisibility(View.VISIBLE);
                    other.setVisibility(View.VISIBLE);

                    numofPeopleET.setText("50");

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
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {


                String phoneNum = phoneNumber.getText().toString();

                if (where.getText().toString().length() == 0){
                    where.setError("Enter venue address");
                }
                else if (autoCompleteTextView.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Event is unfilled, please select event", Toast.LENGTH_LONG).show();
                }
                else if (other.getVisibility() == View.VISIBLE || otherTxtInputLayout.getVisibility() == View.VISIBLE){
                    if (other.getText().toString().length() == 0){
                        other.setError("Enter event");
                    }
                    else {
                        autoCompleteTextView.setVisibility(View.VISIBLE);
                        eventTxtInputLayout.setVisibility(View.VISIBLE);
                        otherTxtInputLayout.setVisibility(View.GONE);
                        other.setVisibility(View.GONE);
                        autoCompleteTextView.setText(other.getText().toString());

                    }
                }

                else if (name.getText().toString().length()==0){
                    name.setError("Enter name");
                }

                else if (companyName.getText().toString().length() == 0){
                    companyName.setText("Without Company");
                }
               else if(phoneNumber.length() != 10){
                   phoneNumber.setError("Invalid phone number");
               }

               else if(phoneNum.charAt(0) != '9'){
                    phoneNumber.setError("Invalid phone number");
                }

               else if (numofPeopleET.getText().toString().length()==0){
                   numofPeopleET.setError("This field is required to answer");
               }

               else {

                   HashMap<String, Object> ReservationDetails = new HashMap<>();
                   ReservationDetails.put("Reservation ID", reservationID);
                   ReservationDetails.put("Name", name.getText().toString());
                    ReservationDetails.put("CompanyName", companyName.getText().toString());
                   ReservationDetails.put("Phone Number", phoneNumber.getText().toString());
                   ReservationDetails.put("ReservationDate", date.getText().toString());
                    ReservationDetails.put("Venue", where.getText().toString());
                   ReservationDetails.put("Event", autoCompleteTextView.getText().toString());
                   ReservationDetails.put("Number of People", numofPeopleET.getText().toString());
                   ReservationDetails.put("User ID", firebaseAuth.getUid());
                   ReservationDetails.put("Status", false);
                   ReservationDetails.put("Time of Reservation", currentTime());
                   ReservationDetails.put("Date of Reservation",currentDate());

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

                    startActivity(new Intent(getApplicationContext(), com.firetera.percyv2.reservationProcess.ReservationDetails.class));
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

    public static String currentDate(){
        return new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
    }

    public static String currentTime(){
        return  new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }



}


