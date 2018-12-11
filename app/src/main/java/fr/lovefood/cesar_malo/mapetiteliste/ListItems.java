package fr.lovefood.cesar_malo.mapetiteliste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import fr.lovefood.cesar_malo.mapetiteliste.Item.Item;
import fr.lovefood.cesar_malo.mapetiteliste.Item.ItemAdapter;
import fr.lovefood.cesar_malo.mapetiteliste.Item.ItemPersistance;

public class ListItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        ListView listItems = (ListView) findViewById(R.id.item_liste_lv);

        ItemPersistance itemPersistance = new ItemPersistance(this);
        ArrayList<Item> items = itemPersistance.getListItems(1);

        ItemAdapter itemAdapter = new ItemAdapter(this, R.layout.items, items);
        listItems.setAdapter(itemAdapter);
    }
}
