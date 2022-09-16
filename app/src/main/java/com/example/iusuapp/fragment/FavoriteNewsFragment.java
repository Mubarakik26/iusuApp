package com.example.iusuapp.fragment;

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
import com.example.iusuapp.SharedPreferenceManager;
import com.example.iusuapp.URLs;
import com.example.iusuapp.adapters.NewsRVAdapter;
import com.example.iusuapp.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FavoriteNewsFragment extends Fragment {

    private ArrayList<News> newsArrayList;
    private NewsRVAdapter newsRVAdapter;
    private RecyclerView newRecyclerView;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_news, container, false);

        newsArrayList= new ArrayList<>();

        newRecyclerView=view.findViewById(R.id.faFavNewsRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        newRecyclerView.setLayoutManager(linearLayoutManager);
        newsJsonRequest();

        return view;
    }

    public void newsJsonRequest() {
        String regNo = SharedPreferenceManager.getInstance(getContext()).getStudent().getRegNo();

        jsonArrayRequest = new JsonArrayRequest(URLs.URL_FETCH_FAVORITE_NEWS+"&regNo="+regNo, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                Log.e("tagResponse","response"+response);
                for (int i = 0; i<response.length();i++){

                    try{

                        jsonObject=response.getJSONObject(i);
                        News news = new News(jsonObject.getInt("postId"),jsonObject.getString("image"),jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("date_Time"),jsonObject.getString("guildOfficialId"),jsonObject.getString("guildPostTitle"));
                        newsArrayList.add(news);
                        Log.e("tagResponse","news: "+news);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("tagResponse","e: "+e);
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