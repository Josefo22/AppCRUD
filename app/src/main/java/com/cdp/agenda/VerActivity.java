package com.cdp.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cdp.agenda.db.DbContactos;
import com.cdp.agenda.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtDireccion, txtDeuda, txtCantidadCambio;
    Button btnGuarda, btnSumarDeuda, btnRestarDeuda;
    FloatingActionButton fabEditar, fabEliminar;

    Contactos contacto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtDeuda = findViewById(R.id.txtDeuda);
        txtCantidadCambio = findViewById(R.id.txtCantidadCambio);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnSumarDeuda = findViewById(R.id.btnSumarDeuda);
        btnRestarDeuda = findViewById(R.id.btnRestarDeuda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbContactos dbContactos = new DbContactos(VerActivity.this);
        contacto = dbContactos.verContacto(id);

        if (contacto != null) {
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtDireccion.setText(contacto.getDireccion());
            txtDeuda.setText(contacto.getDeuda());
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtTelefono.setInputType(InputType.TYPE_NULL);
            txtDireccion.setInputType(InputType.TYPE_NULL);
            txtDeuda.setInputType(InputType.TYPE_NULL);
        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (dbContactos.eliminarContacto(id)) {
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Do nothing
                            }
                        }).show();
            }
        });

        btnSumarDeuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDeuda(true);
            }
        });

        btnRestarDeuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDeuda(false);
            }
        });
    }

    private void actualizarDeuda(boolean sumar) {
        String deudaStr = txtDeuda.getText().toString();
        String cambioStr = txtCantidadCambio.getText().toString();

        if (deudaStr.isEmpty() || cambioStr.isEmpty()) {
            Toast.makeText(this, "La deuda y la cantidad a cambiar no pueden estar vacías", Toast.LENGTH_SHORT).show();
            return;
        }

        double deuda = Double.parseDouble(deudaStr);
        double cambio = Double.parseDouble(cambioStr);

        if (sumar) {
            deuda += cambio;
        } else {
            deuda -= cambio;
            if (deuda < 0) {
                deuda = 0; // Evita que la deuda sea negativa
            }
        }

        txtDeuda.setText(String.format("%.2f", deuda));

        // Actualizar en la base de datos
        DbContactos dbContactos = new DbContactos(VerActivity.this);
        contacto.setDeuda(String.format("%.2f", deuda));
        if (dbContactos.actualizarContacto(contacto)) {
            Toast.makeText(this, "Deuda actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar deuda", Toast.LENGTH_SHORT).show();
        }
    }

    private void lista() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
