package com.example.mobilesvc.Vistas;

import static android.app.PendingIntent.getActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mobilesvc.R;
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

        v.vRegister.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
            //arreglar, quizas cambiar login a fragment y ya?
            //luego ir al main activity/vm
        });

    }

}