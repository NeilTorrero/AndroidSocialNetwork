package com.example.androidsocialnetwork.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidsocialnetwork.Model.Invitation;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import java.util.ArrayList;


public class UserSolicitudes extends ListFragment {
    private ArrayList <Invitation> invitations;
    private Invitation lastInvitationClicked;

    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        invitations = new ArrayList<>();

        //Aqui se llamaria a una funcion de retrofit que adquiriria todos los usuarios disponibles, una vez hecho esto, se borran los dos adds
        ComunicationServer.getInstance().getPendingInvites(this);

        //Mostramos los usuarios
        insertarLista();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        lastInvitationClicked = (Invitation) (getListAdapter()).getItem(position);
        FragmentManager fragmentManager = getFragmentManager();

        DialogFragment dialogFrag = OptionMessage.newInstance(123);
        dialogFrag.setTargetFragment(this, 1);
        dialogFrag.show(getFragmentManager().beginTransaction(), "dialog");

        //Refrescamos los usuarios
        invitations.remove(lastInvitationClicked);
        insertarLista();

    }

    public void insertarLista () {
        if (invitations.size() == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            AlertMessage dialogo = new AlertMessage();
            dialogo.show(fragmentManager, "tagAlerta");
        }
        ArrayAdapter <Invitation> invitationArrayAdapter = new InvitationAdapter(getActivity(),this.getContext(), invitations);
        setListAdapter(invitationArrayAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ComunicationServer.getInstance().changeInvitationState(lastInvitationClicked.getId(),resultCode == Activity.RESULT_OK, UserSolicitudes.this);
    }

    public void setInvitations(ArrayList<Invitation> invitations) {
        this.invitations = invitations;
    }
}

