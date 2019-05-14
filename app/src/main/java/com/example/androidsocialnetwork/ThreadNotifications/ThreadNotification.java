package com.example.androidsocialnetwork.ThreadNotifications;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.MainActivity;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

public class ThreadNotification extends Thread{
    private MainActivity activity;
    private int i;
    private int j;
    public ThreadNotification (MainActivity activity) {
        this.activity = activity;
        i = 0;
        j= 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void run () {
        while (true) {
            checkNotifications();
            if (i == 1) {
                sendNotification(j);
                i = 0;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            j++;
        }
    }
    public void checkNotifications () {
        //Pedir a retrofit que mire si hay invitaciones pendientes
        //ComunicationServer cs = new ComunicationServer();
        //Hay invitaciones pendientes
        /*if (cs.arePendingInvites()) {
            sendNotification(j);
        }*/
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification (int j){
        activity.showNotification(j);
    }
}
