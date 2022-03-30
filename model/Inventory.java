package InventoryProgram.model;

import InventoryProgram.Part;
import InventoryProgram.Product;
import InventoryProgram.model.InHouse;
import InventoryProgram.model.Outsourced;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**The Inventory class keeps lists of parts and products. Used to populate the tableviews, searches, etc.
 * @author Shawn Wayne Wells
 * */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static String changeToCompany;
    private static int changeToMachine;

    /**Used to temporarily store Company Name when modifying part between InHouse or Outsourced.
     * @param companyName Company Name
     * */
    public static void setChangeToCompany(String companyName) { changeToCompany = companyName; }
    /**Used to temporarily store Machine ID when modifying part between InHouse or Outsourced.
     * @param machineID Machine ID
     * */
    public static void setChangeToMachine(int machineID) {changeToMachine = machineID; }

    /**Add new part to All Parts List.
     * @param newPart Part object to add.
     * */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**Add new Product to All Products List.
     * @param newProduct Product object to add.
     * */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**Lookup a Part in All Parts List.
     * @param partID ID of part to lookup
     * @return Returns Selected Part object
     * */
    public static Part lookupPart(int partID) {
        Part returnPart = null;
        for (Part parts : allParts) {
            if (parts.getId() == partID) {
                returnPart = parts;
            }
        }
        return returnPart;
    }

    /**Search list for part that contains a search string.
     * @param name Search String
     * @return returns list of matching Parts
     * */
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> tempList = FXCollections.observableArrayList();
        for (Part parts : allParts) {
            if (parts.getName().toLowerCase().contains(name.toLowerCase())) { tempList.add(parts); }
        }
        return tempList;
    }

    /**Lookup a Product in All Products List.
     * @param productID  ID of product to lookup
     * @return Returns Selected Product object
     * */
    public static Product lookupProduct(int productID) {
        Product returnProduct = null;
        for (Product products : allProducts) {
            if (products.getId() == productID) {
                returnProduct = products;
            }
        }
        return returnProduct;
    }

    /**Search list for product that contains a search string.
     * @param name Search String
     * @return returns list of matching Products
     * */
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> tempList = FXCollections.observableArrayList();
        for (Product products : allProducts) {
            if (products.getName().toLowerCase().contains(name.toLowerCase())) { tempList.add(products); }
        }
        return tempList;
    }

    /**Update information on a part. Required when switching between InHouse and Outsourced.
     * @param index Part ID
     * @param selectedPart Part object to be updated.
     * */
    public static void updatePart(int index, Part selectedPart) {
        int partID = index;
        String name = selectedPart.getName();
        Double price = selectedPart.getPrice();
        int stock = selectedPart.getStock();
        int min = selectedPart.getMin();
        int max = selectedPart.getMax();
        if (selectedPart.getClass() == InHouse.class) {
            allParts.remove(selectedPart);
            Part newPart = new Outsourced(partID,name,price,stock,min,max,changeToCompany);
            allParts.add(newPart);
            changeToCompany = "";
        } else if (selectedPart.getClass() == Outsourced.class) {
            allParts.remove(selectedPart);
            Part newPart = new InHouse(partID,name,price,stock,min,max,changeToMachine);
            allParts.add(newPart);
            changeToMachine = -1;
        }

    }

    /**Updated Product printed to Console.
     * @param index Product ID
     * @param selectedProduct Selected Product object.
     * */
    public static void updateProduct(int index, Product selectedProduct) {
        System.out.println("Product named " + selectedProduct.getName() + " at index " + index + ", updated Successfully!");
    }

    /**Removes Part from All Parts list.
     * @param selectedPart Part object to delete
     * @return returns True if deleted successfully
     * */
    public static boolean deletePart(Part selectedPart) {
        try { allParts.remove(selectedPart); }
        catch (Exception e) { return false; }
        return true;
    }

    /**Removes Product from All Products list.
     * @param selectedProduct  Product object to delete
     * @return returns True if deleted successfully
     * */
    public static boolean deleteProduct(Product selectedProduct) {
        try { allProducts.remove(selectedProduct); }
        catch (Exception e) { return false; }
        return true;
    }

    /**Returns All Parts as an ObservableList
     * @return Returns All Parts
     * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**Returns All Products as an ObservableList
     * @return Returns All Products
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
