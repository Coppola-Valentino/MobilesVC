package com.example.mobilesvc.Vistas;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.RegisterViewBinding;

public class UsuarioCrearFragment extends Fragment {

    private UsuarioCrearViewModel mViewModel;
    private RegisterViewBinding binding;
    private ActivityResultLauncher<Intent> selector;
    private Intent intent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RegisterViewBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(UsuarioCrearViewModel.class);

        mViewModel.getUsuarioMutable().observe(getViewLifecycleOwner(), usuario -> {
            Bundle bundle = new Bundle();
            bundle.putInt("User ID", usuario.getIDUser());

//            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
//                    .navigate(R.id.action_usuarioCrearFragment_to_usuariosFragment, bundle);
        });


        binding.vCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.vNombreRegister.getText().toString();
                String password = binding.vPasswordRegister.getText().toString();
                String direccion = binding.vDireccionRegister.getText().toString();
                String dni = binding.vDniRegister.getText().toString();
                String email = binding.vEmailRegister.getText().toString();
                String genero = binding.vGeneroRegister.getText().toString();
                String Telefono = binding.vTelefonoRegister.getText().toString();
                int telefono = Telefono.isEmpty() ? 0 : Integer.parseInt(Telefono);
                String Edad = binding.vEdadRegister.getText().toString();
                int edad = Edad.isEmpty() ? 0 : Integer.parseInt(Edad);

                //mViewModel.evaluarChipSeleccionado(chipsId);
                mViewModel.crearNuevoUsuario(nombre, password, direccion, dni, email,
                        genero, telefono, edad);

            }
        });

        binding.vVolverRegister.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return binding.getRoot();
    }

}