package com.example.mobilesvc.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilesvc.Clases.Usuario;
import com.example.mobilesvc.databinding.UserViewBinding;
import com.example.mobilesvc.R;

public class UsuarioFragment extends Fragment {

    private UsuarioViewModel mViewModel;
    private UserViewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = UserViewBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        mViewModel.getUsuarioMutable().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.vNombre.setText(usuario.getNombre());
                binding.vPassword.setText(usuario.getPassword());
                binding.vDireccion.setText(usuario.getDireccion());
                binding.vTelefono.setText(String.valueOf(usuario.getTelefono()));
                binding.vEmail.setText(usuario.getEmail());
                binding.vDni.setText(usuario.getDni());
                binding.vGenero.setText(usuario.getGenero());
                binding.vEdad.setText(String.valueOf(usuario.getEdad()));
            }
        });

        mViewModel.cargarUsuario(getArguments());

        binding.vVolverPerfil.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
