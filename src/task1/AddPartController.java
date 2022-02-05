package task1;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is the controller for 'addPart.fxml.' It executes methods when an FXML
 * element in the the Add Part page is pressed/interacted with.
 *
 * ADD PART SCREEN
 *
 *
 * @author Brian Guevara
 */
public class AddPartController implements Initializable {

    // This variable is our reference to the inventory
    private Inventory inventory;

    // Our title. It remains the same for this screen.
    @FXML
    private Text title;

    // This label serves as a way to give the user a message for any error.
    @FXML
    private Label sysMsg;

    // This is the label for the Machine ID/Company name field. It changes
    // depending on which type of Part we want to add.
    @FXML
    private Label miscData;

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
    @FXML
    private TextField miscField;

    // These radio buttons allow the user to choose between InHouse or 
    // Outsourced Parts
    @FXML
    private RadioButton inHouseBtn;
    @FXML
    private RadioButton outsourcedBtn;

    // Initializes the title, program message, and ID field
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the title
        title.setText("Add Part");

        // Set the program message to tell the user to select InHouse/Outsourced
        sysMsg.setText("Select InHouse or Outsourced.");

        // Generate an ID for our part
        idField.setText(String.valueOf(generateID()));

        // Make it so that the ID field cannot be edited
        idField.setEditable(false);
        idField.setDisable(true);
    }

    // Ths method sets the inventory
    public void setInventory(Inventory passedInv) {
        inventory = passedInv;
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
        // get the stock, max, min, price, and misc fields
        int stock = Integer.parseInt(stockField.getText().trim());
        int max = Integer.parseInt(maxField.getText().trim());
        int min = Integer.parseInt(minField.getText().trim());
        String misc = miscField.getText().trim();
        Double price = Double.parseDouble(priceField.getText().trim());

        // Get the ID
        int id = Integer.parseInt(idField.getText().trim());

        // This quickly checks if there are any parts that have our ID.
        // If there is then another number is generated and the user must save again.
        for (Part somePart : inventory.getAllParts()) {
            if (id == somePart.getId()) {
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
        else if (misc.isEmpty()){
            sysMsg.setText("Please check Machine ID/Company Name.");
            return false;
        }

        return true;

    }

    // Method is executed when the InHouse radio button is pressed
    // Goal is to set reset the program message and set the Misc fields
    public void inHouseBtnPressed(ActionEvent e) {
        sysMsg.setText("");
        miscData.setText("Machine ID");
        miscField.setPromptText("Mach ID");
    }

    // Method is executed when the Outsourced radio button is pressed
    // Goal is to set reset the program message and set the Misc fields
    public void outsourcedBtnPressed(ActionEvent e) {
        sysMsg.setText("");
        miscData.setText("Company Name");
        miscField.setPromptText("Comp Name");
    }

    // Method is executed when the Save button is pressed
    // The goal is to create a new part and to add it to the inventory
    public void saveBtnPressed(ActionEvent e) {
        // Try just in case there are any parsing/value errors
        try {

            // Checks if the user selected an Inhouse or Outsourced part
            if (!inHouseBtn.isSelected() && !outsourcedBtn.isSelected()) {
                sysMsg.setText("Error: Pick In-House or Outsourced");
                return;
            }
            // Checks if values are correct
            if (!isCorrectValues()) {
                return;
            }

            // Create a reference of our new product
            Part newPart;

            // If the Inhouse button is on, create an instance of an InHouse
            // object. 
            if (inHouseBtn.isSelected()) {
                newPart = new InHouse(
                        Integer.parseInt(idField.getText().trim()),
                        nameField.getText().trim(),
                        Double.parseDouble(priceField.getText().trim()),
                        Integer.parseInt(stockField.getText().trim()),
                        Integer.parseInt(minField.getText().trim()),
                        Integer.parseInt(maxField.getText().trim()),
                        Integer.parseInt(miscField.getText().trim())
                );

                // Add the part to our inventory
                inventory.addPart(newPart);
            }

            // If the Inhouse button is on, create an instance of an Outsourced
            // object. 
            if (outsourcedBtn.isSelected()) {
                newPart = new Outsourced(
                        Integer.parseInt(idField.getText().trim()),
                        nameField.getText().trim(),
                        Double.parseDouble(priceField.getText().trim()),
                        Integer.parseInt(stockField.getText().trim()),
                        Integer.parseInt(minField.getText().trim()),
                        Integer.parseInt(maxField.getText().trim()),
                        miscField.getText().trim()
                );

                // Add the part to our inventory
                inventory.addPart(newPart);
            }
            
            // Close the window
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.close();

        } catch (RuntimeException f) {
            sysMsg.setText("Please Check your values");
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
