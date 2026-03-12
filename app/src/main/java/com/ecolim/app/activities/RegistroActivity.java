package com.ecolim.app.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.ecolim.app.R;
import com.ecolim.app.database.DatabaseHelper;
import com.ecolim.app.models.Residuo;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    Spinner spinnerTipo, spinnerUnidad;
    TextInputEditText etCantidad, etUbicacion, etTrabajador;
    Button btnGuardar, btnLimpiar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = new DatabaseHelper(this);

        spinnerTipo = findViewById(R.id.spinnerTipo);
        spinnerUnidad = findViewById(R.id.spinnerUnidad);
        etCantidad = findViewById(R.id.etCantidad);
        etUbicacion = findViewById(R.id.etUbicacion);
        etTrabajador = findViewById(R.id.etTrabajador);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnLimpiar = findViewById(R.id.btnLimpiar);

        String[] tipos = {"Organico", "Plastico", "Metal", "Papel", "Vidrio", "Peligroso", "Otro"};
        String[] unidades = {"kg", "litros", "unidades", "m3"};

        spinnerTipo.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, tipos));
        spinnerUnidad.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, unidades));

        btnGuardar.setOnClickListener(v -> guardarResiduo());
        btnLimpiar.setOnClickListener(v -> limpiarFormulario());
    }

    private void limpiarFormulario() {
        etCantidad.setText("");
        etUbicacion.setText("");
        etTrabajador.setText("");
        spinnerTipo.setSelection(0);
        spinnerUnidad.setSelection(0);
        etCantidad.requestFocus();
        Toast.makeText(this, "Formulario reiniciado", Toast.LENGTH_SHORT).show();
    }

    private void guardarResiduo() {
        String cantidadStr = etCantidad.getText().toString().trim();
        String ubicacion = etUbicacion.getText().toString().trim();
        String trabajador = etTrabajador.getText().toString().trim();

        if (cantidadStr.isEmpty() || ubicacion.isEmpty() || trabajador.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double cantidad;
        try {
            cantidad = Double.parseDouble(cantidadStr);
        } catch (NumberFormatException e) {
            etCantidad.setError("Cantidad no válida");
            return;
        }

        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date());

        Residuo r = new Residuo(
                spinnerTipo.getSelectedItem().toString(),
                cantidad,
                spinnerUnidad.getSelectedItem().toString(),
                ubicacion, fecha, trabajador
        );

        long id = db.insertarResiduo(r);
        if (id > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("¡Éxito!")
                    .setMessage("Residuo guardado correctamente")
                    .setPositiveButton("Aceptar", (dialog, which) -> finish())
                    .show();
        } else {
            Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }
}