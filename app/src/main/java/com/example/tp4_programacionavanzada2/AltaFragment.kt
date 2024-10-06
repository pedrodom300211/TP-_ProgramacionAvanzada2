package com.example.tp4_programacionavanzada2

import android.os.Bundle
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

class AltaFragment : Fragment() {
    private lateinit var spinner: Spinner
    private lateinit var IDEditText: EditText
    private lateinit var nombreEditText: EditText
    private lateinit var stockEditText: EditText
    private lateinit var btnGuardar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_alta, container, false)

        // Inicializar el Spinner
        spinner = view.findViewById(R.id.spinner_Categoria)
        IDEditText = view.findViewById(R.id.ET_ID)
        nombreEditText = view.findViewById(R.id.ET_NombreProducto)
        stockEditText = view.findViewById(R.id.ET_Stock)
        btnGuardar = view.findViewById(R.id.btnAgregar)
        // Pasar el Spinner y el Context a DataMainActivity (Java)
        val dataLoader = DataMainActivity(spinner, requireContext())
        dataLoader.fetchData()

        btnGuardar.setOnClickListener {
            val ID = IDEditText.text.toString().toIntOrNull()
            val nombre = nombreEditText.text.toString()
            val stock = stockEditText.text.toString().toIntOrNull()
            val categoriaSeleccionada = spinner.selectedItem as Categoria

            if (ID == null || nombre.isEmpty() || stock == null) {
                Toast.makeText(context, "Por favor, complete todos los campos correctamente.", Toast.LENGTH_SHORT).show()
            } else if (categoriaSeleccionada.id == 0) {
                Toast.makeText(context, "Por favor, seleccione una categoría válida.", Toast.LENGTH_SHORT).show()
            } else if (stock < 0) {
                Toast.makeText(context, "El stock debe ser mayor o igual a 0.", Toast.LENGTH_SHORT).show()
            } else {
                dataLoader.validarArticulo(ID) { existe ->
                    if (existe) {
                        Toast.makeText(context, "El id ingresado ya existe.", Toast.LENGTH_SHORT).show()
                    } else {
                        val articulo = Articulo(ID, categoriaSeleccionada.id, nombre, stock)
                        dataLoader.insertarArticulo(articulo)
                        Toast.makeText(context, "Artículo guardado exitosamente.", Toast.LENGTH_SHORT).show()
                        IDEditText.text.clear()
                        nombreEditText.text.clear()
                        stockEditText.text.clear()
                        spinner.setSelection(0)
                    }
                }
            }
        }


        return view
    }
}