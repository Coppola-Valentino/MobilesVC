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
            }
        });

        vm.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        vm.getDatosCambiados().observe(getViewLifecycleOwner(), result -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_perfilCambiarClaveFragment_to_perfilFragment);
        });


        b.vEditMedicamento.setOnClickListener(v -> {
            String Cantidad = b.vCantidadMedicamentoEdit.getText().toString();
            int cantidad = Cantidad.isEmpty() ? 0 : Integer.parseInt(Cantidad);
            String Dosis = b.vDosisEdit.getText().toString();
            double dosis = Dosis.isEmpty() ? 0 : Integer.parseInt(Dosis);
            String Intervalo = b.vIntervaloMedicamentoEdit.getText().toString();
            double intervalo = Intervalo.isEmpty() ? 0 : Integer.parseInt(Intervalo);
            vm.cambiarDatos(
                    b.vNombreMedicamentoEdit.getText().toString(),
                    cantidad,
                    dosis,
                    intervalo

            );
        });
        return b.getRoot();
    }
}