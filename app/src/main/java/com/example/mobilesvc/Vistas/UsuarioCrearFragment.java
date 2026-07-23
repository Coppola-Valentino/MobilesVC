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

import com.google.android.material.chip.Chip;
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

            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_inmuebleNuevoFragment_to_inmueblesFragment, bundle);
        });


        binding.vCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String direccion = binding.etDireccion.getText().toString();
                String precio = binding.etPrecio.getText().toString();
                boolean comercial = binding.rbComercial.isChecked();
                boolean resindecial = binding.rbResidencial.isChecked();
                String ambiente = binding.srAmbientes.getSelectedItem().toString();
                boolean disponible = binding.cbDisponible.isChecked();
                String superficie = binding.etSuperficie.getText().toString();
                String latitud = binding.etSuperficie.getText().toString();
                String longitud = binding.etSuperficie.getText().toString();

                int chipsId = binding.chipGroupTipo.getCheckedChipId();

                mViewModel.evaluarChipSeleccionado(chipsId);
                mViewModel.crearNuevoInmueble(direccion, precio, comercial, resindecial, ambiente,
                        disponible, superficie, latitud, longitud);

            }
        });

        mViewModel.getUriFotoMutable().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivFotoInmueble.setImageURI(uri);
                binding.ivFotoIcono.setVisibility(View.GONE);
                binding.tvTocaFoto.setVisibility(View.GONE);
                binding.ivFotoInmueble.setVisibility(View.VISIBLE);
            }
        });

        abrirGaleria();
        return binding.getRoot();
    }

    //Quiero que la galeria se inicialice al tocar el boton
    private void abrirGaleria(){
        //Instanciamos el intent para abrir la galeria
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Instanciamos el selector
        selector = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult resultado) {
                        mViewModel.recibirFoto(resultado);
                        Log.d("galeria","onActivityResult"+resultado);
                    }
                });
    }

}