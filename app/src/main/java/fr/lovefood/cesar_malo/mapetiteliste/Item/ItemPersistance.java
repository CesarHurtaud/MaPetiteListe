package fr.lovefood.cesar_malo.mapetiteliste.Item;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import fr.lovefood.cesar_malo.mapetiteliste.InitData;

public class ItemPersistance extends SQLiteOpenHelper{
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "mpl.db";
        private static final String TABLE_ITEMS = "items";
        private static final String ATTRIBUT_ID = "id_item";
        private static final String ATTRIBUT_LIST = "list";
        private static final String ATTRIBUT_DESCRIPTION = "description";
        private static final String ATTRIBUT_QUANTITY = "quantity";
        private static final String ATTRIBUT_UNIT = "unit";
        private static final String ATTRIBUT_CHECKED = "checked";


        public ItemPersistance(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String table_item_create =
                    "CREATE TABLE " + TABLE_ITEMS + "(" + ATTRIBUT_ID + " INTEGER primary key," +
                            ATTRIBUT_LIST + " INTEGER, " + // Foreign key ?
                            ATTRIBUT_DESCRIPTION + " TEXT, " +
                            ATTRIBUT_QUANTITY + " INTEGER, " +
                            ATTRIBUT_UNIT+ " INTEGER, " +
                            ATTRIBUT_CHECKED + " BOOLEAN " + ")";

            db.execSQL(table_item_create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            // Create tables again
            onCreate(db);
        }

        public void addItem(Item i) {
            int id = i.getId_item();
            int list = i.getId_list();
            String desc = i.getDescription();
            int quantity = i.getQuantity();
            int unit = i.getUnit();
            boolean checked = i.isChecked();

            ContentValues item = new ContentValues();
            item.put(ATTRIBUT_ID, id);
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


        public void initData() {
            InitData init = InitData.getInstance();
            ArrayList<Item> list = init.getItems();
            for (Item item : list){
                addItem(item);
            }
        }


        public void delItem(Item i) {
            SQLiteDatabase db = this.getWritableDatabase();
            String id = String.valueOf(i.getId_item());
            db.delete(TABLE_ITEMS, ATTRIBUT_ID + " = ?", new String[]{id});
            db.close();
        }


        public void updateItem(Item i) {
            int list = i.getId_list();
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
            db.update(TABLE_ITEMS, item, ATTRIBUT_ID + " = " + i.getId_item(), null);
            db.close(); // Closing database connection
        }


        public Item getItem(String key) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(TABLE_ITEMS, null,//new String[]{"*"},
            //Cursor cursor = db.query(TABLE_ITEMS, new String[]{ATTRIBUT_ID, ATTRIBUT_LIST, ATTRIBUT_DESCRIPTION, ATTRIBUT_QUANTITY, ATTRIBUT_UNIT, ATTRIBUT_CHECKED},
                    ATTRIBUT_ID + " = ?", new String[]{key}, null, null, null, null);

            Item item = new Item();
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                item.setId_item(cursor.getInt(0));
                item.setId_list(cursor.getInt(1));
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


        /*
        public int countItem() {
            int resultat;
            String countQuery = "SELECT * FROM " + TABLE_itemS;

            SQLiteDatabase db = this.getWritableDatabase();
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
                    item.setId_list(cursor.getInt(1));
                    item.setDescription(cursor.getString(2));
                    item.setQuantity(cursor.getInt(3));
                    item.setUnit(cursor.getInt(4));
                    item.setChecked((cursor.getInt(5) > 0));

                    Log.i("getAllitems", item.toString());
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
                    item.setId_list(cursor.getInt(1));
                    item.setDescription(cursor.getString(2));
                    item.setQuantity(cursor.getInt(3));
                    item.setUnit(cursor.getInt(4));
                    item.setChecked((cursor.getInt(5) > 0));

                    Log.i("getAllitems", item.toString());
                    items.add(item);
                } while (cursor.moveToNext());
            }

            db.close();
            return items;
        }
}
