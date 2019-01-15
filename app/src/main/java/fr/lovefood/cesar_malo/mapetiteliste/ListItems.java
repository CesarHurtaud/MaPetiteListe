package fr.lovefood.cesar_malo.mapetiteliste;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.lovefood.cesar_malo.mapetiteliste.Item.Item;
import fr.lovefood.cesar_malo.mapetiteliste.Item.ItemAdapter;
import fr.lovefood.cesar_malo.mapetiteliste.ToDoList.ToDoList;

public class ListItems extends AppCompatActivity {

    ItemAdapter itemAdapter;
    final DataBaseHelper dbh = new DataBaseHelper(this, null);
    ArrayList<Item> items;
    ToDoList tdl;
    public int tdl_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);



        tdl_id = getIntent().getIntExtra("tdl", 1);

        tdl = dbh.getTDL(tdl_id);
        TextView tdl_name_tv = (TextView) this.findViewById(R.id.tdl_name_lv);
        tdl_name_tv.setText(tdl.getName());

        ListView listItems = (ListView) findViewById(R.id.item_liste_lv);

        items = dbh.getListItems(tdl.getId_list());

        itemAdapter = new ItemAdapter(this, R.layout.items, items);
        listItems.setAdapter(itemAdapter);

        listItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item it = (Item) parent.getItemAtPosition(position);
                dbh.delItem(it);
                updateView();

                return false;
            }
        });


        FloatingActionButton addItem = findViewById(R.id.addItemButton);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popIte = new Intent(getApplicationContext(), PopItemActivity.class);
                int tdl_id_bis = getIntent().getIntExtra("tdl", 1);
                popIte.putExtra("list_id", tdl_id_bis);
                startActivity(popIte);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("test", "onResume activé !");
        updateView();


    }

    public void updateView() {
        items.clear();
        items.addAll(dbh.getListItems(tdl.getId_list()));
        itemAdapter.notifyDataSetChanged();
    }
}
