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
        Bundle bundle = new Bundle();
        mViewModel = new ViewModelProvider(this).get(RecordatorioViewModel.class);

        mViewModel.getRecordatorioMutable().observe(getViewLifecycleOwner(), r -> {
                binding.vCantidad.setText(String.valueOf(r.getCantidad()));
                binding.vIntervalo.setText(String.valueOf(r.getIntervalo()));

                bundle.putSerializable("recordatorio", r);

        });

        mViewModel.cargarRecordatorio(getArguments());

        binding.vVolverRecordatorio.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        binding.vToEditRecordatorio.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_recordatorioFragment_to_recordatorioEditFragment, bundle);
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}