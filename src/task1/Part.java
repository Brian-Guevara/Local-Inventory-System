package task1;

/**
 * This is our Part object/class. It is the parent of our InHouse and Outsourced
 * objects.
 *
 *
 * PART CLASS
 *
 * @author Brian Guevara
 */
public abstract class Part {
    // Part ID
    private int id;
    // Part name
    private String name;
    // Part price
    private double price;
    // Part stock
    private int stock;
    // Minimum stock
    private int min;
    // Maximum stock
    private int max;

    // Default constructor leaves everything blank
    public Part() {
    }

    // Constructor takes in the name, price, stock, min, and max values. It then
    // sets them for this object.
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    // Methods sets the ID
    public void setID(int id) {
        this.id = id;
    }

    // Methods sets the name
    public void setName(String Name) {
        this.name = Name;
    }

    // Methods sets the price
    public void setPrice(double price) {
        this.price = price;
    }

    // Methods sets the stock
    public void setStock(int stock) {
        this.stock = stock;
    }

    // Methods sets the minimum stock
    public void setMin(int min) {
        this.min = min;
    }

    // Methods sets the maximum stock
    public void setMax(int max) {
        this.max = max;
    }

    // Methods gets the ID
    public int getId() {
        return this.id;
    }

    // Methods gets the name
    public String getName() {
        return this.name;
    }

    // Methods gets the price
    public double getPrice() {
        return this.price;
    }

    // Methods gets the stock
    public int getStock() {
        return this.stock;
    }

    // Methods gets the minimum stock
    public int getMin() {
        return this.min;
    }

    // Methods gets the maximum stock
    public int getMax() {
        return this.max;
    }

}
