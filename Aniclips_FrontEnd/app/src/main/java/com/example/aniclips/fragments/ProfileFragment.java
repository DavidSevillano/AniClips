package com.example.aniclips.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aniclips.R;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.profileHeaderContainer, new ProfileHeaderFragment())
                .commit();

        getChildFragmentManager().beginTransaction()
                .replace(R.id.myClipsContainer, new MyClipsFragment())
                .commit();



        return view;
    }
}
