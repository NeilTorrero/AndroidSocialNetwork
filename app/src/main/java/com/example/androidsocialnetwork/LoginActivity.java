package com.example.androidsocialnetwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;

public class LoginActivity extends Activity {
    private boolean existsUser;
    private EditText emailUser;
    private EditText passwordUser;
    private CheckBox checkBox;
    private Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        emailUser = (EditText) findViewById(R.id.username_text_lg);
        passwordUser = (EditText) findViewById(R.id.password_text_lg);
        checkBox = (CheckBox) findViewById(R.id.remember_me);
        signInButton = (Button) findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (startUserSession(emailUser.getText().toString(),passwordUser.getText().toString())) {
                    //We go to main activity
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                //}
                //If the start session have problems we stay in the same session.
            }
        });
    }

    //Return true if everything is OK, but if there is an error return false
    public boolean startUserSession (String email, String password) {
        ComunicationServer cs = new ComunicationServer();
        cs.loginUser(email, password,this);
        return existsUser;
    }

    public void loginCorrect() {
        Toast.makeText(this,"Correct Login!!",Toast.LENGTH_LONG).show();
    }

    public void loginIncorrect() {
        Toast.makeText(this,"Incorrect login!!",Toast.LENGTH_LONG).show();
    }

    public void connectionFailed() {
        Toast.makeText(this,"Connection failed!!",Toast.LENGTH_LONG).show();
    }

    public void setExistsUser(boolean existsUser) {
        this.existsUser = existsUser;
    }
}
