package com.firetera.percyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

        bottomNavigationView.setOnItemSelectedListener(navListener);

        Fragment selectedFragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();



    }

    private NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.navbottom_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navbottom_yourreservation:

                        selectedFragment = new YourReservationFragment();
                        break;
                    case R.id.navbottom_details:
//                        home.GroupID.clear();
                        selectedFragment = new DetailsFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                return true;
            };
}