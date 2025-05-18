package br.com.aula.bookcap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityPagamento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        // Dados recebidos da tela anterior
        String titulo = getIntent().getStringExtra("tituloLivro");
        String valor = getIntent().getStringExtra("valor");

        // Recupera RA do usuário logado
        SharedPreferences prefs = getSharedPreferences("BookCapPrefs", MODE_PRIVATE);
        String ra = prefs.getString("ra", "desconhecido");

        // Marca como pago no banco
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.marcarAluguelComoPago(ra, titulo);

        // Redireciona para pagamento via navegador
        String urlPagamento = "https://www.mercadopago.com.br/checkout/v1/redirect?pref_id=795927880-59c6fe7a-7f20-4fad-8af2-af4ce67a4a95";

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlPagamento));
        startActivity(browserIntent);
        finish(); // fecha a tela de pagamento após redirecionar
    }
}
