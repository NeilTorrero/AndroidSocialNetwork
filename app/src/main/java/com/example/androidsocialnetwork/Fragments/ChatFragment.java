package com.example.androidsocialnetwork.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.Model.DirectMessage;
import com.example.androidsocialnetwork.R;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    private EmojiconEditText chatText;
    private ImageView sendButton;
    private Callbacks mCallbacks;
    private String realusername;
    private LinearLayout relativeLayout;
    private Integer idUser;


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
        relativeLayout = v.findViewById(R.id.chat_layout_2);
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

        final Context context = this.getContext();

        chatText = v.findViewById(R.id.emojicon_edit_text);
        sendButton = v.findViewById(R.id.submit_btn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeMyMessages(chatText.getText().toString());
                chatText.setText("");
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

    public void changeInformation(String nameUser, String photo,Integer id) {
        if (photo != null ) {
            byte[] imageBytes;
            imageBytes = Base64.decode(photo, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            Glide.with(getContext()).asBitmap().load(decodedImage).apply(RequestOptions.circleCropTransform()).into(profilePic);
        }
        userName.setText(nameUser);
        idUser = id;
        ComunicationServer.getInstance().getMessagesYouAndFriend(this,idUser);
    }


    public String getRealusername() {
        return realusername;
    }

    public void setRealusername(String realusername) {
        this.realusername = realusername;
    }


    public void includeChats (ArrayList <DirectMessage> messages) {
        //Toast.makeText(this.getContext(),"Messages loaded", Toast.LENGTH_SHORT).show();
        Collections.sort(messages, new Comparator<DirectMessage>() {
            @Override
            public int compare(DirectMessage o1, DirectMessage o2) {
                return o1.compareTo(o2);
            }
        });
        for (DirectMessage dm: messages) {
            if (!dm.getSender().getDisplayName().equals(userName)) {
                includeMyMessages(dm.getMessage());
            }
            else {
                includeMessageFriend(dm.getMessage());
            }
        }
    }

    private void includeMessageFriend (String message) {
        ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this.getContext());
        tv.setLayoutParams(lparams);
        tv.setBackgroundResource(R.drawable.incoming_message_bubble);
        tv.setText(message);
        tv.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.RIGHT;
        tv.setLayoutParams(params);

        tv.setPadding(20,30,55,30);
        relativeLayout.addView(tv);
    }
    private void includeMyMessages (String message) {
        ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this.getContext());
        tv.setLayoutParams(lparams);
        tv.setBackgroundResource(R.drawable.outgoing_message_bubble);
        tv.setText(message);
        tv.setGravity(Gravity.RIGHT);
        tv.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.RIGHT;
        tv.setLayoutParams(params);

        tv.setPadding(20,30,55,30);
        relativeLayout.addView(tv);
    }


}
