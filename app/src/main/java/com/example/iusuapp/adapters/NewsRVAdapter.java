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
import com.example.iusuapp.R;
import com.example.iusuapp.activity.NewsItemDetailsActivity;
import com.example.iusuapp.models.News;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {

    ArrayList<News> newsArrayList;
    Context context;
    RequestOptions option;

    public NewsRVAdapter(ArrayList<News> newsArrayList, Context context) {
        this.newsArrayList = newsArrayList;
        this.context = context;
        option= new RequestOptions().centerCrop().placeholder(R.drawable.images_placeholder).error(R.drawable.images_placeholder);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsArrayList.get(position);
//        holder.newsImg.setImageResource(news.getImage());


        holder.newsTitleTV.setText(news.getTitle());

        Glide.with(context).load(news.getImage()).apply(option).into(holder.newsImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsItemDetailsActivity.class);

                intent.putExtra("id",news.getId());
                intent.putExtra("title",news.getTitle());
                intent.putExtra("image",news.getImage());
                intent.putExtra("description",news.getDescription());
                intent.putExtra("date",news.getDate());
                intent.putExtra("author",news.getGpostTitle());



                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImg;
        TextView newsTitleTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitleTV=itemView.findViewById(R.id.newsItemTitle);
            newsImg=itemView.findViewById(R.id.newsItemImage);


        }
    }
}