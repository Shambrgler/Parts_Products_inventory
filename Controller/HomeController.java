package InventoryProgram.Controller;

import InventoryProgram.*;
import InventoryProgram.model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

/**This is the controller for the Home window.
 * This is the window that opens when the application is first run and contains methods and functionality for the Home window.
 * @author Shawn Wayne Wells*/
public class HomeController implements Initializable {
    @FXML private Label labelPartInfo, labelProductInfo;
    @FXML private Button button_close, button_part_confirmdelete, button_part_canceldelete, button_product_confirmdelete, button_product_canceldelete;
    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Part, Integer> column_part_id;
    @FXML private TableColumn<Part, Integer> column_part_inventory;
    @FXML private TableColumn<Product, Integer> column_product_id;
    @FXML private TableColumn<Product, Integer> column_product_inventory;
    @FXML private TableColumn<Part, String> column_part_name;
    @FXML private TableColumn<Product, String> column_product_name;
    @FXML private TableColumn<Part, Double> column_part_price;
    @FXML private TableColumn<Product, Double> column_product_price;
    @FXML private TextField text_part_search, text_product_search;

    /**Method used to close the program.*/
    public void closeWindow() {
        Stage stage = (Stage) button_close.getScene().getWindow();
        stage.close();
    }

    /**Method used to switch window to the Add Part form.
     * @param event Action Listener for Home Window.
     * */
    public void switchToAddPart(ActionEvent event) throws IOException {
        Parent partTableParent = FXMLLoader.load(getClass().getResource("../view/window_add_part.fxml"));
        Scene addPartScene = new Scene(partTableParent);
        Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.setScene(addPartScene);
        mainWindow.show();
    }

    /**Method used to switch to the Add Product window.
     * @param event Action Listener
     * */
    public void switchToAddProduct(ActionEvent event) throws IOException {
        Parent partTableParent = FXMLLoader.load(getClass().getResource("../view/window_add_product.fxml"));
        Scene addPartScene = new Scene(partTableParent);
        Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.setScene(addPartScene);
        mainWindow.show();
    }

