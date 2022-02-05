
package task1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is our MAIN PROGRAM. It starts and executes the program.
 *
 *
 * MAIN PROGRAM / TASK 1 JAVA
 *
 * @author Brian Guevara
 */
public class Task1 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        // Loads the main menu's fxml document and opens a window for it
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
