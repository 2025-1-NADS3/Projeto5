package br.com.aula.bookcap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recupera o nome real salvo no login
        SharedPreferences prefs = getSharedPreferences("BookCapPrefs", MODE_PRIVATE);
        String nomeCompleto = prefs.getString("nome", "Usuário");
        String primeiroNome = nomeCompleto.split(" ")[0];

        TextView textUsuario = findViewById(R.id.textUsuario);
        textUsuario.setText("Bem-vindo, " + primeiroNome + "!");

        Button btnBiblioteca = findViewById(R.id.btnBiblioteca);
        Button btnHistorico = findViewById(R.id.btnHistorico);
        Button btnFeedback = findViewById(R.id.btnFeedback);
        MaterialButton btnSair = findViewById(R.id.btnSair);

        btnBiblioteca.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActivityBiblioteca.class));
        });

        btnHistorico.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActivityHistorico.class));
        });

        btnFeedback.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActivityFeedbacks.class));
        });

        btnSair.setOnClickListener(v -> {
            SharedPreferences loginPrefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefs.edit().clear().apply();

            SharedPreferences bookcapPrefs = getSharedPreferences("BookCapPrefs", MODE_PRIVATE);
            bookcapPrefs.edit().clear().apply();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
