package com.example.iusuapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.iusuapp.fragment.AnnouncementFragment;
import com.example.iusuapp.fragment.EventsFragment;
import com.example.iusuapp.fragment.NewsFragment;

public class PostsFragmentAdapter extends FragmentStateAdapter {
    public PostsFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==1){
            return new NewsFragment();
        } else if (position==2){
            return new EventsFragment();
        }

            return new AnnouncementFragment();

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