    /**Method used to switch to the Modify Part window.
     * @param event Action Listener
     * */
    public void switchToModifyPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/window_modify_part.fxml"));
            Parent partTableParent = loader.load();
            Scene modifyPartScene = new Scene(partTableParent);
            ModifyPartController controller = loader.getController();
            controller.initialPart(partTableView.getSelectionModel().getSelectedItem());
            Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainWindow.setScene(modifyPartScene);
            mainWindow.show();
        }
        catch (NullPointerException error) {
            System.out.println("Please make selection");
            labelPartInfo.setText("Please make a selection from the List");
        }
    }

    /**Method to switch to the Modify Product Window.
     * @param event Action Listener
     * <p><b>
     *            RUNTIME ERROR:  I used the Refactor tool in IntelliJ to better organize some of the files in the "src" folder.
     *              The refactor did not properly update the path for the .fxml file locations.  After figuring out the issue, had to manually correct
     *              all the file locations for the .fxml files in all the controller classes and methods.
     * </b></p>*/
    public void switchToModifyProduct(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/window_modify_product.fxml"));
            Parent partTableParent = loader.load();
            Scene modifyPartScene = new Scene(partTableParent);
            ModifyProductController controller = loader.getController();
            controller.initialProduct(productTableView.getSelectionModel().getSelectedItem());
            Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainWindow.setScene(modifyPartScene);
            mainWindow.show();

        }
        catch (NullPointerException error) {
            System.out.println("Please make selection");
            labelProductInfo.setText("Please make a selection from the List");
        }
    }

    /**Activates the Cancel/Confirm delete part buttons.
     * @return Returns void to exit method without taking action.
     * */
    public void deletePart() {
        ObservableList<Part> selectedRow;
        selectedRow = partTableView.getSelectionModel().getSelectedItems();
        if (selectedRow.isEmpty()) {
            return;
        } else if (!(selectedRow.isEmpty())) {
            button_part_confirmdelete.setDisable(false);
            button_part_canceldelete.setDisable(false);
            labelPartInfo.setText("Press Confirm to Delete Part, or Cancel");
        }

    }

    /**Disables the Cancel/Confirm delete part buttons. */
    public void cancelDeletePart() {
        button_part_canceldelete.setDisable(true);
        button_part_confirmdelete.setDisable(true);
        labelPartInfo.setText("Delete Canceled. Make New Selection");
    }

    /**Deletes the selected Part. Then re-disables the Cancel/Confirm delete buttons.*/
    public void confirmDeletePart() {
        Part selectedRow = partTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedRow);
        button_part_canceldelete.setDisable(true);
        button_part_confirmdelete.setDisable(true);
        labelPartInfo.setText("Part Deleted!");
        SetTable();
    }

    /**Activates the Cancel/Confirm delete products buttons.
     * @return Returns void to exit without taking action.
     * */
    public void deleteProduct() {
        ObservableList<Product> selectedRow;
        selectedRow = productTableView.getSelectionModel().getSelectedItems();
        if (selectedRow.isEmpty()) {
            return;
        } else if (!(selectedRow.isEmpty())) {
            button_product_canceldelete.setDisable(false);
            button_product_confirmdelete.setDisable(false);
            labelProductInfo.setText("Press Confirm to Delete Product, or Cancel");
        }
    }

    /**Disables the Cancel/Confirm delete products buttons. */
    public void cancelDeleteProduct() {
        button_product_canceldelete.setDisable(true);
        button_product_confirmdelete.setDisable(true);
        labelProductInfo.setText("Delete Canceled. Make New Selection");
    }

    /**Delete the selected Product.
     * Confirms that Product has no associated parts, then deletes the Product.
     * @return returns void to exit method without taking an action.
     * */
    public void confirmDeleteProduct() {
        Product selectedRow = productTableView.getSelectionModel().getSelectedItem();
        if (!(selectedRow.getAssociatedParts().isEmpty())) {
            labelProductInfo.setText("Please remove associated parts first, before deleting");
            return;
        }
        Inventory.deleteProduct(selectedRow);
        button_product_canceldelete.setDisable(true);
        button_product_confirmdelete.setDisable(true);
        labelProductInfo.setText("Part Deleted!");
        SetTable();
    }

    /**Method required to initialize the window.
     * @param resourceBundle
     * @param url
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        column_part_id.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        column_part_name.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        column_part_inventory.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        column_part_price.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        column_product_id.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        column_product_name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        column_product_inventory.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        column_product_price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        SetTable();

    }

    /**Populates both the Part Tableview and the Product Tableview. */
    public void SetTable() {
        partTableView.setItems(getParts());
        productTableView.setItems(getProducts());
    }

    /**Filters search results.  Updates the Parts table the with filtered search results.*/
    public void UpdatePartTable() {
        boolean isFound = false;
        String searchText = text_part_search.getText().toLowerCase();
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
        if (!isFound) { labelPartInfo.setText("Part Not Found!");}
        if (isFound) { labelPartInfo.setText("Make Selection From the List."); }
        partTableView.setItems(searchParts);
    }

    /**Filters search results.  Updates the Products table the with filtered search results.*/
    public void UpdateProductTable() {
        boolean isFound = false;
        String searchText = text_product_search.getText().toLowerCase();
        ObservableList<Product> searchProducts = FXCollections.observableArrayList();
        for (Product products : Inventory.getAllProducts()) {
            if (String.valueOf(products.getId()).contains(searchText.toLowerCase())) {
                searchProducts.add(products);
                isFound = true;
            }
            else if (products.getName().toLowerCase().contains(searchText)) {
                searchProducts.add(products);
                isFound = true;
            }
        }
        if (!isFound) { labelProductInfo.setText("Part Not Found!"); }
        if (isFound) { labelProductInfo.setText("Make Selection From the List."); }
        productTableView.setItems(searchProducts);
    }

    /**Returns all Parts in an ObservableList.
     * @return Returns list of All Parts.
     * */
    public ObservableList<Part> getParts() {
        return Inventory.getAllParts();
    }

    /**Returns all Products in an ObservableList.
     * @return Returns list of All Products.
     * */
    public ObservableList<Product> getProducts() {
        return Inventory.getAllProducts();
    }
}
