package fr.lovefood.cesar_malo.mapetiteliste.Item;

public class Item {
    private int id_item;
    private int id_list; //foreign key to the list
    private String description;
    private int quantity;
    private int unit; // indicate if the quantity is in ml, cl, l, mg, cg, g, none... -> Correspond au positionnement de la string-array
    private boolean checked; // If the article is already taken


    public Item(){}

    public Item(int id_item, int id_list, String description, int quantity, int unit, boolean checked) {
        this.id_item = id_item;
        this.id_list = id_list;
        this.description = description;
        this.quantity = quantity;
        this.unit = unit;
        this.checked = checked;
    }

    /*---------------------------------- GETTERS & SETTERS ---------------------------------------*/

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {this.id_item = id_item;}

    public int getId_list() {
        return id_list;
    }

    public void setId_list(int id_list) {
        this.id_list = id_list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnit() {return unit;}

    public void setUnit(int unit) {this.unit = unit;}

    public boolean isChecked() {return checked;}

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /*--------------------------------------------------------------------------------------------*/

    @Override
    public String toString() {
        return "Item{" +
                "id_item=" + id_item +
                ", id_list=" + id_list +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", checked=" + checked +
                '}';
    }
}
