package com.example.uriel.ordertracker.App.Model.Dto;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uriel.ordertracker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Uriel on 24-Apr-16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.OrderViewHolder>{

    ArrayList<PlainOrder> orders;

    public RVAdapter(ArrayList<PlainOrder> orders){
        this.orders = orders;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_order, parent, false);
        OrderViewHolder ovh = new OrderViewHolder(v);
        return ovh;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(orders.get(position).getFecha());
        Date orderDate = calendar.getTime();

        holder.clientName.setText(orders.get(position).getClient());
        holder.date.setText(String.valueOf(orderDate));
        holder.total.setText("Total: $" + String.valueOf(orders.get(position).getImporteTotal()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView clientName;
        TextView date;
        TextView total;

        OrderViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            clientName = (TextView)itemView.findViewById(R.id.client_name);
            date = (TextView)itemView.findViewById(R.id.date);
            total = (TextView)itemView.findViewById(R.id.total);
        }
    }
}