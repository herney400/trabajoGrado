package ejemplos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import eu.schudt.javafx.controls.calendar.DatePicker;
import fxml.ControlledScreen;
import fxml.ScreensController;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 import javafx.util.Callback; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.util.StringConverter;
import lectura.CSVReader;
import lectura.Validar;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.DynamicBackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.nnet.learning.ResilientPropagation;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.TransferFunctionType;


/**
 * FXML Controller class
 *
 * @author N550J
 */
public class GuiRedController implements Initializable, ControlledScreen{
     Validar vali=new Validar();
   @FXML private AnchorPane anchorP;
   @FXML private Button boton_cargar_red;
   @FXML private DatePicker fechaInicial;
   @FXML private DatePicker fechaFinal;
   @FXML private GridPane gridPane;
   @FXML Button botonMineria;
   @FXML Button botonInteligencia,botonentrenarRed,botonmine,botonred,boton_cargar_conjunto ;
   @FXML TableView  tablaEntrenamiento;
   @FXML TextField tex_error, tex_max_iteraciones,tex_tasa_aprendizaje,text_num_capas,tex_num_neu_entrada,tex_num_neu_oculta;
   @FXML       int t_max_iteraciones,t_num_capas,t_num_neu_entrada,t_num_neu_ocul;
   @FXML    double t_error,t_tasa_aprendizaje;
    
   @FXML private ComboBox combo_dia_semana,combo_altitud,combo_tipo_comsumidor,
                          combo_fen_climatico,combo_fran_horaria,combo_estrato,
                          combo_mes,funcion_transferencia,regla_aprendizaje;
    File file;
   @FXML CheckBox biasCheck;
   
