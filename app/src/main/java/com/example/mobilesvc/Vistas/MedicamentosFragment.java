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

import com.example.mobilesvc.Adapters.MedicamentoAdapter;
import com.example.mobilesvc.R;
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

        GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL,false);
        binding.vMedicamentosList.setLayoutManager(glm);

        mViewModel.getMedicamentos().observe(getViewLifecycleOwner(), medicamentos -> {
            recetaAdapter = new MedicamentoAdapter(medicamentos,getContext(), getLayoutInflater());
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

        binding.vVolverMedicamentosList.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_medicamentosFragment_to_mainMenuFragment);
        });

        binding.vToMedicamentoCrear.setOnClickListener(v -> {
            int idReceta = getArguments() != null ? getArguments().getInt("idReceta") : -1;
            Bundle bundle = new Bundle();
            bundle.putInt("idReceta", idReceta);
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_medicamentosFragment_to_medicamentoCrearFragment, bundle);
        });

        int idReceta = getArguments() != null ? getArguments().getInt("idReceta") : -1;
        mViewModel.cargarMedicamentos(idReceta);

        return binding.getRoot();
    }

}