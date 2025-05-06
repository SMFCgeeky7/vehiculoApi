package com.example.vehiculosgetcorp.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vehiculosgetcorp.Models.Vehiculo;
import com.example.vehiculosgetcorp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class VehiculoAgregarView extends AppCompatActivity {

    private TextInputEditText etMarcaNuevo, etModeloNuevo, etAnioFabricacionNuevo, etPrecioBaseNuevo,
            etKilometrajeNuevo, etTipoNuevo, etGarantiaAniosNuevo,
            etDescuentoPromocionalNuevo, etEsperanzaVidaNuevo;
    private Button btnAgregarNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_agregar);

        // Inicializar vistas
        etMarcaNuevo = findViewById(R.id.etMarcaNuevo);
        etModeloNuevo = findViewById(R.id.etModeloNuevo);
        etAnioFabricacionNuevo = findViewById(R.id.etAnioFabricacionNuevo);
        etPrecioBaseNuevo = findViewById(R.id.etPrecioBaseNuevo);
        etKilometrajeNuevo = findViewById(R.id.etKilometrajeNuevo);
        etTipoNuevo = findViewById(R.id.etTipoNuevo);
        etGarantiaAniosNuevo = findViewById(R.id.etGarantiaAniosNuevo);
        etDescuentoPromocionalNuevo = findViewById(R.id.etDescuentoPromocionalNuevo);
        etEsperanzaVidaNuevo = findViewById(R.id.etEsperanzaVidaNuevo);
        btnAgregarNuevo = findViewById(R.id.btnAgregarNuevo);

        // Configurar botón para agregar
        btnAgregarNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNuevoVehiculo();
            }
        });
    }

    private void agregarNuevoVehiculo() {
        try {
            // Validar campos obligatorios
            if (etMarcaNuevo.getText().toString().isEmpty() ||
                    etModeloNuevo.getText().toString().isEmpty() ||
                    etAnioFabricacionNuevo.getText().toString().isEmpty() ||
                    etPrecioBaseNuevo.getText().toString().isEmpty() ||
                    etKilometrajeNuevo.getText().toString().isEmpty() ||
                    etTipoNuevo.getText().toString().isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear nuevo vehículo
            Vehiculo nuevoVehiculo = new Vehiculo();
            nuevoVehiculo.setMarca(etMarcaNuevo.getText().toString());
            nuevoVehiculo.setModelo(etModeloNuevo.getText().toString());
            nuevoVehiculo.setAnioFabricacion(Integer.parseInt(etAnioFabricacionNuevo.getText().toString()));
            nuevoVehiculo.setPrecioBase(Integer.parseInt(etPrecioBaseNuevo.getText().toString()));
            nuevoVehiculo.setKilometraje(Integer.parseInt(etKilometrajeNuevo.getText().toString()));
            nuevoVehiculo.setTipo(etTipoNuevo.getText().toString());

            // Asignar ID único (en un sistema real esto lo haría la base de datos)
            nuevoVehiculo.setId((int) (System.currentTimeMillis() % 10000));

            // Procesar campos opcionales
            String garantiaText = etGarantiaAniosNuevo.getText().toString();
            if (!garantiaText.isEmpty()) {
                nuevoVehiculo.setGarantiaAnios(Integer.parseInt(garantiaText));
            }

            String descuentoText = etDescuentoPromocionalNuevo.getText().toString();
            if (!descuentoText.isEmpty()) {
                nuevoVehiculo.setDescuentoPromocional(Integer.parseInt(descuentoText));
            }

            String esperanzaVidaText = etEsperanzaVidaNuevo.getText().toString();
            if (!esperanzaVidaText.isEmpty()) {
                // Crear objeto InformacionAdicional
                Vehiculo.InformacionAdicional infoAdicional = new Vehiculo.InformacionAdicional();
                infoAdicional.setEsperanzaVida(Integer.parseInt(esperanzaVidaText));
                infoAdicional.setDatos(new ArrayList<>()); // Lista vacía inicialmente
                nuevoVehiculo.setInformacionAdicional(infoAdicional);
            }

            // En una aplicación real, aquí enviaríamos el vehículo al servidor
            // Por ahora simplemente retornamos el resultado a la actividad anterior
            Toast.makeText(this, "Vehículo agregado correctamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error en los datos ingresados. Verifique los campos numéricos", Toast.LENGTH_SHORT).show();
        }
    }
}