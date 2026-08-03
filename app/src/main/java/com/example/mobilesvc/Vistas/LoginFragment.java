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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mobilesvc.MainActivity;
import com.example.mobilesvc.R;
import com.example.mobilesvc.databinding.LoginViewBinding;

public class LoginFragment extends Fragment {

    public LiveData<Boolean> getLoginExitoso() {
        return login;
    }
    private LoginViewBinding b;
    private MutableLiveData<Boolean> login = new MutableLiveData<>();

    private LoginViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = LoginViewBinding.inflate(inflater, container, false);

        vm = new ViewModelProvider(this).get(LoginViewModel.class);

        b.vLogIn.setOnClickListener(v -> {
            vm.iniciarSesion(
                    b.vNombreLogin.getText().toString(),
                    b.vPasswordLogin.getText().toString()
            );
        });

        vm.getLoginExitoso().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginFragment_to_mainMenuFragment);
            }
        });

        vm.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        b.vRegister.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_usuarioCrearFragment);
        });

        return b.getRoot();
    }

}