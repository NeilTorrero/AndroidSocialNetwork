package com.example.androidsocialnetwork.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.Model.Profile;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.R;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {
    private TextView userHeightText;
    private TextView userAgeText;
    private TextView userGenderText;
    private EditText userDescription;
    private EditText userHeight;
    private EditText userAge;
    private EditText userGender;
    private TextView username;
    private Button btnEdit;
    private ImageView bellButton;
    private Callbacks mCallbacks;
    private TextView userWeightText;
    private EditText userWeight;

    private boolean textChanged;

    private Profile myProfile;

    public ProfileFragment() {
    }

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
        View v = inflater.inflate(R.layout.activity_profile,container,false);

        textChanged = false;
        username =  v.findViewById(R.id.userName);
        userAge = v.findViewById(R.id.birth_date);
        username =  v.findViewById(R.id.user_name);
        userAge = v.findViewById(R.id.birth_date);
        userAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userAgeText = v.findViewById(R.id.birth_date_text);
        userHeight =  v.findViewById(R.id.height_value);

        userHeightText = v.findViewById(R.id.height_value_text);

        userWeight = v.findViewById(R.id.weight_value_friend);

        userWeightText = v.findViewById(R.id.weight_value_prof);

        userGender =  v.findViewById(R.id.gender_value);
        userGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userDescription = v.findViewById(R.id.description);
        userDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bellButton = v.findViewById(R.id.bell_notifications);
        bellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.gotoUserSolicitudes();
            }
        });

        btnEdit = (Button) v.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (btnEdit.getText().equals("Edit")) {

                   if (textChanged) {
                       textChanged = false;
                       ComunicationServer conn = new ComunicationServer();
                       conn.updateMyProfile(userAge.toString(), userGender.toString(), userHeight.getHeight(), userDescription.toString(), ProfileFragment.this);
                   }
                   userDescription.setFocusableInTouchMode(true);
                   userAge.setFocusableInTouchMode(true);
                   userGender.setFocusableInTouchMode(true);
                   userHeight.setFocusableInTouchMode(true);
                   btnEdit.setText("Done");
               } else {
                   userDescription.setFocusable(false);
                   userAge.setFocusable(false);
                   userGender.setFocusable(false);
                   userHeight.setFocusable(false);
                   btnEdit.setText("Edit");

               }
            }
        });
        userDescription.setFocusable(false);
        userAge.setFocusable(false);
        userGender.setFocusable(false);
        userHeight.setFocusable(false);

        return v;
    }


    /*
         Opcion:
         -  0: Age
         -  1:Gender
         -  2:Height
         -  3:Weight

     */
    public void hideValue (int option) {
        switch (option) {
            case 0:
                userAge.setVisibility(View.GONE);
                userAgeText.setVisibility(View.GONE);
                break;
            case 1:
                userGender.setVisibility(View.GONE);
                userGenderText.setVisibility(View.GONE);
                break;
            case 2:
                userHeight.setVisibility(View.GONE);
                userHeightText.setVisibility(View.GONE);
                break;
            case 3:
                userWeight.setVisibility(View.GONE);
                userWeightText.setVisibility(View.GONE);
                break;
        }
    }

    public void obtainUserInformation () {
        //Metodo que se encaragara de llamar a la funcion de retrofit para adquirir los datos del usuario que estamos ahora mismo y printar la informacion por pantalla
        ComunicationServer conn = new ComunicationServer();
        conn.getMyProfile(this); // Aquesta funcio al obtenir el profile cridara a updateProfile d'aquest mateix fragment
        //TODO: La informacion adquirida la pones en la variable inferior llamada user, yo ya me encaragare de gestionar la informacion

        if (myProfile.getHeight() == 0) {
            hideValue(2);
        }
        else {
            userHeight.setText(""+myProfile.getHeight());
        }
        if (myProfile.getWeight() == 0) {
            hideValue(3);
        }
        else {
            userWeight.setText("" + myProfile.getWeight());
        }
        if (!myProfile.getShowAge()) {
            hideValue(0);
        }
        else {
            userAge.setText(myProfile.getBirthDate());
        }
        if (myProfile.getGender().equals("DO NOT SHOW")) {
            hideValue(1);
        }
        else {
            userGender.setText(myProfile.getGender().getType());
        }

        //TODO: PARA PEPE, MIRAR SI QUIERE UNAS UNIDADES CONCRETAS Y PONERLAS JUNTO A LOS KG POR EJEMPLO
    }

    public void updateProfile(Profile newProfile) {
        myProfile = newProfile;
    }

    public Profile getMyProfile() {
        return myProfile;
    }
}
