package com.firetera.percyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    YourReservationFragment yourReservationFragment = new YourReservationFragment();
    DetailsFragment detailsFragment = new DetailsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return;

                    case R.id.navbottom_yourreservation:

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, yourReservationFragment).commit();
                        return;

                    case R.id.navbottom_details:

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();
                        return;

                }

            }
        });

    }
}