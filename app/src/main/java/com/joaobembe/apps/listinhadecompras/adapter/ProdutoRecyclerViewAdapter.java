package com.joaobembe.apps.listinhadecompras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.RecyclerViewClickInterface;
import com.joaobembe.apps.listinhadecompras.model.Produto;

import java.util.ArrayList;

public class ProdutoRecyclerViewAdapter extends RecyclerView.Adapter<ProdutoRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Produto> produtoArrayList;
    RecyclerViewClickInterface recyclerViewClickInterface;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public ProdutoRecyclerViewAdapter(Context context, ArrayList<Produto> produtoArrayList, RecyclerViewClickInterface recyclerViewClickInterface){
        this.context = context;
        this.produtoArrayList = produtoArrayList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }
    @NonNull
    @Override
    public ProdutoRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.produto_recycler_view_row, parent, false);
        return new ProdutoRecyclerViewAdapter.MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvNomeProduto.setText(produtoArrayList.get(position).getNome());
        holder.tvCodProduto.setText(produtoArrayList.get(position).getGtin());
        holder.tvPrecoProduto.setText("R$" + String.valueOf(produtoArrayList.get(position).getPreco()));
        holder.tvQuantidadeProduto.setText(String.valueOf(produtoArrayList.get(position).getQuantidade()));
        holder.tvPrecoTotalProduto.setText("R$" + String.valueOf(produtoArrayList.get(position).getPrecoTotal()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (produtoArrayList.get(position).isVisible()){
                    holder.relOpt.setVisibility(View.GONE);
                    produtoArrayList.get(position).setVisible(false);
                } else {
                    holder.relOpt.setVisibility(View.VISIBLE);
                    produtoArrayList.get(position).setVisible(true);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return produtoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNomeProduto, tvCodProduto, tvPrecoProduto, tvQuantidadeProduto, tvPrecoTotalProduto;
        ImageView ivProduto;
        RelativeLayout relOpt;
        CardView cardView;

        Button btEditar, btRemover;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvCodProduto = itemView.findViewById(R.id.tvData);
            tvPrecoProduto = itemView.findViewById(R.id.tvPrecoUnitario);
            tvQuantidadeProduto = itemView.findViewById(R.id.tvQuantidadeItens);
            tvPrecoTotalProduto = itemView.findViewById(R.id.tvValorTotal);
            ivProduto = itemView.findViewById(R.id.ivProduto);
            relOpt = itemView.findViewById(R.id.relOpt);
            cardView = itemView.findViewById(R.id.cardView);
            btRemover = itemView.findViewById(R.id.btRemover);

            btRemover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
