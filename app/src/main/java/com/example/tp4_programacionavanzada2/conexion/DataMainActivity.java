package com.example.tp4_programacionavanzada2.conexion;

import android.content.Context;
import android.widget.Spinner;

import com.example.tp4_programacionavanzada2.AltaFragment;
import com.example.tp4_programacionavanzada2.R;
import com.example.tp4_programacionavanzada2.adapter.CategoriaAdapter;
import com.example.tp4_programacionavanzada2.entidad.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataMainActivity {
    private Spinner spinner_cat;
    private Context context;

    public DataMainActivity (Spinner sp, Context co){
        spinner_cat = sp;
        context = co;
    }

    public void fetchData(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Categoria> listaCategorias = new ArrayList<>();
            listaCategorias.add(0, new Categoria(0, "--Seleccionar--"));
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM categoria");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("descripcion");
                    listaCategorias.add(new Categoria(id, nombre));  // Asegúrate de que el constructor de Categoria tenga estos parámetros
                }


                rs.close();;
                st.close();;
                con.close();;


            }catch (Exception e){
                e.printStackTrace();
            }

            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                CategoriaAdapter adapter = new CategoriaAdapter(context, listaCategorias);
                spinner_cat.setAdapter(adapter);
            });
        });
    }
}
