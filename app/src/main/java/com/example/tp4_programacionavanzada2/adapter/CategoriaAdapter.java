package com.example.tp4_programacionavanzada2.adapter;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.example.tp4_programacionavanzada2.entidad.Categoria;

public class CategoriaAdapter extends ArrayAdapter<Categoria> {

    private Context context;
    private List<Categoria> categorias;

    public CategoriaAdapter(Context context, List<Categoria> categorias) {
        super(context, android.R.layout.simple_spinner_item, categorias);
        this.context = context;
        this.categorias = categorias;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el objeto Categoria para esta posición
        Categoria categoria = categorias.get(position);

        // Inflar el layout si es necesario
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        // Establecer el texto con el nombre de la categoría
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(categoria.getDescripcion());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Obtener el objeto Categoria para esta posición
        Categoria categoria = categorias.get(position);

        // Inflar el layout si es necesario
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        // Establecer el texto con el nombre de la categoría
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(categoria.getDescripcion());

        if (categoria.getDescripcion().equals("--Seleccionar--")) {
            textView.setEnabled(false); // Deshabilitar la opción
            textView.setAlpha(0.5f); // Hacerla menos visible
        } else {
            textView.setEnabled(true);
            textView.setAlpha(1.0f); // Restaurar visibilidad
        }

        return convertView;
    }

}
