package com.firetera.percyv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.firetera.percyv2.ReservationProcessPackage.ConfirmationOfReservation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    ReservationFragment yourReservationFragment = new ReservationFragment();
    DetailFragment detailsFragment = new DetailFragment();
    public static String myValue ="Home" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(navListener);
        Fragment selectedFragment = null;

        if (myValue.equalsIgnoreCase("Home")){
            selectedFragment = new HomeFragment();
        }
        else if (myValue.equalsIgnoreCase("MyReservation")){
            selectedFragment = new ReservationFragment();

        }



        getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();



    }
    //calling
    private NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.navhome:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navyourreservation:

                        selectedFragment = new ReservationFragment();
                        break;
                    case R.id.navdetails:
//                        home.GroupID.clear();
                        selectedFragment = new DetailFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                return true;
            };
}



