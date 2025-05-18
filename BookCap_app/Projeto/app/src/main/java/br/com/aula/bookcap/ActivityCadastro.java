package br.com.aula.bookcap;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ActivityCadastro extends AppCompatActivity {

    EditText inputRa, inputNome, inputEmail, inputSenha, inputConfirmarSenha, inputTelefone;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inputRa = findViewById(R.id.inputRa);
        inputNome = findViewById(R.id.inputNome); // campo de nome real
        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        inputConfirmarSenha = findViewById(R.id.inputConfirmarSenha);
        inputTelefone = findViewById(R.id.inputTelefone);
        MaterialButton btnCadastrar = findViewById(R.id.btnCadastrar);

        dbHelper = new DBHelper(this);

        btnCadastrar.setOnClickListener(v -> {
            String ra = inputRa.getText().toString().trim();
            String nome = inputNome.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String senha = inputSenha.getText().toString().trim();
            String confirmarSenha = inputConfirmarSenha.getText().toString().trim();
            String telefone = inputTelefone.getText().toString().trim();

            if (ra.isEmpty() || nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.endsWith("@edu.fecap.br")) {
                Toast.makeText(this, "O email precisa ser do domínio @edu.fecap.br", Toast.LENGTH_SHORT).show();
                return;
            }

            if (senha.length() < 8) {
                Toast.makeText(this, "A senha deve ter pelo menos 8 caracteres.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (telefone.length() < 10 || telefone.length() > 11) {
                Toast.makeText(this, "Digite um telefone válido com DDD.", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("ra", ra);
            values.put("nome", nome); // salva o nome real
            values.put("email", email);
            values.put("senha", senha);
            values.put("telefone", telefone);

            long result = db.insert("usuarios", null, values);

            if (result != -1) {
                Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityCadastro.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Erro ao criar conta. RA ou email já cadastrados.", Toast.LENGTH_SHORT).show();
            }

            db.close();
        });
    }
}
