package com.example.androidsocialnetwork;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.Fragments.ChatFragment;
import com.example.androidsocialnetwork.Fragments.ChatListFragment;
import com.example.androidsocialnetwork.Fragments.FriendFragment;
import com.example.androidsocialnetwork.Fragments.MenuBarFragment;
import com.example.androidsocialnetwork.Fragments.ProfileFragment;
import com.example.androidsocialnetwork.Fragments.UserSolicitudes;

import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class MainActivity extends FragmentActivity implements Callbacks {
    private TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new ChatListFragment();
            fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
        }
        Fragment fragmentMenuBar = fm.findFragmentById(R.id.fragmentMenuBar);
        if (fragmentMenuBar == null) {
            fragmentMenuBar = new MenuBarFragment();
            fm.beginTransaction().add(R.id.fragmentMenuBar,fragmentMenuBar).commit();
        }
        mainText = findViewById(R.id.main_text);
        mainText.setText("Chats");
    }

    public void changeOption (int option) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment oldFragment = fm.findFragmentById(R.id.fragmentContainer);
        Fragment newFragment = null;
        switch (option){
            case 0:
                newFragment = new ChatFragment();
                mainText.setText("Random Chat");
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
        ft.add(R.id.fragmentContainer,newFragment);
        ft.commit();
    }
    public void returnToMainMenu() {
        mainText.setText("Chats");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.fragmentMenuBar);
        MenuBarFragment menuBarFragment = (MenuBarFragment) f;
        menuBarFragment.returnToMainMenu();
    }

    @Override
    public void obtainFriendInformation() {
        mainText.setText("Friend Info");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = (Fragment) fm.findFragmentById(R.id.fragmentContainer);
        if (f != null) {
            ft.remove(f);
        }
        Fragment newFragment = new FriendFragment();
        ft.add(R.id.fragmentContainer,newFragment).commit();
    }

    @Override
    public void returnToChat() {
        mainText.setText("Random Chat");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = (Fragment) fm.findFragmentById(R.id.fragmentContainer);
        if (f != null) {
            ft.remove(f);
        }
        Fragment newFragment = new ChatFragment();
        ft.add(R.id.fragmentContainer,newFragment).commit();
    }

    @Override
    public void gotoUserSolicitudes() {
        mainText.setText("Click to the user to manage the petition");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = (Fragment) fm.findFragmentById(R.id.fragmentContainer);
        if (f != null) {
            ft.remove(f);
        }
        Fragment newFragment = new UserSolicitudes();
        ft.add(R.id.fragmentContainer,newFragment).commit();
    }


}
