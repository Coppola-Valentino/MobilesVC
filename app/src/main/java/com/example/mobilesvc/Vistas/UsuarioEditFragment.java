package com.example.mobilesvc.Vistas;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mobilesvc.Api.ApiClient;
import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.UserEditViewBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UsuarioEditFragment extends Fragment {

    private UsuarioEditViewModel vm;
    private UserEditViewBinding b;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(UsuarioEditViewModel.class);
        b = UserEditViewBinding.inflate(getLayoutInflater());

        vm.cargarUsuario(getArguments());

        vm.getUsuario().observe(getViewLifecycleOwner(), m -> {
            if (m != null) {
                b.vNombreEdit.setText(m.getNombre());
//                b.vPasswordEdit.setText(m.getPassword());
                b.vDireccionEdit.setText(m.getDireccion());
                b.vDniEdit.setText(m.getDni());
                b.vEdadEdit.setText(String.valueOf(m.getEdad()));
                b.vTelefonoEdit.setText(String.valueOf(m.getTelefono()));
                b.vGeneroEdit.setText(m.getGenero());
                b.vEmailEdit.setText(m.getEmail());
                String[] roles = new String[] {"Paciente", "Medico", "Admin"};

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        roles
                );

                b.vRolEdit.setAdapter(adapter);
                switch(m.getRol()){
                        case "Admin":
                            b.vRolEdit.setText(roles[2], false);
                            break;
                        case "Medico":
                            b.vRolEdit.setText(roles[1], false);
                            break;
                        case "Paciente":
                            b.vRolEdit.setText(roles[0], false);
                            break;
                }

            }
        });

        if(ApiClient.obtenerUsuarioRol(requireContext()).equals("Paciente") ||ApiClient.obtenerUsuarioRol(requireContext()).equals("Medico")){
            b.vRolEdit.setVisibility(View.GONE);
            b.textInputLayout.setVisibility(View.GONE);
            b.vRolEdit.setClickable(false);
            b.textInputLayout.setClickable(false);
        }

        vm.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        vm.getDatosCambiados().observe(getViewLifecycleOwner(), result -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });


        b.vEditUsuario.setOnClickListener(v -> {
            String Telefono = b.vTelefonoEdit.getText().toString();
            int telefono = Telefono.isEmpty() ? 0 : Integer.parseInt(Telefono);
            String Edad = b.vEdadEdit.getText().toString();
            int edad = Edad.isEmpty() ? 0 : Integer.parseInt(Edad);
            String rol = b.vRolEdit.getText().toString();
            vm.cambiarDatos(
                    edad,
                    telefono,
                    b.vNombreEdit.getText().toString(),
//                    b.vPasswordEdit.getText().toString(),
                    b.vDireccionEdit.getText().toString(),
                    b.vGeneroEdit.getText().toString(),
                    b.vEmailEdit.getText().toString(),
                    b.vDniEdit.getText().toString(),
                    rol
            );
        });

        b.vVolverUserEdit.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return b.getRoot();
    }
}