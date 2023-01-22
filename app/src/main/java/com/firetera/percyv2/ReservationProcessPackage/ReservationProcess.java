package com.firetera.percyv2.ReservationProcessPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firetera.percyv2.Adapter.FoodPackageAdapter;
import com.firetera.percyv2.Adapter.SelectListiner;
import com.firetera.percyv2.DatePickerFragment;
import com.firetera.percyv2.HomeFragment;
import com.firetera.percyv2.Model.FoodPackageModel;
import com.firetera.percyv2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ReservationProcess extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, SelectListiner  {


    String[] items = {"Birthday Event", "Wedding Event", "Corporate Event", "Party Event", "Other"};
    ProgressBar progressBar;
    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoCompleteTextView;
    Button nextBtn, backArrow;
    TextView date, selectDate;
    TextInputLayout eventTxtInputLayout, otherTxtInputLayout;
    EditText numOfPax, other, where;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String reservationID = HomeFragment.reservationID;
    static String chosenEvent;
    public String firstName, lastName, name, companyName, phoneNumber, email;
    public static String foodNo1, foodNo2, foodNo3, foodNo4;

    ArrayList<FoodPackageModel> list;
    RecyclerView recyclerView;
    FoodPackageAdapter foodPackageAdapter;

    FirebaseFirestore firebaseFirestore;

    public static String price, packageName;
    public static String numOfPeople;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_process);

        selectDate = findViewById(R.id.selectDate_tv);
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        eventTxtInputLayout = findViewById(R.id.editText_event);
        otherTxtInputLayout = findViewById(R.id.other_EditText);
        date = findViewById(R.id.date);
        progressBar = findViewById(R.id.nextBtn_ProgBar);


        firstName = ReservationProcessPersonalInfo.clientFirstName;
        lastName = ReservationProcessPersonalInfo.clientLastName;
        name = firstName + " " + lastName;
        companyName = ReservationProcessPersonalInfo.clientCompanyName;
        phoneNumber = ReservationProcessPersonalInfo.clientMobileNum;
        email = ReservationProcessPersonalInfo.clientEmailAdd;

        numOfPax = findViewById(R.id.numOfPax_RD);
        where = findViewById(R.id.where_RD);
        other = findViewById(R.id.other_RD);

        nextBtn = findViewById(R.id.next_btn);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        backArrow = findViewById(R.id.reservation_backArrow);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.foodPackage_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this ));
        foodPackageAdapter = new FoodPackageAdapter(getApplicationContext(), list, (SelectListiner) this);
        recyclerView.setAdapter(foodPackageAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();


        date.setText(currentDate());
        setUpFoodPackageRecyclerView();

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
                    numOfPax.setText("50");
                    other.setVisibility(View.GONE);
                    otherTxtInputLayout.setVisibility(View.GONE);



                }
                else if (autoCompleteTextView.getText().toString().equals("Other")){

                    autoCompleteTextView.setVisibility(View.GONE);
                    eventTxtInputLayout.setVisibility(View.GONE);
                    otherTxtInputLayout.setVisibility(View.VISIBLE);
                    other.setVisibility(View.VISIBLE);

                    numOfPax.setText("50");

                }
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment datePicker = new DatePickerFragment();
//                datePicker.show(getSupportFragmentManager(), "date picker");
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nextBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);


                if (where.getText().toString().length() == 0){
                    where.setError("Enter venue address");
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                else if (autoCompleteTextView.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Event is unfilled, please select event", Toast.LENGTH_LONG).show();
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                else if (other.getVisibility() == View.VISIBLE || otherTxtInputLayout.getVisibility() == View.VISIBLE){
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
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



               else if (numOfPax.getText().toString().length()==0){
                   numOfPax.setError("This field is required to answer");
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
               }

               else {
                    nextBtn.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    numOfPeople = numOfPax.getText().toString();
                   HashMap<String, Object> ReservationDetails = new HashMap<>();
                   ReservationDetails.put("Reservation ID", reservationID);
                   ReservationDetails.put("Name", name);
                   ReservationDetails.put("CompanyName", companyName);
                   ReservationDetails.put("Phone Number", phoneNumber);
                   ReservationDetails.put("Email", email);
                   ReservationDetails.put("ReservationDate", date.getText().toString());
                   ReservationDetails.put("Venue", where.getText().toString());
                   ReservationDetails.put("Event", autoCompleteTextView.getText().toString());
                   ReservationDetails.put("Number of People", numOfPax.getText().toString());
                   ReservationDetails.put("User ID", firebaseAuth.getUid());
                   ReservationDetails.put("Status", false);
                   ReservationDetails.put("Time of Reservation", currentTime());
                   ReservationDetails.put("Date of Reservation",currentDate());
                   ReservationDetails.put("Price", price);
                   ReservationDetails.put("PackageName", packageName);

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

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nextBtn.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            startActivity(new Intent(getApplicationContext(), ReservationDetails.class));
                        }
                    }, 5000);
               }



            }
        });


        chosenEvent = autoCompleteTextView.getText().toString();



    }





    public static String currentDate(){
        return new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
    }

    public static String currentTime(){
        return  new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private void setUpFoodPackageRecyclerView() {

        firebaseFirestore.collection("FoodPackageList").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(ReservationProcess.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
                else{
                    list.clear();

                    for (QueryDocumentSnapshot document :value) {

                        if (document.exists()) {
                            list.add(new FoodPackageModel(document.get("PackageName").toString(),
                                    document.get("Price").toString(),
                                    document.get("FoodNo1").toString(),
                                    document.get("FoodNo2").toString() ,
                                    document.get("FoodNo3").toString(),
                                    document.get("FoodNo4").toString(),
                                    (Boolean) document.get("Status")));

                            foodPackageAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.YEAR, year);
        c.set(java.util.Calendar.MONTH, month);
        c.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        date.setText(currentDateString);
    }

    @Override
    public void onItemClicked(FoodPackageModel foodPackageModel) {

        price = foodPackageModel.getPrice();
        packageName = foodPackageModel.getPackageName();
        foodNo1 = foodPackageModel.getFoodNo1();
        foodNo2 = foodPackageModel.getFoodNo2();
        foodNo3 = foodPackageModel.getFoodNo3();
        foodNo4 = foodPackageModel.getFoodNo4();

        Toast.makeText(getApplicationContext(), "You selected" + foodPackageModel.getPackageName(), Toast.LENGTH_LONG).show();

    }
}



