package com.firetera.percyv2.ReservationProcessPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firetera.percyv2.Adapter.SelectListiner;
import com.firetera.percyv2.HomeFragment;
import com.firetera.percyv2.LoadingDialog;
import com.firetera.percyv2.Model.FoodPackageModel;
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

import java.text.DecimalFormat;
import java.util.HashMap;

public class PaymentMethod extends AppCompatActivity {

    TextView packageName, foodNo1, foodNo2, foodNo3, foodNo4, totalPrice, precyMobileNum;
    ImageView copy;
    Button reserve_Btn;
    ProgressBar progressBar;
    AutoCompleteTextView autoCompleteTextView;
    FrameLayout frameLayout;

    public String reservationID, date, event, name, phoneNumber, numOfPeople,
            venue, companyName, foodPackageName, email;

    public String numOfPendingReservation;


    String [] items = {"GCash"};
    ArrayAdapter<String> adapterItems;


    public String priceOfFoodPackage = ReservationProcess.price;
    String numOfPax = ReservationProcess.numOfPeople;
    String totalPriceString;
    int priceOfFoodPackageInt, numOfPaxInt, totalPriceInt;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        packageName = findViewById(R.id.PM_packageName);
        foodNo1 = findViewById(R.id.PM_foodNo1);
        foodNo2 = findViewById(R.id.PM_foodNo2);
        foodNo3 = findViewById(R.id.PM_foodNo3);
        foodNo4 = findViewById(R.id.PM_foodNo4);
        totalPrice = findViewById(R.id.PM_totalPrice);
        autoCompleteTextView = findViewById(R.id.modeOfPayment_ACT);
        precyMobileNum = findViewById(R.id.precyMobileNum_TextView);
        copy = findViewById(R.id.copy_ImageView);
        frameLayout = findViewById(R.id.gCash_frameLayout);
        reserve_Btn = findViewById(R.id.reserve_Btn);
        progressBar = findViewById(R.id.reserveBtn_ProgBar);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        reservationID = HomeFragment.reservationID;

        priceOfFoodPackageInt = Integer.parseInt(priceOfFoodPackage);
        numOfPaxInt = Integer.parseInt(numOfPax);

        adapterItems = new ArrayAdapter<String>(this, R.layout.events_list_item, items);
        autoCompleteTextView.setAdapter(adapterItems);

        firebaseFirestore.collection("NumberOfPending"). document("NumberOfPendingReservation") .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
                if (documentSnapshot.exists()) {
                    numOfPendingReservation = documentSnapshot.get("NumberOfPendingReservation").toString();
                }
            }
        });

        reserve_Btn.setVisibility(View.VISIBLE);
        setUpReserveButton();
        setUpFoodPackageName();
        setUpFireBase();
        setUpAutoCompleteTextView();
        totalPriceInt = numOfPaxInt * priceOfFoodPackageInt;

        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");


        totalPriceString = Integer.toString(totalPriceInt);

        totalPrice.setText(numOfPax + " x " + priceOfFoodPackage + " = P" + decimalFormat.format(totalPriceInt));

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("TextView", precyMobileNum.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                clipData.getDescription();

                Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_LONG).show();

            }
        });



    }

    private void setUpReserveButton() {

        reserve_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reserve_Btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                int  intNumOfPendingReservation = Integer.parseInt(numOfPendingReservation);
                intNumOfPendingReservation += 1;

                HashMap<String, Object> ReservationDetails = new HashMap<>();
                ReservationDetails.put("Reservation ID", reservationID);
                ReservationDetails.put("Name", name);
                ReservationDetails.put("CompanyName", companyName);
                ReservationDetails.put("Phone Number", phoneNumber);
                ReservationDetails.put("ReservationDate", date);
                ReservationDetails.put("Venue", venue);
                ReservationDetails.put("Event", event);
                ReservationDetails.put("Number of People", numOfPeople);
                ReservationDetails.put("User ID", firebaseAuth.getUid());
                ReservationDetails.put("Status", false);
                ReservationDetails.put("Time of Reservation", ReservationProcess.currentTime());
                ReservationDetails.put("Date of Reservation", ReservationProcess.currentDate());

                firebaseFirestore.collection("PendingReservation").document(reservationID)
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


                numOfPendingReservation = Integer.toString(intNumOfPendingReservation);

                HashMap<String, String> number = new HashMap<>();
                number.put("NumberOfPendingReservation", numOfPendingReservation );

                firebaseFirestore.collection("NumberOfPending").document("NumberOfPendingReservation")
                        .set(number)
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


                firebaseFirestore.collection("Users").document(firebaseAuth.getUid())
                        .collection("My Reservation")
                        .document(reservationID)
                        .set(ReservationDetails);
                final LoadingDialog loadingDialog = new LoadingDialog(PaymentMethod.this);

                loadingDialog.startLoadingDialog();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                        startActivity(new Intent(PaymentMethod.this, ConfirmationOfReservation.class));
                    }
                },3000);

            }
        });
    }

    private void setUpFoodPackageName() {
        foodNo1.setText(ReservationProcess.foodNo1);
        foodNo2.setText(ReservationProcess.foodNo2);
        foodNo3.setText(ReservationProcess.foodNo3);
        foodNo4.setText(ReservationProcess.foodNo4);

    }

    private void setUpAutoCompleteTextView() {

        frameLayout.setVisibility(View.GONE);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                if (autoCompleteTextView.getText().toString().equals("GCash")){
                    frameLayout.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void setUpFireBase() {

        firebaseFirestore.collection("ClientReservationDetails"). document(reservationID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                reservationID =documentSnapshot.getString("Reservation ID");
                                date = documentSnapshot.getString("ReservationDate");
                                venue = documentSnapshot.getString("Venue");
                                companyName = documentSnapshot.getString("CompanyName");
                                name  = documentSnapshot.getString("Name");
                                event = documentSnapshot.getString("Event");
                                phoneNumber =  "+63" + documentSnapshot.getString("Phone Number");
                                numOfPeople = documentSnapshot.getString("Number of People");
                                packageName.setText(documentSnapshot.getString("PackageName"));
                                email = documentSnapshot.getString("Email");

                            } else{
                                Log.d("TAG", "no such document");
                            }
                        }
                    }
                });
    }


}