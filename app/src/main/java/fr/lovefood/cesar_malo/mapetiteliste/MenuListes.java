package fr.lovefood.cesar_malo.mapetiteliste;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fr.lovefood.cesar_malo.mapetiteliste.ToDoList.TDLAdapter;
import fr.lovefood.cesar_malo.mapetiteliste.ToDoList.ToDoList;

public class MenuListes extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listes);

        ListView list_lv = (ListView) findViewById(R.id.TDL_lv);

        FloatingActionButton addList = findViewById(R.id.addListButton);
        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popInt = new Intent(getApplicationContext(), PopListActivity.class);
                startActivity(popInt);
            }
        });

        final DataBaseHelper dbh = new DataBaseHelper(this, null);
        dbh.initData();
        ArrayList<ToDoList> lists = dbh.getAllTDL();

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

        list_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoList tdl = (ToDoList) parent.getItemAtPosition(position);
                dbh.delTDL(tdl);
                return false;
            }
        });

    }
}
