package com.firetera.percyv2.ReservationProcessPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.firetera.percyv2.R;

public class ReservationProcessPersonalInfo extends AppCompatActivity {

    EditText firstName, lastName, email, mobileNum, address, companyName;
    Button nextBtn;
    ProgressBar progressBar;
    public static String clientFirstName, clientLastName, clientEmailAdd, clientMobileNum, clientAddress, clientCompanyName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_process_personal_info);

        firstName = findViewById(R.id.clientFirstName);
        lastName = findViewById(R.id.clientLastName);
        email = findViewById(R.id.clientEmail);
        mobileNum = findViewById(R.id.clientMobileNumber);
        companyName = findViewById(R.id.clientCompanyName);
        address = findViewById(R.id.clientAddress);
        nextBtn = findViewById(R.id.next_btn);
        progressBar = findViewById(R.id.nextBtn_ProgBar);

        nextBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nextBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String clientEmail = email.getText().toString();

                String mobileNumInside = mobileNum.getText().toString();

                if (firstName.getText().toString().isEmpty()){
                    firstName.setError("Enter your First Name");
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                else if (lastName.getText().toString().isEmpty()){
                    lastName.setError("Enter your Last Name");
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                else if (email.getText().toString().isEmpty()) {
                    email.setError("Enter email");
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    email.setError("Invalid Email");
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                else if (mobileNum.getText().toString().isEmpty()){
                    mobileNum.setError("Enter your Mobile Number");
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                else if(mobileNum.length() != 10){
                    mobileNum.setError("Invalid phone number");
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                else if(mobileNumInside.charAt(0) != '9'){
                    mobileNum.setError("Invalid phone number");
                    nextBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                else {
                    clientFirstName = firstName.getText().toString();
                    clientLastName = lastName.getText().toString();
                    clientEmailAdd = email.getText().toString();
                    clientMobileNum = mobileNum.getText().toString();
                    clientAddress = address.getText().toString();
                    clientCompanyName = companyName.getText().toString();

                    if (companyName.getText().toString().isEmpty()){
                        companyName.setText("(No Company)");
                    }

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nextBtn.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            startActivity(new Intent(getApplicationContext(), ReservationProcess.class));

                        }
                    },3000);
                }
            }
        });





    }
}