package task1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is the controller for 'FXMLDocument.fxml.' It executes methods when an
 * FXML element in the the main menu is pressed/interacted with.
 *
 * MAIN MENU SCREEN
 *
 *
 * @author Brian Guevara
 */
public class FXMLDocumentController implements Initializable {

    // This variable is our reference to the inventory
    private Inventory inventory;

    // This label serves as a way to give the user a message for any error.
    @FXML
    private Label sysMsg;

    // Our title. It remains the same for the main menu.
    @FXML
    private Text title;

    // partView and its columns represent the table for the parts in the inventory.
    @FXML
    public TableView<Part> partView;
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partPricePerUnitColumn;

    // partView and its columns represent the table for the parts in the inventory.
    @FXML
    public TableView<Product> productView;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productPricePerUnitColumn;

    // These are the textfield that the user interacts with.
    @FXML
    private TextField searchPartField;
    @FXML
    private TextField searchProductField;

    // Initializes the values and columns for the menu.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize Title
        title.setText("Inventory System");
        // Set the columns and their values
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        // New inventory is made and parts are added to it.
        inventory = new Inventory();
        addParts();

        // 
        partView.setItems(inventory.getAllParts());
        productView.setItems(inventory.getAllProducts());

    }

    // This method adds parts for immediate use
    public void addParts() {
        inventory.addPart(new Outsourced(155, "Screw", 0.99, 100, 50, 250, "Dewalt"));
        inventory.addPart(new InHouse(101, "Bolt", 1.99, 100, 50, 250, 7117));
        inventory.addProduct((new Product(745, "Drill", 1.99, 20, 0, 200)));
        inventory.addProduct((new Product(521, "Straight laser", 6.69, 90, 10, 800)));
    }

    // Method is executed when the Add button is pressed under the Part table
    // The goal is to open a window where a user can add in fields to create a new part.
    public void addPartPressed(ActionEvent e) throws IOException {
        try {
            // Load our new FXML page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addPart.fxml"));

            Parent addPartFXML = loader.load();

            // Create a scene with our FXML doc.
            Scene nextScene = new Scene(addPartFXML);

            // Initialize the controller and set the inventory
            AddPartController addPartController = loader.getController();
            addPartController.setInventory(inventory);

            // Creates a new stage/window
            Stage stage = new Stage();

            // Sets the owner of the new stage to the scene + window of the title object
            stage.initOwner(title.getScene().getWindow());

            // Reset the part view
            searchPartField.setText("");
            partView.setItems(inventory.getAllParts());

            // Set the scene and show it.
            stage.setScene(nextScene);
            stage.show();

        } catch (RuntimeException f) {
            // Returns an error message if our page is not found.
            sysMsg.setText("Add Part Page not found.");
        }
    }

    // Method is executed when the Modify button is pressed under the Part table
    // The goal is to open up a window where the user can 'modify' a part.
    public void modifyPartPressed(ActionEvent e) throws IOException {
        try {
            // Load our new FXML page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyPart.fxml"));
            Parent modifyPartFXML = loader.load();

            // Create a scene with our FXML doc.
            Scene nextScene = new Scene(modifyPartFXML);

            // Initialize the controller and set the inventory.
            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.setInventory(inventory);

            // Creates a new stage/window
            Stage stage = new Stage();

            // Sets the owner of the new stage to the scene + window of the title object.
            stage.initOwner(title.getScene().getWindow());

            // Set the scene. It does not show until some conditions are checked.
            stage.setScene(nextScene);

            // If the selection model is empty, tell the user and don't change the page.
            if (partView.getSelectionModel().isEmpty()) {
                sysMsg.setText("Please select a part to modify.");
                return;
            }

            // Passes in an InHouse reference if the selected part is an InHouse object
            if (partView.getSelectionModel().getSelectedItem() instanceof InHouse) {
                modifyPartController.setPart((InHouse) partView.getSelectionModel().getSelectedItem());

            }

            // Passes in an Outsourced reference if the selected part is an Outsourced object
            if (partView.getSelectionModel().getSelectedItem() instanceof Outsourced) {
                modifyPartController.setPart((Outsourced) partView.getSelectionModel().getSelectedItem());
            }

            // Clears the field in the main menu and resets the window
            searchPartField.setText("");
            partView.setItems(inventory.getAllParts());

            stage.show();

        } catch (RuntimeException f) {
            // Returns an error message if our page is not found.
            sysMsg.setText("Modify Part Page not found.");
        }
    }

    // Method is executed when the Delete button is pressed under the Part table
    // The goal is to delete the part in realtime.
    public void deletePartPressed() {

        // If the selection model is empty, tell the user.
        if (partView.getSelectionModel().isEmpty()) {
            sysMsg.setText("Please select a part to delete.");
            return;
        }

        // Create a reference to the part that is selected
        Part selected;
        selected = partView.getSelectionModel().getSelectedItem();

        // Delete the object
        inventory.deletePart(selected);

        // Clears the field in the main menu and resets the table
        searchPartField.setText("");
        partView.setItems(inventory.getAllParts());

    }

    // Method is executed when the Add button is pressed under the Product table.
    // The goal is to open a window where a user can create a new Product.
    public void addProductPressed(ActionEvent e) throws IOException {
        try {
            // Load our new FXML page.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addProduct.fxml"));
            Parent addProductFXML = loader.load();

            // Create a scene with our FXML doc.
            Scene nextScene = new Scene(addProductFXML);

            // Initialize the controller and set the inventory.
            AddProductController addProductController = loader.getController();
            addProductController.setInventory(inventory);

            // Creates a new stage/window
            Stage stage = new Stage();

            // Sets the owner of the new stage to the scene + window of the title object.
            stage.initOwner(title.getScene().getWindow());

            searchProductField.setText("");
            productView.setItems(inventory.getAllProducts());

            // Set the scene and show the window.
            stage.setScene(nextScene);

            stage.show();

        } catch (RuntimeException f) {
            // Returns an error message if our page is not found.
            sysMsg.setText("Add Product Page not found.");
        }
    }

    // Method is executed when the Modify button is pressed under the Product table
    // The goal is to open up a window where the user can modify a product.
    public void modifyProductPressed(ActionEvent e) throws IOException {

        try {
            // Load our new FXML page.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyProduct.fxml"));
            Parent modifyProductFXML = loader.load();

            // Create a scene with our FXML doc.
            Scene nextScene = new Scene(modifyProductFXML);

            // Initialize the controller and set the inventory.
            ModifyProductController modProductController = loader.getController();
            modProductController.setInventory(inventory);

            // Creates a new stage/window.
            Stage stage = new Stage();

            // Sets the owner of the new stage to the scene + window of the title object.
            stage.initOwner(title.getScene().getWindow());

            // Set the scene. It does not show until some conditions are checked.
            stage.setScene(nextScene);

            // If our selection model is empty, let the user know and stop there.
            if (productView.getSelectionModel().isEmpty()) {
                sysMsg.setText("Please select a product to modify.");
                return;
            }
            // Sets the product being modified.
            modProductController.setProduct(productView.getSelectionModel().getSelectedItem());

            // Clears the field in the main menu and resets the window
            searchProductField.setText("");
            productView.setItems(inventory.getAllProducts());
            // Shows the window.
            stage.show();

        } catch (RuntimeException f) {
            // Returns an error message if our page is not found.
            sysMsg.setText("Modify Product Page not found.");
        }
    }

    public void deleteProductPressed() {

        // If the selection model is empty, tell the user.
        if (productView.getSelectionModel().isEmpty()) {
            sysMsg.setText("Please select a product to delete.");
            return;
        }

        // Create a reference to the product that is selected
        Product productToDelete = productView.getSelectionModel().getSelectedItem();

        inventory.deleteProduct(productToDelete);

        // Clears the field in the main menu and resets the window
        searchProductField.setText("");
        productView.setItems(inventory.getAllProducts());

    }

    // Method is executed when the Exit button is pressed
    // The goal is to shut down the program entirely.
    public void exitButton(ActionEvent e) {
        System.exit(0);
    }

    // Method is executed when the Search button is pressed above the Part table.
    // The goal is to return a list of parts.
    public void searchPartBtnPressed(ActionEvent e) {
        // Get input from the search field
        String searched = searchPartField.getText().trim().toLowerCase();

        // If the search bar is empty, return all the parts.
        if (searched.isEmpty()) {
            // Return all parts back
            partView.setItems(inventory.getAllParts());
        }

        // We first try to parse an int so we can look it up by ID.
        try {
            // Get 
            ObservableList<Part> foundParts = FXCollections.observableArrayList();
            int idSearch = Integer.parseInt(searched);

            if (!foundParts.contains(inventory.lookupPart(idSearch))) {
                foundParts.add(inventory.lookupPart(idSearch));
            }
            // Set the part view to show the found matches.
            partView.setItems(foundParts);

        } // Otherwise we simply search for all parts that contain the string.
        catch (RuntimeException f) {
            partView.setItems(inventory.lookupPart(searched));

        }

    }

    // Method is executed when the Search button is pressed above the Product table.
    // The goal is to return a list of products.
    public void searchProductBtnPressed(ActionEvent e) {

        // Get input from the search field
        String searched = searchProductField.getText().trim().toLowerCase();

        // If the search bar is empty, return all the products.
        if (searched.isEmpty()) {
            productView.setItems(inventory.getAllProducts());
            return;
        }

        // We first try to parse an int so we can look it up by ID.
        try {
            ObservableList<Product> foundProducts = FXCollections.observableArrayList();

            int idSearch = Integer.parseInt(searched);

            if (!foundProducts.contains(inventory.lookupProduct(idSearch))) {
                foundProducts.add(inventory.lookupProduct(idSearch));
            }
            // Set the product view to show the found matches.
            productView.setItems(foundProducts);

        } // Otherwise we simply search for all products that contain the string.
        catch (RuntimeException f) {
            productView.setItems(inventory.lookupProduct(searched));

        }

    }
}
