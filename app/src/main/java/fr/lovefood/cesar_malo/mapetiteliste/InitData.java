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


    // tous les checked ont été modifié en int
    private InitData(){
        addInitialTDL(new ToDoList(1, "Ma première liste", "01/01/2018"));
        addInitialItem(new Item(1, 1, "lait", 1, 3, 1));
        addInitialItem(new Item(2, 1, "oeufs", 12, 1, 1));
        addInitialItem(new Item(3, 1, "beurre", 250, 5, 1));
        addInitialItem(new Item(4, 1, "chocolat", 400, 5, 1));
        addInitialItem(new Item(5, 1, "sucre", 500, 5, 1));
        addInitialItem(new Item(6, 1, "essence de vanille", 10, 2, 1));

        addInitialTDL(new ToDoList(2, "Ma deuxième liste", "01/02/2018"));
        addInitialItem(new Item(7, 2, "batman", 1, 0, 1));
        addInitialItem(new Item(8, 2, "flash", 1, 0, 1));
        addInitialItem(new Item(9, 2, "watchmen", 1, 0, 1));
        addInitialItem(new Item(10, 2, "barbie", 1, 0, 1));
        addInitialItem(new Item(11, 2, "rastarocket", 1, 0, 1));
        addInitialItem(new Item(12, 2, "Maman j'ai raté l'avion !", 1, 0, 1));
    }  //private constructor.

    public void addInitialItem(Item i){list.add(i);}
    public void addInitialTDL(ToDoList t){lists.add(t);}

    public ArrayList<Item> getItems(){return list;}
    public ArrayList<ToDoList> getLists(){return lists;}

}
