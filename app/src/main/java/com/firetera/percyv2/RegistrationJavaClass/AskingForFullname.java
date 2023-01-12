package com.firetera.percyv2.RegistrationJavaClass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firetera.percyv2.R;

public class AskingForFullname extends AppCompatActivity {

    EditText fullName;
    Button next_Btn;

    public static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking_for_details);

        fullName = findViewById(R.id.AFFname);
        next_Btn = findViewById(R.id.AFFnxt_Btn);

        next_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AskingForEmail.class));

                name = fullName.getText().toString();
            }
        });


    }
}