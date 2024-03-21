package com.joaobembe.apps.listinhadecompras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.model.Produto;

import java.util.ArrayList;

public class ProdutoRecyclerViewAdapter extends RecyclerView.Adapter<ProdutoRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Produto> produtoArrayList;
    public ProdutoRecyclerViewAdapter(Context context, ArrayList<Produto> produtoArrayList){
        this.context = context;
        this.produtoArrayList = produtoArrayList;
    }
    @NonNull
    @Override
    public ProdutoRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.produto_recycler_view_row, parent, false);
        return new ProdutoRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvNomeProduto.setText(produtoArrayList.get(position).getNome());
        holder.tvCodProduto.setText(produtoArrayList.get(position).getGtin());
        holder.tvPrecoProduto.setText("R$" + String.valueOf(produtoArrayList.get(position).getPreco()));
        holder.tvQuantidadeProduto.setText(String.valueOf(produtoArrayList.get(position).getQuantidade()));
        holder.tvPrecoTotalProduto.setText("R$" + String.valueOf(produtoArrayList.get(position).getPrecoTotal()));

    }

    @Override
    public int getItemCount() {
        return produtoArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvNomeProduto, tvCodProduto, tvPrecoProduto, tvQuantidadeProduto, tvPrecoTotalProduto;
        ImageView ivProduto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvCodProduto = itemView.findViewById(R.id.tvCodProduto);
            tvPrecoProduto = itemView.findViewById(R.id.tvPrecoUnitario);
            tvQuantidadeProduto = itemView.findViewById(R.id.tvQuatidade);
            tvPrecoTotalProduto = itemView.findViewById(R.id.tvPreco);
            ivProduto = itemView.findViewById(R.id.ivProduto);
        }
    }
}
