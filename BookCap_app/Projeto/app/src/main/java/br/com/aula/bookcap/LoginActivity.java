package br.com.aula.bookcap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail, inputSenha;
    Button btnLogin;
    TextView btnCriarConta;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Login automático se nome estiver salvo
        SharedPreferences prefs = getSharedPreferences("BookCapPrefs", MODE_PRIVATE);
        String nome = prefs.getString("nome", null);

        if (nome != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnCriarConta = findViewById(R.id.btnCriarConta);

        dbHelper = new DBHelper(this);

        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String senha = inputSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE email = ? AND senha = ?", new String[]{email, senha});

            if (cursor.moveToFirst()) {
                String ra = cursor.getString(cursor.getColumnIndexOrThrow("ra"));
                String nomeUsuario = cursor.getString(cursor.getColumnIndexOrThrow("nome")); // <-- pega o nome real

                // Salva login automático + nome real
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("ra", ra);
                editor.putString("nome", nomeUsuario);
                editor.apply();

                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Email ou senha inválidos.", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
            db.close();
        });

        btnCriarConta.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityCadastro.class));
        });
    }
}
