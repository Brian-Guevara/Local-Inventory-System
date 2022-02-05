package task1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is our Product object/class. It encompasses its own list of parts as
 * well.
 *
 *
 * PRODUCT CLASS
 *
 * @author Brian Guevara
 */
public class Product {

    // Product's parts
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    // Product ID
    private int id;
    // Product name
    private String name;
    // Product price
    private double price;
    // Product stock
    private int stock;
    // Minimum stock
    private int min;
    // Maximum stock
    private int max;

    // Default constructor leaves everything blank
    public Product() {

    }

    // Constructor takes in the name, price, stock, min, and max values. It then
    // sets them for this object.
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    // Method sets the name
    public void setName(String name) {
        this.name = name;
    }

    // Method sets the ID
    public void setId(int id) {
        this.id = id;
    }

    // Method sets the price
    public void setPrice(double price) {
        this.price = price;
    }

    // Method sets the stock count
    public void setStock(int stock) {
        this.stock = stock;
    }

    // Method sets the minimum stock
    public void setMin(int min) {
        this.min = min;
    }

    // Method sets the maximum stock
    public void setMax(int max) {
        this.max = max;
    }

    // Method gets the name
    public String getName() {
        return name;
    }

    // Method gets the ID
    public int getId() {
        return this.id;
    }

    // Method gets the price
    public double getPrice() {
        return this.price;
    }

    // Method gets the stock count
    public int getStock() {
        return this.stock;
    }

    // Method gets the minimum stocks
    public int getMin() {
        return this.min;
    }

    // Method gets the maximum stock 
    public int getMax() {
        return this.max;
    }

    // Method adds a part to this product's parts
    public void addAssociatedPart(Part newPart) {
        this.associatedParts.add(newPart);
    }

    // Method gets all the parts in a product
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    // Method deletes a part from this product
    public boolean deleteAssociatedPart(Part selectedPart) {
        if (associatedParts.contains(selectedPart)) {
            associatedParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }

    }

}
