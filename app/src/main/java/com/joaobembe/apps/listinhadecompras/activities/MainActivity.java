package com.joaobembe.apps.listinhadecompras.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.fragments.CarrinhoFragment;
import com.joaobembe.apps.listinhadecompras.fragments.ConfiguracoesFragment;
import com.joaobembe.apps.listinhadecompras.fragments.PaginalInicialFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    NavigationBarView navigationBarView;
    PaginalInicialFragment paginalInicialFragment = new PaginalInicialFragment();
    CarrinhoFragment carrinhoFragment = new CarrinhoFragment();
    ConfiguracoesFragment configuracoesFragment = new ConfiguracoesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.item_2);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        /*if (item.getItemId() == R.id.item_1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, paginalInicialFragment)
                    .commit();
            return true;
        }*/
        if (item.getItemId() == R.id.item_2) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, carrinhoFragment)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.item_3) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, configuracoesFragment)
                    .commit();
            return true;
        }
        return false;
    }
}