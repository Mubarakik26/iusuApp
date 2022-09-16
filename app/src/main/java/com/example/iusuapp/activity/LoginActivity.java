package com.example.iusuapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.iusuapp.R;
import com.example.iusuapp.SharedPreferenceManager;
import com.example.iusuapp.URLs;
import com.example.iusuapp.VolleySingleton;
import com.example.iusuapp.models.Student;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextView createAccount;
    private AppCompatButton loginBtn;
    TextInputLayout etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPreferenceManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }


        etEmail = findViewById(R.id.edit_text_email_log_in);
        etPassword = findViewById(R.id.edit_text_password_log_in);



        createAccount=findViewById(R.id.textViewCreateAccount);
        loginBtn=findViewById(R.id.btnLogin);



        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentLogin();

            }
        });



    }


    private void studentLogin() {
        //first getting the values
        final String email = etEmail.getEditText().getText().toString();
        final String password = etPassword.getEditText().getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Please enter your email");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }
        Toast.makeText(LoginActivity.this,"data collected",Toast.LENGTH_SHORT).show();
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("tagconvertstr", "["+response+"]");

                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("student");





                                //creating a new student object
                                Student student = new Student(
                                        userJson.getString("regNo"),
                                        userJson.getString("firstName"),
                                        userJson.getString("lastName"),
                                        userJson.getString("profileImage"),
                                        userJson.getString("gender"),
                                        userJson.getString("campus"),
                                        userJson.getString("phone"),
                                        userJson.getString("email"),
                                        userJson.getString("guildOfficialId"),
                                        userJson.getString("academicYear"),
                                        userJson.getString("title"),
                                        userJson.getString("description")

                                );



                                //storing the student in shared preferences
                                SharedPreferenceManager.getInstance(getApplicationContext()).studentLogin(student);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}