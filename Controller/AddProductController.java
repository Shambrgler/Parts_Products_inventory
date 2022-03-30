package InventoryProgram.Controller;

import InventoryProgram.model.Inventory;
import InventoryProgram.Part;
import InventoryProgram.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**This is the controller for the Add Product form window.
 * The main window redirects to this controller when the Add Product button is pushed, contains methods and logic for the Add Product functionality.
 * @author Shawn Wayne Wells
 * */
public class AddProductController implements Initializable {

    @FXML TextField text_add_productid;
    @FXML TextField text_add_productname;
    @FXML TextField text_add_productstock;
    @FXML TextField text_add_productprice;
    @FXML TextField text_add_productmax;
    @FXML TextField text_add_productmin;
    @FXML TextField text_add_partSearch;
    @FXML Label labelinfo;
    @FXML TableView<Part> table_add_product;
    @FXML TableView<Part> table_assoc_product;
    @FXML TableColumn<Part, Integer> product_partID;
    @FXML TableColumn<Part, String> product_partName;
    @FXML TableColumn<Part, Integer> product_partStock;
    @FXML TableColumn<Part, Double> product_partPrice;
    @FXML TableColumn<Part, Integer> assoc_product_partID;
    @FXML TableColumn<Part, String> assoc_product_partName;
    @FXML TableColumn<Part, Integer> assoc_product_partStock;
    @FXML TableColumn<Part, Double> assoc_product_partPrice;
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /**Switch window back to Home window.
     * @param event action listener for detecting actions in the form.*/
    public void switchToHome(ActionEvent event) throws IOException {
        Parent partTableParent = FXMLLoader.load(getClass().getResource("../view/window_home.fxml"));
        Scene addPartScene = new Scene(partTableParent);
        Stage mainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(addPartScene);
        mainWindow.show();
    }

    /**Method to save a new Product.
     * Pulls data from the form and contains logic and data validation, then creates a new Product.
     * @param event Action listener for detecting actions in the form.*/
    public void saveProduct(ActionEvent event) throws Exception {
        int id = -1;
        ArrayList<Integer> allIDs = new ArrayList<Integer>(Inventory.getAllProducts().size());
        for (Product products : Inventory.getAllProducts()) {
            allIDs.add(products.getId());
        }
        for (int i = 0; i < allIDs.size(); i++) {
            if (!(allIDs.contains(i))) {
                id = i;
                break;
            } else { id = allIDs.size(); }
        }
        try {
            String name = text_add_productname.getText();
            if (name.isEmpty()) {
                labelinfo.setText("Please Enter Name");
                throw new Exception("Please Enter Name");
            }
            double price = Double.parseDouble(text_add_productprice.getText());
            if (price <= 0) {
                labelinfo.setText("Enter Positive Number for Price");
                throw new Exception("Enter Positive Number for Price");
            }
            int min = Integer.parseInt(text_add_productmin.getText());
            if (min < 0) {
                labelinfo.setText("Minimum Must be a Positive Number");
                throw new Exception("Min must be a positive number");
            }
            int max = Integer.parseInt(text_add_productmax.getText());
            if ((max < 0) || (max < min)) {
                labelinfo.setText("Max Must Be Greater Than Minimum");
                throw new Exception("Max must be Greater than Min");
            }
            int inventory = Integer.parseInt(text_add_productstock.getText());
            if (inventory < 0) {
                labelinfo.setText("Enter Positive Number for Inventory");
                throw new Exception("Enter Positive Number for Inventory");
            }
            else if (inventory < min || inventory > max) {
                labelinfo.setText("Inventory Must Be Between Min and Max");
                throw new Exception("Inventory must be between min and max");
            }

            Product newProduct = new Product(id,name,price,inventory,min,max);
            for (Part parts : assocParts) { newProduct.addAssociatedPart(parts); }
            Inventory.addProduct(newProduct);

            switchToHome(event);
        }
        catch (NumberFormatException invalid) {
            System.out.println("Enter valid number in all fields");
            labelinfo.setTextFill(Paint.valueOf("red"));
            labelinfo.setText("Enter Valid Number in All Fields");
        }
        catch (Exception invalid) {
            System.out.println("Invalid Entry");
            System.out.println(invalid.getMessage());
            labelinfo.setTextFill(Paint.valueOf("red"));
        }

    }

    /**Method to add an associated part to the selected Product.*/
    public void addAssocPart() {
        Part selectedRow = table_add_product.getSelectionModel().getSelectedItem();
        assocParts.add(selectedRow);
        text_add_partSearch.clear();
        SetAssocPartTable();
    }

    /**Method to remove an associated part from the selected Product.*/
    public void removeAssocPart() {
        Part selectedRow = table_assoc_product.getSelectionModel().getSelectedItem();
        assocParts.remove(selectedRow);
        SetAssocPartTable();
    }

    /**Method required to initialize the window.
     * @param resourceBundle
     * @param url
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product_partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        product_partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        product_partStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        product_partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        assoc_product_partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        assoc_product_partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        assoc_product_partStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        assoc_product_partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        SetPartTable();
    }

    /**Method used to populate the Part Tableview.*/
    public void SetPartTable() {
        table_add_product.setItems(Inventory.getAllParts());
    }

    /**Method used to populate the Associated Parts Tableview.*/
    public void SetAssocPartTable() {
        table_assoc_product.setItems(assocParts);
    }

    /**Method used to filter the Parts table.
     * Retrieves search text and filters results in the Parts table.*/
    public void UpdateSearchTable() {
        boolean isFound = false;
        String searchText = text_add_partSearch.getText().toLowerCase();
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        for (Part parts : Inventory.getAllParts()) {
            if (String.valueOf(parts.getId()).contains(searchText.toLowerCase())) {
                searchParts.add(parts);
                isFound = true;
            }
            else if (parts.getName().toLowerCase().contains(searchText)) {
                searchParts.add(parts);
                isFound = true;
            }
        }
        if (!isFound) { labelinfo.setText("Part Not Found!"); }
        if (isFound) { labelinfo.setText("Make Selection From the List."); }
        table_add_product.setItems(searchParts);
    }
}
