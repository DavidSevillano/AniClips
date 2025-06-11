package com.example.aniclips.activities;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.aniclips.R;
import com.example.aniclips.fragments.HomeFragment;
import com.example.aniclips.fragments.ProfileFragment;
import com.example.aniclips.fragments.SearchFragment;
import com.example.aniclips.utils.HideNavigationBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HideNavigationBar.hideNavigationBar(getWindow().getDecorView());
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            bottomNavigationView.setOnApplyWindowInsetsListener((v, insets) -> {
                v.setPadding(0, 0, 0, 0);
                return insets;
            });

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new HomeFragment())
                    .commit();

            bottomNavigationView.setOnItemSelectedListener(item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.nav_search) {
                    selectedFragment = new SearchFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainContainer, selectedFragment)
                            .commit();
                }
                return true;
            });
        }
    }
}
