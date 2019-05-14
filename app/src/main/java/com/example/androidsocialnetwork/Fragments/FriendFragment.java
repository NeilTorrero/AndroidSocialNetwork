package com.example.androidsocialnetwork.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.Model.Profile;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.R;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import org.w3c.dom.Text;

public class FriendFragment extends Fragment {

    private TextView userDescription;
    private TextView userHeight;
    private TextView userBirthDate;
    private TextView userGender;
    private ImageView friendPhoto;
    private TextView usernameFriend;
    private ImageView backButtonFriend;
    private Callbacks mCallbacks;
    private ImageView blockButton;
    private TextView userWeight;
    private TextView userAge;

    private Profile userProfile;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.friend_profile,container,false);
        usernameFriend = (TextView) v.findViewById(R.id.friendName);
        friendPhoto = (ImageView) v.findViewById(R.id.profilePictureFriend);
        userBirthDate = (TextView) v.findViewById(R.id.birthDateFriend);
        userHeight = (TextView) v.findViewById(R.id.heightValueFriend);
        userWeight = (TextView) v.findViewById(R.id.weightValueFriend);
        userAge = (TextView) v.findViewById(R.id.ageValueFriend);
        userGender = (TextView) v.findViewById(R.id.genderValueFriend);
        userDescription = (TextView) v.findViewById(R.id.descriptionFriend);
        backButtonFriend = (ImageView) v.findViewById(R.id.back_buttonFriend);
        backButtonFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.returnToChat();
            }
        });
        blockButton = v.findViewById(R.id.blockButton);
        blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Cuando el usuario pulse al boton de bloquear se tiene que bloquear tambien mediante retrofit al usuario
                mCallbacks.returnToMainMenu();
            }
        });
        return v;
    }

    public void obtainFriendInformation () {
        //TODO: Con este metodo, se seteara la informacion del usuario (supongo que obtendrás la información en formato usuario), la pones en la variable user
        ComunicationServer conn = new ComunicationServer();
        conn.getMyProfile(FriendFragment.this);

        if (userProfile.getHeight() == 0) {
            userHeight.setVisibility(View.GONE);
        }
        else {
            userHeight.setText("Height:" + userProfile.getHeight());
        }
        if (userProfile.getWeight() == 0) {
            userWeight.setVisibility(View.GONE);
        }
        else {
            userWeight.setText("Weight:" + userProfile.getWeight());
        }
        if (!userProfile.getShowAge()) {
            userAge.setVisibility(View.GONE);
        }
        else {
            userAge.setText("Age:" + userProfile.getBirthDate());
        }
        if (userProfile.getGender().equals("DO NOT SHOW")) {
            userGender.setVisibility(View.GONE);
        }
        else {
            userGender.setText("Gender:" + userProfile.getGender());
        }

    }

    public void updateProfile(Profile newProfile) {
        userProfile = newProfile;
    }
}
