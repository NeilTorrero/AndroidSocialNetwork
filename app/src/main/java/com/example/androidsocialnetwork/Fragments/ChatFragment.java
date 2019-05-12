package com.example.androidsocialnetwork.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidsocialnetwork.R;


public class ChatFragment extends Fragment {
    private ImageView backButton;
    private ImageView profilePic;
    private TextView userName;
    private ImageView infoButton;
    private ImageView settingsButton;
    private TextView chatView;
    private ImageView emojiButton;
    private EditText chatText;
    private ImageView sendButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chat_layout, container, false);

        backButton = v.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        profilePic = v.findViewById(R.id.profilePic);

        userName = v.findViewById(R.id.userName);

        infoButton = v.findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        settingsButton = v.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        emojiButton = v.findViewById(R.id.emoji_button);
        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        chatText = v.findViewById(R.id.text_chat);

        sendButton = v.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
}
