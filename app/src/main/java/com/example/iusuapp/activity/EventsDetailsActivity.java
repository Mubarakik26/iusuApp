package com.example.iusuapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.iusuapp.R;
import com.example.iusuapp.SharedPreferenceManager;
import com.example.iusuapp.URLs;
import com.example.iusuapp.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EventsDetailsActivity extends AppCompatActivity {

    private TextView eventsTitle,eventsDescritption,eventsDate,eventsTime,eventsVenue,eventsAuthor;
    private ImageButton eventsBackBtn;
    private AppCompatButton eventsAddToFavoriteBtn;
    private ImageView eventsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);

        eventsTitle= findViewById(R.id.eventDetailsTitle);
        eventsDescritption= findViewById(R.id.eventDetailsDescription);
        eventsDate= findViewById(R.id.eventDetailsDate);
        eventsTime= findViewById(R.id.eventDetailsTime);
        eventsVenue= findViewById(R.id.eventDetailsVenue);
        eventsAuthor= findViewById(R.id.eventDetailsAuthor);
        eventsBackBtn= findViewById(R.id.eventDetailsBackBtn);
        eventsAddToFavoriteBtn= findViewById(R.id.eventDetailsAddToFavoriteBtn);
        eventsImage= findViewById(R.id.eventDetailsImage);


        eventsTitle.setText(getIntent().getStringExtra("title"));
        eventsDescritption.setText(getIntent().getStringExtra("description"));
        eventsDate.setText("Date: "+getIntent().getStringExtra("date"));
        eventsTime.setText("Time: "+getIntent().getStringExtra("time"));
        eventsVenue.setText("Venue: "+getIntent().getStringExtra("venue"));
        eventsAuthor.setText("By: "+getIntent().getStringExtra("author"));
        Glide.with(this).load(getIntent().getStringExtra("image")).into(eventsImage);


        eventsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsDetailsActivity.this.finish();
            }
        });


        eventsAddToFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventToFavorite();
            }
        });

    }

    public void addEventToFavorite(){
        final String regNo = SharedPreferenceManager.getInstance(this).getStudent().getRegNo();
        final String postId = String.valueOf(getIntent().getIntExtra("id",0));




        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADD_TO_FAVORITE_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tagconvertstr", "["+response+"]");
                        Log.i("tagregNo", "["+regNo+"]");
                        Log.i("tagId", "["+postId+"]");

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("regNo", regNo);
                params.put("postId", postId);

                return params;
            }
        };

        //adding the request to volley
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}