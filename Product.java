package InventoryProgram;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Class for Products.
 * Contains all info and functionality for the products class, along with associated parts.
 * @author Shawn Wayne Wells
 * */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**Constructor for Product Class.
     * @param id Product ID
     * @param name Product name
     * @param price Product Price
     * @param stock Inventory of product
     * @param min Minimum number in inventory
     * @param max Maximum number in inventory
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**Get Product ID.
     * @return return Product ID
     * */
    public int getId() { return id; }

    /**Set Product ID.
     * @param id Product ID
     * */
    public void setId(int id) { this.id = id; }

    /**Get Product Name.
     * @return Return product name
     * */
    public String getName() { return name; }

    /**Set Product Name.
     * @param name Product Name
     * */
    public void setName(String name) { this.name = name; }

    /**Get product price.
     * @return Return product price
     * */
    public double getPrice() { return price; }

    /**Set Product price.
     * @param price Product price
     * */
    public void setPrice(double price) { this.price = price; }

    /**Get product inventory.
     * @return Return product inventory
     * */
    public int getStock() { return stock; }

    /**Set product inventory.
     * @param stock product inventory
     * */
    public void setStock(int stock) { this.stock = stock; }

    /**Get minimum inventory.
     * @return returns minimum inventory
     * */
    public int getMin() { return min; }

    /**Set minimum inventory.
     * @param min minimum inventory
     * */
    public void setMin(int min) { this.min = min; }

    /**Get maximum inventory.
     * @return return maximum inventory
     * */
    public int getMax() { return max; }

    /**Set maximum inventory.
     * @param max maximum inventory
     * */
    public void setMax(int max) { this.max = max; }

    /**Add part to associated parts list.
     * @param part part object ot add
     * */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

   /**Delete part from associated parts list.
    * @param selectedPart selected part to delete
    * @return returns true if deleted
    * */
    public boolean deleteAssociatedPart(Part selectedPart) {
        associatedParts.remove(selectedPart);
        return true;
    }

    /**Returns ObservableList of associated parts.
     * @return returns associated parts list
     * */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

}
