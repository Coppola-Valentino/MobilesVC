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
import com.example.mobilesvc.databinding.RecordatorioEditViewBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordatorioEditFragment extends Fragment {

    private RecordatorioEditViewModel vm;
    private RecordatorioEditViewBinding b;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(RecordatorioEditViewModel.class);
        b = RecordatorioEditViewBinding.inflate(getLayoutInflater());

        vm.cargarRecordatorio(getArguments());

        vm.getRecordatorio().observe(getViewLifecycleOwner(), m -> {
            if (m != null) {
                b.vCantidadEdit.setText(m.getCantidad());
                b.vIntervaloEdit.setText(m.getIntervalo());
            }
        });

        vm.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        vm.getDatosCambiados().observe(getViewLifecycleOwner(), result -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_recordatorioEditFragment_to_recordatorioFragment);
        });


        b.vEditRecordatorio.setOnClickListener(v -> {
            String Cantidad = b.vCantidadEdit.getText().toString();
            int cantidad = Cantidad.isEmpty() ? 0 : Integer.parseInt(Cantidad);
            String Intervalo = b.vIntervaloEdit.getText().toString();
            int intervalo = Intervalo.isEmpty() ? 0 : Integer.parseInt(Intervalo);
            vm.cambiarDatos(
                    intervalo,
                    cantidad
            );
        });

        b.vVolverRecordatorioEdit.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return b.getRoot();
    }
}