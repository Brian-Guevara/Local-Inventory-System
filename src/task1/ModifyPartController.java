package task1;

import java.io.IOException;
import java.net.URL;
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
 * MODIFY PART SCREEN
 *
 *
 * @author Brian Guevara
 */
public class ModifyPartController implements Initializable {

    // This variable is our reference to the inventory
    private Inventory inventory;

    // This is the part we are modifying
    private Part thisPart;

    // Our title. It will add the part ID later
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

    // Initializes the title and ID field
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the title
        title.setText("Modify Part: ");

        // Make it so that the ID field cannot be edited
        idField.setDisable(true);
        idField.setEditable(false);

    }

    // This method adds the part ID to our title
    public void setTitle(int id) {
        title.setText(title.getText() + id);
    }

    // Ths method sets the inventory
    public void setInventory(Inventory passedInv) {
        inventory = passedInv;
    }

    // Method sets the part we are editing. It is designed for InHouse objects
    public void setPart(InHouse part) {
        // Assign the part we are passed from main menu
        thisPart = part;

        // Fills in the InHouse radio button
        inHouseBtn.setSelected(true);

        // Set the fields to the values of said part
        miscData.setText("Machine ID");
        idField.setText(String.valueOf(part.getId()));
        nameField.setText(part.getName());
        stockField.setText(String.valueOf(part.getStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        maxField.setText(String.valueOf(part.getMax()));
        minField.setText(String.valueOf(part.getMin()));
        miscField.setText(String.valueOf(part.getMachineId()));

        // Set the title to include this part's ID
        setTitle(part.getId());
    }

    // Method sets the part we are editing. It is designed for Outsourced 
    // objects
    public void setPart(Outsourced part) {
        // Assign the part we are passed from main menu
        thisPart = part;

        // Fills in the Outsourced radio button
        outsourcedBtn.setSelected(true);

        // Set the fields to the values of said part
        miscData.setText("Company Name");
        idField.setText(String.valueOf(part.getId()));
        nameField.setText(part.getName());
        stockField.setText(String.valueOf(part.getStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        maxField.setText(String.valueOf(part.getMax()));
        minField.setText(String.valueOf(part.getMin()));
        miscField.setText(part.getCompanyName());

        // Set the title to include this part's ID
        setTitle(part.getId());

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
        } else if (misc.isEmpty()) {
            sysMsg.setText("Please check Machine ID/Company Name.");
            return false;
        }

        return true;

    }
    
    // Method is executed when the InHouse radio button is pressed
    // Goal is to set reset the program message, set the Misc fields, and
    // create an appropriate reference to get our misc data.
    public void inHouseBtnPressed(ActionEvent e) {
        sysMsg.setText("");
        miscData.setText("Machine ID");
        miscField.setPromptText("Mach ID");
        miscField.setText("");

        // If this is an Inhouse object, get the machine ID
        if (thisPart instanceof InHouse) {
            InHouse i = (InHouse) thisPart;
            miscField.setText(String.valueOf(i.getMachineId()));
        }
    }
    // Method is executed when the InHouse radio button is pressed
    // Goal is to set reset the program message, set the Misc fields, and
    // create an appropriate reference to get our misc data.
    public void outsourcedBtnPressed(ActionEvent e) {
        sysMsg.setText("");
        miscData.setText("Company Name");
        miscField.setPromptText("Comp Name");
        miscField.setText("");

        // If this is an Outsourced object, get the company name
        if (thisPart instanceof Outsourced) {
            Outsourced o = (Outsourced) thisPart;
            miscField.setText(o.getCompanyName());
        }

    }

    
    // Method is executed when the Save button is pressed
    // The goal is to create a new part and to add it to the inventory
    public void saveBtnPressed(ActionEvent e) {
        //  If the values are contradicting then stop the program here
        try {

            if (!isCorrectValues()) {
                return;
            }
            // We create our part object to replace the current object.
            Part newPart = null;

            // If the Inhouse button is on, create an instance of an InHouse
            // object. Set the machine ID and part ID
            if (inHouseBtn.isSelected()) {
                newPart = new InHouse();
                InHouse inHouse = (InHouse) newPart;
                inHouse.setMachineId(Integer.parseInt(miscField.getText()));
                newPart.setID(Integer.valueOf(idField.getText()));

            } 
            // If the Outsourced button is on, create an instance of an Outsourced
            // object. Set the company name and part ID
            else if (outsourcedBtn.isSelected()) {
                newPart = new Outsourced();
                Outsourced outsource = (Outsourced) newPart;
                outsource.setCompanyName(miscField.getText());
                newPart.setID(Integer.valueOf(idField.getText()));

            }

            // Set the new part's data with the data from the old part
            newPart.setName(nameField.getText().trim());
            newPart.setPrice(Double.valueOf(priceField.getText().trim()));
            newPart.setMax(Integer.valueOf(maxField.getText().trim()));
            newPart.setMin(Integer.valueOf(minField.getText().trim()));
            newPart.setStock(Integer.valueOf(stockField.getText().trim()));

            // This method updates the inventory by replacing the older part and
            // adding a new one
            inventory.updatePart(thisPart.getId(), newPart);

            // Close the window
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.close();

        } catch (RuntimeException f) {
            sysMsg.setText("Please check your values.");
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
