package com.example.iusuapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iusuapp.R;
import com.example.iusuapp.SharedPreferenceManager;
import com.example.iusuapp.URLs;
import com.example.iusuapp.VolleySingleton;
import com.example.iusuapp.adapters.ComplaintCommentRVAdapter;
import com.example.iusuapp.adapters.StudentComplaintRVAdapter;
import com.example.iusuapp.models.Comment;
import com.example.iusuapp.models.Complaint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComplaintDetailsActivity extends AppCompatActivity {

    private TextView compSubject,compMessage,compDate,compGuildPost,compStudentName;
    private ImageButton compBackBtn, sendCommentBtn;
    private EditText commentEditText;

    ArrayList<Comment> commentArrayList;
    ComplaintCommentRVAdapter commentRVAdapter;
    RecyclerView recyclerView;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);

        compSubject= findViewById(R.id.compDetailsSubject);
        compMessage= findViewById(R.id.compDetailsMessage);
        compDate= findViewById(R.id.compDetailsDate);
        compGuildPost= findViewById(R.id.compDetailsGuildPostTitle);
        compStudentName= findViewById(R.id.compDetailsStudentName);
        compBackBtn = findViewById(R.id.compDetailsBackBtn);

        commentEditText = findViewById(R.id.commentEditText);
        sendCommentBtn = findViewById(R.id.commentSendBtn);

        compSubject.setText(getIntent().getStringExtra("subject"));
        compMessage.setText(getIntent().getStringExtra("message"));
        compDate.setText("Date: "+getIntent().getStringExtra("date"));
        compGuildPost.setText("To: "+getIntent().getStringExtra("guildPostTitle"));
        compStudentName.setText("From: "+getIntent().getStringExtra("studentName"));
        recyclerView = findViewById(R.id.compDetailsCommentsRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        complaintCommentsJsonRequest();

        compBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplaintDetailsActivity.this.finish();
            }
        });

        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
                complaintCommentsJsonRequest();
            }
        });
    }

    private void sendComment() {
        final String message = commentEditText.getText().toString();
        final String complaintId = String.valueOf(getIntent().getIntExtra("complaintId",0));
        final String regNo = SharedPreferenceManager.getInstance(getApplicationContext()).getStudent().getRegNo();




        //first we will do the validations
        if (TextUtils.isEmpty(message)) {

            commentEditText.requestFocus();
            return;
        }




        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADD_STUDENT_COMMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tagconvertstr", "["+response+"]");


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
                params.put("message", message);
                params.put("complaintId", complaintId);
                params.put("regNo",regNo);
                return params;
            }
        };

        //adding the request to volley
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    public void complaintCommentsJsonRequest() {
        int complaintId = getIntent().getIntExtra("complaintId",0);

        jsonArrayRequest = new JsonArrayRequest(URLs.URL_FETCH_COMMENTS+"&complaintId="+complaintId, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                commentArrayList=new ArrayList<>();
                Log.e("tag1","legnth: "+response.length());
                Log.e("tag2","com"+response);
                Log.e("tag3","com"+response);
                Log.e("tag4","com"+response);
                JSONObject jsonObject = null;
                for (int i = 0; i<response.length();i++){

                    try{

                        jsonObject=response.getJSONObject(i);
                        Comment comment = new Comment(jsonObject.getInt("commentId"),jsonObject.getInt("complaintId"),jsonObject.getString("message"),jsonObject.getString("date_Time"),jsonObject.getString("regNo"),jsonObject.getString("firstName"),jsonObject.getString("lastName"),jsonObject.getString("guildPostId"),jsonObject.getString("gpTitle"));
                        commentArrayList.add(comment);
                        Log.e("tag2","com"+comment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("tag2","e: "+e);
                    }
                }
                Log.e("tagResponse","response"+commentArrayList);
                commentRVAdapter= new ComplaintCommentRVAdapter(commentArrayList,getApplicationContext());
                recyclerView.setAdapter(commentRVAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tagErrorResponse","response"+error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);


    }
}