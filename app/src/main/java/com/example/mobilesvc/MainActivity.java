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
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = MainMenuViewBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setSupportActionBar(b.appBarMain.toolbar);

        initNavigation();
        initDrawerMenu();

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);

        vm.getToastMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        vm.getUsuario().observe(this, p -> {
            cambiarEncabezado(p.getNombre(), p.getDni(), p.getEmail());
        });
    }

    @SuppressLint("SetTextI18n")
    private void cambiarEncabezado(String nombre, String dni, String email) {
        View headerView = b.navView.getHeaderView(0);

        TextView vNombreBar = headerView.findViewById(R.id.vNombreBar);
        TextView vEmailBar = headerView.findViewById(R.id.vEmailBar);
        TextView vDniBar = headerView.findViewById(R.id.vDniBar);

        vNombreBar.setText(nombre);
        vEmailBar.setText(email);
        vDniBar.setText(dni);
    }
    private void initNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        if (navHostFragment == null) return;

        navController = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.MainMenuViewFragment,
                R.id.UsuarioFragment,
                R.id.RecordatoriosFragment,
                R.id.RecetasFragment,
        )
                .setOpenableLayout(b.drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(
                this,
                navController,
                appBarConfiguration
        );
    }
    private void initDrawerMenu() {
        b.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                cargarUsuario();
            }
        });

        b.navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                showLogoutDialog();
                return true;
            }
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if (handled) {
                b.drawerLayout.closeDrawers();
            }
            return handled;
        });
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

        b.drawerLayout.closeDrawers();
    }
    private void logout() {
        ApiClient.eliminarCredenciales(getApplication());
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void cargarUsuario() {
        vm.cargarUsuario();
    }
}