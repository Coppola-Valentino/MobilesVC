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

import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.R;

import java.util.List;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder> {
    private List<Medicamento> medicamentos;
    private Context context;
    private LayoutInflater layoutInflater;

    public MedicamentoAdapter(List<Medicamento> medicamentos, Context context, LayoutInflater layoutInflater) {
        this.medicamentos = medicamentos;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.card_medicamento, parent, false);
        return new MedicamentoViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHolder holder, int position) {
        Medicamento medicamentoActual = medicamentos.get(position);

        holder.toMedicamento.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("medicamento", medicamentoActual);
            Navigation.findNavController(v)
                    .navigate(R.id.action_medicamentosFragment_to_medicamentoFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return medicamentos.size();
    }

    public class MedicamentoViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView cantidad;
        TextView dosis;
        TextView intervalo;
        Button toMedicamento;

        public MedicamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.vNombreMedCard);
            cantidad = itemView.findViewById(R.id.vCantidadMedCard);
            dosis = itemView.findViewById(R.id.vDosisMedCard);
            intervalo = itemView.findViewById(R.id.vIntervaloMedCard);
            toMedicamento = itemView.findViewById(R.id.vToMedicamento);

        }
    }
}
