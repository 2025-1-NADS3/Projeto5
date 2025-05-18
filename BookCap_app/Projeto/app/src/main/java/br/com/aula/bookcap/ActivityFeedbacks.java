package br.com.aula.bookcap;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacks);

        inputNome = findViewById(R.id.inputNome);
        inputComentario = findViewById(R.id.inputComentario);
        inputNota = findViewById(R.id.inputNota);
        btnEnviar = findViewById(R.id.btnEnviarFeedback);

        dbHelper = new DBHelper(this); // ← inicializa o banco

        btnEnviar.setOnClickListener(v -> {
            String nome = inputNome.getText().toString().trim();
            String comentario = inputComentario.getText().toString().trim();
            float nota = inputNota.getRating();

            if (!nome.isEmpty() && !comentario.isEmpty()) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("nome", nome);
                values.put("comentario", comentario);
                values.put("nota", nota);

                long result = db.insert("feedbacks", null, values);
                db.close();

                if (result != -1) {
                    Toast.makeText(this, "Feedback enviado! Obrigado, " + nome + "!", Toast.LENGTH_SHORT).show();
                    inputNome.setText("");
                    inputComentario.setText("");
                    inputNota.setRating(5);
                } else {
                    Toast.makeText(this, "Erro ao enviar feedback.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
