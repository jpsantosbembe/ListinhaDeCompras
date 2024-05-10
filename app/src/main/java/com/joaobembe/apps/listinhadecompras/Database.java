package com.joaobembe.apps.listinhadecompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.joaobembe.apps.listinhadecompras.model.Carrinho;
import com.joaobembe.apps.listinhadecompras.model.Produto;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "my_database";
    public static final int DB_VERSION = 1;
    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE PRODUTO (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, GTIN TEXT, PRECO REAL, QUANTIDADE INTEGER, PRECO_TOTAL REAL, FOTO_URL TEXT, ID_CARRINHO INTEGER, STATUS_CARRINHO INTEGER, DATA_CARRINHO TEXT);");
        //sqLiteDatabase.execSQL("CREATE TABLE CARRINHO (ID_CARRINHO INTEGER PRIMARY KEY AUTOINCREMENT, DATA_FINALIZACAO TEXT, STATUS TEXT);");
        //sqLiteDatabase.execSQL("CREATE TABLE PRODUTO_CARRINHO (COLUMN_ID_PRODUTO_CARRINHO INTEGER PRIMARY KEY AUTOINCREMENT, ID_PRODUTO_FK INTEGER, ID_CARRINHO_FK INTEGER, FOREIGN KEY(ID_PRODUTO_FK) REFERENCES PRODUTO(ID_PRODUTO), FOREIGN KEY(ID_CARRINHO_FK) REFERENCES CARRINHO(ID_CARRINHO));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists PRODUTO");
        //sqLiteDatabase.execSQL("drop table if exists CARRINHO");
        //sqLiteDatabase.execSQL("drop table if exists PRODUTO_CARRINHO");
    }

    public long insertData(String nome, String gtin, double preco, int quantidade, double precoTotal, String fotoURL, int carrinhoID, int status, String dataCarrinho){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", nome);
        contentValues.put("GTIN", gtin);
        contentValues.put("PRECO", preco);
        contentValues.put("QUANTIDADE", quantidade);
        contentValues.put("PRECO_TOTAL", precoTotal);
        contentValues.put("FOTO_URL", fotoURL);
        contentValues.put("ID_CARRINHO", carrinhoID);
        contentValues.put("STATUS_CARRINHO", status);
        contentValues.put("DATA_CARRINHO", dataCarrinho);
        return db.insert("PRODUTO", null, contentValues);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PRODUTO", null);
        return cursor;
    }

    public Cursor getCarrinhosFinalizados() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT ID_CARRINHO FROM PRODUTO ORDER BY ID_CARRINHO DESC;", null);
        return cursor;
    }


    public Cursor getComprasPorCarrinho(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from produto where ID_CARRINHO=" + String.valueOf(id) + ";", null);
        return cursor;
    }

    public Cursor getUltimoCarrinhoAtivo(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(ID_CARRINHO) AS ID_CARRINHO FROM PRODUTO WHERE STATUS_CARRINHO=1;", null);
        return cursor;
    }

    public Cursor getUltimoCarrinhoFinalizado(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(ID_CARRINHO) AS ID_CARRINHO FROM PRODUTO WHERE STATUS_CARRINHO=0;", null);
        return cursor;
    }

    public void removeProduto(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("PRODUTO", "ID=?", new String[]{String.valueOf(id)});
    }

    public void fecharCarrinho(Carrinho carrinho){
        SQLiteDatabase db = this.getReadableDatabase();
        for (Produto produto: carrinho.getProdutos()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("STATUS_CARRINHO", "0");
            db.update("PRODUTO", contentValues,"ID=?", new String[]{String.valueOf(produto.getId())});
        }
    }
}
