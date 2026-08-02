package com.example.mobilesvc.Vistas;

import static android.app.PendingIntent.getActivity;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mobilesvc.MainActivity;
import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.LoginViewBinding;

public class LoginFragment extends Fragment {

    private LoginViewBinding b;
    private LoginViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = LoginViewBinding.inflate(inflater, container, false);

        b.vLogIn.setOnClickListener(v -> {
            vm.iniciarSesion(
                    b.vNombreLogin.getText().toString(),
                    b.vPasswordLogin.getText().toString()
            );
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_mainMenuFragment);
        });

        b.vRegister.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_usuarioCrearFragment);
        });

        return b.getRoot();
    }

}