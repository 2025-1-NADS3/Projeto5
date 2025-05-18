package br.com.aula.bookcap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookcap.db";
    private static final int DATABASE_VERSION = 5; // Aumente a versão para disparar o onUpgrade

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsuarios = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ra TEXT NOT NULL UNIQUE, " +
                "nome TEXT NOT NULL, " +  // ← AQUI
                "email TEXT NOT NULL UNIQUE, " +
                "senha TEXT NOT NULL, " +
                "telefone TEXT, " +
                "data_criacao TEXT DEFAULT (datetime('now'))" +
                ");";

        String createFeedbacks = "CREATE TABLE feedbacks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "nota REAL NOT NULL, " +
                "comentario TEXT, " +
                "data_envio TEXT DEFAULT (datetime('now'))" +
                ");";

        String createAlugueis = "CREATE TABLE alugueis (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ra_usuario TEXT NOT NULL, " +
                "titulo_livro TEXT NOT NULL, " +
                "data_aluguel TEXT NOT NULL, " +
                "capa_res_id INTEGER DEFAULT 0, " +
                "pago INTEGER DEFAULT 0, " +
                "data_registro TEXT DEFAULT (datetime('now'))" +
                ");";
        db.execSQL("CREATE TABLE IF NOT EXISTS historico(id INTEGER PRIMARY KEY AUTOINCREMENT, ra TEXT, titulo TEXT, data TEXT)");

        db.execSQL(createUsuarios);
        db.execSQL(createFeedbacks);
        db.execSQL(createAlugueis);
    }
    public void registrarAluguel(String ra, String titulo, String data, int capaResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("ra_usuario", ra);
        valores.put("titulo_livro", titulo);
        valores.put("data_aluguel", data);
        valores.put("capa_res_id", capaResId);
        valores.put("pago", 0); // ainda não foi pago
        db.insert("alugueis", null, valores);
        db.close();
    }

    public Cursor listarAlugueisConfirmadosPorRA(String ra) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM alugueis WHERE ra_usuario = ? AND pago = 1", new String[]{ra});
    }
    public void marcarAluguelComoPago(String ra, String titulo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("pago", 1);
        db.update("alugueis", valores, "ra_usuario = ? AND titulo_livro = ?", new String[]{ra, titulo});
        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            db.execSQL("ALTER TABLE alugueis ADD COLUMN capa_res_id INTEGER DEFAULT 0");
        }
        if (oldVersion < 5) {
            db.execSQL("ALTER TABLE alugueis ADD COLUMN pago INTEGER DEFAULT 0");
        }
    }
}
