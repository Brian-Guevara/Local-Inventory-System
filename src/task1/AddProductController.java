package task1;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is the controller for 'addProduct.fxml.' It executes methods when an
 * FXML element in the the Add Product page is pressed/interacted with.
 *
 * ADD PRODUCT SCREEN
 *
 *
 * @author Brian Guevara
 */
public class AddProductController implements Initializable {

    // This variable is our reference to the inventory
    private Inventory inventory;

    // Our title. It remains the same for this screen.
    @FXML
    private Text title;

    // This label serves as a way to give the user a message for any error.
    @FXML
    private Label sysMsg;

    // These are lists for parts we want to add/don't want to add
    private ObservableList<Part> partsToAdd = FXCollections.observableArrayList();
    private ObservableList<Part> otherParts = FXCollections.observableArrayList();

    // All the fields a user can use for inputs
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;

    // The text field input for users to search for parts.
    @FXML
    private TextField searchField;

    // searchView and its columns represent the table for parts found in a search.
    @FXML
    private TableView<Part> searchView;
    @FXML
    private TableColumn<Part, Integer> searchIDColumn;
    @FXML
    private TableColumn<Part, String> searchNameColumn;
    @FXML
    private TableColumn<Part, Integer> searchInventoryColumn;
    @FXML
    private TableColumn<Part, Double> searchPricePerUnitColumn;

    // inProduct and its columns represent the table for parts in a product.
    @FXML
    private TableView<Part> inProductView;
    @FXML
    private TableColumn<Part, Integer> inProductIDColumn;
    @FXML
    private TableColumn<Part, String> inProductNameColumn;
    @FXML
    private TableColumn<Part, Integer> inProductInventoryColumn;
    @FXML
    private TableColumn<Part, Double> inProductPricePerUnitColumn;

    // Initializes the values and columns for the Add Product page.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the title. 
        title.setText("Add Product");

        // Generate an ID for our new product. Make the fields unchangeable.
        idField.setText(String.valueOf(generateID()));
        idField.setEditable(false);
        idField.setDisable(true);

