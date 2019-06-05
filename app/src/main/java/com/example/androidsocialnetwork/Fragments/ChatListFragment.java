package com.example.androidsocialnetwork.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.example.androidsocialnetwork.Model.Chat;
import com.example.androidsocialnetwork.Model.Chatroom;
import com.example.androidsocialnetwork.Model.Invitation;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import java.util.ArrayList;


public class ChatListFragment extends ListFragment {
    private ArrayList <Invitation> chats;
    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        chats = new ArrayList<>();
        //Aqui se llamaria a una funcion de retrofit que adquiriria todos los chats que un usuario tiene abiertos
        //ComunicationServer.getInstance().getAllChatRooms(ChatListFragment.this);
        ComunicationServer.getInstance().getAllAcceptedInvites(this);
        ArrayAdapter <Invitation> chatArrayAdapter = new ChatAdapter(getActivity(),this.getContext(),chats);
        setListAdapter(chatArrayAdapter);
    }

    public void setAllChatRooms(ArrayList<Invitation> chatrooms) {
        if (chatrooms != null) {
            for (int i = 0; i < chatrooms.size(); i++) {
                chats.add(chatrooms.get(i));
            }
            ArrayAdapter <Invitation> chatArrayAdapter = new ChatAdapter(getActivity(),this.getContext(),chats);
            setListAdapter(chatArrayAdapter);
        } else {
            chatrooms = new ArrayList<>();
        }
    }
}
