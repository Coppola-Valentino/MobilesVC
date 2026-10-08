package com.example.mobilesvc.Vistas;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

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
import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.Clases.Receta;
import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.RecordatorioCrearViewBinding;
import com.google.android.material.timepicker.TimeFormat;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.SimpleTimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            bundle.putInt("idUsuario", recordatorio.getUserID());

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
                //Time intervalo = Intervalo.isEmpty() ? null : Time.valueOf(Intervalo);
                //int UserID = ApiClient.obtenerUsuarioId(requireContext());
//                Receta rec2 = mViewModel.getRecetaMutable().getValue();
                int UserID = -1;
                if (mViewModel.getRecetaMutable().getValue() != null) {
                    UserID =  mViewModel.getRecetaMutable().getValue().getPacID();
                } else {
                    UserID = ApiClient.obtenerUsuarioId(requireContext());;
                }
                int MedID = -1;
                if (getArguments() != null && getArguments().containsKey("idMedicamento")) {
                    MedID = getArguments().getInt("idMedicamento");
                }

                if (cantidad < 1 || cantidad > 99) {
                    binding.vCantidadCrear.setError("la Cantidad no puede ser menor que 1 o mayor que 99");
                    return;
                }

                rec.setCantidad(cantidad);
                rec.setIntervalo(binding.vIntervaloCrear.getText().toString());
                rec.setUserID(UserID);
                rec.setMedicamentoID(MedID);
                rec.setEstado(1);

                ApiClient.MiServicio servicio = ApiClient.getServicio();
                String token = ApiClient.obtenerToken(getApplication(mViewModel));
                Call<Medicamento> call2 = servicio.getMedicamentoPorId(token, MedID);

                call2.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                        if (response.isSuccessful()) {
                            Medicamento m = response.body();
                            Call<Receta> call3 = servicio.getRecetaPorId(token, m.getRecID());

                            call3.enqueue(new Callback<>() {
                                @Override
                                public void onResponse(Call<Receta> call, Response<Receta> response) {
                                    if (response.isSuccessful()) {
                                        SimpleDateFormat a = new SimpleDateFormat("hh:mm:dd", Locale.getDefault());;
                                        rec.setUserID(response.body().getPacID());
                                        try {
                                            a.parse(Intervalo);
                                            mViewModel.crearNuevoRecordatorio(rec);
                                        }catch(ParseException e){
                                            binding.vIntervaloCrear.setError("Formato inválido (usar hh:mm:ss)");
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<Receta> call, Throwable t) {
                                    Log.e("API_ERROR", "Fallo en Medicamento (receta): " + t.getMessage());
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<Medicamento> call, Throwable t) {
                        Log.e("API_ERROR", "Fallo en Medicamento (medicamento): " + t.getMessage());
                    }
                });

                //mViewModel.evaluarChipSeleccionado(chipsId);

            }
        });

        binding.vVolverCrearRecordatorio.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return binding.getRoot();
    }

}