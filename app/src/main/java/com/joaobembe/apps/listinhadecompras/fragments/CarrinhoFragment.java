package com.joaobembe.apps.listinhadecompras.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.activities.MainActivity;
import com.joaobembe.apps.listinhadecompras.adapter.ProdutoRecyclerViewAdapter;
import com.joaobembe.apps.listinhadecompras.model.Carrinho;
import com.joaobembe.apps.listinhadecompras.model.Produto;
import java.util.ArrayList;

public class CarrinhoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout linearLayout;
    MaterialDivider materialDivider;
    Carrinho carrinho = new Carrinho();
    ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter;
    RecyclerView recyclerView;
    FloatingActionButton button;
    TextView tvTotalLabel;
    TextView tvPrecoTotal;

    private String mParam1;
    private String mParam2;
    public CarrinhoFragment() {
        // Required empty public constructor
    }

    public static CarrinhoFragment newInstance(String param1, String param2) {
        CarrinhoFragment fragment = new CarrinhoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_carrinho, container, false);

        tvTotalLabel = rootView.findViewById(R.id.tvTotalLabel);
        tvPrecoTotal = rootView.findViewById(R.id.tvPrecoTotal);
        materialDivider = rootView.findViewById(R.id.mdPreco);
        button = rootView.findViewById(R.id.fbAdicionarProduto);
        recyclerView = rootView.findViewById(R.id.listaProdutosRecyclerView);
        linearLayout = rootView.findViewById(R.id.llCarrinhoVazio);

        //carrinho.adicionarProduto(new Produto("Teste", "78945612300", 25.99, 1,"www,w,,ww,"));

        produtoRecyclerViewAdapter = new ProdutoRecyclerViewAdapter(getContext(), carrinho.getProdutos());
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(produtoRecyclerViewAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                tvTotalLabel.setVisibility(View.VISIBLE);
                tvPrecoTotal.setVisibility(View.VISIBLE);
                materialDivider.setVisibility(View.VISIBLE);
                carrinho.adicionarProduto(new Produto("Teste", "78945612300", 25.99, 1,"www,w,,ww,"));
                produtoRecyclerViewAdapter.notifyItemInserted(produtoRecyclerViewAdapter.getItemCount() -1);
                recyclerView.smoothScrollToPosition(produtoRecyclerViewAdapter.getItemCount() - 1);
                tvPrecoTotal.setText(String.valueOf(carrinho.calcularValorTotal()));

                final Dialog dialog = new Dialog(rootView.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.botton_sheet_adicionar);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
        return rootView;
    }
}