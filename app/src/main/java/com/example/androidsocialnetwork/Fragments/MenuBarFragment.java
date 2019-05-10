package com.example.androidsocialnetwork.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidsocialnetwork.R;

import javax.security.auth.callback.Callback;




public class MenuBarFragment extends Fragment  {
    public Callbacks mCallbakcs;

    public interface  Callbacks {
        void changeOption (int option);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbakcs = (Callbacks) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbakcs = null;
    }

    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.fragment_menus_bar,container,false);
        return v;
    }




}
