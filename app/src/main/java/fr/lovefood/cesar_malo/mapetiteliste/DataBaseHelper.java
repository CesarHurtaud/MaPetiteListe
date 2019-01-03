package fr.lovefood.cesar_malo.mapetiteliste;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import fr.lovefood.cesar_malo.mapetiteliste.Item.Item;
import fr.lovefood.cesar_malo.mapetiteliste.ToDoList.ToDoList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mpl.db";

    private static final String TABLE_TDL = "tdls";
    private static final String ATTRIBUT_LIST_ID = "id_list";
    private static final String ATTRIBUT_NAME = "name";
    private static final String ATTRIBUT_DATE = "date";

    private static final String TABLE_ITEMS = "items";
    private static final String ATTRIBUT_ITEM_ID = "id_item";
    private static final String ATTRIBUT_LIST = "list";
    private static final String ATTRIBUT_DESCRIPTION = "description";
    private static final String ATTRIBUT_QUANTITY = "quantity";
    private static final String ATTRIBUT_UNIT = "unit";
    private static final String ATTRIBUT_CHECKED = "checked";

    private final String table_tdl_create =
            "CREATE TABLE " + TABLE_TDL + "(" + ATTRIBUT_LIST_ID + " INTEGER primary key," +
                    ATTRIBUT_NAME + " TEXT, " +
                    ATTRIBUT_DATE + " TEXT)";

    private final String table_item_create =
            "CREATE TABLE " + TABLE_ITEMS + "(" + ATTRIBUT_ITEM_ID + " INTEGER primary key," +
                    ATTRIBUT_LIST + " INTEGER, " + // Foreign key ?
                    ATTRIBUT_DESCRIPTION + " TEXT, " +
                    ATTRIBUT_QUANTITY + " INTEGER, " +
                    ATTRIBUT_UNIT+ " INTEGER, " +
                    ATTRIBUT_CHECKED + " INTEGER " + ")";

    private final String get_number_of_id = "SELECT * FROM " + TABLE_TDL;

    private final String get_item_highest_id = "SELECT *, MAX(" + ATTRIBUT_ITEM_ID + ") FROM " + TABLE_ITEMS;

    public DataBaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context,DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(table_tdl_create);
        db.execSQL(table_item_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TDL);
        // Create tables again
        onCreate(db);
    }

    public void initData() {
        InitData init = InitData.getInstance();

        ArrayList<ToDoList> lists = init.getLists();
        for (ToDoList tdl : lists){
            addTDL(tdl);
            Log.i("persistanceInit", tdl.toString());
        }
        ArrayList<Item> list = init.getItems();
        for (Item item : list){
            addItem(item);
        }
    }

    public void addItem(Item i) {
        int id = i.getId_item();
        int list = i.getList();
        String desc = i.getDescription();
        int quantity = i.getQuantity();
        int unit = i.getUnit();
        int checked = i.getChecked();

        ContentValues item = new ContentValues();
        item.put(ATTRIBUT_ITEM_ID, id);
        item.put(ATTRIBUT_LIST, list);
        item.put(ATTRIBUT_DESCRIPTION, desc);
        item.put(ATTRIBUT_QUANTITY, quantity);
        item.put(ATTRIBUT_UNIT, unit);
        item.put(ATTRIBUT_CHECKED, checked);

        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        db.insert(TABLE_ITEMS, null, item);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }


    public void delItem(Item i) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(i.getId_item());
        db.delete(TABLE_ITEMS, ATTRIBUT_ITEM_ID + " = ?", new String[]{id});
        db.close();
    }

    /*
    public void updateItem(Item i) {
        int list = i.getList();
        String desc = i.getDescription();
        int quantity = i.getQuantity();
        int unit = i.getUnit();
        boolean checked = i.isChecked();

        ContentValues item = new ContentValues();
        item.put(ATTRIBUT_LIST, list);
        item.put(ATTRIBUT_DESCRIPTION, desc);
        item.put(ATTRIBUT_QUANTITY, quantity);
        item.put(ATTRIBUT_UNIT, unit);
        item.put(ATTRIBUT_CHECKED, checked);

        SQLiteDatabase db = this.getWritableDatabase();
        // Updating Row
        db.update(TABLE_ITEMS, item, ATTRIBUT_ITEM_ID + " = " + i.getId_item(), null);
        db.close(); // Closing database connection
    }



    public Item getItem(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, null,//new String[]{"*"},
                // Cursor cursor = db.query(TABLE_ITEMS, new String[]{ATTRIBUT_ID, ATTRIBUT_LIST, ATTRIBUT_DESCRIPTION, ATTRIBUT_QUANTITY, ATTRIBUT_UNIT, ATTRIBUT_CHECKED},
                ATTRIBUT_ITEM_ID + " = ?", new String[]{key}, null, null, null, null);
        Item item = new Item();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            item.setId_item(cursor.getInt(0));
            item.setList(cursor.getInt(1));
            item.setDescription(cursor.getString(2));
            item.setQuantity(cursor.getInt(3));
            item.setUnit(cursor.getInt(4));
            item.setChecked((cursor.getInt(5) > 0));
        } else {
            item.setDescription("no item");
        }
        db.close();
        return item;
    }
    */


    /*
       public int countItem() {
         int resultat;
            String countQuery = "SELECT * FROM " + TABLE_itemS;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            resultat = cursor.getCount();
            db.close();
            return resultat;
        }
        */

    public ArrayList<Item> getListItems(int id_list) {
        ArrayList<Item> items = new ArrayList<Item>();
        String id = String.valueOf(id_list);
        String query = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + ATTRIBUT_LIST + " = " + id + " ORDER BY " + ATTRIBUT_DESCRIPTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId_item(cursor.getInt(0));
                item.setList(cursor.getInt(1));
                item.setDescription(cursor.getString(2));
                item.setQuantity(cursor.getInt(3));
                item.setUnit(cursor.getInt(4));

                //item.setChecked((cursor.getInt(5) > 0));
                item.setCheckedProvisoire(cursor.getInt(5));

                Log.i("getListItems ", item.toString());
                items.add(item);
            } while (cursor.moveToNext());
        }

        db.close();
        return items;
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        String query = "SELECT * FROM " + TABLE_ITEMS + " ORDER BY " + ATTRIBUT_DESCRIPTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId_item(cursor.getInt(0));
                item.setList(cursor.getInt(1));
                item.setDescription(cursor.getString(2));
                item.setQuantity(cursor.getInt(3));
                item.setUnit(cursor.getInt(4));

                //item.setChecked((cursor.getInt(5) > 0));
                item.setCheckedProvisoire(cursor.getInt(5));

                Log.i("getAllitems", item.toString());
                items.add(item);
            } while (cursor.moveToNext());
        }
        db.close();
        return items;
    }

    public int getHighestItemId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(get_item_highest_id, null);
        c.moveToFirst();
        int highestItemID = c.getInt(c.getColumnIndex(ATTRIBUT_ITEM_ID));
        c.close();
        int id_new_item = highestItemID + 1;

        return id_new_item;
    }

    // faudra reprendre cette fonction pour l'adapter comme getHighestItemId
    public int getHighestTDLId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(get_number_of_id, null);
        int numberOfIds = 0;
        numberOfIds = c.getCount();
        c.close();
        int highestID = numberOfIds + 1;


        return highestID;
    }

    public void addTDL(ToDoList t) {
        int id = t.getId_list();
        String name = t.getName();
        String date = t.getDate();

        ContentValues tdl = new ContentValues();
        tdl.put(ATTRIBUT_LIST_ID, id);
        tdl.put(ATTRIBUT_NAME, name);
        tdl.put(ATTRIBUT_DATE, date);

        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        db.insert(TABLE_TDL, null, tdl);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }




    public void delTDL(ToDoList t) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(t.getId_list());
        db.delete(TABLE_TDL, ATTRIBUT_LIST_ID + " = ?", new String[]{id});
        db.close();
    }


    public void updateTDL(ToDoList t) {
        int id = t.getId_list();
        String name = t.getName();
        String date = t.getDate();

        ContentValues tdl = new ContentValues();
        tdl.put(ATTRIBUT_LIST_ID, id);
        tdl.put(ATTRIBUT_NAME, name);
        tdl.put(ATTRIBUT_DATE, date);


        SQLiteDatabase db = this.getWritableDatabase();
        // Updating Row
        db.update(TABLE_TDL, tdl, ATTRIBUT_LIST_ID + " = " + t.getId_list(), null);
        db.close(); // Closing database connection
    }


    public ToDoList getTDL(int key) {
        Log.i("TDLmeGave", "get");
        SQLiteDatabase db = this.getWritableDatabase();
        /*
        Cursor cursor = db.query(TABLE_TDL, new String[]{ATTRIBUT_ID, ATTRIBUT_NAME, ATTRIBUT_DATE},
                ATTRIBUT_ID + " = ?", new String[]{key}, null, null, null, null);
                */
        String query = "SELECT * FROM " + TABLE_TDL + " WHERE " + ATTRIBUT_LIST_ID + " = " + String.valueOf(key);
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