        // Setup Columns
        searchIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        searchNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        searchInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        searchPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        inProductIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        inProductNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        inProductPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        // Set the products in our new product to be displayed in the tableview: inProductView
        inProductView.setItems(partsToAdd);
    }

    // Ths method sets the inventory and sets the parts in the searchView
    public void setInventory(Inventory passedInv) {
        inventory = passedInv;

        for (Part invPart : inventory.getAllParts()) {
            otherParts.add(invPart);
        }

        searchView.setItems(otherParts);

    }

    // This method generates a new product ID for us
    public int generateID() {
        Random rand = new Random();
        int id = rand.nextInt(1000000);
        id = id % 2000;
        return id;
    }

    // Method checks to make sure the input values are correct. It is
    // false is the inputs are incorrect.
    public boolean isCorrectValues() {
        // get the stock, max, min, and price fields
        int stock = Integer.parseInt(stockField.getText().trim());
        int max = Integer.parseInt(maxField.getText().trim());
        int min = Integer.parseInt(minField.getText().trim());
        Double price = Double.parseDouble(priceField.getText().trim());

        // Get the ID
        int id = Integer.parseInt(idField.getText().trim());

        // This quickly checks if there are any products that have our ID.
        // If there is then another number is generated and the user must save again.
        for (Product someProduct : inventory.getAllProducts()) {
            if (id == someProduct.getId()) {
                idField.setText(String.valueOf(generateID()));
                sysMsg.setText("There was another part with same ID. Try again.");
                return false;
            }

        }

        // Stock less than 0
        if (stock < 0) {
            sysMsg.setText("Please check stock.");
            return false;
        } // Min is less than 0
        else if (min < 0) {
            sysMsg.setText("Please check minimum value.");
            return false;
        } // Max is less than 1
        else if (max < 1) {
            sysMsg.setText("Please check maximum value.");
            return false;
        } // Price is less than 0
        else if (price < 0) {
            sysMsg.setText("Please check price value.");
            return false;
        } // Minimum is greater than maximum
        else if (min > max) {
            sysMsg.setText("Please check minimum and maximum values.");
            return false;
        }

        return true;

    }

    // Method is executed when the search bar is pressed
    // The goal is to return a list of parts the user is looking for
    public void searchBtnPressed(ActionEvent e) {

        // Get string from search field and create a new observable list
        String searched = searchField.getText().trim().toLowerCase();
        ObservableList<Part> foundMatches = FXCollections.observableArrayList();

        // First we try to search by ID number and return that one object.
        try {
            int idSearch = Integer.parseInt(searched);
            foundMatches.add(inventory.lookupPart(idSearch));
            searchView.setItems(foundMatches);

            // If unsucessful, we simply return a list of parts that contain
            // the input string        
        } catch (RuntimeException f) {

            foundMatches = inventory.lookupPart(searched);
            searchView.setItems(foundMatches);

        }

    }

    // Method is executed when the add button is clicked
    // the goal is to move the part from one table to another
    public void addBtnPressed(ActionEvent e) {

        if (searchView.getSelectionModel().isEmpty()) {
            sysMsg.setText("Select a part to add.");
            return;
        }

        // Get the part
        Part partToAdd = searchView.getSelectionModel().getSelectedItem();

        // Moves it between the observable lists
        partsToAdd.add(partToAdd);
        otherParts.remove(partToAdd);

        // Clears the field in the main menu and resets the window
        searchField.setText("");
        searchView.setItems(otherParts);

    }

    // Method is executed when the delete  button is clicked
    // the goal is to move the part from the parts that will be in the product
    // and the other parts in the inventory
    public void deleteBtnPressed(ActionEvent e) {

        if (inProductView.getSelectionModel().isEmpty()) {
            sysMsg.setText("Select a part to delete.");
            return;
        }

        // Get Part
        Part partToDelete = inProductView.getSelectionModel().getSelectedItem();

        // Swap the part between the two lists.
        partsToAdd.remove(partToDelete);
        otherParts.add(partToDelete);

        // Clears the field in the main menu and resets the window
        searchField.setText("");
        searchView.setItems(otherParts);
    }

    // Method is executed when the Save button is pressed
    // The goal is to create a new product.
    public void saveBtnPressed(ActionEvent e) {
        // Try just in case there are any parsing/value errors
        try {
            // Checks if values are correct
            if (!isCorrectValues()) {
                return;
            }

            // Create an instance of our new product
            Product newProduct = new Product();

            // Set the product with all the data from the fields
            newProduct.setId(Integer.parseInt(idField.getText().trim()));
            newProduct.setName(nameField.getText().trim());
            newProduct.setPrice(Double.parseDouble(priceField.getText().trim()));
            newProduct.setStock(Integer.parseInt(stockField.getText().trim()));
            newProduct.setMax(Integer.parseInt(maxField.getText().trim()));
            newProduct.setMin(Integer.parseInt(minField.getText().trim()));

            // Add all parts in the bottom table to the product
            for (Part toAdd : partsToAdd) {
                newProduct.addAssociatedPart(toAdd);
            }

            // Deletes any parts that were taken out
            for (Part toDelete : otherParts) {
                if (newProduct.getAllAssociatedParts().contains(toDelete)) {
                    newProduct.deleteAssociatedPart(toDelete);
                }

            }
            // Add the part the inventory
            inventory.addProduct(newProduct);

            // Close the window
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.close();

        } catch (RuntimeException f) {
            sysMsg.setText("Please Check your values.");
            return;

        }
    }

    // Method is executed when the cancel button is pressed.
    // It simply closes the window without making any changes.
    public void cancelBtnPressed(ActionEvent e) throws IOException {
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.close();

    }

}
