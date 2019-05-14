package com.example.androidsocialnetwork;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.Fragments.ChatFragment;
import com.example.androidsocialnetwork.Fragments.ChatListFragment;
import com.example.androidsocialnetwork.Fragments.FriendFragment;
import com.example.androidsocialnetwork.Fragments.MenuBarFragment;
import com.example.androidsocialnetwork.Fragments.ProfileFragment;
import com.example.androidsocialnetwork.Fragments.UserSolicitudes;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

public class MainActivity extends FragmentActivity implements Callbacks {
    private TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new ChatListFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
        Fragment fragmentMenuBar = fm.findFragmentById(R.id.fragment_menu_bar);
        if (fragmentMenuBar == null) {
            fragmentMenuBar = new MenuBarFragment();
            fm.beginTransaction().add(R.id.fragment_menu_bar,fragmentMenuBar).commit();
        }
        mainText = findViewById(R.id.main_text);
        mainText.setText("Chats");
    }

    public void changeOption (int option) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment oldFragment = fm.findFragmentById(R.id.fragment_container);
        Fragment newFragment = null;
        switch (option){
            case 0:
                newFragment = new ChatFragment();
                mainText.setText("Random Chat");
                ComunicationServer cs = new ComunicationServer();
                cs.inviteUser(this);
                break;
            case 1:
                newFragment = new ChatListFragment();
                mainText.setText("Chats");
                break;
            case 2:
                newFragment = new ProfileFragment();
                mainText.setText("My Profile");
                break;

        }
        if (oldFragment != null) {
            ft.remove(oldFragment);
        }
        ft.add(R.id.fragment_container,newFragment);
        ft.commit();
    }
    public void returnToMainMenu() {
        mainText.setText("Chats");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.fragment_menu_bar);
        MenuBarFragment menuBarFragment = (MenuBarFragment) f;
        menuBarFragment.returnToMainMenu();
    }

    @Override
    public void obtainFriendInformation() {
        mainText.setText("Friend Info");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = (Fragment) fm.findFragmentById(R.id.fragment_container);
        if (f != null) {
            ft.remove(f);
        }
        Fragment newFragment = new FriendFragment();
        ft.add(R.id.fragment_container,newFragment).commit();
    }

    @Override
    public void returnToChat() {
        mainText.setText("Random Chat");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = (Fragment) fm.findFragmentById(R.id.fragment_container);
        if (f != null) {
            ft.remove(f);
        }
        Fragment newFragment = new ChatFragment();
        ft.add(R.id.fragment_container,newFragment).commit();
    }

    @Override
    public void gotoUserSolicitudes() {
        mainText.setText("Click to the user to manage the petition");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = (Fragment) fm.findFragmentById(R.id.fragment_container);
        if (f != null) {
            ft.remove(f);
        }
        Fragment newFragment = new UserSolicitudes();
        ft.add(R.id.fragment_container,newFragment).commit();
    }

    @Override
    public void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.add_friend_icon)
                        .setContentTitle("Notification Title")
                        .setContentText("Notification ")
                        .setContentIntent(pendingIntent );

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }


}
