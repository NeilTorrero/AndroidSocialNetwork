package com.example.androidsocialnetwork.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidsocialnetwork.Model.Chat;
import com.example.androidsocialnetwork.Model.Invitation;
import com.example.androidsocialnetwork.R;

import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter<Invitation> {
    private FragmentActivity activity;
    public ChatAdapter (FragmentActivity activity, Context context, ArrayList <Invitation> chats) {
        super (context,0,chats);
        this.activity = activity;
    }
    @Override
    public View getView (int position, View converterView, ViewGroup parent) {
        if (converterView == null) {
            converterView = activity.getLayoutInflater().inflate(R.layout.chatlist_layout,null);
        }
        Invitation chat = getItem(position);
        ImageView imageView = converterView.findViewById(R.id.group_image);
        if (chat.getReceived().getPicture() != null) {
            byte[] imageBytes;
            imageBytes = Base64.decode(chat.getReceived().getPicture(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            Glide.with(getContext()).asBitmap().load(decodedImage).apply(RequestOptions.circleCropTransform()).override(220, 220).into(imageView);
        }
        TextView tv = converterView.findViewById(R.id.chat_name);
        tv.setText(chat.getReceived().getDisplayName());
        TextView tv2 = converterView.findViewById(R.id.last_message);
        tv2.setText(chat.getReceived().getAboutMe());
        return converterView;
    }


}
