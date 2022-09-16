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
import com.example.iusuapp.models.Comment;
import com.example.iusuapp.models.Complaint;

import java.util.ArrayList;

public class ComplaintCommentRVAdapter extends RecyclerView.Adapter<ComplaintCommentRVAdapter.ViewHolder> {
    private ArrayList<Comment> commentArrayList;
    private Context context;


    public ComplaintCommentRVAdapter(ArrayList<Comment> commentArrayList, Context context) {
        this.commentArrayList = commentArrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public ComplaintCommentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_comment_layout_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintCommentRVAdapter.ViewHolder holder, int position) {
        Comment comment = commentArrayList.get(position);

        holder.messageTV.setText(comment.getMessage());
        if(comment.getGuildPostId()!="null") {
            holder.guildPostTV.setText(comment.getGpTitle());
        }else{
            holder.guildPostTV.setText("");
        }

        if(comment.getRegNo()!="null") {
            holder.studentNameTV.setText(comment.getFirstName() + " " + comment.getLastName());
        }else{
            holder.studentNameTV.setText("");
        }
        holder.dateTV.setText(comment.getDate());


    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView messageTV,guildPostTV,studentNameTV,dateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messageTV=itemView.findViewById(R.id.comMessage);
            guildPostTV=itemView.findViewById(R.id.comGuildPost);
            studentNameTV=itemView.findViewById(R.id.comStudentName);
            dateTV=itemView.findViewById(R.id.comDate);



        }
    }
}