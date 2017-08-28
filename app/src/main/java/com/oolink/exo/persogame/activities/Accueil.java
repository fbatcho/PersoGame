package com.oolink.exo.persogame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oolink.exo.persogame.DAO.PersoDAO;
import com.oolink.exo.persogame.R;

public class Accueil extends AppCompatActivity {

    private Button button_create;
    private Button button_acces;
    TextView txt;
    private PersoDAO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button_create = (Button) this.findViewById(R.id.button_create);
        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Accueil.this.startActivity(new Intent(Accueil.this, CreatePersonnage.class));
            }
        });

        button_acces = (Button) this.findViewById(R.id.button_acces);

        button_acces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Accueil.this.startActivity(new Intent(Accueil.this, ConnectPerso.class));

            }
        });


    }

    @Override
    protected void onPause() {
        //data.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
//        data.open();
        super.onResume();
    }


}
