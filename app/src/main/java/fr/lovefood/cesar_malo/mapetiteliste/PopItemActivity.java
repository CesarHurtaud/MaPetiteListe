package fr.lovefood.cesar_malo.mapetiteliste;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.lovefood.cesar_malo.mapetiteliste.Item.Item;


public class PopItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_item_activity);

        final int tdl_id_for_item = getIntent().getIntExtra("list_id", 1);

        Button btn_add_item = findViewById(R.id.popItemButton);
        final EditText quantity = findViewById(R.id.quantityET);
        final EditText description = findViewById(R.id.descriptionET);

        final DataBaseHelper dbHelper = new DataBaseHelper(this, null);

        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Liste id", String.valueOf(tdl_id_for_item));

                int item_id = dbHelper.getHighestItemId();
                String idString = String.valueOf(item_id);
                Log.d("id ", idString);


                String description_item = description.getText().toString();
                Log.d("Nom de l'item", description_item);

                int quantity_item = Integer.parseInt(quantity.getText().toString());
                Log.d("quantité", quantity.getText().toString());

                //int id = dataBaseHelper.getHighestTDLId();
                //String idString = String.valueOf(id);
                //Log.d("id ", idString);

                Item it = new Item(item_id,tdl_id_for_item, description_item, quantity_item,0,0);

                dbHelper.addItem(it);

                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams popItemParams = getWindow().getAttributes();
        popItemParams.gravity = Gravity.CENTER;
        popItemParams.x = 0;
        popItemParams.y = -20;

        getWindow().setAttributes(popItemParams);
    }
}
