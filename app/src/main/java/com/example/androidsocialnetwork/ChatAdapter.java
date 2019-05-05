package com.example.androidsocialnetwork;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.androidsocialnetwork.Model.Chat;

import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private Activity activity;
    public ChatAdapter (Activity activity, Context context, ArrayList <Chat> chats) {
        super (context,0,chats);
        this.activity = activity;
    }
    @Override
    public View getView (int position, View converterView, ViewGroup parent) {
        if (converterView == null) {
            converterView = activity.getLayoutInflater().inflate(R.layout.chat_layout,parent);
        }
        Chat chat = getItem(position);
        converterView.findViewById(R.id.chatName);
        converterView.findViewById(R.id.lastMessage);
        return converterView;
    }


}
