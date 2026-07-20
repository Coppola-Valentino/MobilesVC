package com.example.mobilesvc.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.databinding.MedicamentoViewBinding;
import com.example.mobilesvc.R;

public class MedicamentoFragment extends Fragment {

    private MedicamentoViewModel mViewModel;
    private MedicamentoViewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MedicamentoViewBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(MedicamentoViewModel.class);

        mViewModel.getMedicamentoMutable().observe(getViewLifecycleOwner(), new Observer<Medicamento>() {
            @Override
            public void onChanged(Medicamento medicamento) {
                binding.vNombreMedicamento.setText(medicamento.getNombre());
                binding.vCantidadMedicamento.setText(String.valueOf(medicamento.getCantidad()));
                binding.vIntervaloMedicamento.setText(String.valueOf(medicamento.getIntervalo()));
                binding.vDosis.setText(String.valueOf(medicamento.getDosis()));
            }
        });

        mViewModel.cargarMedicamento(getArguments());

        binding.vVolverMedicamento.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}