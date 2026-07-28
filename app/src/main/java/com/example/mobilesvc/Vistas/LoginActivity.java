package com.example.mobilesvc.Vistas;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilesvc.databinding.LoginViewBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewBinding v;
    private LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = LoginViewBinding.inflate(getLayoutInflater());
        setContentView(v.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);

        vm.getToastMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        v.vLogIn.setOnClickListener(b -> {
            vm.iniciarSesion(
                    v.vNombreLogin.getText().toString(),
                    v.vPasswordLogin.getText().toString()
            );
        });

    }

}