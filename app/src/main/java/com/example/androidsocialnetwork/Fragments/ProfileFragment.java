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
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.R;

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

        username =  v.findViewById(R.id.userName);
        userAge = v.findViewById(R.id.birthDate);
        userAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userAgeText = v.findViewById(R.id.birthDateText);
        userHeight =  v.findViewById(R.id.heightValue);

        userHeightText = v.findViewById(R.id.heightValueText);

        userWeight = v.findViewById(R.id.weightValueText);

        userWeightText = v.findViewById(R.id.weightValue);

        userGender =  v.findViewById(R.id.genderValue);
        userGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bellButton = v.findViewById(R.id.bellNotifications);
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
                   //TODO: Si cambia el bith date y todo eso, cambiar tambien en la plataforma
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

    //TODO: Este metodo será llamado cuando no se quiera mostrar un campo en concreto, por ejemplo cuando haya 0kg (leer condiciones)
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
        //TODO: Metodo que se encaragara de llamar a la funcion de retrofit para adquirir los datos del usuario que estamos ahora mismo y printar la informacion por pantalla

        User user = new User ("nada","nada");
        if (user.getHeight() == 0) {
            hideValue(2);
        }
        else {
            userHeight.setText(""+user.getHeight());
        }
        if (user.getWeight() == 0) {
            hideValue(3);
        }
        else {
            userWeight.setText("" + user.getWeight());
        }
        if (!user.isShowAge()) {
            hideValue(0);
        }
        else {
            userAge.setText(user.getAge());
        }
        if (user.getGender().equals("DO NOT SHOW")) {
            hideValue(1);
        }
        else {
            userGender.setText(user.getGender());
        }
    }
}
