package com.example.iusuapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
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

public class NewsItemDetailsActivity extends AppCompatActivity {

    private TextView newsTitle,newsDescritption,newsDate,newAuthor;
    private ImageButton newsBackBtn;
    private AppCompatButton newsAddToFavoriteBtn;
    private ImageView newsImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item_details);

        newsTitle= findViewById(R.id.newsDetailsTitle);
        newsDescritption= findViewById(R.id.newsDetailsDescription);
        newsDate= findViewById(R.id.newsDetailsDate);
        newAuthor= findViewById(R.id.newsDetailsAuthor);
        newsBackBtn= findViewById(R.id.newsDetailsBackBtn);
        newsAddToFavoriteBtn= findViewById(R.id.newsDetailsAddToFavoriteBtn);
        newsImage= findViewById(R.id.newsDetailsImage);


        newsTitle.setText(getIntent().getStringExtra("title"));
        newsDescritption.setText(getIntent().getStringExtra("description"));
        newsDate.setText(getIntent().getStringExtra("date"));
        newAuthor.setText("By: "+getIntent().getStringExtra("author"));
        Glide.with(this).load(getIntent().getStringExtra("image")).into(newsImage);


        newsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsItemDetailsActivity.this.finish();
            }
        });


        newsAddToFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewsToFavorite();
            }
        });


    }

    public void addNewsToFavorite(){
        final String regNo = SharedPreferenceManager.getInstance(this).getStudent().getRegNo();
        final String postId = String.valueOf(getIntent().getIntExtra("id",0));




        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADD_TO_FAVORITE_NEWS,
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

                Log.i("tagregNo", "["+regNo+"]");
                Log.i("tagId", "["+postId+"]");
                params.put("regNo", regNo);
                params.put("postId", postId);

                return params;
            }
        };

        //adding the request to volley
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}
