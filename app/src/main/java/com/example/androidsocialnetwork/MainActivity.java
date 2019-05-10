package com.example.androidsocialnetwork;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.androidsocialnetwork.Fragments.ChatFragment;
import com.example.androidsocialnetwork.Fragments.ChatListFragment;
import com.example.androidsocialnetwork.Fragments.MenuBarFragment;

public class MainActivity extends FragmentActivity {

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
    }

    public void cambiarMenu (int option) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment oldFragment = fm.findFragmentById(R.id.fragmentContainer);
        Fragment newFragment = null;
        switch (option){
            case 1:
                newFragment = new ChatFragment();
                break;
            case 2:

                break;
            case 3:
                break;

        }
        if (oldFragment != null) {
            ft.remove(oldFragment);
        }
        ft.add(R.id.fragmentContainer,newFragment);
        ft.commit();
    }

}
