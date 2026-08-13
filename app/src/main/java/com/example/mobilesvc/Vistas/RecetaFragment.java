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
import androidx.navigation.Navigation;

import com.example.mobilesvc.Clases.Receta;
import com.example.mobilesvc.databinding.RecetaViewBinding;
import com.example.mobilesvc.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class RecetaFragment extends Fragment {

    private RecetaViewModel mViewModel;
    private RecetaViewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RecetaViewBinding.inflate(inflater, container, false);
        Bundle bundle = new Bundle();
        mViewModel = new ViewModelProvider(this).get(RecetaViewModel.class);

        mViewModel.getRecetaMutable().observe(getViewLifecycleOwner(), r -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            binding.vFecha.setText(dateFormat.format(r.getFecha()));
                bundle.putSerializable("receta", r);
                bundle.putInt("idReceta", r.getIDReceta());

        });

        mViewModel.cargarReceta(getArguments());

        binding.vVolverReceta.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_recetaFragment_to_recetasFragment);
        });

        binding.vToRecetaEdit.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_recetaFragment_to_recetaEditFragment, bundle);
        });

        binding.vToMedicamentosList.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_recetaFragment_to_medicamentosFragment, bundle);
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}