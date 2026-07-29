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
import com.example.mobilesvc.databinding.RecetaCrearViewBinding;

import java.util.Date;

public class RecetaCrearFragment extends Fragment {

    private RecetaCrearViewModel mViewModel;
    private RecetaCrearViewBinding binding;
    private ActivityResultLauncher<Intent> selector;
    private Intent intent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RecetaCrearViewBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(RecetaCrearViewModel.class);

        mViewModel.getRecetaMutable().observe(getViewLifecycleOwner(), receta -> {
            Bundle bundle = new Bundle();
            bundle.putInt("Receta ID", receta.getIDReceta());

            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_recetaCrearFragment_to_recetasFragment, bundle);
        });


        binding.vCrearReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fechaString = binding.vFechaCrear.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                try {
                    Date fecha = dateFormat.parse(fechaString);
                    mViewModel.crearNuevoReceta(fecha);
                } catch (ParseException e) {
                    binding.vFechaCrear.setError("Formato inválido (usar dd/MM/yyyy)");
                }
                //mViewModel.evaluarChipSeleccionado(chipsId);

            }
        });

        return binding.getRoot();
    }

}
