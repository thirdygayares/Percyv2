package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrecyHomePage extends AppCompatActivity {
   CardView foodbutton, schedulebutton, eventthemebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precy_home_page);
        initxml();
        foodphasemethod();
        schedulephasemethod();
        eventthemephase();


    }

    private void eventthemephase() {
        eventthemebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrecyHomePage.this, EventThemeOption.class);
                startActivity(intent);
            }
        });
    }

    private void schedulephasemethod() {
        schedulebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrecyHomePage.this, ScheduleOption.class);
                startActivity(intent);
            }
        });
    }


    private void foodphasemethod() {
        foodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrecyHomePage.this, Foodoption.class);
                startActivity(intent);
            }
        });
    }




    private void initxml() {
        foodbutton = (CardView)findViewById(R.id.foodbutton);
        schedulebutton =(CardView) findViewById(R.id.schedulebutton);
        eventthemebutton = (CardView) findViewById(R.id.eventthemebutton);

    }
}