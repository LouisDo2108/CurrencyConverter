package com.example.viewpager2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<ParseItem> parseItems;

    public ParseAdapter(ArrayList<ParseItem> parseItems, Context context) {
        this.context = context;
        this.parseItems = parseItems;
    }

    @NonNull
    @NotNull
    @Override
    public ParseAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parseitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ParseAdapter.ViewHolder holder, int position) {

        ParseItem parseItem = parseItems.get(position);

        holder.exchange.setText(parseItem.getExchange());
        holder.price.setText(parseItem.getPrice());
        holder.dayChange.setText(parseItem.getDayChange());
        holder.weeklyChange.setText(parseItem.getWeeklyChange());
        holder.monthlyChange.setText(parseItem.getMonthlyChange());
        holder.date.setText(parseItem.getDate());

        setTextColor(holder);
    }

    private void setTextColor(ViewHolder holder)
    {
        if(holder.dayChange.getText().toString().contains("-"))
            holder.dayChange.setTextColor(ContextCompat.getColor(context, R.color.red));
        else
            holder.dayChange.setTextColor(ContextCompat.getColor(context, R.color.green));

        if(holder.weeklyChange.getText().toString().contains("-"))
            holder.weeklyChange.setTextColor(ContextCompat.getColor(context, R.color.red));
        else
            holder.weeklyChange.setTextColor(ContextCompat.getColor(context, R.color.green));

        if(holder.monthlyChange.getText().toString().contains("-"))
            holder.monthlyChange.setTextColor(ContextCompat.getColor(context, R.color.red));
        else
            holder.monthlyChange.setTextColor(ContextCompat.getColor(context, R.color.green));
    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView  exchange, price, dayChange, weeklyChange, monthlyChange, date;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            exchange = itemView.findViewById(R.id.exchange);
            price = itemView.findViewById(R.id.price);
            dayChange = itemView.findViewById(R.id.daychange);
            weeklyChange = itemView.findViewById(R.id.weeklychange);
            monthlyChange = itemView.findViewById(R.id.monthlychange);
            date = itemView.findViewById(R.id.date);
        }
    }
}
