package com.cdp.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.cdp.agenda.adaptadores.ListaContactosAdapter;
import com.cdp.agenda.db.DbContactos;
import com.cdp.agenda.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView txtBuscar; // SearchView para buscar contactos
    private RecyclerView listaContactos; // RecyclerView para mostrar la lista de contactos
    private ArrayList<Contactos> listaArrayContactos; // Lista que contiene los contactos
    private FloatingActionButton fabNuevo; // Botón flotante para agregar nuevo contacto
    private ListaContactosAdapter adapter; // Adaptador para manejar la lista de contactos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa los elementos de la UI
        txtBuscar = findViewById(R.id.txtBuscar);
        listaContactos = findViewById(R.id.listaContactos);
        fabNuevo = findViewById(R.id.favNuevo);

        // Configura el RecyclerView
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        // Cargar contactos desde la base de datos
        DbContactos dbContactos = new DbContactos(MainActivity.this);
        listaArrayContactos = dbContactos.mostrarContactos(); // Cargar todos los contactos

        // Verificar si hay contactos en la base de datos
        if (listaArrayContactos.size() > 0) {
            // Si hay contactos, inicializamos el adaptador
            adapter = new ListaContactosAdapter(MainActivity.this, listaArrayContactos);
            listaContactos.setAdapter(adapter); // Conectamos el adaptador al RecyclerView
        } else {
            // Si no hay contactos, mostramos un mensaje con el contexto correcto
            Toast.makeText(MainActivity.this, "No hay contactos", Toast.LENGTH_LONG).show();
        }

        // Botón para agregar un nuevo contacto
        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro(); // Método para iniciar la actividad de nuevo contacto
            }
        });

        // Agregar el listener para la búsqueda de contactos
        txtBuscar.setOnQueryTextListener(this);
    }

    // Carga el menú en la actividad
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    // Manejo de selección de opciones en el menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuNuevo:
                nuevoRegistro(); // Ir a la pantalla de nuevo registro
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Método para iniciar la actividad de nuevo contacto
    private void nuevoRegistro() {
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }

    // Métodos para la búsqueda de contactos
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false; // No hacemos nada cuando el usuario presiona el botón de búsqueda
    }

    @Override
    public boolean onQueryTextChange(String s) {
        // Filtrar los resultados del adaptador según el texto ingresado en el SearchView
        adapter.filtrar(s);
        return false;
    }
}
