package com.example.androidsocialnetwork;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        final EditText email = (EditText) findViewById(R.id.username_text_reg);
        final EditText password = (EditText) findViewById(R.id.password_text_reg);
        final EditText password2 = (EditText) findViewById(R.id.repeate_password_text_reg);
        Button registerButton = (Button) findViewById(R.id.regis_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.equals(password2)) {
                    registerUser (email.getText().toString(),password.getText().toString());
                    Intent i = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                }
                else {

                }
            }
        });
    }

    public void registerUser (String email, String password) {
        //TODO: Retrofit have to register the user
    }
}
