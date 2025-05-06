package com.example.vehiculosgetcorp.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vehiculosgetcorp.Models.Vehiculo;
import com.example.vehiculosgetcorp.R;

public class VehiculoDetalleView extends AppCompatActivity {

    private TextView tvMarcaDetalle, tvModeloDetalle, tvAnioDetalle, tvPrecioDetalle;
    private Button btnVolverDetalle;
    private Vehiculo vehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_detalle);

        // Inicializar vistas
        tvMarcaDetalle = findViewById(R.id.tvMarcaDetalle);
        tvModeloDetalle = findViewById(R.id.tvModeloDetalle);
        tvAnioDetalle = findViewById(R.id.tvAnioDetalle);
        tvPrecioDetalle = findViewById(R.id.tvPrecioDetalle);
        btnVolverDetalle = findViewById(R.id.btnVolverDetalle);

        // Obtener vehículo del intent
        if (getIntent().hasExtra("vehiculo")) {
            vehiculo = (Vehiculo) getIntent().getSerializableExtra("vehiculo");
            mostrarDatosVehiculo();
        }

        // Configurar botón volver
        btnVolverDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mostrarDatosVehiculo() {
        if (vehiculo != null) {
            tvMarcaDetalle.setText(vehiculo.getMarca());
            tvModeloDetalle.setText(vehiculo.getModelo());
            tvAnioDetalle.setText(String.valueOf(vehiculo.getAnioFabricacion()));
            tvPrecioDetalle.setText(String.valueOf(vehiculo.getPrecioBase()));
        }
    }
}