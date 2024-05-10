package com.joaobembe.apps.listinhadecompras.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joaobembe.apps.listinhadecompras.Database;
import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.adapter.ResumoRecyclerViewAdapter;
import com.joaobembe.apps.listinhadecompras.model.Carrinho;
import com.joaobembe.apps.listinhadecompras.model.Produto;

public class ResumoCompraActivity extends AppCompatActivity {

    Database database;
    RecyclerView recyclerView;
    ResumoRecyclerViewAdapter resumoRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_compra);

        recyclerView = findViewById(R.id.rvHistorico);

        Intent intent = getIntent();

        int carrinho_id = intent.getIntExtra("carrinho", 0);

        Carrinho carrinho = new Carrinho();

        database = new Database(ResumoCompraActivity.this);

        Cursor cursor = database.getComprasPorCarrinho(carrinho_id);

        while (cursor.moveToNext()) {
            long id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String gtin = cursor.getString(2);
            double preco = cursor.getDouble(3);
            int quantidade = cursor.getInt(4);

            carrinho.adicionarProduto(new Produto(id, nome, gtin, preco, quantidade, null, false, 1));
        }

        resumoRecyclerViewAdapter = new ResumoRecyclerViewAdapter(ResumoCompraActivity.this, carrinho.getProdutos());
        recyclerView.setLayoutManager(new LinearLayoutManager(ResumoCompraActivity.this));
        recyclerView.setAdapter(resumoRecyclerViewAdapter);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(ResumoCompraActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}