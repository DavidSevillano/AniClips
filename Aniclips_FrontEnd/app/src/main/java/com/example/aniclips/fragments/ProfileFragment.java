package com.example.aniclips.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import com.example.aniclips.R;

public class ProfileFragment extends Fragment {
    private static final String ARG_USER_ID = "user_id";
    private String userId;
    private View profileMainLayout;

    public static ProfileFragment newInstance(String userId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileMainLayout = view.findViewById(R.id.profileMainLayout);
        profileMainLayout.setVisibility(View.GONE);

        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }

        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_ID, userId);

        ProfileHeaderFragment headerFragment = new ProfileHeaderFragment();
        headerFragment.setArguments(bundle);

        MyClipsFragment myClipsFragment = new MyClipsFragment();
        myClipsFragment.setArguments(bundle);

        headerFragment.setPerfilLoadedListener(() -> {
            if (profileMainLayout != null) {
                profileMainLayout.setVisibility(View.VISIBLE);
                profileMainLayout.setAlpha(0f);
                profileMainLayout.animate().alpha(1f).setDuration(250).start();
            }
        });

        getChildFragmentManager().beginTransaction()
                .replace(R.id.profileHeaderContainer, headerFragment)
                .commit();

        getChildFragmentManager().beginTransaction()
                .replace(R.id.myClipsContainer, myClipsFragment)
                .commit();

        return view;
    }
}