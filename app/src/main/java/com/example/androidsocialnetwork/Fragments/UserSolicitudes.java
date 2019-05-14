package com.example.androidsocialnetwork.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidsocialnetwork.Callbacks.CallbackDialog;
import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.Model.Chat;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;


public class UserSolicitudes extends ListFragment {
    private ArrayList <User> users;
    private User lastUserClicked;

    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        users = new ArrayList<>();

        //TODO: Aqui se llamaria a una funcion de retrofit que adquiriria todos los usuarios disponibles, una vez hecho esto, se borran los dos adds
        ComunicationServer cs = new ComunicationServer();
        cs.getAllUsers(this);

        //Mostramos los usuarios
        insertarLista();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        lastUserClicked = (User) (getListAdapter()).getItem(position);
        FragmentManager fragmentManager = getFragmentManager();

        DialogFragment dialogFrag = OptionMessage.newInstance(123);
        dialogFrag.setTargetFragment(this, 1);
        dialogFrag.show(getFragmentManager().beginTransaction(), "dialog");

        //Refrescamos los usuarios
        users.remove(lastUserClicked);
        insertarLista();

    }

    public void insertarLista () {
        if (users.size() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            AlertMessage dialogo = new AlertMessage();
            dialogo.show(fragmentManager, "tagAlerta");
        }
        ArrayAdapter <User> userArrayAdapter = new UserAdapter(getActivity(),this.getContext(),users);
        setListAdapter(userArrayAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //TODO: Codigo en retrofit donde agregamos el usuario que tenemos en el atributo lastClickedUser
        } else if (resultCode == Activity.RESULT_CANCELED){
            //TODO: Codigo en retrofit donde rechazamos el usuario que tenemos en el atributo lastClickedUser
        }
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}

