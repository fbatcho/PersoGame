package com.oolink.exo.persogame.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.oolink.exo.persogame.database.MySQLiteHelper;
import com.oolink.exo.persogame.metier.Personnage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanny on 07/02/2017.
 */

public class PersoDAO {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper; //Classe de creation de la BDD
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PSEUDO,
            MySQLiteHelper.COLUMN_PRENOM,
            MySQLiteHelper.COLUMN_NOM,
            MySQLiteHelper.COLUMN_PASSWORD,
            MySQLiteHelper.COLUMN_AGE}; //Tableau de toutes les colonnes de la BDD


    public PersoDAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createPerso(String pseudo, String prenom, String nom, String password, int age) throws Exception {
        try {


            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_PSEUDO, pseudo);
            values.put(MySQLiteHelper.COLUMN_PRENOM, prenom);
            values.put(MySQLiteHelper.COLUMN_NOM, nom);
            values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
            values.put(MySQLiteHelper.COLUMN_AGE, age);
            database.insert(MySQLiteHelper.TABLE, null, values);
        } catch (Exception e) {
            System.out.println("Erreur de création");
        }
    }

    /**
     * public void updatePseudo(String pseudo){
     * ContentValues value = new ContentValues();
     * value.put(pseudo,MySQLiteHelper.COLUMN_PSEUDO);
     * database.update(MySQLiteHelper.TABLE,"value",MySQLiteHelper.COLUMN_ID+'='+);
     * }
     **/

    public void deletePerso(Personnage perso) throws Exception {
        try {
            long id = perso.getId();
            database.delete(MySQLiteHelper.TABLE, MySQLiteHelper.COLUMN_ID + '=' + id, null);
        } catch (Exception e) {
            System.out.println("La Suppression a échoué");
        }

    }

    public void deleteAllPerso() throws Exception {
        try {
            database.execSQL("DELETE * FROM " + MySQLiteHelper.TABLE);
        } catch (Exception e) {
            System.out.println("La Suppression a échoué");
        }
    }

    public Personnage getPerso(String pseudo) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE, allColumns, MySQLiteHelper.COLUMN_PSEUDO + "='" + pseudo + "'", null, null, null, null);
        cursor.moveToFirst();
        Personnage perso = new Personnage(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
        cursor.close();
        return perso;
    }

    public List<Personnage> getAllPerso() {
        List<Personnage> personnages = new ArrayList<Personnage>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Personnage perso = new Personnage(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
            personnages.add(perso);
            cursor.moveToNext();
        }
        cursor.close();
        return personnages;
    }

    public boolean isExistPseudo(String pseudo) {
        if (this.getPerso(pseudo) == null) {
            return false;
        }
        return true;
    }

    public Personnage searchPerso(String pseudo, String password) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE, allColumns, MySQLiteHelper.COLUMN_PSEUDO + "='" + pseudo + "'" + " and " + MySQLiteHelper.COLUMN_PASSWORD + "='" + password + "'", null, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        } else {
            cursor.moveToFirst();
            Personnage perso = new Personnage(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
            cursor.close();
            return perso;
        }
    }

}
