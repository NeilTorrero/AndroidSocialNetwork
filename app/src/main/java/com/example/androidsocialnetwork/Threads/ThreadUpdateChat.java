package com.example.androidsocialnetwork.Threads;

import com.example.androidsocialnetwork.Fragments.ChatFragment;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

public class ThreadUpdateChat extends Thread {
    private ChatFragment chatFragment;
    private Integer idUser;

    public ThreadUpdateChat (ChatFragment chatFragment,Integer idUser) {
        this.idUser = idUser;
        this.chatFragment = chatFragment;
    }
    public void  run () {
            try {
                Thread.sleep(10000);
                ComunicationServer.getInstance().getMessagesYouAndFriend(chatFragment,idUser);
            } catch (InterruptedException e) {
            }
    }

}
