package com.firetera.percyv2.LogIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firetera.percyv2.Adapter.MyViewPagerAdapter;
import com.firetera.percyv2.R;
import com.firetera.percyv2.RegistrationJavaClass.RegistrationMethod;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LogInExampleUI extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 myViewPager;
    TextView noAccount;
    MyViewPagerAdapter myViewPagerAdapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_example_ui);

        tabLayout = findViewById(R.id.tab_Layout);
        myViewPager = findViewById(R.id.viewPager2);
        myViewPagerAdapter = new MyViewPagerAdapter(this);
        myViewPager.setAdapter(myViewPagerAdapter);
        noAccount = findViewById(R.id.noaccountyetTxtvw);
        firebaseAuth = FirebaseAuth.getInstance();

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationMethod.class));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        myViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

    }
}