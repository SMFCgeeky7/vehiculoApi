package com.example.vehiculosgetcorp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vehiculosgetcorp.Models.Vehiculo;
import com.example.vehiculosgetcorp.R;
import java.util.List;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder>{
    private List<Vehiculo> vehiculosList;
    private Context context;

    private OnVehiculoClickListener listener;
    public interface OnVehiculoClickListener {
        void onVehiculoClick(Vehiculo vehiculo, int position);

        void onEditarClick(Vehiculo vehiculo, int position);

        void onEliminarClick(Vehiculo vehiculo, int position);
    }

    public VehiculoAdapter(List<Vehiculo> vehiculoList, Context context, OnVehiculoClickListener listener) {
        this.vehiculosList = vehiculoList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehiculo, parent, false);
        return new VehiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int position) {
        Vehiculo vehiculo = vehiculosList.get(position);
        holder.bind(vehiculo, position);
    }

    @Override
    public int getItemCount() {
        return vehiculosList == null ? 0 : vehiculosList.size();
    }

    public class VehiculoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMarca, tvModelo, tvAnioFabricacion, tvPrecioBase;
        private Button btnEditar, btnEliminar;

        public VehiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMarca = itemView.findViewById(R.id.tv_marca);
            tvModelo = itemView.findViewById(R.id.tv_modelo);
            tvAnioFabricacion = itemView.findViewById(R.id.tv_anioFabricacion);
            tvPrecioBase = itemView.findViewById(R.id.tv_precioBase);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }

        public void bind(final Vehiculo vehiculo, final int position) {
            tvMarca.setText(vehiculo.getMarca());
            tvModelo.setText(vehiculo.getModelo());
            tvAnioFabricacion.setText("Año: " + vehiculo.getAnioFabricacion());
            tvPrecioBase.setText("Precio: $" + vehiculo.getPrecioBase());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onVehiculoClick(vehiculo, position);
                }
            });

            btnEditar.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditarClick(vehiculo, position);
                }
            });

            btnEliminar.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEliminarClick(vehiculo, position);
                }
            });
        }
    }
}