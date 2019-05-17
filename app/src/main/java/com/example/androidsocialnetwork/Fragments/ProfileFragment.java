package com.example.androidsocialnetwork.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.Model.Gender;
import com.example.androidsocialnetwork.Model.Profile;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.R;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private TextView userHeightText;
    private TextView userAgeText;
    private EditText userDescription;
    private EditText userHeight;
    private EditText userAge;
    private TextView userGenderText;
    private TextView username;
    private Button btnEdit;
    private ImageView bellButton;
    private Callbacks mCallbacks;
    private TextView userWeightText;
    private EditText userWeight;
    private Spinner userGender;
    private ImageView profilePhoto;

    private boolean textChanged;
    private List<String> genderStrings;

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

        genderStrings = new ArrayList<>();
        ComunicationServer.getInstance().getAllGenders(this); // Demanem la info del Genders


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

        profilePhoto = v.findViewById(R.id.profile_pic);
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 69);
            }
        });
        userGenderText = v.findViewById(R.id.gender_value_text);
        userAgeText = v.findViewById(R.id.birth_date_text);
        userHeight =  v.findViewById(R.id.height_value);
        userHeightText = v.findViewById(R.id.height_value_text);
        userWeight = v.findViewById(R.id.weight_value_prof);
        userWeightText = v.findViewById(R.id.weight_value_text_prof);

        userGender =  (Spinner)v.findViewById(R.id.gender_value);

        userGender.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textChanged = true;
                userGender.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                       conn.updateMyProfile(userAge.toString(), userGender.getSelectedItem().toString(), userHeight.getHeight(), userDescription.toString(), ProfileFragment.this);
                   }
                   userDescription.setFocusableInTouchMode(true);
                   userAge.setFocusableInTouchMode(true);
                   userGender.setFocusable(true);
                   userGender.setClickable(true);
                   userHeight.setFocusableInTouchMode(true);
                   userWeight.setFocusableInTouchMode(true);
                   userWeight.setFocusable(true);
                   userGender.setEnabled(true);
                   btnEdit.setText("Done");
                   profilePhoto.setEnabled(true);
               } else {
                   userDescription.setFocusable(false);
                   userAge.setFocusable(false);
                   userGender.setClickable(false);
                   userHeight.setFocusable(false);
                   userWeight.setFocusable(false);
                   userWeight.setFocusableInTouchMode(false);
                   btnEdit.setText("Edit");

               }
            }
        });
        profilePhoto.setEnabled(false);
        userDescription.setFocusable(false);
        userAge.setFocusable(false);
        userGender.setFocusable(false);
        userGender.setClickable(false);
        userGender.setEnabled(false);
        userHeight.setFocusable(false);
        userWeight.setFocusable(false);
        userWeight.setFocusableInTouchMode(false);
        userGender.setClickable(false);

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
        ComunicationServer.getInstance().getMyProfile(this); // Aquesta funcio al obtenir el profile cridara a updateProfile d'aquest mateix fragment
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
            userGender.setSelection(myProfile.getGender().getId());
        }

        //TODO: PARA PEPE, MIRAR SI QUIERE UNAS UNIDADES CONCRETAS Y PONERLAS JUNTO A LOS KG POR EJEMPLO
    }

    public void updateProfile(Profile newProfile) {
        myProfile = newProfile;
    }

    public void getAllGenders(ArrayList<Gender> genders) {
        for (Gender g: genders) {
            genderStrings.add(g.getType());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, genderStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        userGender.setAdapter(adapter);
        if (genderStrings.size() > 0) {
            userGender.setSelection(0);
            userGender.setFocusable(false);
            userGender.setClickable(false);
            userGender.setEnabled(false);
        }
    }

    public Profile getMyProfile() {
        return myProfile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == 69){
            Uri imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);
            //extraemos el drawable en un bitmap
            Glide.with(getContext()).load(imageUri).apply(RequestOptions.circleCropTransform()).into(profilePhoto);
        }
    }

}
