package com.example.iusuapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.iusuapp.activity.EventsDetailsActivity;
import com.example.iusuapp.models.Events;
import com.example.iusuapp.R;

import java.util.ArrayList;

public class EventsRVAdapter extends RecyclerView.Adapter<EventsRVAdapter.ViewHolder> {


    ArrayList<Events> eventsArrayList;
    Context context;
    RequestOptions option;

    public EventsRVAdapter(ArrayList<Events> eventsArrayList, Context context) {
        this.eventsArrayList = eventsArrayList;
        this.context = context;
        option= new RequestOptions().centerCrop().placeholder(R.drawable.images_placeholder).error(R.drawable.images_placeholder);
    }

    @NonNull
    @Override
    public EventsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsRVAdapter.ViewHolder holder, int position) {

        Events events = eventsArrayList.get(position);
        //holder.eventImage.setImageResource(events.getImage());
        holder.eventTitleTV.setText(events.getTitle());
        holder.eventDateTV.setText(events.getTime()+" | "+events.getDate());

        // holder.eventLikesTV.setText(events.getLikes());

        Glide.with(context).load(events.getImage()).apply(option).into(holder.eventImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventsDetailsActivity.class);
                intent.putExtra("image",events.getImage());
                intent.putExtra("title",events.getTitle());
                intent.putExtra("description",events.getDescription());
                intent.putExtra("date",events.getDate());
                intent.putExtra("time",events.getTime());
                intent.putExtra("venue",events.getVenue());
                intent.putExtra("id",events.getId());
                intent.putExtra("author",events.getGpostTitle());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView eventImage;
        TextView eventTitleTV,eventDateTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventImage=itemView.findViewById(R.id.eventItemImage);
            eventTitleTV=itemView.findViewById(R.id.eventItemTitle);
            eventDateTV=itemView.findViewById(R.id.eventItemDateTime);

        }
    }
}