package com.example.iusuapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.example.iusuapp.SharedPreferenceManager;
import com.example.iusuapp.URLs;
import com.example.iusuapp.activity.AddEditAnnouncement;
import com.example.iusuapp.activity.MainActivity;
import com.example.iusuapp.adapters.AnnouncementRVAdapter;
import com.example.iusuapp.models.Announcement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AnnouncementFragment extends Fragment {


    ArrayList<Announcement> announcementArrayList;
    AnnouncementRVAdapter announcementRVAdapter;
    RecyclerView recyclerView;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcement, container, false);



        Log.e("tag2", SharedPreferenceManager.getInstance(getContext()).getStudent().getGuildOfficialId());
        if(SharedPreferenceManager.getInstance(getContext()).isGuildOfficial()){
            Log.e("tag2","YOU ARE RIGHT"+SharedPreferenceManager.getInstance(getContext()).getStudent().getGuildTitle());
            //findViewById(R.id.ann_fab_btn).setVisibility(View.VISIBLE);
        }else {
            Log.e("tag2","YOU ARE WRONG"+SharedPreferenceManager.getInstance(getContext()).getStudent().getGuildTitle());
            //findViewById(R.id.ann_fab_btn).setVisibility(View.INVISIBLE);
        }

        announcementArrayList= new ArrayList<>();



        recyclerView = view.findViewById(R.id.faAnnouncementRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);



        announcementJsonRequest();

        return view;
    }



    public void announcementJsonRequest() {

        jsonArrayRequest = new JsonArrayRequest(URLs.URL_ANN_GET_ANN, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                Log.e("tagResponse","response"+response);
                JSONObject jsonObject = null;
                for (int i = 0; i<response.length();i++){

                    try{

                        jsonObject=response.getJSONObject(i);
                        Announcement announcement = new Announcement(jsonObject.getInt("id"),jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("date_Time"),jsonObject.getString("guildOfficialId"),jsonObject.getString("guildPostTitle"));
                        announcementArrayList.add(announcement);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                announcementRVAdapter= new AnnouncementRVAdapter(announcementArrayList,getContext());
                recyclerView.setAdapter(announcementRVAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tagErrorResponse", "response" + error.getMessage());
                if (error.getMessage() != null) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);


    }
}