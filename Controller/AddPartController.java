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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**This class is the controller for the Add Part window.
 * The Home window directs here when the Add Part button is pushed,  Contains all functions and logic for the Add Parts form.
 * @author Shawn Wayne Wells
 * */
public class AddPartController implements Initializable {

    @FXML
    private Label labelMachineId,infoLabel;
    @FXML
    private TextField text_add_part_id,text_add_part_name,text_add_part_inventory,text_add_part_price,text_add_part_max,text_add_part_min,text_add_part_machine;
    @FXML
    private RadioButton radio_add_part_inhouse,radio_add_part_outsourced;

    /**Changes the MachineID/CompanyName label.
     * Changes the label when MachineID radio button is clicked.*/
    public void changeToMachineID() {
        labelMachineId.setText("Machine ID");
    }

    /**Changes the MachineID/CompanyName label.
     * Changes the label when Company Name radio button is clicked.*/
    public void changeToCompanyName() {
        labelMachineId.setText("Company Name");
    }

    /**Switches screen to the Home screen.
     * This function is called to change the window back to the Home window.*/
    public void switchToHome(ActionEvent event) throws IOException {
        Parent partTableParent = FXMLLoader.load(getClass().getResource("../view/window_home.fxml"));
        Scene addPartScene = new Scene(partTableParent);
        Stage mainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(addPartScene);
        mainWindow.show();
    }

    /**Method for creating a new Part.
     * This method contains data validation and logic for creating and new part, pulls data from the form validates it and creates a new Part if data is correct.
     * @param event action listener for the Save button.*/
    public void createNewPart(ActionEvent event) {
        int id = -1;
        ArrayList<Integer> allIDs = new ArrayList<Integer>(Inventory.getAllParts().size());
        for (Part parts : Inventory.getAllParts()) {
            allIDs.add(parts.getId());
        }
        for (int i = 0; i < allIDs.size(); i++) {
            if (!(allIDs.contains(i))) {
                id = i;
                break;
            } else { id = allIDs.size(); }
        }

        try {
            String name = text_add_part_name.getText();
            if (name.isEmpty()) {
                infoLabel.setText("Please Enter Name");
                throw new Exception("Please Enter Name");
            }
            double price = Double.parseDouble(text_add_part_price.getText());
            if (price <= 0) {
                infoLabel.setText("Enter Positive Number for Price");
                throw new Exception("Enter Positive Number for Price");
            }
            int min = Integer.parseInt(text_add_part_min.getText());
            if (min < 0) {
                infoLabel.setText("Min must be a positive number");
                throw new Exception("Min must be a positive number");
            }
            int max = Integer.parseInt(text_add_part_max.getText());
            if ((max < 0) || (max < min)) {
                infoLabel.setText("Max Must be Greater Than Min");
                throw new Exception("Max must be Greater than Min");
            }
            int inventory = Integer.parseInt(text_add_part_inventory.getText());
            if (inventory < 0) {
                infoLabel.setText("Enter Positive Number for Inventory");
                throw new Exception("Enter Positive Number for Inventory");
            }
            else if (inventory < min || inventory > max) {
                infoLabel.setText("Inventory must be between min and max");
                throw new Exception("Inventory must be between min and max");
            }
            if (radio_add_part_inhouse.isSelected()) {
                int machineID = Integer.parseInt(text_add_part_machine.getText());
                if (machineID < 0) {
                    infoLabel.setText("Enter number for Machine ID");
                    throw new Exception("Enter number for Machine ID");
                }
                Part newPart = new InHouse(id,name,price,inventory,min,max,machineID);
                Inventory.addPart(newPart);
                infoLabel.setText("New Part Saved!");
                infoLabel.setTextFill(Paint.valueOf("Red"));
                System.out.println("New In House Part Saved: " + name);
                switchToHome(event);

            }
            else if (radio_add_part_outsourced.isSelected()) {
                String companyName = text_add_part_machine.getText();
                if (companyName.isEmpty()) {
                    infoLabel.setText("Enter Company Name");
                    throw new Exception("Enter Company Name");
                }
                Part newPart = new Outsourced(id,name,price,inventory,min,max,companyName);
                Inventory.addPart(newPart);
                infoLabel.setText("New Part Saved!");
                infoLabel.setTextFill(Paint.valueOf("Red"));
                System.out.println("New Outsourced Part Saved: " + name);
                switchToHome(event);
            }
        }
        catch (NumberFormatException invalid) {
            infoLabel.setText("Enter valid number in all fields");
            System.out.println("Enter valid number in all fields");
        }
        catch (Exception invalid) {
            System.out.println("Invalid Entry");
            System.out.println(invalid.getMessage());
        }
    }

    /**Method required to initialize the window.
     * @param resourceBundle
     * @param url
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}