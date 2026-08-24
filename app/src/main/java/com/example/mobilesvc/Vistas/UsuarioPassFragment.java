package com.example.mobilesvc.Vistas;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mobilesvc.Api.ApiClient;
import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.UserPassViewBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UsuarioPassFragment extends Fragment {

    private UsuarioPassViewModel vm;
    private UserPassViewBinding b;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(UsuarioPassViewModel.class);
        b = UserPassViewBinding.inflate(getLayoutInflater());

        vm.cargarUsuario(getArguments());

        vm.getUsuario().observe(getViewLifecycleOwner(), m -> {
            b.vEditPass.setOnClickListener(v -> {
                if(m.getPassword().equals(b.vActPass.getText().toString())) {
                    vm.cambiarPass(b.vActPass.getText().toString(), b.vPass1.getText().toString(), b.vPass2.getText().toString());
                } else {
                    b.vActPass.setText("Contraseña Incorrecta");
                }
            });
        });

        vm.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        vm.getDatosCambiados().observe(getViewLifecycleOwner(), result -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        b.vVolverUserPass.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return b.getRoot();
    }
}