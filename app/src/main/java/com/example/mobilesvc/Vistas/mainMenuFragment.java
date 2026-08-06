package com.example.mobilesvc.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.MainMenuViewBinding;

public class mainMenuFragment extends Fragment {
    private MainMenuViewBinding binding;
    private mainMenuViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MainMenuViewBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(mainMenuViewModel.class);

        vm.getUsuario().observe(getViewLifecycleOwner(), usuario -> {
            if (usuario != null) {
                binding.vCurrentUser.setText(usuario.getNombre());
            }
        });

        vm.cargarUsuario();

        binding.vPacientes.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainMenuFragment_to_usuariosFragment);
        });

        binding.vRecetas.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainMenuFragment_to_recetasFragment);
        });

        return binding.getRoot();
    }
}