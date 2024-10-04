package com.example.tp4_programacionavanzada2.conexion;

import android.content.Context;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tp4_programacionavanzada2.AltaFragment;
import com.example.tp4_programacionavanzada2.R;
import com.example.tp4_programacionavanzada2.adapter.CategoriaAdapter;
import com.example.tp4_programacionavanzada2.entidad.Articulo;
import com.example.tp4_programacionavanzada2.entidad.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

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


    public void insertarArticulo(Articulo articulo) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                String query = "INSERT INTO articulo (id, nombre, stock, idCategoria) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, articulo.getId());
                stmt.setString(2, articulo.getNombre());
                stmt.setInt(3, articulo.getStock());
                stmt.setInt(4, articulo.getIdCategoria());

                int rowsInserted = stmt.executeUpdate();  // Retorna la cantidad de filas afectadas
                if (rowsInserted > 0) {
                    // Éxito
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        Toast.makeText(context, "Artículo insertado exitosamente.", Toast.LENGTH_SHORT).show();
                    });
                }

                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "Error al insertar el artículo.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }


    public void mostarArticulo(int id, Consumer<Articulo> callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Articulo articulo = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                String query = "SELECT * FROM articulo WHERE id = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int articuloId = rs.getInt("id");
                    int idCategoria = rs.getInt("idCategoria");
                    String nombre = rs.getString("nombre");
                    int stock = rs.getInt("stock");
                    articulo = new Articulo(articuloId, idCategoria, nombre, stock);
                }

                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Llamar al callback con el resultado en el hilo principal
            Articulo finalArticulo = articulo;
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> callback.accept(finalArticulo));
        });
    }
}
