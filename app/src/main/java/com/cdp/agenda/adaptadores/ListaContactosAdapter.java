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

    public ListaContactosAdapter(ArrayList<Contactos> listaContactos) {
        this.listaContactos = listaContactos;
        this.listaOriginal = new ArrayList<>();
        this.listaOriginal.addAll(listaContactos);
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contactos contacto = listaContactos.get(position);
        holder.bind(contacto);
    }

    public void filtrar(String txtBuscar) {
        if (txtBuscar.isEmpty()) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        } else {
            List<Contactos> filtrados = listaOriginal.stream()
                    .filter(contacto -> contacto.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                    .collect(Collectors.toList());
            listaContactos.clear();
            listaContactos.addAll(filtrados);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {

        private TextView viewNombre, viewTelefono, viewCorreo, viewDireccion, viewDeuda;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);
            viewDireccion = itemView.findViewById(R.id.viewDireccion);
            viewDeuda = itemView.findViewById(R.id.viewDeuda);
        }

        public void bind(final Contactos contacto) {
            viewNombre.setText(contacto.getNombre());
            viewTelefono.setText(contacto.getTelefono());
            viewDireccion.setText(contacto.getDireccion());
            viewDeuda.setText(contacto.getDeuda());

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
