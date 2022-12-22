package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firetera.percyv2.Adapter.MenuOrFoodAdapter;
import com.firetera.percyv2.Model.MenuOrFoodModel;

import java.util.ArrayList;

public class MenuOrFood extends AppCompatActivity {

    Button menuorfood_backarrow;
    ArrayList<MenuOrFoodModel> menuOrFoodModels = new ArrayList<>();
    RecyclerView recyclerView;

    int[] foodImages = {R.drawable.chickencordonbleu, R.drawable.chickenlollipop, R.drawable.fishfillet, R.drawable.kaldereta,
            R.drawable.lechon, R.drawable.butteredveg, R.drawable.lecheflan, R.drawable.bukosalad, R.drawable.fruitsalad};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_or_food);

        menuorfood_backarrow = findViewById(R.id.menuorfood_backarrow);

        menuorfood_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.myRecyclerView);

        setUpMenuOrFoodModels();
        setUPLinearLayout();

        MenuOrFoodAdapter menuOrFoodAdapter = new MenuOrFoodAdapter(this, menuOrFoodModels);
        recyclerView.setAdapter(menuOrFoodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUPLinearLayout() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                MenuOrFood.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void setUpMenuOrFoodModels() {

        String[] foodNameList = getResources().getStringArray(R.array.food_name_list);
        String[] courseNameList = getResources().getStringArray(R.array.course_name_list);


        for (int i = 0; i < foodNameList.length; i++) {


            menuOrFoodModels.add(new MenuOrFoodModel(foodNameList[i],
                    courseNameList[i],
                    foodImages[i]));

        }

    }
}
