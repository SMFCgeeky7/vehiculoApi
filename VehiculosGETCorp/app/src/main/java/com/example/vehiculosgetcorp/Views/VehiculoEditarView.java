package com.example.vehiculosgetcorp.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vehiculosgetcorp.Models.Vehiculo;
import com.example.vehiculosgetcorp.R;
import com.google.android.material.textfield.TextInputEditText;

public class VehiculoEditarView extends AppCompatActivity {

    private TextInputEditText etId, etMarca, etModelo, etAnioFabricacion, etPrecioBase, etKilometraje, etTipo;
    private TextInputEditText etGarantiaAnios, etDescuentoPromocional, etEsperanzaVida;
    private Button btnActualizar;
    private Vehiculo vehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vehiculo);

        // Inicializar vistas
        etId = findViewById(R.id.etId);
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        etAnioFabricacion = findViewById(R.id.etAnioFabricacion);
        etPrecioBase = findViewById(R.id.etPrecioBase);
        etKilometraje = findViewById(R.id.etKilometraje);
        etTipo = findViewById(R.id.etTipo);
        etGarantiaAnios = findViewById(R.id.etGarantiaAnios);
        etDescuentoPromocional = findViewById(R.id.etDescuentoPromocional);
        etEsperanzaVida = findViewById(R.id.etEsperanzaVida);
        btnActualizar = findViewById(R.id.btnActualizar);

        // Obtener el vehículo pasado como extra
        if (getIntent().hasExtra("vehiculo")) {
            vehiculo = (Vehiculo) getIntent().getSerializableExtra("vehiculo");
            cargarDatosVehiculo();
        } else {
            Toast.makeText(this, "No se recibieron datos del vehículo", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Configurar el botón de actualizar
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarVehiculo();
            }
        });
    }

    private void cargarDatosVehiculo() {
        if (vehiculo != null) {
            etId.setText(String.valueOf(vehiculo.getId()));
            etMarca.setText(vehiculo.getMarca());
            etModelo.setText(vehiculo.getModelo());
            etAnioFabricacion.setText(String.valueOf(vehiculo.getAnioFabricacion()));
            etPrecioBase.setText(String.valueOf(vehiculo.getPrecioBase()));
            etKilometraje.setText(String.valueOf(vehiculo.getKilometraje()));
            etTipo.setText(vehiculo.getTipo());
            etGarantiaAnios.setText(String.valueOf(vehiculo.getGarantiaAnios()));
            etDescuentoPromocional.setText(String.valueOf(vehiculo.getDescuentoPromocional()));

            if (vehiculo.getInformacionAdicional() != null) {
                etEsperanzaVida.setText(String.valueOf(vehiculo.getInformacionAdicional().getEsperanzaVida()));
            }
        }
    }

    private void actualizarVehiculo() {
        try {
            // Actualizar el objeto vehículo con los datos editados
            vehiculo.setMarca(etMarca.getText().toString());
            vehiculo.setModelo(etModelo.getText().toString());
            vehiculo.setAnioFabricacion(Integer.parseInt(etAnioFabricacion.getText().toString()));
            vehiculo.setPrecioBase(Integer.parseInt(etPrecioBase.getText().toString()));
            vehiculo.setKilometraje(Integer.parseInt(etKilometraje.getText().toString()));
            vehiculo.setTipo(etTipo.getText().toString());

            String garantiaText = etGarantiaAnios.getText().toString();
            if (!garantiaText.isEmpty()) {
                vehiculo.setGarantiaAnios(Integer.parseInt(garantiaText));
            }

            String descuentoText = etDescuentoPromocional.getText().toString();
            if (!descuentoText.isEmpty()) {
                vehiculo.setDescuentoPromocional(Integer.parseInt(descuentoText));
            }

            String esperanzaVidaText = etEsperanzaVida.getText().toString();
            if (!esperanzaVidaText.isEmpty() && vehiculo.getInformacionAdicional() != null) {
                vehiculo.getInformacionAdicional().setEsperanzaVida(Integer.parseInt(esperanzaVidaText));
            }

            // Devolver el vehículo actualizado a la actividad anterior
            Toast.makeText(this, "Vehículo actualizado correctamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error en los datos ingresados. Verifique los campos numéricos.", Toast.LENGTH_SHORT).show();
        }
    }
}