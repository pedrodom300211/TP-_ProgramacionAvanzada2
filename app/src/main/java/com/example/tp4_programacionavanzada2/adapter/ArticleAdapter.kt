package com.example.tp4_programacionavanzada2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tp4_programacionavanzada2.R
import com.example.tp4_programacionavanzada2.entidad.Articulo

class ArticleAdapter(context: Context, private val articles: List<Articulo>) :
    ArrayAdapter<Articulo>(context, 0, articles) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)

        val article = articles[position]


        val textViewNombre = view.findViewById<TextView>(R.id.textViewNombre)
        val textViewStock = view.findViewById<TextView>(R.id.textViewStock)

        textViewNombre.text = article.nombre
        textViewStock.text = "Stock: ${article.stock}"

        return view
    }
}
