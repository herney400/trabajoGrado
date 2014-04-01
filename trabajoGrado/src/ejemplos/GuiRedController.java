package ejemplos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.media.jfxmedia.logging.Logger;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;

/**
 * FXML Controller class
 *
 * @author N550J
 */
public class GuiRedController implements Initializable {
   @FXML private AnchorPane anchorP;
   @FXML private Button boton_cargar_red;
   @FXML private DatePicker fechaInicial;
   @FXML private DatePicker fechaFinal;
   @FXML private GridPane gridPane;
   
   private Desktop desktop=Desktop.getDesktop();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       assert  boton_cargar_red != null : "fx:id=\"toolbar\" was not injected: check your FXML file 'guiRed.fxml'.";
       iniciarCalendarios();
    }
    
    @FXML public void cargarRed(ActionEvent E){
    assert  anchorP != null : "fx:id=\"toolbar\" was not injected: check your FXML file 'guiRed.fxml'.";
        Stage stage =new Stage();    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");       
        File file =fileChooser.showOpenDialog(stage);
         
         if(file!=null){
            
             abrirArchivo(file);
         }
        
        
        red();
    }
    
    @FXML public void abrirArchivo(File file){
       String dire=file.getPath();
           System.out.println("----"+dire);
       
    }
    
     public void iniciarCalendarios(){
        fechaInicial = new DatePicker(Locale.ENGLISH);
        fechaFinal = new DatePicker(Locale.ENGLISH);
        
        fechaInicial.setDateFormat(new SimpleDateFormat("yyyy/MM/dd"));
        //teqvime bugunku tarix ucun footere text verilmesi
        fechaInicial.getCalendarView().todayButtonTextProperty().set("Bugun");
        //teqvimde necenci heftenin oldugunun gosterilmesi-bu halda gosterilmir
        fechaInicial.getCalendarView().setShowWeeks(false);
        //teqvime stilin verilmesi
        fechaInicial.getStylesheets().add("estilos/DatePicker.css");
 
        fechaFinal.setDateFormat(new SimpleDateFormat("yyyy/MM/dd"));
        //teqvime bugunku tarix ucun footere text verilmesi
        fechaFinal.getCalendarView().todayButtonTextProperty().set("Bugun");
        //teqvimde necenci heftenin oldugunun gosterilmesi-bu halda gosterilmir
        fechaFinal.getCalendarView().setShowWeeks(false);
        //teqvime stilin verilmesi
        fechaFinal.getStylesheets().add("estilos/DatePicker.css");
        
        gridPane.add(fechaInicial, 1, 1);
        gridPane.add(fechaFinal, 1, 2);
    }
    
    @FXML public void red(){
    
         NeuralNetwork neuralNet = new MultiLayerPerceptron(4, 9, 1);

    }
    
}
