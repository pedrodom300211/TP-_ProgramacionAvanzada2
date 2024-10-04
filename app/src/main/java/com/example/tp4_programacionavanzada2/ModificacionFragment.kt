package com.example.tp4_programacionavanzada2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tp4_programacionavanzada2.conexion.DataMainActivity
import java.util.concurrent.ExecutorService

class ModificacionFragment : Fragment() {

    private lateinit var etIdBuscar: EditText
    private lateinit var etNombre: EditText
    private lateinit var etStock: EditText
    private lateinit var btnBuscar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modificacion, container, false)

        etIdBuscar = view.findViewById(R.id.editTextId)
        etNombre = view.findViewById(R.id.editTextNombre)
        etStock = view.findViewById(R.id.editTextStock)
        btnBuscar = view.findViewById(R.id.btnBuscar)

        btnBuscar.setOnClickListener {
            val id = etIdBuscar.text.toString().toIntOrNull()
            val dataLoader = DataMainActivity(requireActivity().findViewById(R.id.spinner_Categoria), requireContext())

            // Ejecutar en un hilo para no bloquear la UI
            btnBuscar.setOnClickListener {
                val id = etIdBuscar.text.toString().toIntOrNull()
                if (id != null) {
                    val dataLoader = DataMainActivity(requireActivity().findViewById(R.id.spinner_Categoria), requireContext())

                    // Ejecutar la búsqueda del artículo en un hilo separado
                    dataLoader.mostarArticulo(id) { articulo ->
                        // Actualizar la UI en el hilo principal
                        if (articulo != null) {
                            etNombre.setText(articulo.nombre)
                            etStock.setText(articulo.stock.toString())
                        } else {
                            Toast.makeText(requireContext(), "Artículo no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

}