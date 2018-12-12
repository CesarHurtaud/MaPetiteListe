package fr.lovefood.cesar_malo.mapetiteliste.ToDoList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

import fr.lovefood.cesar_malo.mapetiteliste.InitData;

public class TDLPersistance extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mpl.db";
    private static final String TABLE_TDL = "tdls";
    private static final String ATTRIBUT_ID = "id_list";
    private static final String ATTRIBUT_NAME = "name";
    private static final String ATTRIBUT_DATE = "date";


    public TDLPersistance(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String table_tdl_create =
                "CREATE TABLE " + TABLE_TDL + "(" + ATTRIBUT_ID + " INTEGER primary key," +
                        ATTRIBUT_NAME + " TEXT, " +
                        ATTRIBUT_DATE + " TEXT)";

        db.execSQL(table_tdl_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TDL);
        // Create tables again
        onCreate(db);
    }

    public void addTDL(ToDoList t) {
        int id = t.getId_list();
        String name = t.getName();
        String date = t.getDate();

        ContentValues tdl = new ContentValues();
        tdl.put(ATTRIBUT_ID, id);
        tdl.put(ATTRIBUT_NAME, name);
        tdl.put(ATTRIBUT_DATE, date);

        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        db.insert(TABLE_TDL, null, tdl);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void initData() {
        InitData init = InitData.getInstance();
        ArrayList<ToDoList> lists = init.getLists();
        for (ToDoList tdl : lists){
            addTDL(tdl);
            Log.i("persistanceInit", tdl.toString());
        }
    }


    public void delTDL(ToDoList t) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(t.getId_list());
        db.delete(TABLE_TDL, ATTRIBUT_ID + " = ?", new String[]{id});
        db.close();
    }


    public void updateTDL(ToDoList t) {
        int id = t.getId_list();
        String name = t.getName();
        String date = t.getDate();

        ContentValues tdl = new ContentValues();
        tdl.put(ATTRIBUT_ID, id);
        tdl.put(ATTRIBUT_NAME, name);
        tdl.put(ATTRIBUT_DATE, date);


        SQLiteDatabase db = this.getWritableDatabase();
        // Updating Row
        db.update(TABLE_TDL, tdl, ATTRIBUT_ID + " = " + t.getId_list(), null);
        db.close(); // Closing database connection
    }


    public ToDoList getTDL(int key) {
        Log.i("TDLmeGave", "get");
        SQLiteDatabase db = this.getWritableDatabase();
        /*
        Cursor cursor = db.query(TABLE_TDL, new String[]{ATTRIBUT_ID, ATTRIBUT_NAME, ATTRIBUT_DATE},
                ATTRIBUT_ID + " = ?", new String[]{key}, null, null, null, null);
                */
        String query = "SELECT * FROM " + TABLE_TDL + " WHERE " + ATTRIBUT_ID + " = " + String.valueOf(key);
        Cursor cursor = db.rawQuery(query, null);
        Log.i("TDLmeGave", "cursor");

        ToDoList tdl = new ToDoList();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            tdl.setId_list(cursor.getInt(0));
            tdl.setName(cursor.getString(1));
            tdl.setDate(cursor.getString(2));
        } else {
            tdl.setName("no list");
        }
        db.close();
        return tdl;
    }


    public int countTDLs() {
        int resultat;
        String countQuery = "SELECT * FROM " + TABLE_TDL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        resultat = cursor.getCount();
        db.close();
        return resultat;
    }


    public ArrayList<ToDoList> getAllTDL() {
        ArrayList<ToDoList> tdls = new ArrayList<ToDoList>();

        Log.i("persistanceGetting", "1");
        String query = "SELECT * FROM " + TABLE_TDL + " ORDER BY " + ATTRIBUT_DATE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();

        Log.i("persistanceGetting", query);
        Cursor cursor = db.rawQuery(query, null);
        Log.i("persistanceGetting", "2");

        if(cursor.moveToFirst()) {
            do {
                ToDoList tdl = new ToDoList();
                tdl.setId_list(cursor.getInt(0));
                tdl.setName(cursor.getString(1));
                tdl.setDate(cursor.getString(2));

                Log.i("persistanceGetting", tdl.toString());
                tdls.add(tdl);
            } while (cursor.moveToNext());
        }
        db.close();
        return tdls;
    }
}
