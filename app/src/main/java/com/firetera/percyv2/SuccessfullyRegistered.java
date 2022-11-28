package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessfullyRegistered extends AppCompatActivity {

    Button signinnowbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully_registered);

        signinnowbtn = findViewById(R.id.signinnowbtn);


        signinnowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessfullyRegistered.this, SignIn.class);
                startActivity(intent);
            }
        });
    }

}