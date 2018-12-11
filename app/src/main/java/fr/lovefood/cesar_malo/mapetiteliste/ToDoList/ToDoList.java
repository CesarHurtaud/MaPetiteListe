package fr.lovefood.cesar_malo.mapetiteliste.ToDoList;

public class ToDoList {
    private int id_list;
    private String name;
    private String date; // Creation date (or last update)

    public ToDoList(){}

    public ToDoList(int id_list, String name, String date) {
        this.id_list = id_list;
        this.name = name;
        this.date = date;
    }

    /*---------------------------------- GETTERS & SETTERS ---------------------------------------*/
    public int getId_list() {
        return id_list;
    }

    public void setId_list(int id_list) {this.id_list = id_list;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {this.date = date;}

    /*--------------------------------------------------------------------------------------------*/

    @Override
    public String toString() {
        return "ToDoList{" +
                "id_list=" + id_list +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
