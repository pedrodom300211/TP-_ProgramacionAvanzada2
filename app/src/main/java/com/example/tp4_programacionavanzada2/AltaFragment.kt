package com.example.tp4_programacionavanzada2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.tp4_programacionavanzada2.conexion.DataMainActivity

class AltaFragment : Fragment() {
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_alta, container, false)

        // Inicializar el Spinner
        spinner = view.findViewById(R.id.spinner_Categoria)

        // Pasar el Spinner y el Context a DataMainActivity (Java)
        val dataLoader = DataMainActivity(spinner, requireContext())
        dataLoader.fetchData()

        return view
    }
}