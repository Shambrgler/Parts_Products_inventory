package InventoryProgram.Controller;
import InventoryProgram.model.InHouse;
import InventoryProgram.model.Inventory;
import InventoryProgram.model.Outsourced;
import InventoryProgram.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**Controller for the Modify Part form.
 * Populates with selected part from the Home window and allows user to modify a selected part.
 * @author Shawn Wayne Wells
 * */
public class ModifyPartController implements Initializable {

    private Part selectedPart;
    private int partIndex;
    @FXML
    private Label labelMachineId, labelInfo;
    @FXML
    private RadioButton radio_modify_part_inhouse;
    @FXML
    private RadioButton radio_modify_part_outsourced;
    @FXML
    private TextField text_modify_partid, text_modify_partname, text_modify_partstock, text_modify_partprice, text_modify_partmax, text_modify_partmin, text_modify_partmachine;

    /**Change label to Machine ID. */
    public void changeToMachineID() {
        labelMachineId.setText("Machine ID");
    }

    /**Change label to Company Name. */
    public void changeToCompanyName() {
        labelMachineId.setText("Company Name");
    }

    /**Method to set the the text fields.
     * Used by Home controller to populate data on the selected part.
     * @param part Selected Part object.
     * */
    public void initialPart(Part part) {
        selectedPart = part;
        partIndex = Inventory.getAllParts().indexOf(part);
        text_modify_partid.setText(String.valueOf(part.getId()));
        text_modify_partname.setText(part.getName());
        text_modify_partstock.setText(String.valueOf(part.getStock()));
        text_modify_partprice.setText(String.valueOf(part.getPrice()));
        text_modify_partmax.setText(String.valueOf(part.getMax()));
        text_modify_partmin.setText(String.valueOf(part.getMin()));
        if (part.getClass() == InHouse.class) {
            radio_modify_part_inhouse.setSelected(true);
            text_modify_partmachine.setText(String.valueOf(((InHouse) part).getMachineID()));
            labelMachineId.setText("Machine ID");
        } else if (part.getClass() == Outsourced.class) {
            radio_modify_part_outsourced.setSelected(true);
            text_modify_partmachine.setText(((Outsourced) part).getCompanyName());
            labelMachineId.setText("Company Name");
        }
    }

    /**Save changes to selected Part.
     * Contains logic and data validation for saving changes to selected part.
     * */
    public void savePart(ActionEvent event) throws Exception {
        selectedPart = Inventory.lookupPart(Integer.parseInt(text_modify_partid.getText()));
        try {
            String name = text_modify_partname.getText();
            if (name.isEmpty()) {
                labelInfo.setText("Please Enter Name");
                throw new Exception("Please Enter Name");
            }
            double price = Double.parseDouble(text_modify_partprice.getText());
            if (price <= 0) {
                labelInfo.setText("Enter Positive Number for Price");
                throw new Exception("Enter Positive Number for Price");
            }
            int min = Integer.parseInt(text_modify_partmin.getText());
            if (min < 0) {
                labelInfo.setText("Minimum Must be a Positive Number");
                throw new Exception("Min must be a positive number");
            }
            int max = Integer.parseInt(text_modify_partmax.getText());
            if ((max < 0) || (max < min)) {
                labelInfo.setText("Max Must Be Greater Than Minimum");
                throw new Exception("Max must be Greater than Min");
            }
            int inventory = Integer.parseInt(text_modify_partstock.getText());
            if (inventory < 0) {
                labelInfo.setText("Enter Positive Number for Inventory");
                throw new Exception("Enter Positive Number for Inventory");
            }
            else if (inventory < min || inventory > max) {
                labelInfo.setText("Inventory Must Be Between Min and Max");
                throw new Exception("Inventory must be between min and max");
            }

            selectedPart.setName(name);
            selectedPart.setStock(inventory);
            selectedPart.setPrice(price);
            selectedPart.setMax(max);
            selectedPart.setMin(min);
            if (radio_modify_part_inhouse.isSelected() && selectedPart.getClass() == InHouse.class) {
                ((InHouse) selectedPart).setMachineID(Integer.parseInt(text_modify_partmachine.getText()));
                labelInfo.setText("Part Updated.");
                System.out.println("Part Updated: " + name);
                switchToHome(event);

            } else if (radio_modify_part_outsourced.isSelected() && selectedPart.getClass() == Outsourced.class) {
                ((Outsourced) selectedPart).setCompanyName(text_modify_partmachine.getText());
                if (selectedPart.getName().isEmpty()) {
                    labelInfo.setText("Enter Company Name");
                    throw new Exception("Enter Company Name");
                }
                labelInfo.setText("Part Updated.");
                System.out.println("Part Updated: " + name);
                switchToHome(event);
            } else if (radio_modify_part_outsourced.isSelected() && selectedPart.getClass() == InHouse.class) {
                Inventory.setChangeToCompany(text_modify_partmachine.getText());
                Inventory.updatePart(selectedPart.getId(), selectedPart);
                switchToHome(event);
            } else if (radio_modify_part_inhouse.isSelected() && selectedPart.getClass() == Outsourced.class) {
                Inventory.setChangeToMachine(Integer.parseInt(text_modify_partmachine.getText()));
                Inventory.updatePart(selectedPart.getId(), selectedPart);
                switchToHome(event);
            }
        }
        catch (NumberFormatException invalid) {
            System.out.println("Enter valid number in all fields");
            labelInfo.setTextFill(Paint.valueOf("red"));
            labelInfo.setText("Enter Valid Number in All Fields");
        }
        catch (Exception invalid) {
            System.out.println("Invalid Entry");
            System.out.println(invalid.getMessage());
            labelInfo.setTextFill(Paint.valueOf("red"));
        }

    }

    /**Method to return to the Home window.
     * @param event Action Listener.
     * */
    public void switchToHome(ActionEvent event) throws IOException {
        Parent partTableParent = FXMLLoader.load(getClass().getResource("../view/window_home.fxml"));
        Scene addPartScene = new Scene(partTableParent);
        Stage mainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(addPartScene);
        mainWindow.show();
    }

    @Override
    /**Required to initialize window. */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
