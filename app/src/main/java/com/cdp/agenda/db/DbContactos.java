package com.cdp.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cdp.agenda.entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper {

    private Context context;
    private static final String TABLE_CONTACTOS = "contactos";

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String nombre, String telefono, String direccion, String deuda) {
        long id = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("direccion", direccion);
            values.put("deuda", deuda);

            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return id;
    }

    public ArrayList<Contactos> mostrarContactos() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Cursor cursorContactos = null;

        try {
            cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " ORDER BY nombre ASC", null);

            if (cursorContactos.moveToFirst()) {
                do {
                    Contactos contacto = new Contactos();
                    contacto.setId(cursorContactos.getInt(0));
                    contacto.setNombre(cursorContactos.getString(1));
                    contacto.setTelefono(cursorContactos.getString(2));
                    contacto.setDireccion(cursorContactos.getString(3));
                    contacto.setDeuda(cursorContactos.getString(4));
                    listaContactos.add(contacto);
                } while (cursorContactos.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursorContactos != null) {
                cursorContactos.close();
            }
            db.close();
        }

        return listaContactos;
    }
    public boolean actualizarDeuda(int id, double nuevaDeuda) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("deuda", String.valueOf(nuevaDeuda));

        int result = db.update(TABLE_CONTACTOS, values, "id=?", new String[]{String.valueOf(id)});
        return result != -1;
    }


    public Contactos verContacto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Contactos contacto = null;
        Cursor cursorContactos = null;

        try {
            cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

            if (cursorContactos.moveToFirst()) {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setDireccion(cursorContactos.getString(3));
                contacto.setDeuda(cursorContactos.getString(4));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursorContactos != null) {
                cursorContactos.close();
            }
            db.close();
        }

        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String telefono, String direccion, String deuda) {
        boolean correcto = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("direccion", direccion);
            values.put("deuda", deuda);

            int rowsAffected = db.update(TABLE_CONTACTOS, values, "id=?", new String[]{String.valueOf(id)});
            correcto = rowsAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean actualizarContacto(Contactos contacto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", contacto.getNombre());
        values.put("telefono", contacto.getTelefono());
        values.put("direccion", contacto.getDireccion());
        values.put("deuda", contacto.getDeuda());

        int result = db.update(TABLE_CONTACTOS, values, "id = ?", new String[]{String.valueOf(contacto.getId())});
        db.close();
        return result > 0;
    }

    public boolean eliminarContacto(int id) {
        boolean correcto = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int rowsDeleted = db.delete(TABLE_CONTACTOS, "id=?", new String[]{String.valueOf(id)});
            correcto = rowsDeleted > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return correcto;
    }
}
