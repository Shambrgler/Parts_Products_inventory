package InventoryProgram;

import InventoryProgram.model.InHouse;
import InventoryProgram.model.Inventory;
import InventoryProgram.model.Outsourced;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**Main Class used to launch the application and populate dummy data for the tables.
 * @author Shawn Wayne Wells
 * <p><b>
 *     JAVADOCS: Javadocs folder located under the "src" folder
 *     COMPATIBLE FEATURE:  I would add the functionality to store information between runs of the program in a database of some sort.
 *     Right now the data only lasts while the program is running and is lost when the program closes.
 * </b></p>
 * */
public class Main extends Application {




    @Override
    /**Sets up Main Window.
     * @param primaryStage Primary Stage
     * */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/window_home.fxml"));
        primaryStage.setTitle("Inventory Management System");
        Scene scene = new Scene(root, 1200,600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**Main Method used to launch program.
     * @param args args
     * */
    public static void main(String[] args) throws Exception {

        Part brakes = new Outsourced(0,"Brakes",15.0,10,5,15,"Acme Brakes");
        Inventory.addPart(brakes);
        Part wheel = new InHouse(1,"Wheel",25.0,10,4,64,1234);
        Inventory.addPart(wheel);
        Part seat = new Outsourced(2,"Seat",12.0,5,2,25,"Acme Seats");
        Inventory.addPart(seat);
        Product bike = new Product(0,"Bike",100,5,1,20);
        Inventory.addProduct(bike);
        bike.addAssociatedPart(brakes);
        launch(args);
    }
}
