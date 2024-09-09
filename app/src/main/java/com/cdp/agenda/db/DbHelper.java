package com.cdp.agenda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agenda.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    public static final String TABLE_CONTACTOS = "contactos";
    public static final String COL_ID = "id";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_TELEFONO = "telefono";
    public static final String COL_DIRECCION = "direccion";
    public static final String COL_DEUDA = "deuda";

    // Sentencia SQL para crear la tabla
    private static final String SQL_CREATE_CONTACTOS =
            "CREATE TABLE " + TABLE_CONTACTOS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_NOMBRE + " TEXT," +
                    COL_TELEFONO + " TEXT," +
                    COL_DIRECCION + " TEXT," +
                    COL_DEUDA + " TEXT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes implementar la lógica para actualizar la base de datos si cambia la versión
    }
}

