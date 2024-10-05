package com.example.tp4_programacionavanzada2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.tp4_programacionavanzada2.adapter.ArticleAdapter
import com.example.tp4_programacionavanzada2.conexion.DataMainActivity
import com.example.tp4_programacionavanzada2.entidad.Articulo

class ListadoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listado, container, false)
        val listViewProductos = view.findViewById<ListView>(R.id.listViewProductos)


        val dataMainActivity = DataMainActivity(null, requireContext())


        dataMainActivity.obtenerArticulos { listaArticulos ->
            if (listaArticulos.isNotEmpty()) {

                val adapter = ArticleAdapter(requireContext(), listaArticulos)
                listViewProductos.adapter = adapter
            }
        }

        return view
    }
    override fun onResume() {
        super.onResume()


        val dataMainActivity = DataMainActivity(null, requireContext())
        dataMainActivity.obtenerArticulos { listaArticulos ->
            if (listaArticulos.isNotEmpty()) {
                val adapter = ArticleAdapter(requireContext(), listaArticulos)
                val listViewProductos = view?.findViewById<ListView>(R.id.listViewProductos)
                listViewProductos?.adapter = adapter
            }
        }
    }
}
