package com.example.mobilesvc.Vistas;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobilesvc.Adapters.UsuarioAdapter;
import com.example.mobilesvc.databinding.LoginViewBinding;
import com.example.mobilesvc.databinding.UsersViewBinding;
import com.example.mobilesvc.R;

public class UsuariosFragment extends Fragment {
    private UsersViewBinding binding;
    private UsuariosViewModel mViewModel;
    private UsuarioAdapter usuarioAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = UsersViewBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(UsuariosViewModel.class);

        mViewModel.getUsuarios().observe(getViewLifecycleOwner(), usuarios -> {
            usuarioAdapter = new UsuarioAdapter(usuarios,getContext(), getLayoutInflater());

            GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL,false);

            binding.vUsersList.setLayoutManager(glm);
            binding.vUsersList.setAdapter(usuarioAdapter);
        });

        mViewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        mViewModel.cargarUsuarios();

        return binding.getRoot();
    }

}