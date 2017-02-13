package com.oolink.exo.persogame.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oolink.exo.persogame.DAO.PersoDAO;
import com.oolink.exo.persogame.R;
import com.oolink.exo.persogame.metier.Personnage;

public class Parametres extends AppCompatActivity {

    private Button deconnect;
    private ImageButton editPseudo;
    private TextView pseudoUp;
    private TextView nameUp;
    SharedPreferences sharedPreferences;
    private PersoDAO data = new PersoDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(CreatePersonnage.MY_PREFERENCES, Context.MODE_PRIVATE);
        String savePseudo = sharedPreferences.getString("monPseudo", "No name defined");
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Personnage personnage = data.getPerso(savePseudo);

        pseudoUp = (TextView) findViewById(R.id.pseudoUp);
        nameUp = (TextView) findViewById(R.id.nameUp);

        pseudoUp.setText(personnage.getPseudo());
        nameUp.setText(personnage.getNom());

        editPseudo=(ImageButton) findViewById(R.id.editPseudo);
        editPseudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=(EditText) pseudoUp;
            }
        });

        deconnect = (Button) findViewById(R.id.deconnect);
        deconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                Parametres.this.startActivity(new Intent(Parametres.this, Accueil.class));

            }
        });
    }

}
