package br.com.aula.bookcap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityVerFeedbacks extends AppCompatActivity {

    LinearLayout layoutFeedbacks;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_feedbacks);

        layoutFeedbacks = findViewById(R.id.layoutFeedbacks);
        dbHelper = new DBHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nome, comentario, nota, data_envio FROM feedbacks ORDER BY id DESC", null);

        while (cursor.moveToNext()) {
            String nome = cursor.getString(0);
            String comentario = cursor.getString(1);
            float nota = cursor.getFloat(2);
            String data = cursor.getString(3);

            // Nome
            TextView nomeView = new TextView(this);
            nomeView.setText("🧑 " + nome + " — " + data);
            nomeView.setTextSize(16f);
            nomeView.setPadding(0, 16, 0, 4);
            layoutFeedbacks.addView(nomeView);

            // Nota
            RatingBar stars = new RatingBar(this, null, android.R.attr.ratingBarStyleSmall);
            stars.setNumStars(5);
            stars.setStepSize(0.5f);
            stars.setRating(nota);
            stars.setIsIndicator(true);
            layoutFeedbacks.addView(stars);

            // Comentário
            TextView comentarioView = new TextView(this);
            comentarioView.setText("💬 " + comentario);
            comentarioView.setTextSize(14f);
            comentarioView.setPadding(0, 4, 0, 8);
            layoutFeedbacks.addView(comentarioView);
        }

        cursor.close();
        db.close();
    }
}
