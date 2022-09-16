package com.example.iusuapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.iusuapp.fragment.AnnouncementFragment;
import com.example.iusuapp.fragment.EventsFragment;
import com.example.iusuapp.fragment.FavoriteEventsFragment;
import com.example.iusuapp.fragment.FavoriteNewsFragment;
import com.example.iusuapp.fragment.NewsFragment;

public class FavoritesFragmentAdapter extends FragmentStateAdapter {
    public FavoritesFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==1){
            return new FavoriteEventsFragment();
       } else {

            return new FavoriteNewsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
