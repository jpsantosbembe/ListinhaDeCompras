package com.joaobembe.apps.listinhadecompras.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.adapter.ProdutoRecyclerViewAdapter;
import com.joaobembe.apps.listinhadecompras.model.Produto;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarrinhoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarrinhoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public CarrinhoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarrinhoFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_carrinho, container, false);

        FloatingActionButton button = rootView.findViewById(R.id.fbAdicionarProduto);
        RecyclerView recyclerView = rootView.findViewById(R.id.listaProdutosRecyclerView);

        ArrayList<Produto> produtoList = new ArrayList<>();
        produtoList.add(new Produto("Teste", "78945612300", 25.99, 1,"www,w,,ww,"));

        ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter;

        produtoRecyclerViewAdapter = new ProdutoRecyclerViewAdapter(getContext(), produtoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(produtoRecyclerViewAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtoList.add(new Produto("Teste", "78945612300", 25.99, 1,"www,w,,ww,"));
                produtoRecyclerViewAdapter.notifyItemInserted(produtoRecyclerViewAdapter.getItemCount() -1);
                recyclerView.smoothScrollToPosition(produtoRecyclerViewAdapter.getItemCount() - 1);
            }
        });
        return rootView;
    }
}