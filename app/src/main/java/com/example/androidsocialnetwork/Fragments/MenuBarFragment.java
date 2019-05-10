package com.example.androidsocialnetwork.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.androidsocialnetwork.R;

import javax.security.auth.callback.Callback;




public class MenuBarFragment extends Fragment  {
    public Callbacks mCallbacks;

    public interface  Callbacks {
        void changeOption (int option);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.fragment_menus_bar,container,false);
        final ImageView image0 = (ImageView) v.findViewById(R.id.chatRandomButton);
        final ImageView image1 = (ImageView) v.findViewById(R.id.chatsListsButton);
        final ImageView image2 = (ImageView) v.findViewById(R.id.profileMenuButton);

        image0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //image0.setBackgroundResource(R.drawable.chat_selected);
                //image1.setBackgroundResource(R.drawable.chats_list);
                //image2.setBackgroundResource(R.drawable.profile);
                mCallbacks.changeOption(0);
            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //image0.setBackgroundResource(R.drawable.chat);
                //image1.setBackgroundResource(R.drawable.chats_list_selected);
                //image2.setBackgroundResource(R.drawable.profile);
                mCallbacks.changeOption(1);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //image0.setBackgroundResource(R.drawable.chat);
                //image1.setBackgroundResource(R.drawable.chats_list);
                //image2.setBackgroundResource(R.drawable.profile_selected);
                mCallbacks.changeOption(2);
            }
        });
        return v;
    }


}
