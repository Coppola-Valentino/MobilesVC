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

import com.example.mobilesvc.Clases.Receta;
import com.example.mobilesvc.databinding.RecetaViewBinding;
import com.example.mobilesvc.R;

public class RecetaFragment extends Fragment {

    private RecetaViewModel mViewModel;
    private RecetaViewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RecetaViewBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(RecetaViewModel.class);

        mViewModel.getRecetaMutable().observe(getViewLifecycleOwner(), new Observer<Receta>() {
            @Override
            public void onChanged(Receta receta) {
                binding.vFecha.setText(String.valueOf(receta.getFecha()));
            }
        });

        mViewModel.cargarReceta(getArguments());

        binding.vVolverReceta.setOnClickListener(v -> {
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