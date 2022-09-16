package com.example.iusuapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.iusuapp.activity.LoginActivity;
import com.example.iusuapp.models.Student;

public class SharedPreferenceManager {

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_REG_NO = "keyregno";
    private static final String KEY_FIRST_NAME = "keyfirstname";
    private static final String KEY_LAST_NAME = "keylastname";
    private static final String KEY_PROFILE_IMAGE = "keyprofileimage";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_CAMPUS = "keycampus";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GUILD_OFFICIAL_ID = "keyguildofficialid";
    private static final String KEY_ACADEMICYEAR = "keyacademicyear";
    private static final String KEY_GUILD_TITLE = "keyguildtitle";
    private static final String KEY_GUILD_DESCRIPTION = "keyguilddescription";
    private static final String IMAGE_URL = "IMAGE_URL";
    private static final String KEY_URL = "IMAGE_URL.url";


    private static SharedPreferenceManager mInstance;
    private static Context ctx;

    private SharedPreferenceManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferenceManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void studentLogin(Student student) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_REG_NO, student.getRegNo());
        editor.putString(KEY_FIRST_NAME, student.getFirstName());
        editor.putString(KEY_LAST_NAME, student.getLastName());
        editor.putString(KEY_PROFILE_IMAGE, student.getProfileImage());
        editor.putString(KEY_GENDER, student.getGender());
        editor.putString(KEY_CAMPUS, student.getCampus());
        editor.putString(KEY_PHONE, student.getPhone());
        editor.putString(KEY_EMAIL, student.getEmail());
        editor.putString(KEY_GUILD_OFFICIAL_ID, student.getGuildOfficialId());
        editor.putString(KEY_GUILD_TITLE, student.getGuildTitle());
        editor.putString(KEY_GUILD_DESCRIPTION, student.getGuildDescription());
        editor.putString(KEY_ACADEMICYEAR, student.getAcademicYear());

        editor.apply();
    }

    //this method will checker whether student is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_REG_NO, null) != null;
    }

    public boolean isGuildOfficial() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String resp = sharedPreferences.getString(KEY_GUILD_OFFICIAL_ID, null);
        Log.e("taggoid","test: "+resp.length()+"..");

        if(resp!=null&&resp!="null"&&resp.length()<4){
            return true;
        } else {
            return false;
        }


    }



    //this method will give the logged in student
    public Student getStudent() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Student(
                sharedPreferences.getString(KEY_REG_NO, null),
                sharedPreferences.getString(KEY_FIRST_NAME, null),
                sharedPreferences.getString(KEY_LAST_NAME, null),
                sharedPreferences.getString(KEY_PROFILE_IMAGE, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_CAMPUS, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_GUILD_OFFICIAL_ID, null),
                sharedPreferences.getString(KEY_ACADEMICYEAR, null),
                sharedPreferences.getString(KEY_GUILD_TITLE, null),
                sharedPreferences.getString(KEY_GUILD_DESCRIPTION, null)


        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }

    public String getProfileImageUrl() {
        SharedPreferences preferences = ctx.getSharedPreferences(IMAGE_URL, Context.MODE_PRIVATE);
        return preferences.getString(KEY_URL, "hello");
    }

    public void setProfileImageUrl(String url) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(IMAGE_URL, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_URL, url);
        editor.apply();
    }

}