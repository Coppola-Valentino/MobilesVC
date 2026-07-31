package com.example.mobilesvc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilesvc.databinding.MainMenuViewBinding;
import com.example.mobilesvc.Api.ApiClient;
import com.example.mobilesvc.Vistas.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private MainMenuViewBinding b;
    private MainViewModel vm;
    //private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = MainMenuViewBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        initNavigation();
        //initDrawerMenu();

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);

        vm.getToastMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        vm.getUsuario().observe(this, p -> {
           b.vCurrentUser.setText(p.getNombre());
        });
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
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
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