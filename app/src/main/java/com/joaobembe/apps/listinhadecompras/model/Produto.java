package com.joaobembe.apps.listinhadecompras.model;

import android.os.Parcelable;

public class Produto{
    String nome;
    String gtin;
    double preco;
    int quantidade;
    double precoTotal;
    String fotoURL;

    public Produto(String nome, String gtin, double preco, int quantidade, String fotoURL) {
        this.nome = nome;
        this.gtin = gtin;
        this.preco = preco;
        this.quantidade = quantidade;
        this.precoTotal = preco * quantidade;
        this.fotoURL = fotoURL;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
        this.precoTotal = preco * quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.precoTotal = preco * quantidade;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public String getFotoURL() {
        return fotoURL;
    }

    public void setFotoURL(String fotoURL) {
        this.fotoURL = fotoURL;
    }
}
