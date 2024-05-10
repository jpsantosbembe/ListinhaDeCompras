package com.joaobembe.apps.listinhadecompras.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joaobembe.apps.listinhadecompras.Database;
import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.adapter.HistoricoRecyclerViewAdapter;
import com.joaobembe.apps.listinhadecompras.adapter.ProdutoRecyclerViewAdapter;
import com.joaobembe.apps.listinhadecompras.model.Carrinho;
import com.joaobembe.apps.listinhadecompras.model.Produto;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracoesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfiguracoesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    HistoricoRecyclerViewAdapter historicoRecyclerViewAdapter;
    RecyclerView recyclerView;

    Database database;

    public ConfiguracoesFragment() {
        // Required empty public constructor
    }

    public static ConfiguracoesFragment newInstance(String param1, String param2) {
        ConfiguracoesFragment fragment = new ConfiguracoesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_configuracoes, container, false);

        database = new Database(rootView.getContext());

        recyclerView = rootView.findViewById(R.id.rvHistorico);

        ArrayList<Carrinho> carrinhos = new ArrayList<>();

        Cursor cursor = database.getCarrinhosFinalizados();

        while (cursor.moveToNext()) {
            Carrinho carrinho = new Carrinho();
            Cursor cursorProdutos = database.getComprasPorCarrinho(cursor.getInt(0));
            while (cursorProdutos.moveToNext()) {
                long id = cursorProdutos.getInt(0);
                String nome = cursorProdutos.getString(1);
                String gtin = cursorProdutos.getString(2);
                double preco = cursorProdutos.getDouble(3);
                int quantidade = cursorProdutos.getInt(4);
                int carrinho_id = cursorProdutos.getInt(7);
                carrinho.adicionarProduto(new Produto(id, nome, gtin, preco, quantidade, null, false, carrinho_id));
            }
            carrinhos.add(carrinho);
        }

        historicoRecyclerViewAdapter = new HistoricoRecyclerViewAdapter(getContext(), carrinhos);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(historicoRecyclerViewAdapter);

        return rootView;
    }
}