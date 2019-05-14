package com.example.androidsocialnetwork.ThreadNotifications;

import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

public class ThreadNotification extends Thread{
    private Callbacks mCallbacks;
    public void run () {
        checkNotifications ();
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void checkNotifications () {
        //Pedir a retrofit que mire si hay invitaciones pendientes
        ComunicationServer cs = new ComunicationServer();
        //Hay invitaciones pendientes
        if (cs.arePendingInvites()) {
            sendNotification();
        }
    }
    public void sendNotification (){
        //TODO: Metodo al que vamos cuando hay peticiones pendientes i enviamos notificacion
        mCallbacks.showNotification();
    }
}
