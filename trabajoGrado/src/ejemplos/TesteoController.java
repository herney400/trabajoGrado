package ejemplos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import accesoDatos.Conexion;
import fxml.ControlledScreen;
import fxml.ScreensController;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.neuroph.util.io.JDBCInputAdapter;

/**
 * FXML Controller class
 *
 * @author N550J
 */
public class TesteoController implements Initializable, ControlledScreen {
   static String url = "jdbc:postgresql://localhost/postgres";
    static String user = "postgres";
    static String password = "tesis";
    
    double precio, consumo, pago;
    @FXML private AnchorPane anchorTesteo;
    Conexion conexion=new Conexion();
    Connection connexion ;
    double errorTotal;
    double errorMax;
    double erroMin;
    ;
    @FXML Button botonCargarRed;
    ScreensController myController; 

    public TesteoController() {
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        assert  anchorTesteo != null : "fx:id=\"toolbar\" was not injected: check your FXML file 'tecteo.fxml'.";
    }
    
    public void cargarRNA(){
    
        System.out.println("dklasñfjsdkfjañsdlfja");
    }
    
    public void cargarDatoPruebaTXT(){
    
    
    }
    
    public void cargarDatosBD() throws ClassNotFoundException{
       try {
           String consulta="COPY (SELECT medida, hora, franja_horaria, ano,mes, dia , total_consumo \n" +
            "FROM historico_consumo, medida, tiempo,fecha, cliente ) TO  'D:\\\\excell\\\\file.csv'  delimiter ';' ;";
           Connection con=null;
           PreparedStatement pst = null;
           Class.forName("org.postgresql.Driver");
           con = DriverManager.getConnection(url, user, password);
           pst = con.prepareStatement(consulta);
           pst.execute();
            ArrayList<String> resultado = new ArrayList<>(0);
           System.out.println("ssssss");
        
            
           System.out.println("lfsdlf");
           
           
//      JDBCInputAdapter JDBCinput= new JDBCInputAdapter( (Connection) conexion, sql);      
       } catch (SQLException ex) {
           Logger.getLogger(TesteoController.class.getName()).log(Level.SEVERE, null, ex);
       }
      
    }
    
    public void cargarDatosConsumo(){
    
        
    }
    
    public void cargarDatosPago(){
    
        
   }
    
    public void cargarDatosPrecio(){
    
        
   }
     public double getErroMin() {
        return erroMin;
    }

    public double getErrorMax() {
        return errorMax;
    }

    public double getConsumo() {
        return consumo;
    }

    public double getPago() {
        return pago;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
       myController = screenPage; //To change body of generated methods, choose Tools | Templates.
    }
}
