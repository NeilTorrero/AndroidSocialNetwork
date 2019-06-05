package com.example.androidsocialnetwork.Threads;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.androidsocialnetwork.Fragments.ChatFragment;
import com.example.androidsocialnetwork.Model.DirectMessage;
import com.example.androidsocialnetwork.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ThreadPutImage extends Thread{

    private String url_2;
    private ChatFragment chatFragment;
    private Activity activity;
    private Integer contador;
    private ArrayList <DirectMessage> messages;
    private boolean b;
    public  ThreadPutImage (String url, ChatFragment chatFragment, Activity activity, Integer contador, ArrayList <DirectMessage> messages,boolean b) {
        this.url_2 = url;
        this.chatFragment = chatFragment;
        this.activity = activity;
        this.contador =contador;
        this.messages = messages;
        this.b = b;
    }

    public void run () {
        URL url = null;
        try {
            url = new URL(url_2);
            final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            activity.runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           chatFragment.loadImage(bmp,messages,contador,b);
                                       }
                                   }
            );

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
