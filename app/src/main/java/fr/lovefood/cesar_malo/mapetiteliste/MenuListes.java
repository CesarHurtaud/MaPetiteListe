package fr.lovefood.cesar_malo.mapetiteliste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

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
        //Intent intent = new Intent (this, ListItems.class);
        //startActivity(intent);
/*
        ListView list_lv = (ListView) findViewById(R.id.TDL_lv);

        TDLPersistance tdlPersistance = new TDLPersistance(this);
        ArrayList<ToDoList> lists = tdlPersistance.getAllTDL();

        TDLAdapter tdlAdapter = new TDLAdapter(this, R.layout.todolists, lists);
        list_lv.setAdapter(tdlAdapter);
        */
    }
}
