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
import com.firetera.percyv2.Model.VenueModel;
import com.firetera.percyv2.R;

import java.util.ArrayList;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.MyViewHolder> {

    Context context;
    ArrayList<VenueModel>venueModels;

    public VenueAdapter (Context context, ArrayList<VenueModel> venueModels){
        this.context = context;
        this.venueModels = venueModels;



    }

    @NonNull
    @Override
    public VenueAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.venue_list, parent, false);
        return new VenueAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueAdapter.MyViewHolder holder, int position) {
        holder.venueName.setText(venueModels.get(position).getVenueName());
        holder.venueAddress.setText(venueModels.get(position).getVenueAddress());
        holder.venueImage.setImageResource(venueModels.get(position).getVenueImage());


    }

    @Override
    public int getItemCount() {
        return venueModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView venueImage;
        TextView venueName, venueAddress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            venueImage = itemView.findViewById(R.id.venueImage_ImgView);
            venueName = itemView.findViewById(R.id.venueName_TxtView);
            venueAddress = itemView.findViewById(R.id.venueAddress_TxtView);

        }
    }
}