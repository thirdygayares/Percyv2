package com.firetera.percyv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firetera.percyv2.Model.EventThemeModel;
import com.firetera.percyv2.R;

import java.util.ArrayList;

public class EventThemeAdapter extends RecyclerView.Adapter<EventThemeAdapter.MyViewHolder> {

    Context context;
    ArrayList<EventThemeModel>eventThemeModel;

    public EventThemeAdapter (Context context, ArrayList<EventThemeModel> eventThemeModel){
        this.context = context;
        this.eventThemeModel = eventThemeModel;



    }

    @NonNull
    @Override
    public EventThemeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_list, parent, false);
        return new EventThemeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventThemeAdapter.MyViewHolder holder, int position) {
        holder.pricePerPeople.setText(eventThemeModel.get(position).getPricePerPeople());
        holder.eventName.setText(eventThemeModel.get(position).getEventName());
        holder.eventImage.setImageResource(eventThemeModel.get(position).getEventImage());
        holder.numOfPeople.setText(eventThemeModel.get(position).getNumOfPeople());

    }

    @Override
    public int getItemCount() {
        return eventThemeModel.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView eventImage;
        TextView pricePerPeople, eventName, numOfPeople;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.event_Image);
            pricePerPeople = itemView.findViewById(R.id.pricePerPeople);
            numOfPeople = itemView.findViewById(R.id.numOfPeople);
            eventName = itemView.findViewById(R.id.eventName_txtView);
        }
    }
}