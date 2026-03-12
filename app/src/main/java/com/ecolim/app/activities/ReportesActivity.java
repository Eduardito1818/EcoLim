package com.ecolim.app.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ecolim.app.R;
import com.ecolim.app.database.DatabaseHelper;
import com.ecolim.app.models.Residuo;
import java.util.List;

public class ReportesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ResiduoAdapter adapter;
    Spinner spinnerFiltro;
    Button btnFiltrar;
    TextView tvTotal, tvSuma;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        spinnerFiltro = findViewById(R.id.spinnerFiltro);
        btnFiltrar = findViewById(R.id.btnFiltrar);
        tvTotal = findViewById(R.id.tvTotal);
        tvSuma = findViewById(R.id.tvSuma);

        String[] filtros = {"Todos", "Organico", "Plastico", "Metal", "Papel", "Vidrio", "Peligroso"};
        spinnerFiltro.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, filtros));

        setupRecyclerView();
        actualizarEstadisticas("Todos");

        btnFiltrar.setOnClickListener(v -> {
            String seleccion = spinnerFiltro.getSelectedItem().toString();
            List<Residuo> filtrada = seleccion.equals("Todos") ? db.obtenerTodos() : db.filtrarPorTipo(seleccion);
            adapter.actualizarLista(filtrada);
            actualizarEstadisticas(seleccion);
        });
    }

    private void setupRecyclerView() {
        adapter = new ResiduoAdapter(db.obtenerTodos());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(residuo -> {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar Registro")
                    .setMessage("¿Estás seguro de que quieres eliminar este reporte?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        db.eliminarResiduo(residuo.getId());
                        String seleccion = spinnerFiltro.getSelectedItem().toString();
                        adapter.actualizarLista(seleccion.equals("Todos") ? db.obtenerTodos() : db.filtrarPorTipo(seleccion));
                        actualizarEstadisticas(seleccion);
                        Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    private void actualizarEstadisticas(String tipo) {
        List<Residuo> lista = tipo.equals("Todos") ? db.obtenerTodos() : db.filtrarPorTipo(tipo);
        double suma = db.obtenerSumaCantidad(tipo);
        tvTotal.setText("Registros: " + lista.size());
        tvSuma.setText("Total acumulado: " + String.format("%.2f", suma));
    }
}