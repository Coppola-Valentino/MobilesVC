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
import com.example.mobilesvc.databinding.MedicamentoEditViewBinding;

public class MedicamentoEditFragment extends Fragment {

    private MedicamentoEditViewModel vm;
    private MedicamentoEditViewBinding b;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(MedicamentoEditViewModel.class);
        b = MedicamentoEditViewBinding.inflate(getLayoutInflater());

        vm.cargarMedicamento(getArguments());

        vm.getMedicamento().observe(getViewLifecycleOwner(), m -> {
            if (m != null) {
                b.vNombreMedicamentoEdit.setText(m.getNombre());
                b.vCantidadMedicamentoEdit.setText(String.valueOf(m.getCantidad()));
                b.vDosisEdit.setText(String.valueOf(m.getDosis()));
                b.vIntervaloMedicamentoEdit.setText(String.valueOf(m.getIntervalo()));
                b.vMarcaEdit.setText(m.getMarca());
            }
        });

        vm.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        vm.getDatosCambiados().observe(getViewLifecycleOwner(), result -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });


        b.vEditMedicamento.setOnClickListener(v -> {
            String nombre = b.vNombreMedicamentoEdit.getText().toString();
            String marca = b.vMarcaEdit.getText().toString();
            String Cantidad = b.vCantidadMedicamentoEdit.getText().toString();
            int cantidad = Cantidad.isEmpty() ? 0 : Integer.parseInt(Cantidad);
            String Dosis = b.vDosisEdit.getText().toString();
            double dosis = Dosis.isEmpty() ? 0 : Double.parseDouble(Dosis);
            String Intervalo = b.vIntervaloMedicamentoEdit.getText().toString();
            double intervalo = Intervalo.isEmpty() ? 0 : Double.parseDouble(Intervalo);
            if (nombre.length() < 2 || nombre.length() > 30) {
                b.vNombreMedicamentoEdit.setError("Nombre Invalido");
                return;
            }
            if (marca.length() < 2 || marca.length() > 30) {
                b.vMarcaEdit.setError("Marca Invalida");
                return;
            }
            if (cantidad < 1 || cantidad > 99) {
                b.vCantidadMedicamentoEdit.setError("la Cantidad no puede ser menor que 1 o mayor que 99");
                return;
            }
            if (intervalo < 0.5 || intervalo > 168) {
                b.vIntervaloMedicamentoEdit.setError("el Intervalo no puede ser menor que media hora o mayor que una semana");
                return;
            }
            if (dosis < 1 || dosis > 9999) {
                b.vDosisEdit.setError("la Dosis no puede ser menor que 1 o mayor que 9999");
                return;
            }
            vm.cambiarDatos(
                    nombre,
                    marca,
                    cantidad,
                    dosis,
                    intervalo

            );
        });

        b.vVolverMedicamentoEdit.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return b.getRoot();
    }
}