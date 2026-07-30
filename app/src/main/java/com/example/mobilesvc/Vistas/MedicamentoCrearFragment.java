package com.example.mobilesvc.Vistas;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.ParseException;

import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.MedicamentoCrearViewBinding;

import java.util.Date;

public class MedicamentoCrearFragment extends Fragment {

    private MedicamentoCrearViewModel mViewModel;
    private MedicamentoCrearViewBinding binding;
    private ActivityResultLauncher<Intent> selector;
    private Intent intent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MedicamentoCrearViewBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(MedicamentoCrearViewModel.class);

        mViewModel.getMedicamentoMutable().observe(getViewLifecycleOwner(), medicamento -> {
            Bundle bundle = new Bundle();
            bundle.putInt("Medicamento ID", medicamento.getIDMedicamento());

            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_medicamentoCrearFragment_to_medicamentosFragment, bundle);
        });


        binding.vCrearMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.vNombreMedicamentoCrear.getText().toString();
                String Cantidad = binding.vCantidadMedicamentoCrear.getText().toString();
                int cantidad = Cantidad.isEmpty() ? 0 : Integer.parseInt(Cantidad);
                String Intervalo = binding.vIntervaloMedicamentoCrear.getText().toString();
                double intervalo = Intervalo.isEmpty() ? 0 : Integer.parseInt(Intervalo);
                String Dosis = binding.vDosisCrear.getText().toString();
                double dosis = Dosis.isEmpty() ? 0 : Integer.parseInt(Dosis);

                //mViewModel.evaluarChipSeleccionado(chipsId);
                mViewModel.crearNuevoMedicamento(nombre, cantidad, intervalo, dosis);

            }
        });

        binding.vVolverMedicamentoCrear.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return binding.getRoot();
    }

}