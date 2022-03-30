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
import java.util.ResourceBundle;

/**Controller for the Modify Product form.
 * Populates with data from selected product on home window, allows user to modify a product and add/remove associated parts.
 * @author Shawn Wayne Wells
 * */
public class ModifyProductController implements Initializable {

    private Product selectedProduct;
    private int productIndex;

    @FXML TextField text_modify_productid;
    @FXML TextField text_modify_productname;
    @FXML TextField text_modify_productstock;
    @FXML TextField text_modify_productprice;
    @FXML TextField text_modify_productmax;
    @FXML TextField text_modify_productmin;
    @FXML TextField text_modify_partSearch;
    @FXML Label labelinfo;
    @FXML TableView<Part> table_modify_product;
    @FXML TableView<Part> table_assoc_product;
    @FXML TableColumn<Part, Integer> product_partID;
    @FXML TableColumn<Part, String> product_partName;
    @FXML TableColumn<Part, Integer> product_partStock;
    @FXML TableColumn<Part, Double> product_partPrice;
    @FXML TableColumn<Part, Integer> assoc_product_partID;
    @FXML TableColumn<Part, String> assoc_product_partName;
    @FXML TableColumn<Part, Integer> assoc_product_partStock;
    @FXML TableColumn<Part, Double> assoc_product_partPrice;

    /**Populate data for selected Product.
     * Used by Home window to populate all the fields for the selected Product.
     * @param product selected Product object
     * */
    public void initialProduct(Product product) {
        selectedProduct = product;
        productIndex = Inventory.getAllProducts().indexOf(product);
        text_modify_productid.setText(String.valueOf(product.getId()));
        text_modify_productname.setText(product.getName());
        text_modify_productstock.setText(String.valueOf(product.getStock()));
        text_modify_productprice.setText(String.valueOf(product.getPrice()));
        text_modify_productmax.setText(String.valueOf(product.getMax()));
        text_modify_productmin.setText(String.valueOf(product.getMin()));
        SetAssocPartTable();
    }

    /**Switch window to the Home window.
     * @param event Action Listener
     * */
    public void switchToHome(ActionEvent event) throws IOException {
        Parent partTableParent = FXMLLoader.load(getClass().getResource("../view/window_home.fxml"));
        Scene addPartScene = new Scene(partTableParent);
        Stage mainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(addPartScene);
        mainWindow.show();
    }

    /**Save changes to selected Product.
     * Contains logic and data validation for saving changes to selected Product.
     * @param event Action listener
     * */
    public void saveProduct(ActionEvent event) throws Exception {
        selectedProduct = Inventory.lookupProduct(Integer.parseInt(text_modify_productid.getText()));
        try {
            String name = text_modify_productname.getText();
            if (name.isEmpty()) {
                labelinfo.setText("Please Enter Name");
                throw new Exception("Please Enter Name");
            }
            double price = Double.parseDouble(text_modify_productprice.getText());
            if (price <= 0) {
                labelinfo.setText("Enter Positive Number for Price");
                throw new Exception("Enter Positive Number for Price");
            }
            int min = Integer.parseInt(text_modify_productmin.getText());
            if (min < 0) {
                labelinfo.setText("Minimum Must be a Positive Number");
                throw new Exception("Min must be a positive number");
            }
            int max = Integer.parseInt(text_modify_productmax.getText());
            if ((max < 0) || (max < min)) {
                labelinfo.setText("Max Must Be Greater Than Minimum");
                throw new Exception("Max must be Greater than Min");
            }
            int inventory = Integer.parseInt(text_modify_productstock.getText());
            if (inventory < 0) {
                labelinfo.setText("Enter Positive Number for Inventory");
                throw new Exception("Enter Positive Number for Inventory");
            }
            else if (inventory < min || inventory > max) {
                labelinfo.setText("Inventory Must Be Between Min and Max");
                throw new Exception("Inventory must be between min and max");
            }

            selectedProduct.setName(name);
            selectedProduct.setStock(inventory);
            selectedProduct.setPrice(price);
            selectedProduct.setMax(max);
            selectedProduct.setMin(min);
            Inventory.updateProduct(Inventory.getAllProducts().indexOf(selectedProduct), selectedProduct);
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

    /**Adds the selected Part to Products associated parts list. */
    public void addAssocPart() {
        Part selectedRow;
        selectedRow = table_modify_product.getSelectionModel().getSelectedItem();
        selectedProduct.addAssociatedPart(Inventory.lookupPart(selectedRow.getId()));
        SetPartTable();
        SetAssocPartTable();
        text_modify_partSearch.clear();
    }

    /**Removes Part from Products associated parts list.
     * @return Returns if no part is selected
     * */
    public void removeAssocPart() {
        Part selectedRow;
        if (table_assoc_product.getSelectionModel().isEmpty()) { return; }
        else {
            selectedRow = table_assoc_product.getSelectionModel().getSelectedItem();
            selectedProduct.deleteAssociatedPart(Inventory.lookupPart(selectedRow.getId()));
            SetPartTable();
            SetAssocPartTable();
        }
    }


    @Override
    /**Used to initialize the window. */
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

    /**Gets Parts and populates the Parts Tableview. */
    public void SetPartTable() {
        table_modify_product.setItems(getParts());
    }

    /**Gets Associated Parts and populates the associated parts Tableview. */
    public void SetAssocPartTable() {
        table_assoc_product.setItems(getAssocParts());
    }

    /**Gets an ObservableList of all Parts.
     * @return returns ObservableList of all Parts
     * */
    public ObservableList<Part> getParts() {
        ObservableList<Part> partsList = FXCollections.observableArrayList();
        for (Part parts : Inventory.getAllParts()) { partsList.add(parts); }
        return partsList;
    }

    /**Gets ObservableList of selected Products associated parts.
     * @return returns Observable list of associated parts
     * <p><b>
     *     RUNTIME ERROR:  When this window loaded I was running this function to populate the initial list,
     *     but was getting a NullPointer Exception because it was running before the selected product information
     *     was loaded.  I had to separate the two tables into two different functions then add one to the "Initial Product"
     *     method so that the product information was loaded first, then the associated parts was loaded after.
     * </b></p>*/
    public ObservableList<Part> getAssocParts() {
        ObservableList<Part> partsList = FXCollections.observableArrayList();
        for (Part parts : selectedProduct.getAssociatedParts()) {
            partsList.add(parts);
        }
        return partsList;
    }

    /**Used to filter Parts tableview with the search text.
     * Filters Parts Tableview by ID or Name
     * */
    public void UpdateSearchTable() {
        boolean isFound = false;
        String searchText = text_modify_partSearch.getText().toLowerCase();
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

        table_modify_product.setItems(searchParts);
    }
}
