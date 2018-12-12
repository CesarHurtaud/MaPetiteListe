package fr.lovefood.cesar_malo.mapetiteliste;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.lovefood.cesar_malo.mapetiteliste.Item.Item;
import fr.lovefood.cesar_malo.mapetiteliste.Item.ItemAdapter;
import fr.lovefood.cesar_malo.mapetiteliste.Item.ItemPersistance;
import fr.lovefood.cesar_malo.mapetiteliste.ToDoList.TDLAdapter;
import fr.lovefood.cesar_malo.mapetiteliste.ToDoList.TDLPersistance;
import fr.lovefood.cesar_malo.mapetiteliste.ToDoList.ToDoList;

public class MenuListes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listes);

        ListView list_lv = (ListView) findViewById(R.id.TDL_lv);

        TDLPersistance tdlPersistance = new TDLPersistance(this);
        tdlPersistance.initData();
        ArrayList<ToDoList> lists = tdlPersistance.getAllTDL();

        TDLAdapter tdlAdapter = new TDLAdapter(this, R.layout.todolists, lists);
        list_lv.setAdapter(tdlAdapter);

        list_lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoList tdl = (ToDoList) parent.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), ListItems.class);
                intent.putExtra("tdl", tdl.getId_list());
                startActivity(intent);
            }
        });
    }
}