   String Column1MapKey = "A";
   String Column2MapKey = "B";
   String Column3MapKey = "C";
    ScreensController myController;   
   private Desktop desktop=Desktop.getDesktop();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       assert  boton_cargar_red != null : "fx:id=\"toolbar\" was not injected: check your FXML file 'guiRed.fxml'.";
       assert botonentrenarRed!= null : "fx:id=\"botonentrenarRed\" was not injected: check your FXML file 'guiRed.fxml'.";
       assert tablaEntrenamiento!= null : "fx:id=\"tablaEntrenamiento\" was not injected: check your FXML file 'guiRed.fxml'.";
       iniciarCalendarios();
       iniciarControles();
//       Image imageload = new Image(getClass().getResourceAsStream("/imagenes/load.png"));
       Image imageMine = new Image(getClass().getResourceAsStream("/imagenes/mineri.png"));
       Image imageRed = new Image(getClass().getResourceAsStream("/imagenes/red3.png")); 
      // botonentrenarRed.setGraphic(new ImageView(imageDecline));
       botonmine.setGraphic(new ImageView(imageMine));
       botonred.setGraphic(new ImageView(imageRed));
//       boton_cargar_conjunto.setGraphic(new ImageView(  imageload));
    
    
    }
    
    @FXML public void iniciarControles(){
          assert funcion_transferencia != null : "fx:id=\"funcion_transferencia\" was not injected: check your FXML file 'guiRed.fxml'.";
          ObservableList<String> optionscombo_funcion_transferencia = FXCollections.observableArrayList("Tangencial", "Sigmoidal","Lineal");
          funcion_transferencia.setItems(optionscombo_funcion_transferencia);
          funcion_transferencia.getSelectionModel().selectLast();
                  
          assert regla_aprendizaje != null : "fx:id=\"funcion_transferencia\" was not injected: check your FXML file 'guiRed.fxml'.";
          ObservableList<String> optionscombo_regla_aprendizaje= FXCollections.observableArrayList("BackPropagatión", "BackPropagatión con Momentum", "Resilient BackPropagatión", "Dynamic BackPropagatión");
          regla_aprendizaje.setItems(optionscombo_regla_aprendizaje);
          regla_aprendizaje.getSelectionModel().selectLast(); 
          
    }
    @FXML public void  enntrenarRed(ActionEvent event){
     
       
        
    }
    
    @FXML public void cargarRed(ActionEvent E){
    assert  anchorP != null : "fx:id=\"toolbar\" was not injected: check your FXML file 'guiRed.fxml'.";
        Stage stage =new Stage();    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");       
        File file =fileChooser.showOpenDialog(stage);

        if(file!=null){
           NeuralNetwork neuralNet;
           neuralNet = NeuralNetwork.load(file.getPath());
         }else{
           // Dialogs.showInformationDialog(stage, "No seleccionaste ningun archivo",  "Información", "Dialogo");
            new AlertDialog(stage, "No eligio archivo!", AlertDialog.ICON_ERROR).showAndWait();
        }
        
        
//        red();
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
    
   
    @FXML
    private void irMineria(ActionEvent event){
       myController.setScreen(Ejemplos.screen1ID);
    }
     @FXML
    private void irInteligencia(ActionEvent event){
       myController.setScreen(Ejemplos.screen2ID);
    }
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
         myController = screenPage;
    }
    @FXML
    public void cargarDatosEntrenamiento(ActionEvent event){
        
       
        TableColumn<Map, String> firstDataColumn = new TableColumn<>("Class A");
        TableColumn<Map, String> secondDataColumn = new TableColumn<>("Class B"); 
        TableColumn<Map, String> terceraDataColumn = new TableColumn<>("Class C");
        firstDataColumn.setCellValueFactory(new MapValueFactory(Column1MapKey));
        firstDataColumn.setMinWidth(130);
        secondDataColumn.setCellValueFactory(new MapValueFactory(Column2MapKey));
        secondDataColumn.setMinWidth(130);
         terceraDataColumn.setCellValueFactory(new MapValueFactory(Column3MapKey));
        terceraDataColumn.setMinWidth(130);
        tablaEntrenamiento.setItems(generateDataInMap());       
        tablaEntrenamiento.setEditable(true);
        tablaEntrenamiento.getSelectionModel().setCellSelectionEnabled(true);
        tablaEntrenamiento.getColumns().setAll(firstDataColumn, secondDataColumn,terceraDataColumn);
      
        Callback<TableColumn<Map, String>, TableCell<Map, String>>
            cellFactoryForMap = new Callback<TableColumn<Map, String>,
                TableCell<Map, String>>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        return new TextFieldTableCell(new StringConverter() {
                            @Override
                            public String toString(Object t) {
                                return t.toString();
                            }
                            @Override
                            public Object fromString(String string) {
                                return string;
                            }                                    
                        });
                    }
        };
        firstDataColumn.setCellFactory(cellFactoryForMap);
        secondDataColumn.setCellFactory(cellFactoryForMap);
        System.out.println("---------------------*");
    }
    
    public void abrirArchivoEntrenamiento(ActionEvent event){    
     Stage stage =new Stage();    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");       
          file =fileChooser.showOpenDialog(stage);
          
        //opulateTable( file.getPath(), true);
        
    }
    
    
    public void tomarDatosEntrenamiento(ActionEvent event){
       
         if(vali.validaDouble(tex_error)){
            t_error=Double.parseDouble(tex_error.getText().toString());
         }if(vali.validaDouble(tex_max_iteraciones)){
            t_max_iteraciones=Integer.parseInt(tex_max_iteraciones.getText().toString());
         } if(vali.validaDouble(tex_tasa_aprendizaje)){
            t_tasa_aprendizaje=Double.parseDouble(tex_tasa_aprendizaje.getText().toString());
         }if(vali.validarEntero(text_num_capas)){
            t_num_capas=Integer.parseInt(text_num_capas.getText().toString());
         }if(vali.validarEntero(tex_num_neu_entrada)){
            t_num_neu_entrada=Integer.parseInt(tex_num_neu_entrada.getText().toString()); 
         }if(vali.validarEntero(tex_num_neu_oculta)){
            t_num_neu_ocul=Integer.parseInt(tex_num_neu_oculta.getText().toString());  
         }
          
    }
     @FXML public void crearRed(double error,int maxIteraciones, double tazaAprendi, int numCapas, int neur_entrada,int neu_oculta,int neu_salida){
        
         String funcion_trans=   funcion_transferencia.getValue().toString();
         String regla_aprendiz = regla_aprendizaje.getValue().toString();
         NeuralNetwork neuralNet = new MultiLayerPerceptron(5,6,7);
         String archivoEnte =file.getPath();
         
         DataSet conjEntre=null;
         try {
             conjEntre=TrainingSetImport.importFromFile(archivoEnte, neur_entrada, neu_salida, ",");
         } catch (IOException ex) {
            System.out.println("Archivo no encontrado");
         } catch (NumberFormatException ex) {
            System.out.println("Error leyendo archivo o el formato esta dañado!");
         }
         
         
         if(biasCheck.isSelected()){
             BiasNeuron bias=new BiasNeuron();
             if(funcion_trans.equals("Tangencial")){
                 MultiLayerPerceptron redNeu=new MultiLayerPerceptron(TransferFunctionType.TANH, neur_entrada,neu_oculta,neu_salida);
                    if(regla_aprendiz.equals("BackPropagatión")){
                        MomentumBackpropagation reglaAprendisaje=(MomentumBackpropagation)redNeu.getLearningRule();
                    }if(regla_aprendiz.equals("BackPropagatión con Momentum")){

                    }if(regla_aprendiz.equals("Resilient BackPropagatión")){
                        ResilientPropagation reglaApredizaje=(ResilientPropagation)redNeu.getLearningRule();
                    }if(regla_aprendiz.equals("Dynamic BackPropagatión")){
                       DynamicBackPropagation reglaAprendizaje=(DynamicBackPropagation)redNeu.getLearningRule();
                    }
                  
             }else if(funcion_trans.equals("Sigmoidal")){
                MultiLayerPerceptron redNeu=new MultiLayerPerceptron(TransferFunctionType.SIGMOID, neur_entrada,neu_oculta,neu_salida);
             }else if(funcion_trans.equals("Lineal")){
                MultiLayerPerceptron redNeu=new MultiLayerPerceptron(TransferFunctionType.LINEAR, neur_entrada,neu_oculta,neu_salida);
             
             } 
             
         }else{
         
             
         }
        
    }

     private ObservableList<Map> generateDataInMap() {
        int max = 20;
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 1; i < max; i++) {
            Map<String, String> dataRow = new HashMap<>(); 
            String value1 = "A" + i;
            String value2 = "B" + i;
            String value3 = "C" + i;
            dataRow.put(Column1MapKey, value1);
            dataRow.put(Column2MapKey, value2); 
            dataRow.put(Column3MapKey, value3);
            allData.add(dataRow);
        }
          
        
        return allData;
    }
    
     
     
     
  
  public void llenarTablaEntrena(ActionEvent event) throws FileNotFoundException, IOException{
       Stage stage =new Stage();    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");       
        File file =fileChooser.showOpenDialog(stage);
     
       String[] fila=null;
       String csvArchivo=file.getPath();
       CSVReader csvLector=new CSVReader(new FileReader(csvArchivo));       
       List contenido =csvLector.readAll();
       ObservableList<String> list = FXCollections.<String>observableList(contenido);
      
       
       csvLector.close();
       TableColumn<String,String> firstDataColumn= new TableColumn<String,String>("uno");
       
//       String[] siguLinea;
//       String[] sinComas;
//       while((siguLinea=csvLector.readNext())!=null){            
//           System.out.println("clima:["+siguLinea[0]);
//       }
       
       
       tablaEntrenamiento.setItems(list);
       
       tablaEntrenamiento.getColumns().addAll(firstDataColumn);      
       
       
       
  }
  
  
}
