package com.ecolim.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ecolim.app.models.Residuo;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ecolim.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "residuos";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT NOT NULL," +
                "cantidad REAL NOT NULL," +
                "unidad TEXT NOT NULL," +
                "ubicacion TEXT NOT NULL," +
                "fecha TEXT NOT NULL," +
                "trabajador TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public long insertarResiduo(Residuo r) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tipo", r.getTipo());
        cv.put("cantidad", r.getCantidad());
        cv.put("unidad", r.getUnidad());
        cv.put("ubicacion", r.getUbicacion());
        cv.put("fecha", r.getFecha());
        cv.put("trabajador", r.getTrabajador());
        long id = db.insert(TABLE, null, cv);
        db.close();
        return id;
    }

    public int eliminarResiduo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public List<Residuo> obtenerTodos() {
        List<Residuo> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                Residuo r = new Residuo();
                r.setId(cursor.getInt(0));
                r.setTipo(cursor.getString(1));
                r.setCantidad(cursor.getDouble(2));
                r.setUnidad(cursor.getString(3));
                r.setUbicacion(cursor.getString(4));
                r.setFecha(cursor.getString(5));
                r.setTrabajador(cursor.getString(6));
                lista.add(r);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Residuo> filtrarPorTipo(String tipo) {
        List<Residuo> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE tipo=? ORDER BY id DESC", new String[]{tipo});
        if (cursor.moveToFirst()) {
            do {
                Residuo r = new Residuo();
                r.setId(cursor.getInt(0));
                r.setTipo(cursor.getString(1));
                r.setCantidad(cursor.getDouble(2));
                r.setUnidad(cursor.getString(3));
                r.setUbicacion(cursor.getString(4));
                r.setFecha(cursor.getString(5));
                r.setTrabajador(cursor.getString(6));
                lista.add(r);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public double obtenerSumaCantidad(String tipo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (tipo.equals("Todos")) {
            cursor = db.rawQuery("SELECT SUM(cantidad) FROM " + TABLE, null);
        } else {
            cursor = db.rawQuery("SELECT SUM(cantidad) FROM " + TABLE + " WHERE tipo=?", new String[]{tipo});
        }
        double suma = 0;
        if (cursor.moveToFirst()) {
            suma = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return suma;
    }
}