package com.example.mobilesvc.Vistas;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilesvc.Api.ApiClient;
import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.RecordatorioCrearViewBinding;

public class RecordatorioCrearFragment extends Fragment {

    private RecordatorioCrearViewModel mViewModel;
    private RecordatorioCrearViewBinding binding;
    private ActivityResultLauncher<Intent> selector;
    private Intent intent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RecordatorioCrearViewBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(RecordatorioCrearViewModel.class);

        mViewModel.getRecordatorioMutable().observe(getViewLifecycleOwner(), recordatorio -> {
            Bundle bundle = new Bundle();
            bundle.putInt("Recordatorio ID", recordatorio.getIDRec());

            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_recordatorioCrearFragment_to_recordatoriosFragment, bundle);
        });


        binding.vCrearRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Recordatorio rec = new Recordatorio();
                String Cantidad = binding.vCantidadCrear.getText().toString();
                int cantidad = Cantidad.isEmpty() ? 0 : Integer.parseInt(Cantidad);
                String Intervalo = binding.vIntervaloCrear.getText().toString();
                int intervalo = Intervalo.isEmpty() ? 0 : Integer.parseInt(Intervalo);
                int UserID = ApiClient.obtenerUsuarioId(requireContext());
                int MedID = -1;
                if (getArguments() != null && getArguments().containsKey("idMedicamento")) {
                    MedID = getArguments().getInt("idMedicamento");
                }

                rec.setCantidad(cantidad);
                rec.setIntervalo(intervalo);
                rec.setUserID(UserID);
                rec.setMedicamentoID(MedID);

                //mViewModel.evaluarChipSeleccionado(chipsId);
                mViewModel.crearNuevoRecordatorio(rec);

            }
        });

        binding.vVolverCrearRecordatorio.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return binding.getRoot();
    }

}