/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejemplos;

import fxml.ScreensController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author N550J
 */
public class Ejemplos extends Application {
    
    
    public static String screen1ID = "FXMLDocument";
    public static String screen1File = "FXMLDocument.fxml";
    public static String screen2ID = "guiRed";
    public static String screen2File = "guiRed.fxml";
    public static String screen3ID = "principal";
    public static String screen3File = "principal.fxml";
    @Override
    public void start(Stage stage) throws Exception {
        ejecutarGui();
      //Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"));
        
        
         ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Ejemplos.screen1ID, Ejemplos.screen1File);
        mainContainer.loadScreen(Ejemplos.screen2ID, Ejemplos.screen2File);
        mainContainer.loadScreen(Ejemplos.screen3ID, Ejemplos.screen3File);
        
        mainContainer.setScreen(Ejemplos.screen3ID);
       
//        
//   //   Parent root = FXMLLoader.load(getClass().getResource("/fxml/guiRed.fxml"));
//        Group root = new Group();
// //       stage.initStyle(StageStyle.DECORATED);
//         System.out.println("---------------------->");
////        Scene scene;
////        scene = new Scene(root); 
////        scene.getStylesheets().add(Ejemplos.class.getResource("/estilos/guired.css").toExternalForm());
////        stage.setScene(scene);
//        stage.setResizable(900);
//        stage.setMinHeight(800);
//        stage.setMaxHeight(900);
//        stage.setMaxWidth(800);
////        root.getChildren().addAll(mainContainer);
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        stage.sizeToScene();
        Scene scene = new Scene(root);
        stage.setScene(scene);
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
