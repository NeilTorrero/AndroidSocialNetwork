package com.example.androidsocialnetwork.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.Model.Profile;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.Model.UserDTO;
import com.example.androidsocialnetwork.R;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    private String hola;
    private Profile userProfile;
    private String lastUri;


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
        usernameFriend = (TextView) v.findViewById(R.id.friend_name);
        usernameFriend.setText(hola);
        friendPhoto = (ImageView) v.findViewById(R.id.profile_picture_friend);
        userBirthDate = (TextView) v.findViewById(R.id.birth_date_friend);
        userHeight = (TextView) v.findViewById(R.id.height_value_friend);
        userWeight = (TextView) v.findViewById(R.id.weight_value_friend);
        userAge = (TextView) v.findViewById(R.id.age_value_friend);
        userGender = (TextView) v.findViewById(R.id.gender_value_friend);
        userDescription = (TextView) v.findViewById(R.id.description_friend);
        backButtonFriend = (ImageView) v.findViewById(R.id.back_button_friend);
        backButtonFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.returnToChat();
            }
        });
        blockButton = v.findViewById(R.id.block_button);
        final Context context = this.getContext();
        blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Cuando el usuario pulse al boton de bloquear se tiene que bloquear tambien mediante retrofit al usuario
                ComunicationServer.getInstance().blockUser(userProfile, context);
                mCallbacks.returnToMainMenu();
            }
        });
        obtainFriendInformation();
        return v;
    }

    public void obtainFriendInformation () {
        //Con este metodo, se seteara la informacion del usuario (supongo que obtendrás la información en formato usuario), la pones en la variable user
        ComunicationServer.getInstance().getUserById(usernameFriend.getText().toString(),this);

    }

    public void updateProfile(UserDTO newProfile) {
        updateDescription(newProfile.getId());
    }

    private void updateDescription(int id) {
        ComunicationServer.getInstance().getProfileById(id,this);
    }


    public void getProfileSuccesful(Profile body) {
        userProfile = body;
        if (body.getDisplayName() != null) {
            usernameFriend.setText(body.getDisplayName());
        }
        if (body.getAboutMe() != null) {
            userDescription.setText(body.getAboutMe());
        }

        if (body.getHeight() == null  || body.getHeight() == 0) {
            userHeight.setVisibility(View.GONE);
        }
        else {
            userHeight.setText("Height:" + body.getHeight().toString());
        }
        if (body.getWeight() == null || body.getWeight() == 0) {
            userWeight.setVisibility(View.GONE);
        }
        else {
            userWeight.setText("Weight:" + body.getWeight().toString());
        }
        if (body.getShowAge() == null || !body.getShowAge()) {
            userAge.setVisibility(View.GONE);
        }
        else {
            userAge.setText("Age:" + body.getBirthDate());
        }
        if (body.getGender() == null || body.getGender().equals("DO NOT SHOW")) {
            userGender.setVisibility(View.GONE);
        }
        else {
            userGender.setText("Gender:" + body.getGender().getType());
        }
        if (body.getPicture() != null) {
            byte[] imageBytes;
            imageBytes = Base64.decode(body.getPicture(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            lastUri = body.getPicture();
            Glide.with(getContext()).asBitmap().load(decodedImage).apply(RequestOptions.circleCropTransform()).into(friendPhoto);
        }
    }

    public void stablishUsername(String usernameFriend) {
        hola = usernameFriend;
    }

    public String getHola() {
        return hola;
    }

    public void setHola(String hola) {
        this.hola = hola;
    }

    public TextView getUsernameFriend() {
        return usernameFriend;
    }

    public void setUsernameFriend(TextView usernameFriend) {
        this.usernameFriend = usernameFriend;
    }

    public String getLastUri() {
        return lastUri;
    }

    public void setLastUri(String lastUri) {
        this.lastUri = lastUri;
    }
}
