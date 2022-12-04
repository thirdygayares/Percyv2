package com.firetera.percyv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firetera.percyv2.Model.MenuOrFoodModel;
import com.firetera.percyv2.R;

import java.util.ArrayList;

public class MenuOrFoodAdapter extends RecyclerView.Adapter<MenuOrFoodAdapter.MyViewHolder> {

    Context context;
    ArrayList<MenuOrFoodModel>menuOrFoodModels;

    public MenuOrFoodAdapter (Context context, ArrayList<MenuOrFoodModel> menuOrFoodModels){
        this.context = context;
        this.menuOrFoodModels = menuOrFoodModels;



    }

    @NonNull
    @Override
    public MenuOrFoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MenuOrFoodAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuOrFoodAdapter.MyViewHolder holder, int position) {
        holder.tvfoodName.setText(menuOrFoodModels.get(position).getFoodName());
        holder.tvcourseName.setText(menuOrFoodModels.get(position).getCourseName());
        holder.imageView.setImageResource(menuOrFoodModels.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return menuOrFoodModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvfoodName, tvcourseName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvfoodName = itemView.findViewById(R.id.textView);
            tvcourseName = itemView.findViewById(R.id.textView1);
        }
    }
}