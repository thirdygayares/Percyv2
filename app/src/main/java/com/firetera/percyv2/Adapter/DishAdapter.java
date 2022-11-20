package com.firetera.percyv2.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firetera.percyv2.Model.DishModel;
import com.firetera.percyv2.R;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.MyViewHolder> {

    private final InterfaceJoma interfaceJomas;

    Context context;
    ArrayList<DishModel> dishModels;

    public DishAdapter(Context context, ArrayList<DishModel> dishModels, InterfaceJoma interfaceJomas){
        this.context = context;
        this.dishModels = dishModels;
        this.interfaceJomas = interfaceJomas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_dish_layout, parent, false);
        return new MyViewHolder(view, interfaceJomas);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.imagefood.setImageResource(dishModels.get(position).getImage());
        holder.names.setText(dishModels.get(position).getDishName());
        holder.price.setText(String.valueOf("Php " + dishModels.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return dishModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagefood;
        TextView names,price;

        public MyViewHolder(@NonNull View itemView, InterfaceJoma interfaceJomas) {
            super(itemView);

            imagefood = (ImageView) itemView.findViewById(R.id.imagefood);
            names = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);

            itemView.setOnClickListener(view -> {
                if(interfaceJomas != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        interfaceJomas.onItemClick(pos, "danao");
                    }
                }
            });
        }
    }
}
