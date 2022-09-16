package com.example.iusuapp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iusuapp.R;
import com.example.iusuapp.SharedPreferenceManager;
import com.example.iusuapp.activity.AddEditAnnouncement;
import com.example.iusuapp.activity.AddEditEventActivity;
import com.example.iusuapp.activity.AddEditNewsActivity;
import com.example.iusuapp.activity.MainActivity;
import com.example.iusuapp.adapters.PostsFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

//public class HomeFragment extends Fragment implements View.OnClickListener {
public class HomeFragment extends Fragment  {

//    ColorStateList def;
//    TextView item1, item2, item3, select;


    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private PostsFragmentAdapter adapter;
    private TextView greetingTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout=view.findViewById(R.id.fhTabLayout);
        viewPager2=view.findViewById(R.id.fhViewPager);
        greetingTextView = view.findViewById(R.id.fhTextViewGreeting);

        if (SharedPreferenceManager.getInstance(getContext()).isGuildOfficial()) {
            view.findViewById(R.id.fraghome_fab_btn).setVisibility(View.VISIBLE);
            Log.e("tagFAB","fab: "+SharedPreferenceManager.getInstance(getContext()).isGuildOfficial());
            Log.e("tagGOID","goid: "+SharedPreferenceManager.getInstance(getContext()).getStudent().getGuildOfficialId());
        }
        view.findViewById(R.id.fraghome_fab_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Announcements"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));


        greetingTextView.setText("Hello "+SharedPreferenceManager.getInstance(getContext()).getStudent().getFirstName()+" "+SharedPreferenceManager.getInstance(getContext()).getStudent().getLastName());

        FragmentManager fragmentManager = getChildFragmentManager();
        adapter = new PostsFragmentAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });





        return view;
    }


    private void showDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout announcementLayout = dialog.findViewById(R.id.layoutAnnouncement);
        LinearLayout newsLayout = dialog.findViewById(R.id.layoutNews);
        LinearLayout eventLayout = dialog.findViewById(R.id.layoutEvent);

        announcementLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent intent = new Intent(getContext(), AddEditAnnouncement.class) ;
              startActivity(intent);
            }
        });

        newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                startActivity(new Intent(getContext(), AddEditNewsActivity.class));

            }
        });

        eventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                startActivity(new Intent(getContext(), AddEditEventActivity.class));
            }
        });



        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}