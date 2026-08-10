package com.example.mobilesvc.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesvc.Clases.Usuario;
import com.example.mobilesvc.R;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuariosViewHolder> {
    private List<Usuario> usuarios;
    private Context context;
    private LayoutInflater layoutInflater;

    public UsuarioAdapter(List<Usuario> usuarios, Context context, LayoutInflater layoutInflater) {
        this.usuarios = usuarios;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.card_usuario, parent, false);
        return new UsuariosViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UsuariosViewHolder holder, int position) {
        Usuario usuarioActual = usuarios.get(position);
        holder.dni.setText(usuarioActual.getDni());
        holder.nombre.setText(usuarioActual.getNombre());
        holder.telefono.setText(String.valueOf(usuarioActual.getTelefono()));
        holder.email.setText(usuarioActual.getEmail());

        holder.toUser.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuarioActual);
            Navigation.findNavController(v)
                    .navigate(R.id.action_usuariosFragment_to_usuarioFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class UsuariosViewHolder extends RecyclerView.ViewHolder {
        TextView telefono;
        TextView nombre;
        TextView email;
        TextView dni;
        Button toUser;

        public UsuariosViewHolder(@NonNull View itemView) {
            super(itemView);
            telefono = itemView.findViewById(R.id.vTelefonoCard);
            nombre = itemView.findViewById(R.id.vNombreCard);
            dni = itemView.findViewById(R.id.vDniCard);
            email = itemView.findViewById(R.id.vEmailCard);
            toUser = itemView.findViewById(R.id.vToUsuario);
        }
    }
}
