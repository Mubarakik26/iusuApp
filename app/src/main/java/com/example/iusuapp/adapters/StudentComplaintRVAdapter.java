package com.example.iusuapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iusuapp.R;
import com.example.iusuapp.activity.AnnouncementDetailsActivity;
import com.example.iusuapp.activity.ComplaintDetailsActivity;
import com.example.iusuapp.models.Announcement;
import com.example.iusuapp.models.Complaint;

import java.util.ArrayList;

public class StudentComplaintRVAdapter extends RecyclerView.Adapter<StudentComplaintRVAdapter.ViewHolder> {
    private ArrayList<Complaint> complaintArrayList;
    private Context context;


    public StudentComplaintRVAdapter(ArrayList<Complaint> complaintArrayList, Context context) {
        this.complaintArrayList = complaintArrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public StudentComplaintRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_complaint_layout_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentComplaintRVAdapter.ViewHolder holder, int position) {
        Complaint complaint= complaintArrayList.get(position);

        holder.subjectTV.setText(complaint.getSubject());
        holder.guildPostTV.setText(complaint.getGpTitle());
        holder.dateTV.setText(complaint.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ComplaintDetailsActivity.class);

                intent.putExtra("subject",complaint.getSubject());
                intent.putExtra("message",complaint.getMessage());
                intent.putExtra("guildPostId",complaint.getGuildPostId());
                intent.putExtra("guildPostTitle",complaint.getGpTitle());
                intent.putExtra("complaintId",complaint.getComplaintId());
                intent.putExtra("regNo",complaint.getRegNo());
                intent.putExtra("studentName",complaint.getFirstName()+" "+complaint.getLastName());
                intent.putExtra("date",complaint.getDate());




                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return complaintArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView subjectTV,guildPostTV,dateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectTV=itemView.findViewById(R.id.scSubject);
            guildPostTV=itemView.findViewById(R.id.scGuildPost);
            dateTV=itemView.findViewById(R.id.scDate);



        }
    }
}