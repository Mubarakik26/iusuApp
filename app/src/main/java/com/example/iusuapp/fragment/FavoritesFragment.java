package com.example.iusuapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iusuapp.R;
import com.example.iusuapp.adapters.FavoritesFragmentAdapter;
import com.example.iusuapp.adapters.PostsFragmentAdapter;
import com.google.android.material.tabs.TabLayout;


public class FavoritesFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FavoritesFragmentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        tabLayout=view.findViewById(R.id.ffTabLayout);
        viewPager2=view.findViewById(R.id.ffViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));

        FragmentManager fragmentManager = getChildFragmentManager();
        adapter = new FavoritesFragmentAdapter(fragmentManager,getLifecycle());
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
}