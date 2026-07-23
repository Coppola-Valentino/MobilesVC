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

import com.example.mobilesvc.Adapters.RecordatorioAdapter;
import com.example.mobilesvc.databinding.LoginViewBinding;
import com.example.mobilesvc.databinding.RecordatoriosViewBinding;
import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.R;

public class RecordatoriosFragment extends Fragment {
    private RecordatoriosViewBinding binding;
    private RecordatoriosViewModel mViewModel;
    private RecordatorioAdapter recordatorioAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = RecordatoriosViewBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(RecordatoriosViewModel.class);

        mViewModel.getRecordatorios().observe(getViewLifecycleOwner(), recordatorios -> {
            recordatorioAdapter = new RecordatorioAdapter(recordatorios,getContext(), getLayoutInflater());

            GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL,false);

            binding.vRecordatoriosList.setLayoutManager(glm);
            binding.vRecordatoriosList.setAdapter(recordatorioAdapter);
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

        mViewModel.cargarRecordatorios();

        return binding.getRoot();
    }

}