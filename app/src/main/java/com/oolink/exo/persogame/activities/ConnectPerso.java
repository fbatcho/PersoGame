package com.oolink.exo.persogame.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oolink.exo.persogame.DAO.PersoDAO;
import com.oolink.exo.persogame.R;
import com.oolink.exo.persogame.metier.Personnage;

import java.util.List;

public class ConnectPerso extends AppCompatActivity {

    private EditText usePseudo;
    private EditText usePassword;
    private Button connect;

    private PersoDAO data;

    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_perso);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = new PersoDAO(this);
        data.open();

        usePseudo = (EditText) findViewById(R.id.usePseudo);
        usePassword = (EditText) findViewById(R.id.usePassword);
        connect = (Button) findViewById(R.id.connect);
        sharedPreferences = getSharedPreferences(CreatePersonnage.MY_PREFERENCES, Context.MODE_PRIVATE);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Personnage personnage = data.searchPerso(usePseudo.getText().toString(), usePassword.getText().toString());
                if (personnage == null) {
                    usePseudo.setError("Pseudo et/ou mot de passe inconnu");
                    usePassword.setError("Pseudo et/ou mot de passe inconnu");
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(CreatePersonnage.PSEUDO, usePseudo.getText().toString());
                    editor.commit();
                    ConnectPerso.this.startActivity(new Intent(ConnectPerso.this, MainActivity.class));

                }

            }
        });

    }

    @Override
    protected void onPause() {
        data.close();
        super.onPause();

    }

    @Override
    protected void onResume() {
        data.open();
        super.onResume();
    }

}
