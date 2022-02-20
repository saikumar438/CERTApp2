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
        private TextView title,impact,userName,description,incidentType,effected,address;
        private LinearLayout itemLayout;


        ReportHolder2(View itemView){
            super(itemView);
            userName=itemView.findViewById(R.id.currentUser);
            title=itemView.findViewById(R.id.reportName);
            impact=itemView.findViewById(R.id.impact);
            incidentType=itemView.findViewById(R.id.incidentType);
            description=itemView.findViewById(R.id.description);
            effected=itemView.findViewById(R.id.effected);
            address=itemView.findViewById(R.id.address);
            itemLayout =  itemView.findViewById(R.id.itemLayout);


        }

        void setDetails(ReportModel report){
            userName.setText(report.getUserName());
            impact.setText(report.getImpactLevel());
            title.setText(report.getTitle());
            incidentType.setText(report.getTypeOfIncident());
            description.setText(report.getNotes());
            if(report.getRed()==""){
                report.setRed("0");
            }
            if(report.getBlack()==""){
                report.setBlack("0");
            }
            if(report.getYellow()==""){
                report.setYellow("0");
            }
            if(report.getGreen()==""){
                report.setGreen("0");
            }
            int red = Integer.parseInt(report.getRed()) + Integer.parseInt(report.getBlack()) + Integer.parseInt(report.getYellow()) + Integer.parseInt(report.getGreen());
            effected.setText((String.valueOf(red)));
            address.setText(report.getAddress());
        }


    }
}
