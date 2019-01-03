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

import fr.lovefood.cesar_malo.mapetiteliste.ToDoList.ToDoList;

public class PopListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_list_activity);

        Button btn_add = findViewById(R.id.popListButton);
        final EditText nomListe = findViewById(R.id.listNameET);

        final DataBaseHelper dataBaseHelper = new DataBaseHelper(this, null);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom = nomListe.getText().toString();
                Log.d("Nom de la liste", nom);

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(c);
                Log.d("Date de création ", formattedDate);

                int id = dataBaseHelper.getHighestTDLId();
                String idString = String.valueOf(id);
                Log.d("id ", idString);

                ToDoList tdl = new ToDoList(id, nom, formattedDate);
                dataBaseHelper.addTDL(tdl);

                finish();
            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams popListParams = getWindow().getAttributes();
        popListParams.gravity = Gravity.CENTER;
        popListParams.x = 0;
        popListParams.y = -20;

        getWindow().setAttributes(popListParams);
    }
}
