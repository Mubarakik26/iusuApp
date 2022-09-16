package com.example.iusuapp.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

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
import com.example.iusuapp.activity.CreateAccountActivity;
import com.example.iusuapp.adapters.AnnouncementRVAdapter;
import com.example.iusuapp.adapters.StudentComplaintRVAdapter;
import com.example.iusuapp.models.Announcement;
import com.example.iusuapp.models.Complaint;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HelpFragment extends Fragment {

    ArrayList<Complaint> complaintArrayList;
    StudentComplaintRVAdapter complaintRVAdapter;
    RecyclerView recyclerView;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    AlertDialog dialog;
    CardView makeComplaintCard;
    TextInputLayout subject,message;
    AutoCompleteTextView guildPosts;
    AppCompatButton postBtn;
    List<String> posts;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_help, container, false);

        complaintArrayList= new ArrayList<>();

        makeComplaintCard= view.findViewById(R.id.fhMakeComplaintCard);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Make post");

        View dialogView = getLayoutInflater().inflate(R.layout.make_complaint_dialog,null);


        guildPosts=dialogView.findViewById(R.id.compEditTextGuildPostAutoComplete);
        subject=dialogView.findViewById(R.id.compSubject);
        message=dialogView.findViewById(R.id.compMessage);
        postBtn=dialogView.findViewById(R.id.compMakeComplaint);
        posts=new ArrayList<>();

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendComplaint();


                dialog.dismiss();
                studentComplaintsJsonRequest();
            }
        });

        builder.setView(dialogView);
        dialog=builder.create();

        makeComplaintCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGuildPosts();
                dialog.show();
            }
        });



        recyclerView = view.findViewById(R.id.myComplaintRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);



        studentComplaintsJsonRequest();

        return view;
    }

    public void studentComplaintsJsonRequest() {
        String regNo = SharedPreferenceManager.getInstance(getContext()).getStudent().getRegNo();

        jsonArrayRequest = new JsonArrayRequest(URLs.URL_FETCH_STUDENT_COMPLAINTS+"&regNo="+regNo, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                Log.e("tagResponse","response"+response);
                JSONObject jsonObject = null;
                for (int i = 0; i<response.length();i++){

                    try{

                        jsonObject=response.getJSONObject(i);
                        Complaint complaint = new Complaint(jsonObject.getInt("complaintId"),jsonObject.getString("subject"),jsonObject.getString("message"),jsonObject.getString("date_Time"),jsonObject.getString("regNo"),jsonObject.getString("firstName"),jsonObject.getString("lastName"),jsonObject.getString("guildPostId"),jsonObject.getString("gpTitle"));
                        complaintArrayList.add(complaint);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                complaintRVAdapter= new StudentComplaintRVAdapter(complaintArrayList,getContext());
                recyclerView.setAdapter(complaintRVAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tagErrorResponse","response"+error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);


    }

    private void sendComplaint() {
        final String subjectET = subject.getEditText().getText().toString();
        final String messageET = message.getEditText().getText().toString();
        final String guildPostId = String.valueOf(posts.indexOf(""+guildPosts.getText().toString())+1);
        final String regNo = SharedPreferenceManager.getInstance(getContext()).getStudent().getRegNo();





        if (TextUtils.isEmpty(subjectET)) {

            subject.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(messageET)) {

            message.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(messageET)) {

            guildPosts.requestFocus();
            return;
        }

        Log.e("tagResponse","msg: "+guildPosts.getText());
        Log.e("tagResponse","msg: "+posts.indexOf(""+guildPosts.getText().toString()));


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADD_COMPLAINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tagconvertstr", "["+response+"]");


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("subject", subjectET);
                params.put("message", messageET);
                params.put("guildPostId", guildPostId);
                params.put("regNo",regNo);
                return params;
            }
        };

        //adding the request to volley
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

    private void getGuildPosts () {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        //if everything is fine
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_FETCH_GUILD_POST_TITLES,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                posts=new ArrayList<>();
                Log.e("tag","this is "+response);
                for (int i=0;i<response.length();i++){
                    try{
                        JSONObject responseObj = response.getJSONObject(i);
                        String postTitle = responseObj.getString("title");
                        Log.e("tag","this is ");
                        Log.e("tag","this is "+response);
                        Log.e("tag","this is "+postTitle);

                        posts.add(postTitle);

                        Toast.makeText(getContext(), "fine "+postTitle, Toast.LENGTH_SHORT).show();

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }


                ArrayAdapter guildOfficialAdapter = new ArrayAdapter(getContext(),R.layout.dropdown_list_item, posts);
                guildOfficialAdapter.setDropDownViewResource(R.layout.dropdown_list_item);
                guildPosts.setAdapter(guildOfficialAdapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);

    }

}