package com.example.mobilesvc.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.R;

import java.util.List;

public class RecordatorioAdapter extends RecyclerView.Adapter<RecordatorioAdapter.RecordatorioViewHolder> {
    private List<Recordatorio> recordatorios;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecordatorioAdapter(List<Recordatorio> recordatorios, Context context, LayoutInflater layoutInflater) {
        this.recordatorios = recordatorios;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public RecordatorioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.card_recordatorio, parent, false);
        return new RecordatorioViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecordatorioViewHolder holder, int position) {
        Recordatorio recordatorioActual = recordatorios.get(position);

        holder.toRecordatorio.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("recordatorio", recordatorioActual);
            Navigation.findNavController(v)
                    .navigate(R.id.action_recordatoriosFragment_to_recordatorioFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return recordatorios.size();
    }

    public class RecordatorioViewHolder extends RecyclerView.ViewHolder {
        TextView cantidad;
        TextView intervalo;
        Button toRecordatorio;

        public RecordatorioViewHolder(@NonNull View itemView) {
            super(itemView);
            cantidad = itemView.findViewById(R.id.vCantidadCard);
            intervalo = itemView.findViewById(R.id.vIntervaloCard);
            toRecordatorio = itemView.findViewById(R.id.vToRecordatorio);
        }
    }
}
