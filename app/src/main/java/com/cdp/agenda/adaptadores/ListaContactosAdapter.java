package com.cdp.agenda.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdp.agenda.R;
import com.cdp.agenda.VerActivity;
import com.cdp.agenda.entidades.Contactos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    private ArrayList<Contactos> listaContactos;
    private ArrayList<Contactos> listaOriginal;
    private Context context;

    // Constructor
    public ListaContactosAdapter(Context context, ArrayList<Contactos> listaContactos) {
        this.context = context;
        this.listaContactos = listaContactos;
        this.listaOriginal = new ArrayList<>();
        this.listaOriginal.addAll(listaContactos);  // Copia de seguridad de los contactos
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout del item del contacto
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        // Obtenemos el contacto actual
        Contactos contacto = listaContactos.get(position);
        holder.bind(contacto);  // Mostramos los datos del contacto
    }

    // Devuelve la cantidad de contactos que hay en la lista
    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    // Método para filtrar los contactos según el texto de búsqueda
    public void filtrar(String txtBuscar) {
        if (txtBuscar.isEmpty()) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        } else {
            ArrayList<Contactos> filtrados = new ArrayList<>();
            for (Contactos contacto : listaOriginal) {
                if (contacto.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                    filtrados.add(contacto);
                }
            }
            listaContactos.clear();
            listaContactos.addAll(filtrados);
        }
        notifyDataSetChanged();  // Notificamos al adaptador que los datos han cambiado
    }

    // ViewHolder que contiene los elementos de cada item de la lista
    public static class ContactoViewHolder extends RecyclerView.ViewHolder {

        private TextView viewNombre, viewTelefono, viewDireccion, viewDeuda;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            // Referenciamos los TextViews del layout item_contacto.xml
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
            viewDireccion = itemView.findViewById(R.id.viewDireccion);
            viewDeuda = itemView.findViewById(R.id.viewDeuda);
        }

        // Método que llena los datos de un contacto en los TextViews
        public void bind(final Contactos contacto) {
            viewNombre.setText(contacto.getNombre());
            viewTelefono.setText(contacto.getTelefono());
            viewDireccion.setText(contacto.getDireccion());
            viewDeuda.setText(contacto.getDeuda());

            // Click listener para abrir VerActivity cuando se haga clic en un contacto
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID", contacto.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
