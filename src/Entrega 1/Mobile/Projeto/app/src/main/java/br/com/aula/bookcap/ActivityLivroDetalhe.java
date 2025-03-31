package br.com.aula.bookcap;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityLivroDetalhe extends AppCompatActivity {

    TextView tituloLivro, descricaoLivro;
    ImageView capaLivro;
    Button btnDisponivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_livro_detalhe);

        capaLivro = findViewById(R.id.capaLivro);
        tituloLivro = findViewById(R.id.tituloLivro);
        descricaoLivro = findViewById(R.id.descricaoLivro);
        btnDisponivel = findViewById(R.id.btnDisponivel);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String titulo = getIntent().getStringExtra("titulo");
        String descricao = getIntent().getStringExtra("descricao");
        int capaResId = getIntent().getIntExtra("capa", R.drawable.menino_do_pijama_listrado);

        tituloLivro.setText(titulo);
        descricaoLivro.setText(descricao);
        capaLivro.setImageResource(capaResId);
    }
}
