package com.example.androidsocialnetwork.Fragments;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidsocialnetwork.Model.Chat;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.R;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {
    private FragmentActivity activity;
    public UserAdapter(FragmentActivity activity, Context context, ArrayList <User> users) {
        super (context,0,users);
        this.activity = activity;
    }
    @Override
    public View getView (int position, View converterView, ViewGroup parent) {
        if (converterView == null) {
            converterView = activity.getLayoutInflater().inflate(R.layout.userlist_layout,null);
        }
        User user = getItem(position);
        TextView tv = converterView.findViewById(R.id.userEmail);
        tv.setText(user.getEmail());
        TextView tv2 = converterView.findViewById(R.id.descriptionUserList);
        tv2.setText(user.getDescription());
        return converterView;
    }


}
