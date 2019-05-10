package com.example.androidsocialnetwork.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidsocialnetwork.R;


public class MenuBarFragment extends Fragment {
    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.fragment_menus_bar,container,false);
        return v;
    }



}
