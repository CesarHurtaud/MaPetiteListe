package fr.lovefood.cesar_malo.mapetiteliste;

import android.util.Log;

import fr.lovefood.cesar_malo.mapetiteliste.Item.Item;
import fr.lovefood.cesar_malo.mapetiteliste.ToDoList.ToDoList;

import java.util.ArrayList;

public class InitData {
    private ArrayList<Item> list = new ArrayList<Item>();
    private ArrayList<ToDoList> lists = new ArrayList<ToDoList>();
    private static InitData Instance;

    public static InitData getInstance(){
        if (Instance == null){ //if there is no instance available... create new one
            Instance = new InitData();
        }
        return Instance;
    }

    private InitData(){
        addTDL(new ToDoList(1, "Ma première liste", "01/01/2018"));
        addItem(new Item(1, 1, "lait", 1, 3, false));
        addItem(new Item(2, 1, "oeufs", 12, 1, false));
        addItem(new Item(3, 1, "beurre", 250, 5, false));
        addItem(new Item(4, 1, "chocolat", 400, 5, false));
        addItem(new Item(5, 1, "sucre", 500, 5, true));
        addItem(new Item(6, 1, "essence de vanille", 10, 2, false));

        addTDL(new ToDoList(2, "Ma deuxième liste", "01/02/2018"));
        addItem(new Item(1, 2, "batman", 1, 1, false));
        addItem(new Item(2, 2, "flash", 1, 1, false));
        addItem(new Item(3, 2, "watchmen", 1, 1, false));
        addItem(new Item(4, 2, "barbie", 1, 1, false));
        addItem(new Item(5, 2, "rastarocket", 1, 1, true));
        addItem(new Item(6, 2, "Maman j'ai raté l'avion !", 1, 1, false));
    }  //private constructor.

    public void addItem(Item i){list.add(i);}
    public void addTDL(ToDoList t){lists.add(t);}

    public ArrayList<Item> getItems(){return list;}
    public ArrayList<ToDoList> getLists(){return lists;}

}
