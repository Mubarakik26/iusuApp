package com.example.iusuapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.iusuapp.AppHelper;
import com.example.iusuapp.R;
import com.example.iusuapp.SharedPreferenceManager;
import com.example.iusuapp.URLs;
import com.example.iusuapp.VolleyMultipartRequest;
import com.example.iusuapp.models.Student;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private ImageButton profileBackBtn;
    String[] genderItems,campusItems;
    TextInputLayout editTextRegno, editTextFirstName, editTextLastName,editTextGenderLayout,editTextCampusLayout, editTextPhone,editTextEmail,editTextPassword,editTextConfirmPassword;
    AutoCompleteTextView editTextCampus,editTextGender;
    ShapeableImageView profileImage;

    AppCompatButton updateProfileBtn;
    TextView changeProfileImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        updateProfileBtn=findViewById(R.id.epButtonEditProfile);
        changeProfileImage=findViewById(R.id.prTextViewChangeProfileIImage);

        profileImage = findViewById(R.id.epImageViewProfileImage);
        editTextRegno= findViewById(R.id.epEditTextRegno);
        editTextFirstName=findViewById(R.id.epEditTextFirstName);
        editTextLastName=findViewById(R.id.epEditTextLastName);
        editTextCampus= findViewById(R.id.epEditTextCampusAutoComplete);
        editTextCampusLayout= findViewById(R.id.epEditTextCampusLayout);
        editTextGender= findViewById(R.id.epEditTextGenderAutoComplete);
        editTextGenderLayout= findViewById(R.id.epEditTextGender);
        editTextEmail=findViewById(R.id.epEditTextEmail);
        editTextPhone=findViewById(R.id.epEditTextPhone);
        profileBackBtn=findViewById(R.id.epBackBtn);




        campusItems = getResources().getStringArray(R.array.campus);
        genderItems = getResources().getStringArray(R.array.gender);

        ArrayAdapter campusAdapter = new ArrayAdapter(EditProfileActivity.this,R.layout.dropdown_list_item, campusItems);
        campusAdapter.setDropDownViewResource(R.layout.dropdown_list_item);
        editTextCampus.setAdapter(campusAdapter);

         ArrayAdapter genderAdapter = new ArrayAdapter(EditProfileActivity.this,R.layout.dropdown_list_item, genderItems);
        genderAdapter.setDropDownViewResource(R.layout.dropdown_list_item);
        editTextGender.setAdapter(genderAdapter);

        Student student = SharedPreferenceManager.getInstance(getApplicationContext()).getStudent();

        editTextRegno.getEditText().setText(student.getRegNo());
        editTextFirstName.getEditText().setText(student.getFirstName());
        editTextLastName.getEditText().setText(student.getLastName());
        editTextGender.setText(student.getGender());
        editTextCampus.setText(student.getCampus());
        editTextPhone.getEditText().setText(student.getPhone());
        editTextEmail.getEditText().setText(student.getEmail());
        Glide.with(this).load(student.getProfileImage()).into(profileImage);


        profileBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileActivity.this.finish();
            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfileImage();
            }
        });

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPost();
            }
        });

    }

    public void chooseProfileImage(){
        Dexter.withActivity(EditProfileActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setFixAspectRatio(true)
                                .setAllowRotation(true)
                                .setAspectRatio(1,1)
                                .setMultiTouchEnabled(true)
                                .start(EditProfileActivity.this);


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if(permissionDeniedResponse.isPermanentlyDenied()){
                            AlertDialog.Builder builder=new AlertDialog.Builder(EditProfileActivity.this);
                            builder.setTitle("Permission Required").setMessage("Permission to access storage is required to pick image. Please go to setting to enable permission to access storage")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package",getPackageName(),null));
                                            startActivityForResult(intent,51);
                                        }
                                    }).setNegativeButton("Cancel",null).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                profileImage.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("tagre: ",""+error);
            }
        }
    }

    private void uploadPost() {

        final String regNo = editTextRegno.getEditText().getText().toString().trim();
        final String firstName = editTextFirstName.getEditText().getText().toString().trim();
        final String lastName = editTextLastName.getEditText().getText().toString().trim();
        final String gender = editTextGender.getText().toString().trim();
        final String campus = editTextCampusLayout.getEditText().getText().toString().trim();
        final String phone = editTextPhone.getEditText().getText().toString().trim();
        final String email = editTextEmail.getEditText().getText().toString().trim();



        editTextRegno.setError(null);
        editTextFirstName.setError(null);
        editTextLastName.setError(null);
        editTextCampusLayout.setError(null);
        editTextGenderLayout.setError(null);
        editTextEmail.setError(null);
        editTextPhone.setError(null);


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





        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.UPDATE_PROFILE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                        try {
                            Log.i("tagconvertstr", "["+new String(response.data)+"]");
                            JSONObject obj = new JSONObject(new String(response.data));

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
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("regNo", regNo);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("campus", campus);
                params.put("phone", phone);
                params.put("gender", gender);
                params.put("email", email);

                return params;
            }


            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("profileImage", new DataPart(imagename + ".png", AppHelper.getFileDataFromDrawable(getApplicationContext(), profileImage.getDrawable()), "image/jpg"));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }



}