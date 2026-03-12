package com.ecolim.app.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ecolim.app.R;
import com.ecolim.app.models.Residuo;
import java.util.List;

public class ResiduoAdapter extends RecyclerView.Adapter<ResiduoAdapter.ViewHolder> {

    private List<Residuo> lista;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(Residuo residuo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ResiduoAdapter(List<Residuo> lista) {
        this.lista = lista;
    }

    public void actualizarLista(List<Residuo> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_residuo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Residuo r = lista.get(position);
        holder.tvTipo.setText(r.getTipo());
        holder.tvFecha.setText(r.getFecha());
        holder.tvCantidad.setText(r.getCantidad() + " " + r.getUnidad());
        holder.tvUbicacion.setText(r.getUbicacion());
        holder.tvTrabajador.setText("Por: " + r.getTrabajador());

        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(r);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipo, tvFecha, tvCantidad, tvUbicacion, tvTrabajador;
        ImageButton btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            tvTrabajador = itemView.findViewById(R.id.tvTrabajador);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}