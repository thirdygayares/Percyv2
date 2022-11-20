package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.GridLayout;
import com.firetera.percyv2.Adapter.DishAdapter;
import com.firetera.percyv2.Adapter.InterfaceJoma;
import com.firetera.percyv2.Model.DishModel;
import com.firetera.percyv2.R;

import java.util.ArrayList;

public class Foodoption extends AppCompatActivity implements InterfaceJoma {

    RecyclerView recycler_dish;
    ArrayList<DishModel> dishModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodoption);

        recycler_dish = (RecyclerView) findViewById(R.id.recycler_dish);

        setupDish();

        DishAdapter dishAdapter = new DishAdapter(Foodoption.this,dishModels, this );
        recycler_dish.setAdapter(dishAdapter);
        recycler_dish.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));

    }

    private void setupDish() {
        ArrayList<Integer> imager = new ArrayList<Integer>();
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<Integer> price = new ArrayList<Integer>();

        imager.add(R.drawable.adobo);
        imager.add(R.drawable.afritada);
        imager.add(R.drawable.sinigang);
        imager.add(R.drawable.menudo);
        imager.add(R.drawable.pares);

        name.add("Adobo");
        name.add("Afritada");
        name.add("Sinigang");
        name.add("Menudo");
        name.add("Pares");

        price.add(120);
        price.add(200);
        price.add(50);
        price.add(150);
        price.add(180);

//        public DishModel(int image, String dishName, int price)
        for(int i=0; i<name.size(); i++){
            dishModels.add(new DishModel(imager.get(i),name.get(i),price.get(i)));
        }

    }

    @Override
    public void onItemClick(int pos, String joma) {

    }
}