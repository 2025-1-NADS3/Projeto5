package br.com.aula.bookcap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import java.util.Calendar;

public class ActivityLivroDetalhe extends AppCompatActivity {

    private String dataSelecionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_detalhe);

        DBHelper dbHelper = new DBHelper(this);
        SharedPreferences prefs = getSharedPreferences("BookCapPrefs", MODE_PRIVATE);
        String raUsuario = prefs.getString("ra", "desconhecido");

        ImageView capa = findViewById(R.id.capaLivro);
        TextView titulo = findViewById(R.id.tituloLivro);
        TextView descricao = findViewById(R.id.descricaoLivro);
        Button btnAlugar = findViewById(R.id.btnAlugar);
        Button btnVer = findViewById(R.id.btnVerDisponibilidade);

        // Pega os dados passados pela Activity anterior
        Bundle extras = getIntent().getExtras();
        final int capaResId = (extras != null) ? extras.getInt("imagem", 0) : 0;
        if (extras != null) {
            titulo.setText(extras.getString("titulo"));
            capa.setImageResource(capaResId);
            descricao.setText(extras.getString("descricao"));
        }


        btnVer.setOnClickListener(v -> {
            final Calendar hoje = Calendar.getInstance();
            hoje.add(Calendar.DAY_OF_MONTH, 1); // começa a partir de amanhã

            int ano = hoje.get(Calendar.YEAR);
            int mes = hoje.get(Calendar.MONTH);
            int dia = hoje.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ActivityLivroDetalhe.this,
                    (view, year, month, dayOfMonth) -> {
                        Calendar selecionada = Calendar.getInstance();
                        selecionada.set(year, month, dayOfMonth);

                        int diaSemana = selecionada.get(Calendar.DAY_OF_WEEK);
                        if (diaSemana == Calendar.SUNDAY) {
                            btnVer.setText("Domingo indisponível!");
                            dataSelecionada = null;
                        } else {
                            dataSelecionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                            btnVer.setText("Data Selecionada: " + dataSelecionada);
                        }
                    },
                    ano, mes, dia
            );

            datePickerDialog.getDatePicker().setMinDate(hoje.getTimeInMillis());
            datePickerDialog.show();
        });

        btnAlugar.setOnClickListener(v -> {
            if (dataSelecionada == null) {
                Toast.makeText(this, "Escolha uma data antes de alugar!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Salvar no banco com todos os dados necessários
            dbHelper.registrarAluguel(
                    raUsuario,
                    titulo.getText().toString(),
                    dataSelecionada,
                    capaResId
            );

            // Ir para tela de pagamento
            Intent intent = new Intent(ActivityLivroDetalhe.this, ActivityPagamento.class);
            intent.putExtra("tituloLivro", titulo.getText().toString());
            intent.putExtra("valor", "0.01");
            intent.putExtra("data", dataSelecionada);
            startActivity(intent);
        });
    }
}
