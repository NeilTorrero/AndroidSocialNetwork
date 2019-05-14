package com.example.androidsocialnetwork.Fragments;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidsocialnetwork.Model.Chat;
import com.example.androidsocialnetwork.Model.Invitation;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.Model.UserDTO;
import com.example.androidsocialnetwork.R;

import java.util.ArrayList;

public class InvitationAdapter extends ArrayAdapter<Invitation> {
    private FragmentActivity activity;
    public InvitationAdapter(FragmentActivity activity, Context context, ArrayList <Invitation> invitations) {
        super (context,0, invitations);
        this.activity = activity;
    }
    @Override
    public View getView (int position, View converterView, ViewGroup parent) {
        if (converterView == null) {
            converterView = activity.getLayoutInflater().inflate(R.layout.userlist_layout,null);
        }
        Invitation invitation = getItem(position);
        UserDTO user = invitation.getSent().getUser();
        TextView tv = converterView.findViewById(R.id.user_email);
        tv.setText(user.getEmail());
        TextView tv2 = converterView.findViewById(R.id.description_user_list);
        tv2.setText(user.getFirstName());
        return converterView;
    }


}
