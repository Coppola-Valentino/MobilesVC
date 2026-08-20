package com.example.mobilesvc.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mobilesvc.Api.ApiClient;
import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.MainMenuViewBinding;

public class mainMenuFragment extends Fragment {
    private MainMenuViewBinding binding;
    private mainMenuViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MainMenuViewBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(mainMenuViewModel.class);
        Bundle bundle = new Bundle();

        vm.getUsuario().observe(getViewLifecycleOwner(), usuario -> {
            if (usuario != null) {
                binding.vCurrentUser.setText(usuario.getNombre());
                bundle.putSerializable("usuario", usuario);
            }
        });

        vm.cargarUsuario();

        if (ApiClient.obtenerUsuarioRol(requireContext()).equals("Medico") || ApiClient.obtenerUsuarioRol(requireContext()).equals("Admin")) {
            binding.vPacientes.setVisibility(View.VISIBLE);
            binding.vPacientes.setOnClickListener(v -> {
                Navigation.findNavController(v).navigate(R.id.action_mainMenuFragment_to_usuariosFragment);
            });
        } else {
            binding.vPacientes.setVisibility(View.GONE);
            binding.vPacientes.setClickable(false);
        }

        binding.vPerfil.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainMenuFragment_to_usuarioFragment, bundle);
        });

        binding.vRecetas.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainMenuFragment_to_recetasFragment);
        });

        binding.vRecordatorios.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainMenuFragment_to_recordatoriosFragment);
        });

        binding.vLogOut.setOnClickListener( v ->{
            Navigation.findNavController(v).navigate(R.id.action_mainMenuFragment_to_loginFragment);
        });

        return binding.getRoot();
    }
}