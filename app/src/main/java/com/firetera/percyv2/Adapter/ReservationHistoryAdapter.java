package com.firetera.percyv2.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.firetera.percyv2.Model.ReservationHistoryModel;
import com.firetera.percyv2.R;
import java.util.ArrayList;

public class ReservationHistoryAdapter extends RecyclerView.Adapter<ReservationHistoryAdapter.MyViewHolder> {

    Context context;
    ArrayList<ReservationHistoryModel> list;

    public ReservationHistoryAdapter(Context context, ArrayList<ReservationHistoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReservationHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.reservation_history_list, parent, false);

        return  new ReservationHistoryAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReservationHistoryAdapter.MyViewHolder holder, int position) {

        ReservationHistoryModel reservationHistoryModel = list.get(position);
        holder.reservationDate.setText(reservationHistoryModel.getReservationDate());
        holder.name.setText(reservationHistoryModel.getName());
        holder.mobileno.setText(reservationHistoryModel.getMobilenum());
        holder.reservationID.setText(reservationHistoryModel.getReservationID());
        holder.event.setText(reservationHistoryModel.getEvent());
        holder.numofPeople.setText(reservationHistoryModel.getNumofPeople());
        holder.companyName.setText(reservationHistoryModel.getCompanyName());
        holder.venue.setText(reservationHistoryModel.getVenue());


        if(reservationHistoryModel.getStatus().equals(true)){
            holder.statusCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.approvedbg));
            holder.statusTxtView.setTextColor(ContextCompat.getColor(context, R.color.fontcolorapproved));
            holder.statusTxtView.setText("Approved");
        }
        else if (reservationHistoryModel.getStatus().equals(false)){
            holder.statusCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pendingbg));
            holder.statusTxtView.setTextColor(ContextCompat.getColor(context, R.color.fontcolorpending));
            holder.statusTxtView.setText("Pending");

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView reservationID, name, mobileno, reservationDate, event, numofPeople, statusTxtView, venue, companyName;
        CardView statusCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            reservationID = itemView.findViewById(R.id.reservationID);
            name = itemView.findViewById(R.id.name);
            mobileno = itemView.findViewById(R.id.mobileNumber);
            reservationDate = itemView.findViewById(R.id.reservationDate);
            event = itemView.findViewById(R.id.event);
            numofPeople = itemView.findViewById(R.id.numofPeople);
            statusCardView = itemView.findViewById(R.id.pending_cardView);
            statusTxtView = itemView.findViewById(R.id.status);
            venue = itemView.findViewById(R.id.venue);
            companyName = itemView.findViewById(R.id.companyName);
        }
    }
}
