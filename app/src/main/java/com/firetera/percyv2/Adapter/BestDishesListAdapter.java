package com.firetera.percyv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firetera.percyv2.Model.BestDishesListModel;
import com.firetera.percyv2.Model.BestDishesListModel;
import com.firetera.percyv2.R;

import java.util.ArrayList;

public class BestDishesListAdapter extends RecyclerView.Adapter<BestDishesListAdapter.MyViewHolder> {

    Context context;
    ArrayList<BestDishesListModel>bestDishesListModel;

    public BestDishesListAdapter (Context context, ArrayList<BestDishesListModel> bestDishesListModel){
        this.context = context;
        this.bestDishesListModel = bestDishesListModel;



    }

    @NonNull
    @Override
    public BestDishesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.best_dishes_list, parent, false);
        return new BestDishesListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestDishesListAdapter.MyViewHolder holder, int position) {
        holder.dishName.setText(bestDishesListModel.get(position).getDishName());
        holder.courseName.setText(bestDishesListModel.get(position).getCourseName());
        holder.dishesImage.setImageResource(bestDishesListModel.get(position).geteventImages());


    }

    @Override
    public int getItemCount() {
        return bestDishesListModel.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView dishesImage;
        TextView dishName, courseName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dishesImage = itemView.findViewById(R.id.bestDishes_imageView);
            dishName = itemView.findViewById(R.id.dishName_txtView);
            courseName = itemView.findViewById(R.id.courseName_txtView);
        }
    }
}