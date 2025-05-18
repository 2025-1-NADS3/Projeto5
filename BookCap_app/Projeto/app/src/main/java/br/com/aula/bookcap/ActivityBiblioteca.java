package br.com.aula.bookcap;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActivityBiblioteca extends AppCompatActivity {

    int[] imagens = {
            R.drawable.economia_micro_e_macro,
            R.drawable.analise_e_projeto_de_sistemas,
            R.drawable.a_mao_invisivel,
            R.drawable.as_relacoes_internacionais_depois_de_1945,
            R.drawable.introducao_a_o_capital_de_karl_marx,
            R.drawable.politica_ambiental_global_e_o_brasil,
            R.drawable.entendendo_algoritmos,
            R.drawable.menino_do_pijama_listrado
    };
    String[] descricoes = {
            "Este livro aborda os fundamentos da economia micro e macro, ideal para iniciantes e estudantes.",
            "Aprenda técnicas modernas de análise e projeto de sistemas com exemplos práticos e atualizados.",
            "Um mergulho na economia liberal e os conceitos de Adam Smith, com linguagem acessível.",
            "Estudo das transformações políticas globais após 1945, com foco nas relações internacionais.",
            "Uma introdução didática e crítica ao pensamento de Karl Marx e sua obra O Capital.",
            "Explore a política ambiental e os desafios do Brasil no cenário global sustentável.",
            "Aprenda algoritmos de forma visual, prática e divertida — ideal para quem está começando.",
            "A emocionante história de um menino em tempos de guerra — comovente e impactante."
    };

    String[] titulos = {
            "Economia Micro e Macro",
            "Análise e Projeto de Sistemas",
            "A Mão Invisível",
            "Relações Internacionais",
            "Introdução a o capital de Karl Marx",
            "Política ambiental global e o Brasil",
            "Entendendo Algoritmos",
            "Menino do Pijama Listrado"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        GridLayout grid = findViewById(R.id.gridLivros);

        for (int i = 0; i < titulos.length; i++) {
            CardView card = new CardView(this);
            card.setRadius(30f);
            card.setCardElevation(32f);
            card.setUseCompatPadding(true);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(16, 16, 16, 16);
            card.setLayoutParams(params);

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(16, 16, 16, 22);

            ImageView img = new ImageView(this);
            img.setImageResource(imagens[i]);
            img.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    420
            ));
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            img.setAdjustViewBounds(true);

            TextView txt = new TextView(this);
            txt.setText(titulos[i]);
            txt.setTextSize(16f);
            txt.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            txt.setPadding(0, 12, 0, 0);

            layout.addView(img);
            layout.addView(txt);
            card.addView(layout);

            final int index = i;
            card.setOnClickListener(v -> {
                Intent intent = new Intent(ActivityBiblioteca.this, ActivityLivroDetalhe.class);
                intent.putExtra("titulo", titulos[index]);
                intent.putExtra("imagem", imagens[index]);
                intent.putExtra("descricao", descricoes[index]);
                startActivity(intent);
            });

            grid.addView(card);
        }
    }
}
