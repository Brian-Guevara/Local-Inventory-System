package task1;

/**
 * This is our Outsourced object/class. It extends the Part object.
 *
 *
 * OUTSOURCED CLASS
 *
 * @author Brian Guevara
 */
public class Outsourced extends Part {

    // This variable is exclusive to this part
    private String companyName;

    // Call the default Part constructor
    public Outsourced() {
        super();
    }

    // Calls the Part constructor (setting all part information) and sets the company name
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    // Methods sets the company name of the part
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // Method returns the company name
    public String getCompanyName() {
        return this.companyName;
    }

}
