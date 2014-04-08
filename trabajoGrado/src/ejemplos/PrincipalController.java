package ejemplos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fxml.ControlledScreen;
import fxml.ScreensController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sun.plugin.javascript.navig.AnchorArray;

/**
 * FXML Controller class
 *
 * @author arcilapalacios
 */
public class PrincipalController implements Initializable, ControlledScreen  {
  
    ScreensController myController;    
    Image imageMineria = new Image(getClass().getResourceAsStream("/imagenes/mineria.png"));  
    Image imageRed = new Image(getClass().getResourceAsStream("/imagenes/red.jpg"));  
    @FXML Button botonMineria = new Button();
    @FXML Button botonRed = new Button();
     @FXML private AnchorPane anchor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           botonMineria.setGraphic(new ImageView(imageMineria));
           botonRed.setGraphic(new ImageView(imageRed));
           assert anchor != null : "fx:id=\"toolbar\" was not injected: check your FXML file 'principal.fxml'.";
           anchor.getStylesheets().add("/estilos/principal.css");
    }    

    @Override
    public void setScreenParent(ScreensController screenPage) {
      myController=screenPage;

     }
     @FXML
    private void goToScreen3(ActionEvent event){
       myController.setScreen(Ejemplos.screen1ID);
    }
     @FXML
    private void irared(ActionEvent event){
       myController.setScreen(Ejemplos.screen2ID);
    }
    
}
