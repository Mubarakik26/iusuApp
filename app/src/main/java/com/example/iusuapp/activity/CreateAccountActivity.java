package com.example.iusuapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class CreateAccountActivity extends AppCompatActivity {

    String[] genderItems,facultyItems,campusItems;
    TextInputLayout editTextRegno, editTextFirstName, editTextLastName,editTextCampusLayout,editTextEmail,editTextPassword,editTextConfirmPassword;
    AutoCompleteTextView editTextCampus;

    AppCompatButton createAccountBtn;
    TextView gotoLogin;
    CheckBox taCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createAccountBtn=findViewById(R.id.caButtonCreateAccount);
        gotoLogin=findViewById(R.id.caTextVeiwLogin);


        editTextRegno= findViewById(R.id.caEditTextRegno);
        editTextFirstName=findViewById(R.id.caEditTextFirstName);
        editTextLastName=findViewById(R.id.caEditTextLastName);
        editTextCampus= findViewById(R.id.caEditTextCampusAutoComplete);
        editTextCampusLayout= findViewById(R.id.caEditTextCampusLayout);
        editTextEmail=findViewById(R.id.caEditTextEmail);
        editTextPassword=findViewById(R.id.caEditTextPassword);
        editTextConfirmPassword=findViewById(R.id.caEditTextConfirmPassword);



        campusItems = getResources().getStringArray(R.array.campus);

        ArrayAdapter campusAdapter = new ArrayAdapter(CreateAccountActivity.this,R.layout.dropdown_list_item, campusItems);
        campusAdapter.setDropDownViewResource(R.layout.dropdown_list_item);
        editTextCampus.setAdapter(campusAdapter);

        //if the user is already logged in we will directly start the MainActivity (profile) activity
        if (SharedPreferenceManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }


        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });






    }

    private void registerUser() {
        final String regNo = editTextRegno.getEditText().getText().toString().trim();
        final String firstName = editTextFirstName.getEditText().getText().toString().trim();
        final String lastName = editTextLastName.getEditText().getText().toString().trim();


        final String campus = editTextCampusLayout.getEditText().getText().toString().trim();

        final String email = editTextEmail.getEditText().getText().toString().trim();
        final String password = editTextPassword.getEditText().getText().toString().trim();
        final String confirmPassword = editTextConfirmPassword.getEditText().getText().toString().trim();




        editTextRegno.setError(null);
        editTextFirstName.setError(null);
        editTextLastName.setError(null);

        editTextCampusLayout.setError(null);

        editTextEmail.setError(null);
        editTextPassword.setError(null);
        editTextConfirmPassword.setError(null);



        //first we will do the validations
        if (TextUtils.isEmpty(regNo)) {
            editTextRegno.setError("Please enter Regno");
            editTextRegno.requestFocus();
            return;
        } else{
            editTextRegno.setError(null);
        }

        if (TextUtils.isEmpty(firstName)) {
            editTextFirstName.setError("Please enter first name");
            editTextFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            editTextFirstName.setError("Please enter last name");
            editTextFirstName.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(campus)) {
            editTextCampusLayout.setError("Please select your campus");
            editTextCampusLayout.requestFocus();
            return;
        }



        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }
//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()&&!email.endsWith("@stud.iuiu.ac.ug"))
        if (!email.endsWith("@stud.iuiu.ac.ug")) {
            editTextEmail.setError("Enter a valid school email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            editTextConfirmPassword.setError("Enter a password");
            editTextConfirmPassword.requestFocus();
            return;
        } else if(!confirmPassword.equals(password)){
            editTextConfirmPassword.setError("Passwords do not match");
            editTextConfirmPassword.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CREATE_ACCOUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("tagconvertstr", "["+response+"]");
                        try {


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
                                        userJson.getString("academicYear"),
                                        userJson.getString("title"),
                                        userJson.getString("description"),
                                        userJson.getString("guildOfficialId")
                                );

                                //storing the student in shared preferences
                                SharedPreferenceManager.getInstance(getApplicationContext()).studentLogin(student);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
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
                params.put("regNo", regNo);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("campus", campus);
                params.put("phone", "");
                params.put("gender", "");
               params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}