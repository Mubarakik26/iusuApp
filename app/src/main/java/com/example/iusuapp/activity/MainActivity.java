package com.example.iusuapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.iusuapp.R;
import com.example.iusuapp.fragment.FavoritesFragment;
import com.example.iusuapp.fragment.HelpFragment;
import com.example.iusuapp.fragment.HomeFragment;
import com.example.iusuapp.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation= findViewById(R.id.maBottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_favorite));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_help));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_profile));


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch (item.getId()){
                    case 1:
                        fragment = new HomeFragment();
                        break;

                    case 2:
                        fragment= new FavoritesFragment();
                        break;
                    case 3:
                        fragment=new HelpFragment();
                        break;

                    case 4:
                        fragment=new ProfileFragment();
                        break;

                }

                loadFragment(fragment);
            }


        });


        bottomNavigation.setCount(3,"10");
        bottomNavigation.show(1,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(),"You clicked item "+item.getId(),Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(),"You reselected item "+item.getId(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.maFrameLayout,fragment)
                .commit();
    }
}