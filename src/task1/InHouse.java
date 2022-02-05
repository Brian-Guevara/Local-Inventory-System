package task1;

/**
 * This is our InHouse object/class. It extends the Part object.
 *
 *
 * IN HOUSE CLASS
 *
 * @author Brian Guevara
 */
public class InHouse extends Part {

    // This variable is exclusive to this part
    private int machineId;

    // Call the default Part constructor
    public InHouse() {
        super();
    }

    // Calls the Part constructor (setting all part information) and sets the machine ID
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;

    }

    // Method sets machine id
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    // Method returns the machine id
    public int getMachineId() {
        return this.machineId;
    }

}
