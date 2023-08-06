package com.UAS_AKB_IF5_10120205.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.UAS_AKB_IF5_10120205.R;
import com.UAS_AKB_IF5_10120205.view.activity.MainActivity;

public class ProfileFragment extends Fragment {
// 10120205 - Raya Adhary - IF5

    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().hide();
    }
}

// 10120205 - Raya Adhary - IF5

