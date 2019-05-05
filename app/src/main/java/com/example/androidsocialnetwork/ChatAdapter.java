package com.example.androidsocialnetwork;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidsocialnetwork.Model.Chat;

import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private FragmentActivity activity;
    public ChatAdapter (FragmentActivity activity, Context context, ArrayList <Chat> chats) {
        super (context,0,chats);
        this.activity = activity;
    }
    @Override
    public View getView (int position, View converterView, ViewGroup parent) {
        if (converterView == null) {
            converterView = activity.getLayoutInflater().inflate(R.layout.chatlist_layout,null);
        }
        Chat chat = getItem(position);
        TextView tv = converterView.findViewById(R.id.chatName);
        tv.setText(chat.getGroupName());
        TextView tv2 = converterView.findViewById(R.id.lastMessage);
        tv2.setText(chat.getLastMessage());
        return converterView;
    }


}
