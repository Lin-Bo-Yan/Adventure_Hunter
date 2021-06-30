package com.example.mountaineering2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


public class Fragment_mountain extends Fragment {
    private View mountain;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mountain =  inflater.inflate(R.layout.fragment_mountain,container,false);


        return mountain;
    }



}
