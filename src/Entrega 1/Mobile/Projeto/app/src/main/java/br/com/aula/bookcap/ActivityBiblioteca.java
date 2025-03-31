package br.com.aula.bookcap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityBiblioteca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_biblioteca);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        GridLayout gridLayout = findViewById(R.id.gridLivros);

        // CLICK do LIVRO 1
        LinearLayout livro1 = findViewById(R.id.livro1);
        livro1.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityBiblioteca.this, ActivityLivroDetalhe.class);
            intent.putExtra("titulo", "O Menino do Pijama Listrado");
            intent.putExtra("descricao", "Conta a amizade entre Bruno, filho de um oficial nazista, e Shmuel, um prisioneiro judeu.");
            intent.putExtra("capa", R.drawable.menino_do_pijama_listrado);
            startActivity(intent);
        });

        // Adiciona os demais livros (Livro 2 a 9)
        for (int i = 2; i <= 9; i++) {
            String titulo = "Livro " + i;

            Button livroBtn = new Button(this);
            livroBtn.setText(titulo);
            livroBtn.setAllCaps(false);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(16, 16, 16, 16);
            livroBtn.setLayoutParams(params);

            int finalI = i;
            livroBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, ActivityLivroDetalhe.class);
                intent.putExtra("titulo", titulo);
                intent.putExtra("descricao", "Descrição genérica para " + titulo);
                intent.putExtra("capa", R.drawable.menino_do_pijama_listrado); // genérica por enquanto
                startActivity(intent);
            });

            gridLayout.addView(livroBtn);
        }
    }
}
