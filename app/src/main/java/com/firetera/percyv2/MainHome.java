package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firetera.percyv2.Adapter.BestDishesListAdapter;
import com.firetera.percyv2.Adapter.EventThemeAdapter;
import com.firetera.percyv2.Model.BestDishesListModel;
import com.firetera.percyv2.Model.EventThemeModel;

import java.util.ArrayList;

public class MainHome extends AppCompatActivity {


    RecyclerView eventRecyclerView, bestDishesRecyclerView;


    ArrayList<EventThemeModel> eventThemeModels = new ArrayList<>();
    int[] eventImages = {R.drawable.bdaypic, R.drawable.weddingpic,
                        R.drawable.corporatepic, R.drawable.businesspic,
                        R.drawable.eventspic};

    ArrayList<BestDishesListModel> bestDishesListModels = new ArrayList<>();
    int[] bestDishesImages = {R.drawable.chickencordonbleu1, R.drawable.chickenlollipop2,
                            R.drawable.fishfillet3, R.drawable.kaldereta4,
                            R.drawable.lechon5,  R.drawable.butteredveggie6,
                            R.drawable.lecheflan7,  R.drawable.bukosalad8,  R.drawable.fruitsalad9};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        eventRecyclerView = findViewById(R.id.eventRecyclerview);
        bestDishesRecyclerView = findViewById(R.id.bestDishesRecyclerview);

        setUpBestDishesRecyclerView();
        setUpEventRecyclerView();

    }

    private void setUpEventRecyclerView() {

        EventThemeAdapter eventThemeAdapter = new EventThemeAdapter(this, eventThemeModels);
        eventRecyclerView.setAdapter(eventThemeAdapter);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                MainHome.this, LinearLayoutManager.HORIZONTAL, false
        );
        eventRecyclerView.setLayoutManager(linearLayoutManager);
        eventRecyclerView.setItemAnimator(new DefaultItemAnimator());

        String[] dishNameList = getResources().getStringArray(R.array.event_name_list);
        String[] coursNameList = getResources().getStringArray(R.array.num_of_people_list);
        String[] pricePerPeopleList = getResources().getStringArray(R.array.price_perpeople_list);


        for (int i = 0; i < dishNameList.length; i++) {


            eventThemeModels.add(new EventThemeModel(dishNameList[i],
                    coursNameList[i],
                    pricePerPeopleList[i],
                    eventImages[i]));

        }
    }

    private void setUpBestDishesRecyclerView() {

        BestDishesListAdapter bestDishesListAdapter = new BestDishesListAdapter(this, bestDishesListModels);
        bestDishesRecyclerView.setAdapter(bestDishesListAdapter);
        bestDishesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                MainHome.this, LinearLayoutManager.HORIZONTAL, false
        );
        bestDishesRecyclerView.setLayoutManager(linearLayoutManager);
        bestDishesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        String[] dishNameList = getResources().getStringArray(R.array.food_name_list);
        String[] courseNameList = getResources().getStringArray(R.array.course_name_list);



        for (int i = 0; i < dishNameList.length; i++) {


            bestDishesListModels.add(new BestDishesListModel(dishNameList[i],
                    courseNameList[i],
                    bestDishesImages[i]));

        }
    }



}
