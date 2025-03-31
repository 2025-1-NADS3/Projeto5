package br.com.aula.bookcap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatDelegate;

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

        
        Button btnBiblioteca = findViewById(R.id.btnBiblioteca);
        btnBiblioteca.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityBiblioteca.class);
            startActivity(intent);
        });


        Button btnHistorico = findViewById(R.id.btnHistorico);
        btnHistorico.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityHistorico.class);
            startActivity(intent);
        });


        Button btnExtrato = findViewById(R.id.btnExtrato);
        btnExtrato.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityExtrato.class);
            startActivity(intent);
        });

        Button btnFeedback = findViewById(R.id.btnFeedback);
        btnFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityFeedbacks.class);
            startActivity(intent);
        });




    }
}
