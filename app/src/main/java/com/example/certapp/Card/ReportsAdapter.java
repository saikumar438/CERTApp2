package com.example.certapp.Card;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.certapp.R;

import java.util.ArrayList;


public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportHolder2> {

    //card adapter class
    private Context context2;
    private ArrayList<ReportModel> reports;
    private ClickListener clickListener;

    public ReportsAdapter(Context context2, ArrayList<ReportModel> reports) {

        this.context2 = context2;
        this.reports = reports;
    }

    @NonNull
    @Override
    public ReportHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context2).inflate(R.layout.card_item,parent,false);
        return new ReportHolder2(view);

    }

    @Override
    public void onBindViewHolder(ReportHolder2 holder, @SuppressLint("RecyclerView") int position) {
        ReportModel report=reports.get(position);
        holder.setDetails(report);
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v,report,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class ReportHolder2 extends RecyclerView.ViewHolder{
        private TextView reportName,incidentlvl,category;
        private LinearLayout itemLayout;


        ReportHolder2(View itemView){
            super(itemView);
            reportName=itemView.findViewById(R.id.user);
            incidentlvl=itemView.findViewById(R.id.severity);
            category=itemView.findViewById(R.id.incident_type);
            itemLayout =  itemView.findViewById(R.id.itemLayout);


        }

        void setDetails(ReportModel report){
            reportName.setText(report.getUser());
            incidentlvl.setText(report.getSeverity());
            category.setText(report.getIncident_Type());
        }


    }
}
