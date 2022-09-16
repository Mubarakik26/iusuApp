package com.example.iusuapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.iusuapp.activity.AddEditEventActivity;
import com.example.iusuapp.adapters.EventsRVAdapter;
import com.example.iusuapp.models.Events;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EventsFragment extends Fragment {

    ArrayList<Events> eventsArrayList;
    RecyclerView eventsRecyclerView;
    EventsRVAdapter eventsRVAdapter;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_events, container, false);

        eventsArrayList=new ArrayList<>();

        eventsRecyclerView= view.findViewById(R.id.rv_events);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        eventsRecyclerView.setLayoutManager(linearLayoutManager);
        eventsJsonRequest();

//        view.findViewById(R.id.events_fab_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), AddEditEventActivity.class));
//            }
//        });


        return view;
    }

    public void eventsJsonRequest() {

        jsonArrayRequest = new JsonArrayRequest(URLs.URL_GET_EVENT, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i<response.length();i++){

                    try{

                        jsonObject=response.getJSONObject(i);
                        Events events = new Events(jsonObject.getInt("id"),jsonObject.getString("image"),jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("date"),jsonObject.getString("venue"),jsonObject.getString("time"),jsonObject.getString("guildOfficialId"),jsonObject.getString("gptitle"));
                        eventsArrayList.add(events);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                eventsRVAdapter= new EventsRVAdapter(eventsArrayList,getContext());
                eventsRecyclerView.setAdapter(eventsRVAdapter);

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