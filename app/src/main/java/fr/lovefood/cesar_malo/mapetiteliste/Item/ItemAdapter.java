package fr.lovefood.cesar_malo.mapetiteliste.Item;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import fr.lovefood.cesar_malo.mapetiteliste.DataBaseHelper;
import fr.lovefood.cesar_malo.mapetiteliste.R;

public class ItemAdapter extends ArrayAdapter<Item> {
    ArrayList<Item> items;
    Context context;
    int ressource;

    private boolean spinnerTouched = false;


    public ItemAdapter(Context context, int resource, ArrayList<Item> data) {
        super(context, resource, data);
        this.items = data;
        this.context = context;
        this.ressource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(ressource, parent, false);


        final DataBaseHelper dbh = new DataBaseHelper(context.getApplicationContext(), null);


        final Item item = items.get(position);
        
        TextView item_tv = (TextView) v.findViewById(R.id.Item_text);
        item_tv.setText(item.getDescription());

        TextView quantity_tv = (TextView) v.findViewById(R.id.Item_quantity);
        quantity_tv.setText(String.valueOf(item.getQuantity()));


        Spinner unit_tv = (Spinner) v.findViewById(R.id.Quantity_unit);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(context,
                R.array.allUnits, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        unit_tv.setAdapter(adapterSpinner);
        unit_tv.setSelection(item.getUnit());



        unit_tv.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                spinnerTouched = true;
                //Log.d("spinner", "touched");
                return false;
            }
        });
        unit_tv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("spinner ", "selected : " + parent.getItemAtPosition(position).toString());
                if (spinnerTouched == true) {
                    Log.d("spinner ", "activated");
                    dbh.updateItem(item, position);
                    spinnerTouched = false;
                }







            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //en attente, cf problèmes sqlite
        //CheckBox checkedItem = (CheckBox) v.findViewById(R.id.Selected_item);
        //checkedItem.setChecked(item.isChecked());

        return v;

    }
}
