package com.example.mobilesvc.Vistas;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.RecetaEditViewBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecetaEditFragment extends Fragment {

    private RecetaEditViewModel vm;
    private RecetaEditViewBinding b;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(RecetaEditViewModel.class);
        b = RecetaEditViewBinding.inflate(getLayoutInflater());

        vm.cargarReceta(getArguments());

        vm.getReceta().observe(getViewLifecycleOwner(), m -> {
            if (m != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                b.vFechaEdit.setText(dateFormat.format(m.getFecha()));
            }
        });

        vm.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        vm.getDatosCambiados().observe(getViewLifecycleOwner(), result -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });


        b.vEditReceta.setOnClickListener(v -> {
            String fecha = b.vFechaEdit.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            try {
            Date Fecha = dateFormat.parse(fecha);
                vm.cambiarDatos(
                        Fecha
                );
            } catch (ParseException e) {
                b.vFechaEdit.setError("Formato inválido (usar yyyy/MM/dd)");
            }
        });

        b.vVolverRecetaEdit.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return b.getRoot();
    }
}