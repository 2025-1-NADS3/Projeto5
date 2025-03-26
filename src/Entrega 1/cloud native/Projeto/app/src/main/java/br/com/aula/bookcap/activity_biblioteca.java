package br.com.aula.bookcap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_biblioteca extends AppCompatActivity {

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

        for (int i = 1; i <= 9; i++) {
            String titulo = "Livro " + i;

            // Cria botão
            Button livroBtn = new Button(this);
            livroBtn.setText(titulo);
            livroBtn.setAllCaps(false);

            // Define layout correto com margens
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(16, 16, 16, 16); // margem entre botões

            livroBtn.setLayoutParams(params);

            // Ação ao clicar: abrir tela de detalhes
            livroBtn.setOnClickListener(v -> {
                Intent intent = new Intent(activity_biblioteca.this, activity_livro_detalhe.class);
                intent.putExtra("tituloLivro", titulo);
                startActivity(intent);
            });

            // Adiciona ao GridLayout
            gridLayout.addView(livroBtn);
        }
    }
}
