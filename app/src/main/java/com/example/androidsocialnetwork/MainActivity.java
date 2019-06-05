package com.example.androidsocialnetwork;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.example.androidsocialnetwork.Model.Profile;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.Model.UserDTO;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;
import com.example.androidsocialnetwork.ThreadNotifications.ThreadNotification;

public class MainActivity extends FragmentActivity implements Callbacks {
    private TextView mainText;
    private Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new ThreadNotification(this);
        thread.start();
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
        ComunicationServer.getInstance().getMyProfileMainActivity(this);
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
                ComunicationServer.getInstance().inviteRandomUser(this,myProfile);
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
            if (oldFragment instanceof ChatFragment) {
                ((ChatFragment)oldFragment).disconnectThread();
            }
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
        String usernameFriend = ((ChatFragment) f).getRealusername();
        if (f != null) {
            ft.remove(f);
        }
        Fragment newFragment = new FriendFragment();
        ((FriendFragment) newFragment).stablishUsername(usernameFriend);
        ft.add(R.id.fragment_container,newFragment).commit();
    }

    @Override
    public void returnToChat() {
        mainText.setText("Random Chat");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = (Fragment) fm.findFragmentById(R.id.fragment_container);
        String nameUser = ((FriendFragment) f).getHola();
        if (f != null) {
            ft.remove(f);
        }
        ChatFragment newFragment = new ChatFragment();
        ft.add(R.id.fragment_container,newFragment).commit();
        ComunicationServer.getInstance().gotoPreviousUser(this,nameUser);
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification(int j) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

        NotificationChannel mChannel = new NotificationChannel("my_channel_01", "notification", NotificationManager.IMPORTANCE_HIGH);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getBaseContext())
                        .setContentTitle("New mail from " + "test@gmail.com")
                        .setContentText("Subject")
                        .setContentIntent(pendingIntent).setAutoCancel(true)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setStyle(bigText).setChannelId("my_channel_01");


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(j, mBuilder.build());

    }


    public void changeChatInformation(User user, Profile profile) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        ChatFragment chatFragment = (ChatFragment) fragment;
        chatFragment.changeInformation(profile.getDisplayName(),profile.getPicture(),profile.getId());
        chatFragment.setRealusername (user.getLogin());
    }



    public void setMyProfile(Profile body) {
        myProfile = body;
    }

    public void reloadChatInformation(UserDTO u, Profile body) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        ChatFragment chatFragment = (ChatFragment) fragment;
        chatFragment.changeInformation(body.getDisplayName(),body.getPicture(),body.getId());
        chatFragment.setRealusername (u.getLogin());
    }
}
