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
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
 import javafx.util.Callback; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;


/**
 * FXML Controller class
 *
 * @author N550J
 */
public class GuiRedController implements Initializable, ControlledScreen{
   @FXML private AnchorPane anchorP;
   @FXML private Button boton_cargar_red;
   @FXML private DatePicker fechaInicial;
   @FXML private DatePicker fechaFinal;
   @FXML private GridPane gridPane;
   @FXML Button botonMineria;
   @FXML Button botonInteligencia,botonentrenarRed,botonmine,botonred ;
   @FXML TableView  tablaEntrenamiento;
   
   @FXML private ComboBox combo_dia_semana,combo_altitud,combo_tipo_comsumidor,
                          combo_fen_climatico,combo_fran_horaria,combo_estrato,combo_mes;
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
       Image imageDecline = new Image(getClass().getResourceAsStream("/imagenes/guardar.png"));
       Image imageMine = new Image(getClass().getResourceAsStream("/imagenes/mineri.png"));
       Image imageRed = new Image(getClass().getResourceAsStream("/imagenes/red3.png")); 
       botonentrenarRed.setGraphic(new ImageView(imageDecline));
       botonmine.setGraphic(new ImageView(imageMine));
       botonred.setGraphic(new ImageView(imageRed));
       
    }
    
    @FXML public void iniciarControles(){
      TextField tex_error, tex_max_iteraciones,tex_tasa_aprendizaje,text_num_capas,tex_num_neu_entrada,tex_num_neu_oculta;
      
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
        File file =fileChooser.showOpenDialog(stage);
       
       
//            if (file.exists() && file.canRead()) {
//                DataSourceReader dsr1 = new FileSource(file);  
//                String[] cols ={"FNAME","LNAME","ADDRESS"};
//                MyCSVDataSource ds1 = new MyCSVDataSource(dsr1, cols);
//                TableView tableView = new TableView();
//                tableView.setItems(ds1.getData());
//
//                System.out.println("CSV : " + ds1.getData().size());
//        
//        
//            }
        populateTable( file.getPath(), true);
        
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
    
     
     
     
     private void populateTable( /*final TableView<ObservableList<StringProperty>> table,*/final String urlSpec, final boolean hasHeader) {
    tablaEntrenamiento.getItems().clear();
    tablaEntrenamiento.getColumns().clear();
//    tablaEntrenamiento.setPlaceholder(new Label("Loading..."));
         
    Task<Void> task = new Task<Void>() {
        
      @Override
      protected Void call() throws Exception {
        BufferedReader in = getReaderFromUrl(urlSpec);
        // Header line
        if (hasHeader) {
          final String headerLine = in.readLine();
          final String[] headerValues = headerLine.split(" ");
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              for (int column = 0; column < headerValues.length; column++) {
                tablaEntrenamiento.getColumns().add(
                    createColumn(column, headerValues[column]));
              }
            }
          });
        }

        // Data:
 System.out.println("**********");
        String dataLine;
        while ((dataLine = in.readLine()) != null) {
          final String[] dataValues = dataLine.split(" ");
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              // Add additional columns if necessary:
               
              for (int columnIndex = tablaEntrenamiento.getColumns().size(); columnIndex < dataValues.length; columnIndex++) {
                tablaEntrenamiento.getColumns().add(createColumn(columnIndex, ""));
              }
              // Add data to table:
              ObservableList<StringProperty> data = FXCollections
                  .observableArrayList();
              for (String value : dataValues) {
                data.add(new SimpleStringProperty(value));
              }
              tablaEntrenamiento.getItems().add(data);
            }
          });
        }
        return null;
      }
    };
   
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
  }

  private TableColumn<ObservableList<StringProperty>, String> createColumn(final int columnIndex, String columnTitle) {
      
    TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
    String title;
    if (columnTitle == null || columnTitle.trim().length() == 0) {
      title = "Column " + (columnIndex + 1);
    } else {
      title = columnTitle;
    }
    column.setText(title);
    column
        .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
            ObservableList<StringProperty> values = cellDataFeatures.getValue();
            if (columnIndex >= values.size()) {
              return new SimpleStringProperty("");
            } else {
              return cellDataFeatures.getValue().get(columnIndex);
            }
          }
        });
    return column;
  }

  private BufferedReader getReaderFromUrl(String urlSpec) throws Exception {
    URL url = new URL(urlSpec);
    URLConnection connection = url.openConnection();
    InputStream in = connection.getInputStream();
    return new BufferedReader(new InputStreamReader(in));
  }

  
  public void llenarTablaEntrena(ActionEvent event){
       Stage stage =new Stage();    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");       
        File file =fileChooser.showOpenDialog(stage);
     
       // FileSource fs = new FileSource("test.csv");  
       // Now creating my datasource 
        
       
       // FileSource fs = new FileSource("test.csv");  
       // Now creating my datasource 
//       CSVDataSource dataSource = new CSVDataSource(  
//                 file.getPath(), "order-id", "order-item-id");  
//       @SuppressWarnings("rawtypes")  
//       //TableView table1 = new TableView();  
//       TableColumn<?, ?> orderCol = dataSource.getNamedColumn("order-id");  
//       TableColumn<?, ?> itemCol = dataSource.getNamedColumn("order-item-id");    
//       tablaEntrenamiento.getColumns().addAll(orderCol, itemCol);  
//       tablaEntrenamiento.setItems(dataSource);
  }
  
  
}
