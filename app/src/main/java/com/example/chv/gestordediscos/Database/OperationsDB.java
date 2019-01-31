package com.example.chv.gestordediscos.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chv.gestordediscos.Model.Pelicula;

import java.util.ArrayList;

/**
 * Created by CHV on 28/02/2018.
 */

public class OperationsDB {
    private MySqliteHelper dbHelper;
    private SQLiteDatabase database;
    private String[] allFields = {
            MySqliteHelper.CAMPO_ID,
            MySqliteHelper.CAMPO_TITULO,
            MySqliteHelper.CAMPO_DESCRIPCION,
            MySqliteHelper.CAMPO_URL_IMAGEN,
            MySqliteHelper.CAMPO_VALORACION,
            MySqliteHelper.CAMPO_DURACION
    };


    public OperationsDB(Context context)

    {
        dbHelper = new MySqliteHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Pelicula createPelicula(String titulo, String descripcion, String urlImagen, String valoracion, String duracion) {
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.CAMPO_TITULO, titulo);
        values.put(MySqliteHelper.CAMPO_DESCRIPCION, descripcion);
        values.put(MySqliteHelper.CAMPO_URL_IMAGEN, urlImagen);
        values.put(MySqliteHelper.CAMPO_VALORACION, valoracion);
        values.put(MySqliteHelper.CAMPO_DURACION, duracion);
        long insertId = database.insert(MySqliteHelper.TABLE_NAME, null, values);
        Pelicula nuevaPelicula = new Pelicula(insertId, titulo, descripcion, urlImagen, valoracion, duracion);
        return nuevaPelicula;
    }

    public void updateDB (String[] changedArray){
        database.delete(MySqliteHelper.TABLE_NAME,null,changedArray);


    }

    public void deletePelicula(Pelicula peliculaToDelete) {
        database.delete(MySqliteHelper.TABLE_NAME, MySqliteHelper.CAMPO_ID + " = " + peliculaToDelete.getId(), null);
    }

    public void updatePelicula(Pelicula peliculaToUpdate, String titulo, String descripcion, String urlImagen, String valoracion, String duracion) {
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.CAMPO_TITULO, titulo);
        values.put(MySqliteHelper.CAMPO_DESCRIPCION, descripcion);
        values.put(MySqliteHelper.CAMPO_URL_IMAGEN, urlImagen);
        values.put(MySqliteHelper.CAMPO_VALORACION, valoracion);
        values.put(MySqliteHelper.CAMPO_DURACION, duracion);
        database.update(MySqliteHelper.TABLE_NAME, values, MySqliteHelper.CAMPO_ID + " = " + peliculaToUpdate.getId(), null);
    }

    public ArrayList<Pelicula> getAllPeliculas() {
        ArrayList<Pelicula> listaToReturn = new ArrayList<Pelicula>();
        Cursor cursor = database.query(MySqliteHelper.TABLE_NAME, allFields, null, null, null, null, null);
        cursor.moveToFirst();
        Pelicula peliculaAux = null;
        while (!cursor.isAfterLast()) {
            peliculaAux = cursorToPelicula(cursor);
            listaToReturn.add(peliculaAux);
            cursor.moveToNext();
        }
        cursor.close();
        return listaToReturn;
    }

    public Pelicula getPelicula(Long idPelicula) {
        Cursor cursor = database.query(MySqliteHelper.TABLE_NAME, allFields, MySqliteHelper.CAMPO_ID + " = " + idPelicula, null, null, null, null);
        cursor.moveToFirst();
        Pelicula peliculaAux = null;
        if (!cursor.isAfterLast()) {
            peliculaAux = cursorToPelicula(cursor);
        }
        cursor.close();
        return peliculaAux;
    }

    public Pelicula getUltimaPelicula() {
        Cursor cursor = database.rawQuery("SELECT "
                + MySqliteHelper.CAMPO_ID + ","
                + MySqliteHelper.CAMPO_TITULO + ","
                + MySqliteHelper.CAMPO_DESCRIPCION + ","
                + MySqliteHelper.CAMPO_URL_IMAGEN + ","
                + MySqliteHelper.CAMPO_VALORACION + ","
                + MySqliteHelper.CAMPO_DURACION + " FROM "
                + MySqliteHelper.TABLE_NAME + " WHERE "
                + MySqliteHelper.CAMPO_ID + " = (SELECT MAX("
                + MySqliteHelper.CAMPO_ID + ") FROM "
                + MySqliteHelper.TABLE_NAME + ")", null);
        cursor.moveToFirst();
        Pelicula peliculaAux = null;
        if (!cursor.isAfterLast()) {
            peliculaAux = cursorToPelicula(cursor);
        }
        cursor.close();
        return peliculaAux;
    }

    private Pelicula cursorToPelicula(Cursor cursor) {
        long idPelicula = cursor.getLong(0);
        String titulo = cursor.getString(1);
        String descripcion = cursor.getString(2);
        String urlImagen = cursor.getString(3);
        String valoracion = cursor.getString(4);
        String duracion = cursor.getString(5);
        Pelicula peliculaToReturn = new Pelicula(idPelicula, titulo, descripcion, urlImagen, valoracion, duracion);
        return peliculaToReturn;
    }
}
