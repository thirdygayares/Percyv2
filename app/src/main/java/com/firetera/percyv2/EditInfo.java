package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditInfo extends AppCompatActivity {

    TextView fullName_TxtView, fullNameHint, phoneNumber_TxtView, phoneNumberHint;
    EditText fullName_EditTxt, phoneNumber_EditTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        fullName_TxtView = findViewById(R.id.fullName_TxtView);
        fullName_EditTxt = findViewById(R.id.fullName_EditTxt);
        fullNameHint = findViewById(R.id.fullNameHint);
        phoneNumber_EditTxt = findViewById(R.id.phoneNumber_EditTxt);
        phoneNumber_TxtView = findViewById(R.id.phoneNumber_TxtView);
        phoneNumberHint = findViewById(R.id.phoneNumberHint);


        fullName_TxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName_TxtView.setVisibility(View.GONE);
                fullName_EditTxt.setVisibility(View.VISIBLE);
                fullNameHint.setVisibility(View.VISIBLE);
            }
        });

        phoneNumber_TxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber_TxtView.setVisibility(View.GONE);
                phoneNumber_EditTxt.setVisibility(View.VISIBLE);
                phoneNumberHint.setVisibility(View.VISIBLE);
            }
        });

    }
}