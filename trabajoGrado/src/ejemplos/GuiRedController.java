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
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 import javafx.util.Callback; 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.util.StringConverter;
import lectura.CSVReader;
import lectura.Validar;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
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
   //Base de datos
   static String url = "jdbc:postgresql://190.85.249.22/trabajodegrado";
    static String user = "postgres";
    static String password = "tesis";  
     String[][] entrada ;
    //fin Base de datos
    
    
    
    
    Validar vali=new Validar();
   @FXML private AnchorPane anchorP;
   @FXML private Button boton_cargar_red;
   @FXML private DatePicker fechaInicial;
   @FXML private TextArea texareaDatos;
   
   @FXML private DatePicker fechaFinal;
   @FXML private GridPane gridPane;
   @FXML Button botonMineria,botonValidarRed;
   @FXML Button botonInteligencia,botonentrenarRed,botonmine,botonred,boton_cargar_conjunto ;
   @FXML TableView  tablaEntrenamiento;
   @FXML TextField tex_error, tex_max_iteraciones,tex_tasa_aprendizaje,text_num_capas,tex_num_neu_entrada,tex_num_neu_oculta,t_numero_neuro_salida;
   @FXML       int t_max_iteraciones,t_num_capas,t_num_neu_entrada,t_num_neu_ocul,t_neuro_salida;
   @FXML    double t_error,t_tasa_aprendizaje;
   String regla_aprendiz;
   String funcion_trans; 
   @FXML private ComboBox combo_dia_semana,combo_altitud,combo_tipo_comsumidor,
                          combo_fen_climatico,combo_fran_horaria,combo_estrato,
                          combo_mes,funcion_transferencia,regla_aprendizaje;
    File file;
   @FXML CheckBox biasCheck,checkConectarentradasalida ;   
   String Column1MapKey = "A";
   String Column2MapKey = "B";
   String Column3MapKey = "C";
    ScreensController myController;   
   private Desktop desktop=Desktop.getDesktop();
            boolean bias ;        
