package com.example.androidsocialnetwork.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.R;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;


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
    private Callbacks mCallbacks;



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
                mCallbacks.returnToMainMenu();
            }
        });

        profilePic = v.findViewById(R.id.profile_pic);

        userName = v.findViewById(R.id.userName);

        infoButton = v.findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.obtainFriendInformation();
            }
        });



        ImageView emoticono = (ImageView) v.findViewById(R.id.emoji_btn);
        EmojiconEditText emojiconEditText = (EmojiconEditText) v.findViewById(R.id.emojicon_edit_text);
        ImageView send = (ImageView) v.findViewById(R.id.submit_btn);
        View root = (View) v.findViewById(R.id.root_view);
        EmojIconActions emojIconActions = new EmojIconActions(getContext(),root,emojiconEditText,emoticono,"#F44336","#e8e8e8","#f4f4f4");
        emojIconActions.ShowEmojIcon();


        return v;
    }
}
