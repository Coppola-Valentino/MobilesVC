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

import com.example.mobilesvc.Clases.Receta;
import com.example.mobilesvc.R;

import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder> {
    private List<Receta> receta;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecetaAdapter(List<Receta> receta, Context context, LayoutInflater layoutInflater) {
        this.receta = receta;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.card_receta, parent, false);
        return new RecetaViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecetaViewHolder holder, int position) {
        Receta recetaActual = receta.get(position);

        holder.toReceta.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("receta", recetaActual);
            Navigation.findNavController(v)
                    .navigate(R.id.action_recetasFragment_to_recetaFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return receta.size();
    }

    public class RecetaViewHolder extends RecyclerView.ViewHolder {
        TextView fecha; //cambiar luego a date algo
        Button toReceta;

        public RecetaViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.vFechaCard);
            toReceta = itemView.findViewById(R.id.vToReceta);
        }
    }
}
