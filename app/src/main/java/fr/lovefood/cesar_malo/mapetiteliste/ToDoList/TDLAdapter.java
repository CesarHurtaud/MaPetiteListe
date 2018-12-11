package fr.lovefood.cesar_malo.mapetiteliste.ToDoList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import fr.lovefood.cesar_malo.mapetiteliste.Item.Item;
import fr.lovefood.cesar_malo.mapetiteliste.R;

public class TDLAdapter extends ArrayAdapter<ToDoList> {
    ArrayList<ToDoList> lists;
    Context context;
    int ressource;

    public TDLAdapter(Context context, int resource, ArrayList<ToDoList> data) {
        super(context, resource, data);
        this.lists = data;
        this.context = context;
        this.ressource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(ressource, parent, false);

        ToDoList tdl = lists.get(position);


        return v;

    }
}
