package com.example.rafael_perez.mi_peso_ideal.Progress;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rafael_perez.mi_peso_ideal.Helper.DateFormatHelper;
import com.example.rafael_perez.mi_peso_ideal.R;

import java.util.ArrayList;
import java.util.Date;

public class ProgressListAdapter extends RecyclerView.Adapter<ProgressListAdapter.MyViewHolder> {
    private ArrayList<Double> values;
    private ArrayList<Date> values_dates;

    public ProgressListAdapter(ArrayList<Double> values, ArrayList<Date> values_dates){
        this.values = values;
        this.values_dates = values_dates;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        MyViewHolder viewHolder = new MyViewHolder(inflater.inflate(R.layout.progress_list_item, viewGroup, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.setData(values.get(position).toString(), values_dates.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_value, txt_date;

        public MyViewHolder(@NonNull View view) {
            super(view);
            txt_value = view.findViewById(R.id.value);
            txt_date = view.findViewById(R.id.date);
        }

        public void setData(String value, String date){
            txt_value.setText(value);
            DateFormatHelper.formatDate(txt_date, date);
        }
    }
}
