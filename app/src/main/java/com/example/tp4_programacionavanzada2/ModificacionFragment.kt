package com.example.tp4_programacionavanzada2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tp4_programacionavanzada2.conexion.DataMainActivity
import com.example.tp4_programacionavanzada2.entidad.Articulo
import com.example.tp4_programacionavanzada2.entidad.Categoria

class ModificacionFragment : Fragment() {

    private lateinit var etIdBuscar: EditText
    private lateinit var etNombre: EditText
    private lateinit var etStock: EditText
    private lateinit var btnBuscar: Button
    private lateinit var btnModificar: Button
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modificacion, container, false)

        etIdBuscar = view.findViewById(R.id.editTextId)
        etNombre = view.findViewById(R.id.editTextNombre)
        etStock = view.findViewById(R.id.editTextStock)
        spinner = view.findViewById(R.id.spinnerCategoria)
        btnBuscar = view.findViewById(R.id.btnBuscar)
        btnModificar = view.findViewById(R.id.btnModificar)

        val dataLoader = DataMainActivity(spinner, requireContext())
        dataLoader.fetchData()

        btnBuscar.setOnClickListener {
            val id = etIdBuscar.text.toString().toIntOrNull()
            if (id != null) {
                dataLoader.mostarArticulo(id) { articulo ->
                    if (articulo != null) {
                        etNombre.setText(articulo.nombre)
                        etStock.setText(articulo.stock.toString())

                        val categoriaLista = spinner.adapter
                        if (categoriaLista != null) {
                            for (i in 0 until categoriaLista.count) {
                                val categoria = categoriaLista.getItem(i) as Categoria
                                if (categoria.id == articulo.idCategoria) {
                                    spinner.setSelection(i)
                                    break
                                }
                            }
                        }

                    } else {
                        Toast.makeText(requireContext(), "Artículo no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }


        btnModificar.setOnClickListener {
            val ID = etIdBuscar.text.toString().toIntOrNull()
            val nombre = etNombre.text.toString()
            val stock = etStock.text.toString().toIntOrNull()
            val categoriaSeleccionada = spinner.selectedItem as? Categoria

            if (ID != null && nombre.isNotEmpty() && stock != null && categoriaSeleccionada != null) {
                val articulo = Articulo(ID, categoriaSeleccionada.id, nombre, stock)
                dataLoader.modificarArticulo(articulo)
            } else {
                Toast.makeText(context, "Datos inválidos. Verifique los campos.", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }
}
