package br.com.aula.bookcap;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActivityHistorico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        GridLayout grid = findViewById(R.id.gridHistorico);
        DBHelper db = new DBHelper(this);
        SharedPreferences prefs = getSharedPreferences("BookCapPrefs", MODE_PRIVATE);
        String raUsuario = prefs.getString("ra", "desconhecido");

        Cursor cursor = db.listarAlugueisConfirmadosPorRA(raUsuario);

        while (cursor.moveToNext()) {
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo_livro"));
            String data = cursor.getString(cursor.getColumnIndexOrThrow("data_aluguel"));
            int capaResId = cursor.getInt(cursor.getColumnIndexOrThrow("capa_res_id"));

            // Card container
            CardView card = new CardView(this);
            card.setRadius(35f);
            card.setCardElevation(25f);
            card.setUseCompatPadding(true);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.MATCH_PARENT;
            params.setMargins(0, 0, 0, 32);
            card.setLayoutParams(params);

            // Layout vertical completo do card
            LinearLayout layoutVertical = new LinearLayout(this);
            layoutVertical.setOrientation(LinearLayout.VERTICAL);
            layoutVertical.setPadding(24, 24, 24, 24);

            // Título do livro dentro do card
            TextView txtTituloTopo = new TextView(this);
            txtTituloTopo.setText(titulo);
            txtTituloTopo.setTextSize(20f);
            txtTituloTopo.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            txtTituloTopo.setTextColor(getResources().getColor(android.R.color.black));
            txtTituloTopo.setPadding(0, 0, 0, 32);

            // Layout horizontal com capa + informações
            LinearLayout layoutHorizontal = new LinearLayout(this);
            layoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);

            // Capa do livro
            ImageView img = new ImageView(this);
            img.setImageResource(capaResId);
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(200, 300);
            img.setLayoutParams(imgParams);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);

            // Layout da parte direita (infos)
            LinearLayout infoLayout = new LinearLayout(this);
            infoLayout.setOrientation(LinearLayout.VERTICAL);
            infoLayout.setPadding(32, 0, 0, 0);

            LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            infoLayout.setLayoutParams(infoParams);
            infoLayout.setGravity(android.view.Gravity.START | android.view.Gravity.CENTER_VERTICAL);

            TextView txtDataAluguel = new TextView(this);
            txtDataAluguel.setText("📅 Alugado em: " + data);
            txtDataAluguel.setTextSize(16f);

            TextView txtDataDevolucao = new TextView(this);
            txtDataDevolucao.setText("📦 Devolução: " + calcularDevolucao(data));
            txtDataDevolucao.setTextSize(16f);

            TextView txtValor = new TextView(this);
            txtValor.setText("💰 Valor: R$0,01");
            txtValor.setTextSize(16f);

            infoLayout.addView(txtDataAluguel);
            infoLayout.addView(txtDataDevolucao);
            infoLayout.addView(txtValor);

            layoutHorizontal.addView(img);
            layoutHorizontal.addView(infoLayout);

            layoutVertical.addView(txtTituloTopo);
            layoutVertical.addView(layoutHorizontal);

            card.addView(layoutVertical);
            grid.addView(card);
        }

        cursor.close();
    }

    private String calcularDevolucao(String data) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dataAluguel = sdf.parse(data);
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(dataAluguel);
            cal.add(java.util.Calendar.DAY_OF_MONTH, 10);
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            return "Data inválida";
        }
    }

}
