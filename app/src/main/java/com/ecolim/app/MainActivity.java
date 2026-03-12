package com.ecolim.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.ecolim.app.activities.RegistroActivity;
import com.ecolim.app.activities.ReportesActivity;

public class MainActivity extends AppCompatActivity {

    Button btnRegistrar, btnReportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnReportes = findViewById(R.id.btnReportes);

        btnRegistrar.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class))
        );

        btnReportes.setOnClickListener(v ->
                startActivity(new Intent(this, ReportesActivity.class))
        );
    }
}