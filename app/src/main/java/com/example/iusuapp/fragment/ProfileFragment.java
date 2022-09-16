package com.example.iusuapp.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.iusuapp.R;
import com.example.iusuapp.SharedPreferenceManager;
import com.example.iusuapp.activity.EditProfileActivity;
import com.example.iusuapp.models.Student;
import com.google.android.material.imageview.ShapeableImageView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileFragment extends Fragment {

    AppCompatButton editProfileButton, logoutBtn;
    TextView regno,name,gender,campus,phone,email;
    ShapeableImageView profileImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editProfileButton= view.findViewById(R.id.prButtonEditProfile);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });


        regno=view.findViewById(R.id.prTextViewRegno);
        name=view.findViewById(R.id.prTextViewFullname);
        gender=view.findViewById(R.id.prTextViewGender);
       campus=view.findViewById(R.id.prTextViewCampus);
        phone=view.findViewById(R.id.prTextViewPhone);
        email=view.findViewById(R.id.prTextViewEmail);
        logoutBtn=view.findViewById(R.id.prButtonLogout);


        Student student = SharedPreferenceManager.getInstance(getContext()).getStudent();
        regno.setText(student.getRegNo());
        name.setText(student.getFirstName()+" "+student.getLastName());
        gender.setText(student.getGender());

        campus.setText(student.getCampus());
        phone.setText(student.getPhone());
        email.setText(student.getEmail());


        profileImage=view.findViewById(R.id.prImageViewProfileImage);
        Glide.with(this).load(SharedPreferenceManager.getInstance(getContext()).getStudent().getProfileImage()).into(profileImage);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceManager.getInstance(getContext()).logout();
            }
        });



        Toast.makeText(getContext(), ""+SharedPreferenceManager.getInstance(getContext()).getStudent().getProfileImage()+"", Toast.LENGTH_SHORT).show();



        return view;
    }







}