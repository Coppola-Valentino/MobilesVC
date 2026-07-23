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

import com.example.mobilesvc.Adapters.MedicamentoAdapter;
import com.example.mobilesvc.databinding.MedicamentosViewBinding;

public class MedicamentosFragment extends Fragment {
    private MedicamentosViewBinding binding;
    private MedicamentosViewModel mViewModel;
    private MedicamentoAdapter recetaAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = MedicamentosViewBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(MedicamentosViewModel.class);

        mViewModel.getMedicamentos().observe(getViewLifecycleOwner(), medicamentos -> {
            recetaAdapter = new MedicamentoAdapter(medicamentos,getContext(), getLayoutInflater());

            GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL,false);

            binding.vMedicamentosList.setLayoutManager(glm);
            binding.vMedicamentosList.setAdapter(recetaAdapter);
        });

        mViewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        //mViewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
        //    binding.vMensajeCargandoRecordatorios.setText(message);
        //});

        //mViewModel.getMessageVisible().observe(getViewLifecycleOwner(), visible -> {
        //    binding.vMensajeCargandoRecordatorios.setVisibility(visible);
        //);

        mViewModel.cargarMedicamentos();

        return binding.getRoot();
    }

}