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

import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.databinding.RecordatorioViewBinding;
import com.example.mobilesvc.R;

public class RecordatorioFragment extends Fragment {

    private RecordatorioViewModel mViewModel;
    private RecordatorioViewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RecordatorioViewBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(RecordatorioViewModel.class);

        mViewModel.getRecordatorioMutable().observe(getViewLifecycleOwner(), new Observer<Recordatorio>() {
            @Override
            public void onChanged(Recordatorio recordatorio) {
                binding.vCantidad.setText(String.valueOf(recordatorio.getCantidad()));
                binding.vIntervalo.setText(String.valueOf(recordatorio.getIntervalo()));
            }
        });

        mViewModel.cargarRecordatorio(getArguments());

        binding.vVolverRecordatorio.setOnClickListener(v -> {
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