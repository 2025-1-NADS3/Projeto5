package br.com.aula.bookcap;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_livro_detalhe extends AppCompatActivity {

    TextView tituloLivro, descricaoLivro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_livro_detalhe);

        // Aplica padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tituloLivro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tituloLivro = findViewById(R.id.tituloLivro);
        descricaoLivro = findViewById(R.id.descricaoLivro);

        // nome do livro
        String titulo = getIntent().getStringExtra("tituloLivro");

        if (titulo != null) {
            tituloLivro.setText(titulo);

            //  descrição livros
            descricaoLivro.setText("Esta é a descrição do " + titulo + ". Em breve, mais detalhes aqui!");
        } else {
            tituloLivro.setText("Livro Desconhecido");
            descricaoLivro.setText("Não foi possível carregar os detalhes.");
        }
    }
}
