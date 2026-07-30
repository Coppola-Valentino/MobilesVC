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

import com.example.mobilesvc.Clases.Usuario;
import com.example.mobilesvc.databinding.UserViewBinding;
import com.example.mobilesvc.R;

public class UsuarioFragment extends Fragment {

    private UsuarioViewModel mViewModel;
    private UserViewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = UserViewBinding.inflate(inflater, container, false);
        Bundle bundle = new Bundle();
        mViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        mViewModel.getUsuarioMutable().observe(getViewLifecycleOwner(), u -> {
                binding.vNombre.setText(u.getNombre());
                binding.vPassword.setText(u.getPassword());
                binding.vDireccion.setText(u.getDireccion());
                binding.vTelefono.setText(String.valueOf(u.getTelefono()));
                binding.vEmail.setText(u.getEmail());
                binding.vDni.setText(u.getDni());
                binding.vGenero.setText(u.getGenero());
                binding.vEdad.setText(String.valueOf(u.getEdad()));

                bundle.putSerializable("usuario", u);
        });

        mViewModel.cargarUsuario(getArguments());

        binding.vVolverPerfil.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        binding.vToEditUsuario.setOnClickListener(v -> {
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
