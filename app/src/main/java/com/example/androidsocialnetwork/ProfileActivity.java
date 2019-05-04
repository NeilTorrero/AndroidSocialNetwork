package com.example.androidsocialnetwork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private TextView userDescription;
    private TextView userHeight;
    private TextView userBirthDate;
    private TextView userGender;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userBirthDate = (TextView) findViewById(R.id.birthDate);
        userHeight = (TextView) findViewById(R.id.heightValue);
        userGender = (TextView) findViewById(R.id.genderValue);
        userDescription = (TextView) findViewById(R.id.description);
        btnEdit = (Button) findViewById(R.id.btn_edit);



    }
}