File archivo = null;
   /*
    Iniciar objetos
   */
   
    CrearRed crearRed =new CrearRed();
   int mometunInt;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       assert  boton_cargar_red != null : "fx:id=\"toolbar\" was not injected: check your FXML file 'guiRed.fxml'.";
       assert botonentrenarRed!= null : "fx:id=\"botonentrenarRed\" was not injected: check your FXML file 'guiRed.fxml'.";
       assert tablaEntrenamiento!= null : "fx:id=\"tablaEntrenamiento\" was not injected: check your FXML file 'guiRed.fxml'.";
       assert texareaDatos !=null: "fx:id=\"texareaDatos\" was not injected: check your FXML file 'guiRed.fxml'.";
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
          ObservableList<String> optionscombo_regla_aprendizaje= FXCollections.observableArrayList("BackPropagation", "BackPropagation con Momentum", "Resilient BackPropagation", "Dynamic BackPropagation");
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
    @FXML
    private void  irTesteoRNA(ActionEvent event){
      myController.setScreen(Ejemplos.screen4ID);
    
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
    
    public void abrirArchivoEntrenamiento(ActionEvent event) throws FileNotFoundException, IOException{    
        Stage stage =new Stage();    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir archivo de entrenamiento");       
          file =fileChooser.showOpenDialog(stage); 
        FileInputStream fstream = new FileInputStream(file.getPath());
            // Creamos el objeto de entrada
            DataInputStream entrada = new DataInputStream(fstream);
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            // Leer el archivo linea por linea
            while ((strLinea = buffer.readLine()) != null)   {
                // Imprimimos la línea por pantalla
                texareaDatos.appendText(strLinea+"\n");
            } 
    }
    
    
    
    @FXML
    public void abrirDatosDesdeBD() throws IOException  {
         
     // File archivo = null;
      FileReader fr = null;
      BufferedReader br = null; 
      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File ("D:\\excell\\file.csv");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);        
       
        ArrayList medidaEntrada =new ArrayList();
        ArrayList horaentrada=new ArrayList();
        ArrayList franjaEntrada=new ArrayList();
        ArrayList anoEntrada=new ArrayList();
        ArrayList diaEntrada=new ArrayList();
        ArrayList mesEntrada=new ArrayList();
        ArrayList consumoEntrada=new ArrayList();
         // Lectura del fichero
         String linea;
          String [] cosito = null;
         while((linea=br.readLine())!=null){
            System.out.println(linea);
            cosito= linea.split(";");
              medidaEntrada.add(cosito[0]);
              horaentrada.add(cosito[1]);
              franjaEntrada.add(cosito[2]);
              anoEntrada.add(cosito[3]);
              diaEntrada.add(cosito[4]);
              mesEntrada.add(cosito[5]);
              consumoEntrada.add(cosito[6]);                 
         }
            //Cada linea queda dividida por el ; y eso devuelve un array con los valores de la linea  
            System.out.println("leidoodooddodo");
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         try{                   
            if( null != fr ){  
               fr.close();    
            }                 
         }catch (Exception e2){
            e2.printStackTrace();
         }
      }
      
      
      
        System.out.println("ddd");
    }
    
    public void pasarAcolumnas(String[] array, String line){
      
        for (int i = 0; i < 100000; i++) {
            
            for (int j = 0; j < array.length; j++) {
                entrada[j][i]=line.split(";").toString();
            }
            
        }
    
    
    }
    
    
    
    
    
    
    public void tomarDatosEntrenamiento(ActionEvent event){
        Stage stage =new Stage(); 
          funcion_trans=   funcion_transferencia.getValue().toString();
    regla_aprendiz = regla_aprendizaje.getValue().toString();
        
        bias= biasCheck.isSelected();        
         if(vali.validaDouble(tex_error)){
            t_error=Double.parseDouble(tex_error.getText().toString());
         }if(vali.validaDouble(tex_max_iteraciones)){
            t_max_iteraciones=Integer.parseInt(tex_max_iteraciones.getText().toString());
         }if(vali.validaDouble(tex_tasa_aprendizaje)){
            t_tasa_aprendizaje=Double.parseDouble(tex_tasa_aprendizaje.getText().toString());
         }if(vali.validarEntero(text_num_capas)){
            t_num_capas=Integer.parseInt(text_num_capas.getText().toString());
         }if(vali.validarEntero(tex_num_neu_entrada)){
            t_num_neu_entrada=Integer.parseInt(tex_num_neu_entrada.getText().toString()); 
         }if(vali.validarEntero(tex_num_neu_oculta)){
            t_num_neu_ocul=Integer.parseInt(tex_num_neu_oculta.getText().toString());  
         }if(vali.validarEntero(t_numero_neuro_salida)){
            t_neuro_salida=Integer.parseInt(t_numero_neuro_salida.getText().toString());        
         }
         
         entrenarRed();
    }
    public void entrenarRed(){
    
         if(regla_aprendizaje.getValue().toString().equals("BackPropagation con Momentum")){
                crearRed.crearRedconMomentun(t_error, t_max_iteraciones, t_tasa_aprendizaje, t_num_capas, t_num_neu_entrada, 
                t_num_neu_ocul, t_neuro_salida,mometunInt, funcion_trans ,regla_aprendiz,file.getPath(), bias);

            
         }if(regla_aprendizaje.getValue().toString().equals("BackPropagation")){
               
             
         }
    
    }
    
    
    @FXML public int ventanaEmergente( ){
        
        if (regla_aprendizaje.getValue().toString().equals("BackPropagation con Momentum")) {
            final Stage primaryStage  =new Stage();
            primaryStage.setTitle("ingrese Momentum");           
            //  final Popup popup = new Popup(); popup.setX(400); popup.setY(300);
            //  popup.getContent().addAll(new Circle(25, 25, 50, Color.AQUAMARINE));
            final TextField  momentumTexfield= new TextField("Momentum");
            Button botonaceptar = new Button("Aceptar");        
            botonaceptar.setOnAction(new EventHandler<ActionEvent>() {             
            @Override public void handle(ActionEvent event) {       
            if(vali.validarEntero(momentumTexfield)){
             mometunInt= Integer.parseInt(  momentumTexfield.getText().toString());
             primaryStage.hide();
          }else{
             momentumTexfield.setText(" ");
           }          
      }});    
            Button botoncancelar = new Button("Cancelar");
            botoncancelar.setOnAction(new EventHandler<ActionEvent>() {
             @Override public void handle(ActionEvent event) {
                  primaryStage.hide();            
             }
           }); 
               HBox layout = new HBox(10);
               layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
               layout.getChildren().addAll(momentumTexfield, botonaceptar, botoncancelar);
               primaryStage.setScene(new Scene(layout));
               primaryStage.show();     
        }
       
        return mometunInt;
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
                    if(regla_aprendiz.equals("BackPropagation")){
                        MomentumBackpropagation reglaAprendisaje=(MomentumBackpropagation)redNeu.getLearningRule();
                        reglaAprendisaje.setLearningRate(error);
                        reglaAprendisaje.setMomentum(error);
                       
                    }if(regla_aprendiz.equals("BackPropagation con Momentum")){
                                             
                         
                    }if(regla_aprendiz.equals("Resilient BackPropagation")){
                        ResilientPropagation reglaApredizaje=(ResilientPropagation)redNeu.getLearningRule();
                    }if(regla_aprendiz.equals("Dynamic BackPropagation")){
                       DynamicBackPropagation reglaAprendizaje=(DynamicBackPropagation)redNeu.getLearningRule();
                    }
                  
             }else if(funcion_trans.equals("Sigmoidal")){
                MultiLayerPerceptron redNeu=new MultiLayerPerceptron(TransferFunctionType.SIGMOID, neur_entrada,neu_oculta,neu_salida);
                   if(regla_aprendiz.equals("BackPropagation")){
                        MomentumBackpropagation reglaAprendisaje=(MomentumBackpropagation)redNeu.getLearningRule();
                    }if(regla_aprendiz.equals("BackPropagation con Momentum")){

                    }if(regla_aprendiz.equals("Resilient BackPropagation")){
                        ResilientPropagation reglaApredizaje=(ResilientPropagation)redNeu.getLearningRule();
                    }if(regla_aprendiz.equals("Dynamic BackPropagation")){
                       DynamicBackPropagation reglaAprendizaje=(DynamicBackPropagation)redNeu.getLearningRule();
                    }
             }else if(funcion_trans.equals("Lineal")){
                 
                MultiLayerPerceptron redNeu=new MultiLayerPerceptron(TransferFunctionType.LINEAR, neur_entrada,neu_oculta,neu_salida);
                    if(regla_aprendiz.equals("BackPropagation")){
                        MomentumBackpropagation reglaAprendisaje=(MomentumBackpropagation)redNeu.getLearningRule();
                    }if(regla_aprendiz.equals("BackPropagation con Momentum")){
                                              System.out.println("---------------");
                    }if(regla_aprendiz.equals("Resilient BackPropagation")){
                        ResilientPropagation reglaApredizaje=(ResilientPropagation)redNeu.getLearningRule();
                    }if(regla_aprendiz.equals("Dynamic BackPropagation")){
                       DynamicBackPropagation reglaAprendizaje=(DynamicBackPropagation)redNeu.getLearningRule();
                    }  
             } 
             
         }else{
               
             
         }
        
    }
     public void instanciarRed(){
     
     
     
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
