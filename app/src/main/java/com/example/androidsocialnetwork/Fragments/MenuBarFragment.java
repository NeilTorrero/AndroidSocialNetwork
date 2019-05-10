package com.example.androidsocialnetwork.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        ImageView image0 = (ImageView) v.findViewById(R.id.chatRandomButton);
        ImageView image1 = (ImageView) v.findViewById(R.id.chatsListsButton);
        ImageView image2 = (ImageView) v.findViewById(R.id.profileMenuButton);
        return v;
    }


}
