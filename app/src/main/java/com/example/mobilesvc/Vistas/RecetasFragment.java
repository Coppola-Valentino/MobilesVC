package com.example.mobilesvc.Vistas;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobilesvc.Adapters.RecetaAdapter;
import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.RecetasViewBinding;

public class RecetasFragment extends Fragment {
    private RecetasViewBinding binding;
    private RecetasViewModel mViewModel;
    private RecetaAdapter recetaAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = RecetasViewBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(RecetasViewModel.class);

        mViewModel.getRecetas().observe(getViewLifecycleOwner(), recetas -> {
            recetaAdapter = new RecetaAdapter(recetas,getContext(), getLayoutInflater());

            GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL,false);

            binding.vRecetasList.setLayoutManager(glm);
            binding.vRecetasList.setAdapter(recetaAdapter);
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

        binding.vVolverRecetas.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        binding.vToRecetasCrear.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_recetasFragment_to_recetaCrearFragment);
        });

        mViewModel.cargarRecetas();

        return binding.getRoot();
    }

}