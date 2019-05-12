package com.example.androidsocialnetwork.Fragments;

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
import android.widget.TextView;

import com.example.androidsocialnetwork.R;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {
    private TextView userHeightText;
    private TextView userBirthDateText;
    private TextView userGenderText;
    private EditText userDescription;
    private EditText userHeight;
    private EditText userBirthDate;
    private EditText userGender;
    private TextView username;
    private Button btnEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.activity_profile,container,false);

        username =  v.findViewById(R.id.userName);
        userBirthDate = v.findViewById(R.id.birthDate);
        userBirthDate.addTextChangedListener(new TextWatcher() {
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
        userBirthDateText = v.findViewById(R.id.birthDateText);
        userHeight =  v.findViewById(R.id.heightValue);
        userHeight.addTextChangedListener(new TextWatcher() {
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
        userHeightText = v.findViewById(R.id.heightValueText);


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

        btnEdit = (Button) v.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (btnEdit.getText().equals("Edit")) {
                   //TODO: Si cambia el bith date y todo eso, cambiar tambien en la plataforma
                   userDescription.setFocusableInTouchMode(true);
                   userBirthDate.setFocusableInTouchMode(true);
                   userGender.setFocusableInTouchMode(true);
                   userHeight.setFocusableInTouchMode(true);
                   btnEdit.setText("Done");
               } else {
                   userDescription.setFocusable(false);
                   userBirthDate.setFocusable(false);
                   userGender.setFocusable(false);
                   userHeight.setFocusable(false);
                   btnEdit.setText("Edit");

               }
            }
        });
        userDescription.setFocusable(false);
        userBirthDate.setFocusable(false);
        userGender.setFocusable(false);
        userHeight.setFocusable(false);

        return v;
    }

    //TODO: Este metodo será llamado cuando no se quiera mostrar un campo en concreto, por ejemplo cuando haya 0kg (leer condiciones)
    /*
         Opcion:
         -0:BirthDate
         -1:Gender
         -2:Height

     */
    public void hideValue (int option) {
        switch (option) {
            case 0:
                userBirthDate.setVisibility(View.GONE);
                userBirthDateText.setVisibility(View.GONE);
                break;
            case 1:
                userGender.setVisibility(View.GONE);
                userGenderText.setVisibility(View.GONE);
                break;
            case 2:
                userHeight.setVisibility(View.GONE);
                userHeightText.setVisibility(View.GONE);
                break;
        }
    }
}
