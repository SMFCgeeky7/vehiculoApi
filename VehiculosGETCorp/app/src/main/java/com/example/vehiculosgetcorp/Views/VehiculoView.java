package com.example.vehiculosgetcorp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vehiculosgetcorp.Adapters.VehiculoAdapter;
import com.example.vehiculosgetcorp.Models.Vehiculo;
import com.example.vehiculosgetcorp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VehiculoView extends AppCompatActivity implements VehiculoAdapter.OnVehiculoClickListener {
    private RecyclerView rvVehiculo;
    private VehiculoAdapter vehiculoAdapter;
    private List<Vehiculo> vehiculoList;
    private ProgressBar progressBar;
    private Button btnCargar;
    private Button btnAgregar;
    private RequestQueue requestQueue;
    private final String URL = "https://raw.githubusercontent.com/adancondori/TareaAPI/main/api/vehiculos.json";
    private static final int EDITAR_VEHICULO_REQUEST = 1001;
    private static final int AGREGAR_VEHICULO_REQUEST = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);
        
        rvVehiculo = findViewById(R.id.rvVehiculo);
        progressBar = findViewById(R.id.progressBar);
        btnCargar = findViewById(R.id.btnCargar);
        btnAgregar = findViewById(R.id.btnAgregar);

        vehiculoList = new ArrayList<>();
        rvVehiculo.setLayoutManager(new LinearLayoutManager(this));
        vehiculoAdapter = new VehiculoAdapter(vehiculoList, this, this);
        rvVehiculo.setAdapter(vehiculoAdapter);

        requestQueue = Volley.newRequestQueue(this);

        btnCargar.setOnClickListener(v -> cargarDatos());

        // Cargar datos automáticamente al iniciar
        cargarDatos();

        // Configurar botón para agregar nuevos vehículos
        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(VehiculoView.this, VehiculoAgregarView.class);
            startActivityForResult(intent, AGREGAR_VEHICULO_REQUEST);
        });
    }

    private void cargarDatos() {
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            vehiculoList.clear();
                            JSONArray jsonArray = response.getJSONArray("vehiculos");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Vehiculo vehiculo = new Vehiculo();
                                vehiculo.setId(jsonObject.getInt("id"));
                                vehiculo.setMarca(jsonObject.getString("marca"));
                                vehiculo.setModelo(jsonObject.getString("modelo"));
                                vehiculo.setAnioFabricacion(jsonObject.getInt("anioFabricacion"));
                                vehiculo.setPrecioBase(jsonObject.getInt("precioBase"));
                                vehiculo.setKilometraje(jsonObject.getInt("kilometraje"));
                                vehiculo.setTipo(jsonObject.getString("tipo"));

                                // Procesar campos opcionales
                                try {
                                    vehiculo.setGarantiaAnios(jsonObject.getInt("garantiaAnios"));
                                } catch (JSONException e) {
                                    // Este campo puede no existir en todos los tipos de vehículos
                                }

                                try {
                                    vehiculo.setDescuentoPromocional(jsonObject.getInt("descuentoPromocional"));
                                } catch (JSONException e) {
                                    // Este campo puede no existir en todos los tipos de vehículos
                                }

                                // Procesar la información adicional
                                if (jsonObject.has("informacionAdicional")) {
                                    JSONObject infoAdicionalJson = jsonObject.getJSONObject("informacionAdicional");

                                    // Crear objeto InformacionAdicional
                                    Vehiculo.InformacionAdicional infoAdicional = new Vehiculo.InformacionAdicional();

                                    // Establecer esperanza de vida
                                    if (infoAdicionalJson.has("esperanzaVida")) {
                                        infoAdicional.setEsperanzaVida(infoAdicionalJson.getInt("esperanzaVida"));
                                    }

                                    // Procesar datos
                                    if (infoAdicionalJson.has("datos")) {
                                        JSONArray datosArray = infoAdicionalJson.getJSONArray("datos");
                                        List<Vehiculo.Dato> datosList = new ArrayList<>();

                                        for (int j = 0; j < datosArray.length(); j++) {
                                            JSONObject datoJson = datosArray.getJSONObject(j);
                                            Vehiculo.Dato dato = new Vehiculo.Dato();
                                            dato.setNombreDato(datoJson.getString("nombreDato"));
                                            dato.setValor(datoJson.getString("valor"));
                                            datosList.add(dato);
                                        }

                                        infoAdicional.setDatos(datosList);
                                    }

                                    // Asignar al vehículo
                                    vehiculo.setInformacionAdicional(infoAdicional);
                                }

                                vehiculoList.add(vehiculo);
                            }
                            vehiculoAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);

                            // Mostrar mensaje si no se cargaron vehículos
                            if (vehiculoList.isEmpty()) {
                                mostrarToastGrande("No se encontraron vehículos en la respuesta", Toast.LENGTH_SHORT);
                            } else {
                                mostrarToastExito("Se cargaron " + vehiculoList.size() + " vehículos correctamente", Toast.LENGTH_SHORT);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            mostrarToastGrande("Error procesando JSON: " + e.getMessage(), Toast.LENGTH_LONG);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);

                        // Mostrar mensaje de error más detallado
                        String errorMsg = "Error de conexión";
                        if (error.networkResponse != null) {
                            errorMsg += " - Código: " + error.networkResponse.statusCode;
                        } else if (error.getMessage() != null) {
                            errorMsg += ": " + error.getMessage();
                        } else if (error.toString() != null) {
                            errorMsg += ": " + error.toString();
                        }

                        mostrarToastGrande(errorMsg, Toast.LENGTH_LONG);
                        // Imprimir el error para depuración
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onVehiculoClick(Vehiculo vehiculo, int position) {
        // Abrir la vista de detalle
        Intent intent = new Intent(this, VehiculoDetalleView.class);
        intent.putExtra("vehiculo", vehiculo);
        startActivity(intent);
    }

    @Override
    public void onEditarClick(Vehiculo vehiculo, int position) {
        // Lanzar la actividad de edición de vehículo
        Intent intent = new Intent(this, VehiculoEditarView.class);
        intent.putExtra("vehiculo", vehiculo);
        intent.putExtra("position", position);
        startActivityForResult(intent, EDITAR_VEHICULO_REQUEST);
    }

    @Override
    public void onEliminarClick(Vehiculo vehiculo, int position) {
        // Aquí iría la lógica para eliminar el vehículo
        // Por ahora solo mostramos un mensaje y lo eliminamos de la lista
        vehiculoList.remove(position);
        vehiculoAdapter.notifyItemRemoved(position);
        mostrarToastExito("Vehículo eliminado: " + vehiculo.getMarca() + " " + vehiculo.getModelo(), Toast.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDITAR_VEHICULO_REQUEST && resultCode == RESULT_OK) {
            // Actualizar la vista después de editar un vehículo
            vehiculoAdapter.notifyDataSetChanged();
            mostrarToastExito("Vehículo actualizado correctamente", Toast.LENGTH_SHORT);
        } else if (requestCode == AGREGAR_VEHICULO_REQUEST && resultCode == RESULT_OK) {
            // Actualizar la vista después de agregar un vehículo
            vehiculoAdapter.notifyDataSetChanged();
            mostrarToastExito("Vehículo agregado correctamente", Toast.LENGTH_SHORT);
        }
    }

    // Método para mostrar un Toast grande y personalizado de éxito (verde)
    private void mostrarToastExito(String mensaje, int duracion) {
        // Crear un nuevo layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.parseColor("#BB000000")); // Fondo negro semi-transparente

        // Añadir un borde al layout
        layout.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        layout.setBackgroundColor(Color.parseColor("#4CAF50")); // Fondo verde para mensajes de éxito
        layout.setPadding(40, 40, 40, 40);

        // Crear un TextView con texto grande
        TextView text = new TextView(this);
        text.setText(mensaje);
        text.setTextColor(Color.WHITE);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Texto más grande
        text.setGravity(Gravity.CENTER);
        text.setPadding(10, 10, 10, 10);

        // Añadir el TextView al layout
        layout.addView(text);

        // Crear y mostrar el Toast personalizado
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duracion);
        toast.setView(layout);
        toast.show();
    }

    // Método para mostrar un Toast grande y personalizado
    private void mostrarToastGrande(String mensaje, int duracion) {
        // Crear un nuevo layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.parseColor("#BB000000")); // Fondo negro semi-transparente

        // Añadir un borde al layout
        layout.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        layout.setBackgroundColor(Color.RED); // Fondo rojo para errores
        layout.setPadding(40, 40, 40, 40);

        // Crear un TextView con texto grande
        TextView text = new TextView(this);
        text.setText(mensaje);
        text.setTextColor(Color.WHITE);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Texto más grande
        text.setGravity(Gravity.CENTER);
        text.setPadding(10, 10, 10, 10);

        // Añadir el TextView al layout
        layout.addView(text);

        // Crear y mostrar el Toast personalizado
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duracion);
        toast.setView(layout);
        toast.show();
    }
}