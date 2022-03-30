package InventoryProgram.model;

import InventoryProgram.Part;

/**The InHouse class extends the Part class. Contains a constructor with an additional parameter, MachineID.
 * @author Shawn Wayne Wells
 * */
public class InHouse extends Part {

    private int machineID;

    /**Constructor for InHouse Part class.
     * @param id Part ID number
     * @param name Part name
     * @param price Part Price
     * @param stock Current number in inventory
     * @param min Minimum required in inventory
     * @param max Maximum allowed in inventory
     * @param machineID In house Machine ID
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**Set Machine ID. */
    public void setMachineID(int machineID) { this.machineID = machineID; }
    /**Get Machine ID. */
    public int getMachineID() { return this.machineID; }
}
