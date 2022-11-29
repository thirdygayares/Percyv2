package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CateringInfo extends AppCompatActivity {

    ImageView cateringinfo_backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catering_info);

        cateringinfo_backarrow = findViewById(R.id.cateringinfo_backarrow);

        cateringinfo_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), PrecyHomePage.class));
            }
        });
    }
}