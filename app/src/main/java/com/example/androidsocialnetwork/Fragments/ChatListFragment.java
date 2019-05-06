package com.example.androidsocialnetwork.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

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
        chats.add (new Chat ("Hoala","Loop"));
        ArrayAdapter <Chat> chatArrayAdapter = new ChatAdapter(getActivity(),this.getContext(),chats);
        setListAdapter(chatArrayAdapter);
    }
}
