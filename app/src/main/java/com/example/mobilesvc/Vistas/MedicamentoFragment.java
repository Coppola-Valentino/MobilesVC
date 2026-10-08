package com.example.mobilesvc.Vistas;

import static android.view.View.INVISIBLE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mobilesvc.Api.ApiClient;
import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.Clases.Receta;
import com.example.mobilesvc.databinding.MedicamentoViewBinding;
import com.example.mobilesvc.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoFragment extends Fragment {
    private Context context;
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
                binding.vMarca.setText(m.getMarca());
                bundle.putSerializable("medicamento", m);
                bundle.putInt("idMedicamento", m.getIDMedicamento());
                bundle.putInt("idReceta", m.getRecID());
//                bundle.putInt("idUsuario", mViewModel.getRecetaMutable().getValue().getPacID());
        });

        mViewModel.cargarMedicamento(getArguments());

        binding.vVolverMedicamento.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_medicamentoFragment_to_medicamentosFragment, bundle);
        });
        if (ApiClient.obtenerUsuarioRol(requireContext()).equals("Adulto Responsable")) {
            binding.vToMedicamentoEdit.setVisibility(View.VISIBLE);
            binding.vToMedicamentoEdit.setOnClickListener(v -> {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                        .navigate(R.id.action_medicamentoFragment_to_medicamentoEditFragment, bundle);
            });
        } else {
            binding.vToMedicamentoEdit.setVisibility(View.GONE);
            binding.vToMedicamentoEdit.setClickable(false);
        }

        binding.vToRecordatorioCrear2.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_medicamentoFragment_to_recordatorioCrearFragment, bundle);
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}