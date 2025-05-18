package br.com.aula.bookcap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityExtrato extends AppCompatActivity {

    LinearLayout layoutExtrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extrato);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainExtrato), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        layoutExtrato = findViewById(R.id.layoutExtrato);

        String raUsuario = getIntent().getStringExtra("nomeUsuario"); // RA enviado pelo login

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT titulo_livro, data_aluguel FROM alugueis WHERE ra_usuario = ?", new String[]{raUsuario});

        if (cursor.moveToFirst()) {
            do {
                String titulo = cursor.getString(0);
                String data = cursor.getString(1);

                TextView item = new TextView(this);
                item.setText("📚 " + titulo + "\n📅 Alugado em: " + data);
                item.setTextSize(16);
                item.setPadding(16, 16, 16, 16);
                item.setBackgroundColor(0xFFE8F5E9); // verde claro
                item.setTextColor(0xFF000000);
                item.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                layoutExtrato.addView(item);
            } while (cursor.moveToNext());
        } else {
            TextView vazio = new TextView(this);
            vazio.setText("Nenhum livro alugado ainda.");
            vazio.setTextSize(16);
            vazio.setPadding(16, 32, 16, 16);
            layoutExtrato.addView(vazio);
        }

        cursor.close();
        db.close();
    }
}
