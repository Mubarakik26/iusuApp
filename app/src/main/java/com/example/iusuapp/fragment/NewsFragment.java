package com.example.iusuapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.iusuapp.R;
import com.example.iusuapp.URLs;
import com.example.iusuapp.activity.AddEditNewsActivity;
import com.example.iusuapp.adapters.NewsRVAdapter;
import com.example.iusuapp.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewsFragment extends Fragment {
    private ArrayList<News> newsArrayList;
    private NewsRVAdapter newsRVAdapter;
    private RecyclerView newRecyclerView;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        newsArrayList= new ArrayList<>();

        newRecyclerView=view.findViewById(R.id.faNewsRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        newRecyclerView.setLayoutManager(linearLayoutManager);
        newsJsonRequest();

//        view.findViewById(R.id.faNewsFAB).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), AddEditNewsActivity.class));
//            }
//        });


        return view;
    }

    public void newsJsonRequest() {

        jsonArrayRequest = new JsonArrayRequest(URLs.URL_NEWS_FETCH, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                Log.e("tagResponse","response"+response);
                for (int i = 0; i<response.length();i++){

                    try{

                        jsonObject=response.getJSONObject(i);
                        News news = new News(jsonObject.getInt("id"),jsonObject.getString("image"),jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("date_Time"),jsonObject.getString("guildOfficialId"),jsonObject.getString("guildPostTitle"));
                        newsArrayList.add(news);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                newsRVAdapter= new NewsRVAdapter(newsArrayList,getContext());
                newRecyclerView.setAdapter(newsRVAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);


    }
}