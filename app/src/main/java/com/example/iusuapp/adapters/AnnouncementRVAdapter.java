package com.example.iusuapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iusuapp.activity.AnnouncementDetailsActivity;
import com.example.iusuapp.activity.EventsDetailsActivity;
import com.example.iusuapp.models.Announcement;
import com.example.iusuapp.R;

import java.util.ArrayList;

public class AnnouncementRVAdapter extends RecyclerView.Adapter<AnnouncementRVAdapter.ViewHolder> {
    private ArrayList<Announcement> announcementArrayList;
    private Context context;


    public AnnouncementRVAdapter(ArrayList<Announcement> announcementArrayList, Context context) {
        this.announcementArrayList = announcementArrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public AnnouncementRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_item_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementRVAdapter.ViewHolder holder, int position) {
        Announcement announcement= announcementArrayList.get(position);

        holder.titleTV.setText(announcement.getTitle());
        holder.contentTV.setText(announcement.getMessage());
        holder.dateTV.setText(announcement.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnnouncementDetailsActivity.class);

                intent.putExtra("title",announcement.getTitle());
                intent.putExtra("description",announcement.getMessage());
                intent.putExtra("date",announcement.getDate());
                intent.putExtra("author",announcement.getAuthor());
                intent.putExtra("guildOfficialId",announcement.getGo_id());
                intent.putExtra("announcementId",announcement.getAnnouncementId());



                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return announcementArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV,contentTV,dateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV=itemView.findViewById(R.id.annItemTitle);
            contentTV=itemView.findViewById(R.id.annItemDescription);
            dateTV=itemView.findViewById(R.id.annItemDate);



        }
    }
}