package com.example.certapp.reports;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.certapp.R;

public class IncidentListAdapter extends RecyclerView.Adapter<IncidentListAdapter.IncidentViewHolder> {
    private Model incident_model;
    private Context context;
    public static int incidentId=0;

    public IncidentListAdapter(Model incident_model, Context context) {
        super();
        this.incident_model = incident_model;
        this.context=context;
    }

    public static class IncidentViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearView;

        public IncidentViewHolder(LinearLayout lin_layout) {
            super(lin_layout);
            linearView = lin_layout;
        }
    }



    @NonNull
    @Override
    public IncidentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout lin_layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incident_view, parent, false);
        IncidentViewHolder linviewhol = new IncidentViewHolder(lin_layout);
        return linviewhol;
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        LinearLayout hold_view = holder.linearView;
        TextView word_holder = hold_view.findViewById(R.id.incident_holder);
        word_holder.setText(incident_model.getIncidentsarray().get(position).incidentNam);

        word_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("asdfg",incident_model.getIncidentsarray().get(position).incidentId+"");
                incidentId=incident_model.getIncidentsarray().get(position).incidentId;
                Intent in=new Intent(context, ReportsMainActivity.class);

                context.startActivity(in);

            }
        });
    }

    @Override
    public int getItemCount() {
        return incident_model.getIncidentsarray().size();
    }
}
