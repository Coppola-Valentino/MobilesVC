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
        Bundle bundle = new Bundle();
        mViewModel = new ViewModelProvider(this).get(MedicamentoViewModel.class);

        mViewModel.getMedicamentoMutable().observe(getViewLifecycleOwner(), m -> {
                binding.vNombreMedicamento.setText(m.getNombre());
                binding.vCantidadMedicamento.setText(String.valueOf(m.getCantidad()));
                binding.vIntervaloMedicamento.setText(String.valueOf(m.getIntervalo()));
                binding.vDosis.setText(String.valueOf(m.getDosis()));
                bundle.putSerializable("medicamento", m);

        });

        mViewModel.cargarMedicamento(getArguments());

        binding.vVolverMedicamento.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        binding.vToMedicamentoEdit.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_usuarioFragment_to_usuarioEditFragment, bundle);
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}