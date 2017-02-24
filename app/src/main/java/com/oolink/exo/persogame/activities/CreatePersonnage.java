package com.oolink.exo.persogame.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oolink.exo.persogame.DAO.PersoDAO;
import com.oolink.exo.persogame.R;
import com.oolink.exo.persogame.metier.Personnage;

import java.util.List;

public class CreatePersonnage extends AppCompatActivity {

    private EditText pseudo;
    private EditText nom;
    private EditText prenom;
    private EditText age;
    private EditText password;
    private EditText passwordConf;
    private Button valider;

    private PersoDAO datasource;

    private final Context context = this;

    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String PSEUDO = "monPseudo";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_personnage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        })**/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        datasource = new PersoDAO(this);
        datasource.open();

        pseudo = (EditText) this.findViewById(R.id.pseudo);
        nom = (EditText) this.findViewById(R.id.nom);
        prenom = (EditText) this.findViewById(R.id.prenom);
        age = (EditText) this.findViewById(R.id.age);
        password = (EditText) this.findViewById(R.id.password);
        passwordConf = (EditText) this.findViewById(R.id.passwordConf);
        valider = (Button) this.findViewById(R.id.valider);
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(pseudo.getText().toString())) {
                    pseudo.setError("Ce champ est obligatoire");
                } else if (TextUtils.isEmpty(nom.getText().toString())) {
                    nom.setError("Ce champ est obligatoire");
                } else if (TextUtils.isEmpty(prenom.getText().toString())) {
                    prenom.setError("Ce champ est obligatoire");
                } else if (TextUtils.isEmpty(age.getText().toString())) {
                    age.setError("Ce champ est obligatoire");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Ce champ est obligatoire");
                } else if (TextUtils.isEmpty(passwordConf.getText().toString())) {
                    passwordConf.setError("Ce champ est obligatoire");
                }
                //Gestion du mot de passe


                else if (!password.getText().toString().equals(passwordConf.getText().toString())) {
                    password.setError("Assurez-vous que vos deux mot de passe soient identiques");
                    passwordConf.setError("Assurez-vous que vos deux mot de passe soient identiques");
                } else {
                    int ageNumber = Integer.parseInt(age.getText().toString());
                    try {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PSEUDO, pseudo.getText().toString());
                        editor.commit();

                        datasource.createPerso(pseudo.getText().toString(), prenom.getText().toString(), nom.getText().toString(), password.getText().toString(), ageNumber);
                        CreatePersonnage.this.startActivity(new Intent(CreatePersonnage.this, MainActivity.class));

                    } catch (Exception e) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        // set title
                        alertDialogBuilder.setTitle("Erreur crétion de personnage");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("La création a échoué /n Retourner à l'accueil.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        CreatePersonnage.this.startActivity(new Intent(CreatePersonnage.this, Accueil.class));
                                    }
                                })
                                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }

                }

            }
        });
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }


}
