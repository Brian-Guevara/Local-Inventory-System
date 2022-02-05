package task1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is our inventory object/class. It contains the 2 observable lists for
 * our parts and products. It contains all the methods needed to
 * add/modify/delete its
 *
 *
 * INVENTORY CLASS
 *
 * @author Brian Guevara
 */
public class Inventory {

    // These are our observable lists. One for parts, the other for products
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // This method adds a part to our part observable list.
    public void addPart(Part newPart) {
        this.allParts.add(newPart);
    }

    // This method adds a product to our product observable list.
    public void addProduct(Product newProduct) {
        this.allProducts.add(newProduct);
    }

    // This method returns a part with a requested ID.
    public Part lookupPart(int partID) {
        // Look for the part in the inventory.
        for (Part currentPart : this.allParts) {
            if (currentPart.getId() == partID) {
                return currentPart;
            }

        }
        return null;

    }

    // This Method returns an observable list of all parts that contain
    // the passed in string.
    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundMatches = FXCollections.observableArrayList();
        for (Part currentPart : this.allParts) {
            if (currentPart.getName().toLowerCase().contains(partName)) {
                foundMatches.add(currentPart);
            }
        }
        return foundMatches;

    }

    // This method returns a product with a requested ID
    public Product lookupProduct(int productID) {
        // Look for the product in the inventory.
        for (Product currentProduct : this.allProducts) {
            if (currentProduct.getId() == productID) {
                return currentProduct;
            }

        }
        return null;

    }

    // This Method returns an observable list of all products that contain
    // the passed in string.
    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundMatches = FXCollections.observableArrayList();
        for (Product currentProduct : this.allProducts) {
            if (currentProduct.getName().toLowerCase().contains(productName)) {
                foundMatches.add(currentProduct);
            }
        }
        return foundMatches;

    }

    // Method returns all the parts in our inventory
    public ObservableList<Part> getAllParts() {
        return this.allParts;
    }

    // Method returns all the products in our inventory
    public ObservableList<Product> getAllProducts() {
        return this.allProducts;
    }

    // Method deletes a part from the inventory
    public boolean deletePart(Part selectedPart) {
        if (this.allParts.contains(selectedPart)) {

            // Delete the part from any product that contains it
            for (Product prod : this.allProducts) {
                if (prod.getAllAssociatedParts().contains(selectedPart)) {
                    prod.deleteAssociatedPart(selectedPart);
                }
            }
            // Remove part from our list
            this.allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }

    }

    // Method deletes a product from the inventory
    public boolean deleteProduct(Product selectedProduct) {
        if (this.allProducts.contains(selectedProduct)) {
            // Remove product from our list
            this.allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }

    }

    // Method 'updates' a part by taking in a new part and replacing the old one
    public void updatePart(int index, Part newPart) {

        Part oldPart = lookupPart(newPart.getId());
        
        // Looks through all the products for this part, deletes the part from 
        // products and adds the new one instead. 
        for (Product product : this.allProducts) {
            if (product.getAllAssociatedParts().contains(oldPart)) {
                product.deleteAssociatedPart(oldPart);
                product.addAssociatedPart(newPart);

            }

        }
        
        // Delete old part and add the new one to our inventory
        deletePart(oldPart);
        addPart(newPart);

    }
    // Method 'updates' a product by taking in a new part and replacing the old one
    public void updateProduct(int index, Product newProduct) {

        Product oldProduct = lookupProduct(index);
        
        
        // Delete the old product and add the new one.
        deleteProduct(oldProduct);
        addProduct(newProduct);

    }

}
