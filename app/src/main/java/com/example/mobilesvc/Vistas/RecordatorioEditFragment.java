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
import com.google.android.material.timepicker.TimeFormat;

import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
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
                b.vCantidadEdit.setText(String.valueOf(m.getCantidad()));
                b.vIntervaloEdit.setText(String.valueOf(m.getIntervalo()));
                if (m.getEstado() == 1){
                    b.vEditEstado.setChecked(true);
                } else {
                    b.vEditEstado.setChecked(false);
                }

            }
        });

        vm.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        vm.getDatosCambiados().observe(getViewLifecycleOwner(), result -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });


        b.vEditRecordatorio.setOnClickListener(v -> {
            String Cantidad = b.vCantidadEdit.getText().toString();
            int cantidad = Cantidad.isEmpty() ? 0 : Integer.parseInt(Cantidad);
            String Intervalo = b.vIntervaloEdit.getText().toString();
            //Time intervalo = Intervalo.isEmpty() ? null : Time.valueOf(Intervalo);
            int estado;
            if (b.vEditEstado.isChecked()){
                estado = 1;
            } else {
                estado = 0;
            }
            if (cantidad < 1 || cantidad > 99) {
                b.vCantidadEdit.setError("la Cantidad no puede ser menor que 1 o mayor que 99");
                return;
            }
            SimpleDateFormat a = new SimpleDateFormat("hh:mm:dd", Locale.getDefault());
            try {
                a.parseObject(Intervalo);
                vm.cambiarDatos(
                        b.vIntervaloEdit.getText().toString(),
                        cantidad,
                        estado
                );
            }catch(ParseException e){
                b.vIntervaloEdit.setError("Formato inválido (usar hh:mm:ss)");
            }
        });

        b.vVolverRecordatorioEdit.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return b.getRoot();
    }
}