package com.example.chv.gestordediscos.Database;

/**
 * Created by CHV on 28/02/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "peliculas.db";
    public static final String TABLE_NAME = "pelicula";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_TITULO = "titulo";
    public static final String CAMPO_DESCRIPCION = "descripcion";
    public static final String CAMPO_URL_IMAGEN = "urlImagen";
    public static final String CAMPO_VALORACION = "valoracion";
    public static final String CAMPO_DURACION = "duracion";
    public static final int DATABASE_VERSION = 1;

    private String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_NAME + "("
                + CAMPO_ID + " integer primary key autoincrement, "
                + CAMPO_TITULO + " text not null, "
                + CAMPO_DESCRIPCION + " text not null, "
                + CAMPO_URL_IMAGEN + " text not null, "
                + CAMPO_VALORACION + " text not null, "
                + CAMPO_DURACION + " text not null"
            + ");";

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(DATABASE_CREATE);
    }
}