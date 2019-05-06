package com.example.androidsocialnetwork;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.example.androidsocialnetwork.Fragments.ChatListFragment;
import com.example.androidsocialnetwork.Fragments.MenuBarFragment;

public class ChatsDisplayActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_display);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentList);
        if (fragment == null) {
            fragment = new ChatListFragment();
            fm.beginTransaction().add(R.id.fragmentList,fragment).commit();
        }
        Fragment fragmentMenuBar = fm.findFragmentById(R.id.fragmentMenuBar);
        if (fragmentMenuBar == null) {
            fragmentMenuBar = new MenuBarFragment();
            fm.beginTransaction().add(R.id.fragmentMenuBar,fragmentMenuBar).commit();
        }
    }
}
