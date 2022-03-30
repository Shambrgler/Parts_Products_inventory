package InventoryProgram.model;

import InventoryProgram.Part;

/**Outsourced class extends the part class with an additional constructor and parameter "CompanyName".
 * @author Shawn Wayne Wells
 * */
public class Outsourced extends Part {

    private String companyName;

    /**Constructor for Outsourced class.
     * @param id Part ID
     * @param name Part name
     * @param price Part price
     * @param stock Available parts in inventory
     * @param min Minimum number of parts in inventory
     * @param max Maximum number of parts in inventory
     * @param companyName Company name for Outsourced Part
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**Get Company Name.
     * @return Return Company Name
     * */
    public String getCompanyName() { return companyName; }

    /**Set Company name.
     * @param companyName Company Name
     * */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
