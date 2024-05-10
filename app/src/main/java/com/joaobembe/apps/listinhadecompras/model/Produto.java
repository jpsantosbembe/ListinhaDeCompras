package com.joaobembe.apps.listinhadecompras.model;

import android.os.Parcelable;

public class Produto{
    long id;
    String nome;
    String gtin;
    double preco;
    int quantidade;
    double precoTotal;
    String fotoURL;
    boolean isVisible;
    int carrinho;

    public Produto(long id, String nome, String gtin, double preco, int quantidade, String fotoURL, boolean isVisible, int carrinho) {
        this.id = id;
        this.nome = nome;
        this.gtin = gtin;
        this.preco = preco;
        this.quantidade = quantidade;
        this.precoTotal = preco * quantidade;
        this.fotoURL = fotoURL;
        this.isVisible = isVisible;
        this.carrinho = carrinho;
    }

    public int getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(int carrinho) {
        this.carrinho = carrinho;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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
