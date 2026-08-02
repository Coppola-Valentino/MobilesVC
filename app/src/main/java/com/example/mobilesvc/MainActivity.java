package com.example.mobilesvc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesvc.databinding.ContentMainBinding;
import com.example.mobilesvc.databinding.MainMenuViewBinding;
import com.example.mobilesvc.Api.ApiClient;
import com.example.mobilesvc.Vistas.LoginFragment;

public class MainActivity extends AppCompatActivity {
    private MainMenuViewBinding b;
    private ContentMainBinding binding;
    private MainViewModel vm;
    //private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ContentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initNavigation();
        //initDrawerMenu();

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);

        vm.getToastMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        vm.getUsuario().observe(this, p -> {
           b.vCurrentUser.setText(p.getNombre());
        });
//
//        b.vPacientes.setOnClickListener(b -> {
//            Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
//                    .navigate(R.id.action_mainActivity_to_usuariosFragment);
//
//        });
//
//        b.vRecetas.setOnClickListener(b -> {
//            Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
//                    .navigate(R.id.action_mainActivity_to_recetasFragment);
//
//        });
//
//        b.vLogOut.setOnClickListener(b -> {
//            showLogoutDialog();
//        });

    }
    private void initNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        if (navHostFragment == null) return;

        navController = navHostFragment.getNavController();
    }
    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("¿Estas seguro que quierés cerrar sesion?")
                .setPositiveButton("Si", (dialog, which) -> {
                    logout();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }
    private void logout() {
        ApiClient.eliminarCredenciales(getApplication());
        Intent i = new Intent(MainActivity.this, LoginFragment.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    public void cargarUsuario() {
        vm.cargarUsuario();
    }
    }