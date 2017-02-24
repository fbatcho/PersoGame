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
 private Button edit;

    private TextView pseudoUp;
    private TextView nameUp;
    private TextView surnameUp;
    private TextView passwordUp;
    private TextView ageUp;

    private EditText updatePseudo;
    private EditText updateName;
    private EditText updateSurname;
    private EditText updatePassword;
    private EditText updateAge;

    private SharedPreferences sharedPreferences;
    private PersoDAO data = new PersoDAO(this);
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(CreatePersonnage.MY_PREFERENCES, Context.MODE_PRIVATE);
        String savePseudo = sharedPreferences.getString("monPseudo", "No name defined");
        editor = sharedPreferences.edit();
        data.open();

        final Personnage personnage = data.getPerso(savePseudo);

        pseudoUp = (TextView) findViewById(R.id.pseudoUp);
        nameUp = (TextView) findViewById(R.id.nameUp);
        surnameUp = (TextView) findViewById(R.id.surnameUp);
        passwordUp = (TextView) findViewById(R.id.passwordUp);
        ageUp = (TextView) findViewById(R.id.ageUp);



        pseudoUp.setText(personnage.getPseudo());
        nameUp.setText(personnage.getNom());
        surnameUp.setText(personnage.getPrenom());
        passwordUp.setText(personnage.getPassword());
        ageUp.setText(personnage.getAge());


        updatePseudo=(EditText) findViewById(R.id.updatePseudo);
        updateName=(EditText) findViewById(R.id.updateName);
        updateSurname=(EditText) findViewById(R.id.updateSurname);
        updatePassword=(EditText) findViewById(R.id.updatePassword);
        updateAge=(EditText) findViewById(R.id.updateAge);


        edit=(Button) findViewById(R.id.modifier);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pseudoUp.setVisibility(View.GONE);
                updatePseudo.setVisibility(View.VISIBLE);
                updatePseudo.setText(personnage.getPseudo());

                nameUp.setVisibility(View.GONE);
                updateName.setVisibility(View.VISIBLE);
                updateName.setText(personnage.getNom());

                surnameUp.setVisibility(View.GONE);
                updateSurname.setVisibility(View.VISIBLE);
                updateSurname.setText(personnage.getPrenom());

                passwordUp.setVisibility(View.GONE);
                updatePassword.setVisibility(View.VISIBLE);
                updatePassword.setText(personnage.getPassword());

                ageUp.setVisibility(View.GONE);
                updateAge.setVisibility(View.VISIBLE);
                updateAge.setText(personnage.getAge());

                edit.setText("Valider");
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
