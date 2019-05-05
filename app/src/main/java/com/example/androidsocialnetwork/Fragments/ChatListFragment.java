package com.example.androidsocialnetwork.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.androidsocialnetwork.ChatAdapter;
import com.example.androidsocialnetwork.Model.Chat;

import java.util.ArrayList;


public class ChatListFragment extends ListFragment {
    private ArrayList <Chat> chats;
    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        chats = new ArrayList<>();
        chats.add(new Chat("Javatos","LoOp"));
        chats.add(new Chat("Jajajas","Loop"));
        chats.add(new Chat("Hula","LoOp"));
        chats.add(new Chat("Jijiji","Loop"));
        chats.add(new Chat("jajaja","loop"));
        ArrayAdapter <Chat> chatArrayAdapter = new ChatAdapter(getActivity(),this.getContext(),chats);
        setListAdapter(chatArrayAdapter);
    }
}
