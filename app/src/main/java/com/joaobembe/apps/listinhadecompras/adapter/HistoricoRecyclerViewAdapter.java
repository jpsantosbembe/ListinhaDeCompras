package com.joaobembe.apps.listinhadecompras.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.joaobembe.apps.listinhadecompras.Database;
import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.activities.ResumoCompraActivity;
import com.joaobembe.apps.listinhadecompras.model.Carrinho;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoricoRecyclerViewAdapter extends RecyclerView.Adapter<HistoricoRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Carrinho> carrinhoArrayList;

    public HistoricoRecyclerViewAdapter(Context context, ArrayList<Carrinho> carrinhoArrayList) {
        this.context = context;
        this.carrinhoArrayList = carrinhoArrayList;
    }

    @NonNull
    @Override
    public HistoricoRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.historico_recycler_view_row, parent, false);
        return new HistoricoRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoRecyclerViewAdapter.MyViewHolder holder, int position) {

        Database database = new Database(context);

        Cursor cursor = database.getComprasPorCarrinho(position+1);

        if (cursor.moveToNext()) {
            String dateString = cursor.getString(9);

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            try {
                Date date = dateFormat.parse(dateString);
                String formattedDate = outputFormat.format(date);
                holder.tvData.setText(formattedDate);
            } catch ( ParseException e) {
                e.printStackTrace();
            }
        }

        holder.idHistorico.setText("00" + String.valueOf(position));
        holder.tvQuantidadeItens.setText(String.valueOf(carrinhoArrayList.get(position).getProdutos().size()) + " itens");
        holder.tvValorTotal.setText("R$" + String.valueOf(carrinhoArrayList.get(position).calcularValorTotal()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ResumoCompraActivity.class);
                intent.putExtra("carrinho", carrinhoArrayList.get(position).getProdutos().get(0).getCarrinho());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return carrinhoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idHistorico, tvData, tvQuantidadeItens, tvValorTotal;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idHistorico = itemView.findViewById(R.id.idHistorico);
            tvData = itemView.findViewById(R.id.tvData);
            tvQuantidadeItens = itemView.findViewById(R.id.tvQuantidadeItens);
            tvValorTotal = itemView.findViewById(R.id.tvValorTotal);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
