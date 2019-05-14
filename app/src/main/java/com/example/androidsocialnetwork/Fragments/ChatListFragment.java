package com.example.androidsocialnetwork.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.example.androidsocialnetwork.Model.Chat;
import com.example.androidsocialnetwork.Model.Chatroom;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import java.util.ArrayList;


public class ChatListFragment extends ListFragment {
    private ArrayList <Chat> chats;
    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        chats = new ArrayList<>();
        ComunicationServer cs = new ComunicationServer();
        cs.getAllChatRooms(ChatListFragment.this);
        

        ArrayAdapter <Chat> chatArrayAdapter = new ChatAdapter(getActivity(),this.getContext(),chats);
        setListAdapter(chatArrayAdapter);
    }

    public void setAllChatRooms(ArrayList<Chatroom> chatrooms) {
        for (int i = 0; i < chatrooms.size(); i++) {
            chats.add(new Chat(chatrooms.get(i).getTopic(), chatrooms.get(i).getMessages().get(chatrooms.get(i).getMessages().size()).getMessage()));
        }
    }
}
