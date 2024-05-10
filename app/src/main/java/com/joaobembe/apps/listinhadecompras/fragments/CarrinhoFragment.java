package com.joaobembe.apps.listinhadecompras.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joaobembe.apps.listinhadecompras.Database;
import com.joaobembe.apps.listinhadecompras.R;
import com.joaobembe.apps.listinhadecompras.RecyclerViewClickInterface;
import com.joaobembe.apps.listinhadecompras.activities.MainActivity;
import com.joaobembe.apps.listinhadecompras.activities.ResumoCompraActivity;
import com.joaobembe.apps.listinhadecompras.activities.ScanActivity;
import com.joaobembe.apps.listinhadecompras.activities.StartActivity;
import com.joaobembe.apps.listinhadecompras.adapter.ProdutoRecyclerViewAdapter;
import com.joaobembe.apps.listinhadecompras.model.Carrinho;
import com.joaobembe.apps.listinhadecompras.model.Produto;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Date;

public class CarrinhoFragment extends Fragment implements RecyclerViewClickInterface {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialDivider materialDivider;
    Carrinho carrinho = new Carrinho();
    ProdutoRecyclerViewAdapter produtoRecyclerViewAdapter;
    RecyclerView recyclerView;
    FloatingActionButton button;
    FloatingActionButton buttonFechar;
    TextView tvTotalLabel;
    TextView tvPrecoTotal;


    Database database;

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

        if (!carrinho.getProdutos().isEmpty()) {
            System.out.println("oi");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carrinho, container, false);


        tvTotalLabel = rootView.findViewById(R.id.tvTotalLabel);
        tvPrecoTotal = rootView.findViewById(R.id.tvPrecoTotal);
        materialDivider = rootView.findViewById(R.id.mdPreco);
        button = rootView.findViewById(R.id.fbAdicionar);
        buttonFechar = rootView.findViewById(R.id.fbFecharCarrinho);
        recyclerView = rootView.findViewById(R.id.listaProdutosRecyclerView);
        produtoRecyclerViewAdapter = new ProdutoRecyclerViewAdapter(getContext(), carrinho.getProdutos(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(produtoRecyclerViewAdapter);
        produtoRecyclerViewAdapter.setOnItemClickListener(new ProdutoRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                database.removeProduto(carrinho.getProdutos().get(position).getId());
                carrinho.removerProduto(carrinho.getProdutos().get(position));
                produtoRecyclerViewAdapter.notifyItemRemoved(position);
                tvPrecoTotal.setText(String.valueOf(carrinho.calcularValorTotal()));
            }
        });

        database = new Database(rootView.getContext());

        buttonFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.fecharCarrinho(carrinho);
                Intent intent = new Intent(getContext(), ResumoCompraActivity.class);
                intent.putExtra("carrinho", carrinho.getProdutos().get(0).getCarrinho());
                startActivity(intent);
                getActivity().finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(rootView.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.botton_sheet_adicionar);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);

                //se escanear qr code
                LinearLayout linearLayoutScan = dialog.findViewById(R.id.llScan);
                linearLayoutScan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ScanOptions options = new ScanOptions();
                        options.setPrompt("Escanear produto");
                        options.setBeepEnabled(true);
                        options.setOrientationLocked(true);
                        options.setCaptureActivity(ScanActivity.class);
                        scanLauncher.launch(options);
                        dialog.dismiss();
                    }
                });

                LinearLayout linearLayoutManual = dialog.findViewById(R.id.llManual);
                linearLayoutManual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialogManual = new Dialog(rootView.getContext());
                        dialogManual.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogManual.setContentView(R.layout.adicionar_manual_dialog);
                        dialogManual.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialogManual.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogManual.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                        EditText qtde = dialogManual.findViewById(R.id.etQtde);
                        ImageButton ibMenos = dialogManual.findViewById(R.id.ibMenos);
                        ImageButton ibMais = dialogManual.findViewById(R.id.ibMais);

                        ibMenos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (Integer.parseInt(qtde.getText().toString()) > 1 ) {
                                    qtde.setText(String.valueOf(Integer.parseInt(qtde.getText().toString()) - 1));
                                }
                            }
                        });

                        ibMais.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                qtde.setText(String.valueOf(Integer.parseInt(qtde.getText().toString()) + 1));
                            }
                        });

                        Button buttonAdicionar = dialogManual.findViewById(R.id.buttonManual);
                        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText etNomeProduto = dialogManual.findViewById(R.id.etNomeProduto);
                                EditText etCodigoProduto = dialogManual.findViewById(R.id.etCodigoProduto);
                                EditText etPrecoProduto = dialogManual.findViewById(R.id.etPrecoProduto);
                                EditText etQtde = dialogManual.findViewById(R.id.etQtde);


                                int ultimoCarrinhoAtivo = 1;
                                Date date = new Date();

                                Cursor cursor = database.getUltimoCarrinhoFinalizado();
                                if (cursor.moveToNext()) {
                                    if (cursor.getInt(0) == 0) {
                                        ultimoCarrinhoAtivo = 1;
                                    } else {
                                        ultimoCarrinhoAtivo = cursor.getInt(0) + 1;
                                    }
                                }

                                long produtoId = database.insertData(etNomeProduto.getText().toString(),
                                        etCodigoProduto.getText().toString(),
                                        Double.parseDouble(etPrecoProduto.getText().toString()),
                                        Integer.parseInt(etQtde.getText().toString()),
                                        Double.parseDouble(etPrecoProduto.getText().toString()) * Integer.parseInt(etQtde.getText().toString()),
                                        null,
                                        ultimoCarrinhoAtivo,
                                        1,
                                        date.toString());

                                carrinho.adicionarProduto(new Produto(
                                        produtoId,
                                        etNomeProduto.getText().toString(),
                                        etCodigoProduto.getText().toString(),
                                        Double.parseDouble(etPrecoProduto.getText().toString()),
                                        Integer.parseInt(etQtde.getText().toString()),
                                        null,
                                        false,
                                        ultimoCarrinhoAtivo));

                                produtoRecyclerViewAdapter.notifyItemInserted(produtoRecyclerViewAdapter.getItemCount() -1);
                                //recyclerView.smoothScrollToPosition(produtoRecyclerViewAdapter.getItemCount() - 1);
                                tvPrecoTotal.setText(String.valueOf(carrinho.calcularValorTotal()));
                                dialogManual.dismiss();
                                tvTotalLabel.setVisibility(View.VISIBLE);
                                tvPrecoTotal.setVisibility(View.VISIBLE);
                                materialDivider.setVisibility(View.VISIBLE);
                                buttonFechar.setVisibility(View.VISIBLE);
                            }
                        });

                        dialogManual.show();
                        dialog.dismiss();
                    }
                });

                ImageView imageViewFechar = dialog.findViewById(R.id.cancelButton);
                imageViewFechar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return rootView;
    }

    ActivityResultLauncher<ScanOptions> scanLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {

            Dialog dialogManual = new Dialog(getContext());
            dialogManual.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogManual.setContentView(R.layout.adicionar_manual_dialog);
            dialogManual.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogManual.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogManual.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            EditText etCodigoProduto = dialogManual.findViewById(R.id.etCodigoProduto);
            etCodigoProduto.setText(result.getContents().toString());
            dialogManual.show();
            Button buttonAdicionar = dialogManual.findViewById(R.id.buttonManual);
            buttonAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText etNomeProduto = dialogManual.findViewById(R.id.etNomeProduto);
                    EditText etPrecoProduto = dialogManual.findViewById(R.id.etPrecoProduto);
                    EditText etQtde = dialogManual.findViewById(R.id.etQtde);

                    int ultimoCarrinhoAtivo = 1;
                    Date date = new Date();

                    Cursor cursor = database.getUltimoCarrinhoFinalizado();
                    if (cursor.moveToNext()) {
                        if (cursor.getInt(0) == 0) {
                            ultimoCarrinhoAtivo = 1;
                        } else {
                            ultimoCarrinhoAtivo = cursor.getInt(0) + 1;
                        }
                    }

                    long produtoId = database.insertData(etNomeProduto.getText().toString(),
                            etCodigoProduto.getText().toString(),
                            Double.parseDouble(etPrecoProduto.getText().toString()),
                            1,
                            Double.parseDouble(etPrecoProduto.getText().toString()) * 1,
                            null,
                            ultimoCarrinhoAtivo,
                            1,
                            date.toString()
                    );

                    carrinho.adicionarProduto(new Produto(
                            produtoId,
                            etNomeProduto.getText().toString(),
                            etCodigoProduto.getText().toString(),
                            Double.parseDouble(etPrecoProduto.getText().toString()),
                            Integer.parseInt(etQtde.getText().toString()),
                            null,
                            false,
                            ultimoCarrinhoAtivo));

                    produtoRecyclerViewAdapter.notifyItemInserted(produtoRecyclerViewAdapter.getItemCount() -1);
                    //recyclerView.smoothScrollToPosition(produtoRecyclerViewAdapter.getItemCount() - 1);
                    tvPrecoTotal.setText(String.valueOf(carrinho.calcularValorTotal()));
                    dialogManual.dismiss();
                    tvTotalLabel.setVisibility(View.VISIBLE);
                    tvPrecoTotal.setVisibility(View.VISIBLE);
                    materialDivider.setVisibility(View.VISIBLE);
                    buttonFechar.setVisibility(View.VISIBLE);
                }
            });
            //produtoRecyclerViewAdapter.notifyItemInserted(produtoRecyclerViewAdapter.getItemCount() -1);
            //recyclerView.smoothScrollToPosition(produtoRecyclerViewAdapter.getItemCount() - 1);
            tvPrecoTotal.setText(String.valueOf(carrinho.calcularValorTotal()));
        }
    });

    @Override
    public void onItemClick(int position) {
        System.out.println("TESTEEEEEEEEEEEEEE" +
                "EEE\noioiiiiiiiiiiiiiiiiiiiiiiiii\nqeqeravahbahjbahjab");

    }

    @Override
    public void onLongItemClick(int position) {

    }
}