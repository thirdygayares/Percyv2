package com.firetera.percyv2.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firetera.percyv2.Model.FoodPackageModel;
import com.firetera.percyv2.R;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;

public class FoodPackageAdapter extends RecyclerView.Adapter<FoodPackageAdapter.MyViewHolder> {

    Context context;
    ArrayList<FoodPackageModel> list;
    private SelectListiner listiner;

    public FoodPackageAdapter(Context context, ArrayList<FoodPackageModel> list, SelectListiner listiner) {
        this.context = context;
        this.list = list;
        this.listiner = listiner;
    }

    @NonNull
    @Override
    public FoodPackageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.foodpackage_list, parent, false);

        return  new FoodPackageAdapter.MyViewHolder(view, listiner);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPackageAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FoodPackageModel foodPackageModel = list.get(position);
        holder.packageName.setText(foodPackageModel.getPackageName());
        holder.price.setText(foodPackageModel.getPrice());
        holder.foodNo1.setText(foodPackageModel.getFoodNo1());
        holder.foodNo2.setText(foodPackageModel.getFoodNo2());
        holder.foodNo3.setText(foodPackageModel.getFoodNo3());
        holder.foodNo4.setText(foodPackageModel.getFoodNo4());

                if(foodPackageModel.getStatus().equals(true)){
                    holder.cardView.setCardBackgroundColor(Color.DKGRAY);
                }else if(foodPackageModel.getStatus().equals(false)){
                    holder.cardView.setCardBackgroundColor(Color.WHITE);
                }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView packageName, foodNo1, foodNo2, foodNo3, foodNo4, price;
        CardView cardView;


        public MyViewHolder(@NonNull View itemView, SelectListiner selectListiner ) {
            super(itemView);

            packageName = itemView.findViewById(R.id.packageName_TextView);
            price = itemView.findViewById(R.id.price_TextView);
            foodNo1 = itemView.findViewById(R.id.foodNo1_TextView);
            foodNo2 = itemView.findViewById(R.id.foodNo2_TextView);
            foodNo3 = itemView.findViewById(R.id.foodNo3_TextView);
            foodNo4 = itemView.findViewById(R.id.foodNo4_TextView);
            cardView = itemView.findViewById(R.id.foodPackage_cardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectListiner != null ){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            selectListiner.onItemClick(pos);
                        }

                    }
                }
            });


        }
    }
}
