package br.com.aula.bookcap;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityFeedbacks extends AppCompatActivity {

    EditText inputNome, inputComentario;
    RatingBar inputNota;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacks);

        inputNome = findViewById(R.id.inputNome);
        inputComentario = findViewById(R.id.inputComentario);
        inputNota = findViewById(R.id.inputNota);
        btnEnviar = findViewById(R.id.btnEnviarFeedback);

        btnEnviar.setOnClickListener(v -> {
            String nome = inputNome.getText().toString();
            String comentario = inputComentario.getText().toString();
            float nota = inputNota.getRating();

            if (!nome.isEmpty() && !comentario.isEmpty()) {
                Toast.makeText(this, "Feedback enviado! Obrigado, " + nome + "!", Toast.LENGTH_SHORT).show();

                // Reset visual
                inputNome.setText("");
                inputComentario.setText("");
                inputNota.setRating(5);
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
