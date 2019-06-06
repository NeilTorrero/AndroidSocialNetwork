package com.example.androidsocialnetwork.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidsocialnetwork.MainActivity;
import com.example.androidsocialnetwork.Model.Chat;
import com.example.androidsocialnetwork.Model.Chatroom;
import com.example.androidsocialnetwork.Model.Invitation;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import java.util.ArrayList;


public class ChatListFragment extends ListFragment {
    private ArrayList <Invitation> chats;
    private MainActivity mainActivity;


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
            ((MainActivity)getActivity()).disableProgressBar();
            chats = new ArrayList<>();
            for (int j = 0; j < chatrooms.size()-1;j++) {
                for (int w = j+1; w < chatrooms.size();w++) {
                    if (chatrooms.get(j).getReceived().getDisplayName().equals(chatrooms.get(w).getReceived().getDisplayName())) {
                        chatrooms.remove(w);
                        w--;
                    }
                }
                if (chatrooms.get(j).getReceived().getDisplayName().equals(ComunicationServer.getInstance().getUserProfile().getDisplayName())) {
                    chatrooms.remove(j);
                    j--;
                }
            }
            for (int i = 0; i < chatrooms.size(); i++) {
                chats.add(chatrooms.get(i));
            }
            ArrayAdapter <Invitation> chatArrayAdapter = new ChatAdapter(getActivity(),this.getContext(),chats);
            setListAdapter(chatArrayAdapter);
        } else {
            chatrooms = new ArrayList<>();
        }
    }

    public void passActivity (MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mainActivity.getNewFriendChat(chats.get(position).getReceived());
    }

}
