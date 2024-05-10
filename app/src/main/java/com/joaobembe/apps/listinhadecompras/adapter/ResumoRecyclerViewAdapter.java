package com.joaobembe.apps.listinhadecompras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.model.Produto;

import java.util.ArrayList;

public class ResumoRecyclerViewAdapter extends RecyclerView.Adapter<ResumoRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Produto> produtoArrayList;

    public ResumoRecyclerViewAdapter(Context context, ArrayList<Produto> produtoArrayList) {
        this.context = context;
        this.produtoArrayList = produtoArrayList;
    }

    @NonNull
    @Override
    public ResumoRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.resumo_recycler_view_row, parent, false);
        return new ResumoRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumoRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.idItem.setText("00" + String.valueOf(position));
        holder.tvCodProduto.setText(produtoArrayList.get(position).getGtin());
        holder.tvNomeProduto.setText(produtoArrayList.get(position).getNome());
        holder.tvQuantidade.setText(String.valueOf(produtoArrayList.get(position).getQuantidade()));
        holder.tvPreco.setText("R$" + String.valueOf(produtoArrayList.get(position).getPreco() * produtoArrayList.get(position).getQuantidade()));
    }

    @Override
    public int getItemCount() {
        return produtoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idItem, tvCodProduto, tvNomeProduto, tvQuantidade, tvPreco;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idItem = itemView.findViewById(R.id.idItem);
            tvCodProduto = itemView.findViewById(R.id.tvData);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidadeItens);
            tvPreco = itemView.findViewById(R.id.tvValorTotal);
        }
    }
}
