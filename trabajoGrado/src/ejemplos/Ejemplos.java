/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejemplos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author N550J
 */
public class Ejemplos extends Application {
    
    
   
    @Override
    public void start(Stage stage) throws Exception {
        ejecutarGui();
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"));
//       Parent root = FXMLLoader.load(getClass().getResource("/fxml/estilos.fxml"));
        stage.initStyle(StageStyle.DECORATED);
        Scene scene;
        scene = new Scene(root); 
        scene.getStylesheets().add(Ejemplos.class.getResource("/estilos/estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(800);
        stage.setMaxHeight(900);
        stage.setMaxWidth(800);
        stage.show();
        
    }
    public void ejecutarGui(){
        
    
    
    }
   

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       
        
    }
    
}
