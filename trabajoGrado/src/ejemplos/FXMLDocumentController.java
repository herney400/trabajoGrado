/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejemplos;

import accesoDatos.Conexion;
import accesoDatos.Consultas;
import eu.schudt.javafx.controls.calendar.DatePicker;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CheckMenuItemBuilder;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuBuilder;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author N550J
 */
public class FXMLDocumentController implements Initializable {
     
    @FXML private PieChart mibarchar ;
    @FXML private LineChart<Double, Double> graph;
    @FXML private BubbleChart<Double, Double> buble;
    static final Duration ANIMATION_DURATION = new Duration(500);
    static final double ANIMATION_DISTANCE = 0.15;
    String drilldownCss="";
    
    //Consultas ComboBox
    @FXML private ComboBox tipo_filtro;
    @FXML private ComboBox combo_empresa;
    @FXML private ComboBox combo_medida;
    @FXML private ComboBox combo_tipo;
    @FXML private ComboBox combo_tipo_cliente;
    @FXML private ComboBox combo_estrato;
    @FXML private ComboBox combo_barrio;
    @FXML private ComboBox combo_region;
    @FXML private ComboBox combo_ciudad;
    @FXML private ComboBox combo_altitud;
    @FXML private ComboBox combo_zona;
    @FXML private ComboBox combo_franja;
    @FXML private ComboBox combo_hora;
    @FXML private ComboBox combo_dia_semana;
    @FXML private ComboBox combo_dia;
    @FXML private ComboBox combo_mes;
    @FXML private ComboBox combo_ano;
    @FXML private Label caption ;
    @FXML private MenuBar  menubar;
    @FXML private ToolBar toolbar;
    @FXML private Tab tab_h_consumo;
    @FXML private Tab tab_h_precio;
    @FXML private Tab tab_h_pagos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciar_varios();
        menuBar();
        iniciarCombos();
    } 
    
    public void iniciar_varios(){
        drilldownCss = FXMLDocumentController.class.getResource("/estilos/DrilldownChart.css").toExternalForm();
        assert toolbar != null : "fx:id=\"toolbar\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert menubar != null : "fx:id=\"menubar\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_h_consumo != null : "fx:id=\"tab_h_consumo\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_h_precio != null : "fx:id=\"tab_h_precio\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_h_pagos != null : "fx:id=\"tab_h_pagos\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
    }
    
    public void iniciarCombos(){
        Conexion con = new Conexion(); 
        assert mibarchar != null : "fx:id=\"mibarchar\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        
        assert combo_tipo != null : "fx:id=\"combo_tipo\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        ObservableList<String> optionsTipo = FXCollections.observableArrayList("consolidado", "promedio");
        combo_tipo.setItems(optionsTipo);
        combo_tipo.getSelectionModel().selectLast();
        
        assert tipo_filtro != null : "fx:id=\"tipo_filtro\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        ObservableList<String> options = FXCollections.observableArrayList("ciudad", "cliente", "empresa");
        tipo_filtro.setItems(options);
        tipo_filtro.getSelectionModel().selectLast();      
        
        assert combo_empresa != null : "fx:id=\"combo_empresa\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datosComboEmpresa=con.LlenarCommbo("select empresa from empresa order by id_empresa");
        datosComboEmpresa.add("Todos");
        combo_empresa.getItems().clear();
        combo_empresa.setItems(datosComboEmpresa);
        combo_empresa.getSelectionModel().selectLast();
        
        assert combo_medida != null : "fx:id=\"combo_medida\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datosComboMedida=con.LlenarCommbo("select medida from medida order by id_medida");
        datosComboMedida.add("Todos");
        combo_medida.getItems().clear();
        combo_medida.setItems(datosComboMedida);
        combo_medida.getSelectionModel().selectLast();
        
        assert combo_tipo_cliente != null : "fx:id=\"combo_tipo_cliente\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datosComboTipoCliente = con.LlenarCommbo("select tipo_cliente from tipo_cliente order by tipo_cliente");
        datosComboTipoCliente.add("Todos");
        combo_tipo_cliente.getItems().clear();
        combo_tipo_cliente.setItems(datosComboTipoCliente);
        combo_tipo_cliente.getSelectionModel().selectLast();
        
        assert combo_estrato != null : "fx:id=\"combo_estrato\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_estrato = con.LlenarCommbo("select distinct estrato from cliente order by estrato");
        datoscombo_estrato.add("Todos");
        combo_estrato.getItems().clear();
        combo_estrato.setItems(datoscombo_estrato);
        combo_estrato.getSelectionModel().selectLast();
        
        assert combo_barrio != null : "fx:id=\"combo_barrio\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_barrio = con.LlenarCommbo("select distinct barrio from cliente order by barrio");
        datoscombo_barrio.add("Todos");
        combo_barrio.getItems().clear();
        combo_barrio.setItems(datoscombo_barrio);
        combo_barrio.getSelectionModel().selectLast();
        
        assert combo_region != null : "fx:id=\"combo_region\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_region = con.LlenarCommbo("select distinct region from region order by region");
        datoscombo_region.add("Todos");
        combo_region.getItems().clear();
        combo_region.setItems(datoscombo_region);
        combo_region.getSelectionModel().selectLast();
        
        assert combo_ciudad != null : "fx:id=\"combo_ciudad\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datosComboValues=con.LlenarCommbo("select ciudad from ciudad order by id_ciudad");
        datosComboValues.add("Todos");
        combo_ciudad.getItems().clear();
        combo_ciudad.setItems(datosComboValues);
        combo_ciudad.getSelectionModel().selectLast();
        
        assert combo_altitud != null : "fx:id=\"combo_altitud\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_altitud=con.LlenarCommbo("select distinct altitud from ciudad");
        datoscombo_altitud.add("Todos");
        combo_altitud.getItems().clear();
        combo_altitud.setItems(datoscombo_altitud);
        combo_altitud.getSelectionModel().selectLast();
        
        assert combo_zona != null : "fx:id=\"combo_zona\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_zona=con.LlenarCommbo("select distinct zona from ciudad");
        datoscombo_zona.add("Todos");
        combo_zona.getItems().clear();
        combo_zona.setItems(datoscombo_zona);
        combo_zona.getSelectionModel().selectLast(); 
        
        assert combo_franja != null : "fx:id=\"combo_franja\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datosComboFranja=con.LlenarCommbo("select distinct (franja_horaria) from tiempo");
        datosComboFranja.add("Todos");
        combo_franja.getItems().clear();
        combo_franja.setItems(datosComboFranja);
        combo_franja.getSelectionModel().selectLast();
        
        assert combo_hora != null : "fx:id=\"combo_hora\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_hora=con.LlenarCommbo("select distinct (hora) from tiempo");
        datoscombo_hora.add("Todos");
        combo_hora.getItems().clear();
        combo_hora.setItems(datoscombo_hora);
        combo_hora.getSelectionModel().selectLast();
        
        assert combo_dia_semana != null : "fx:id=\"combo_dia_semana\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_dia_semana=con.LlenarCommbo("select distinct (dia_semana) from tiempo");
        datoscombo_dia_semana.add("Todos");
        combo_dia_semana.getItems().clear();
        combo_dia_semana.setItems(datoscombo_dia_semana);
        combo_dia_semana.getSelectionModel().selectLast();
        
        assert combo_dia != null : "fx:id=\"combo_dia\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_dia=con.LlenarCommbo("select distinct (dia) from fecha");
        datoscombo_dia.add("Todos");
        combo_dia.getItems().clear();
        combo_dia.setItems(datoscombo_dia);
        combo_dia.getSelectionModel().selectLast();
        
        assert combo_mes != null : "fx:id=\"combo_mes\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_mes=con.LlenarCommbo("select distinct (mes) from fecha");
        datoscombo_mes.add("Todos");
        combo_mes.getItems().clear();
        combo_mes.setItems(datoscombo_mes);
        combo_mes.getSelectionModel().selectLast();
        
        assert combo_ano != null : "fx:id=\"combo_ano\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_ano=con.LlenarCommbo("select distinct (ano) from fecha");
        datoscombo_ano.add("Todos");
        combo_ano.getItems().clear();
        combo_ano.setItems(datoscombo_ano);
        combo_ano.getSelectionModel().selectLast();
    }
    
    /**
     * 
     */
    @FXML private void cambiarTabPagos(){
//        if(tab_h_precio.isSelected()){
//            
//        }
//        if(tab_h_consumo.isSelected()){
//            
//        }
//        if(tab_h_pagos.isSelected()){
//            
//        }
    }
    
    /**
     * 
     */
    @FXML private void cambiarTabConsumo(){
//        if(tab_h_precio.isSelected()){
//            
//        }
//        if(tab_h_consumo.isSelected()){
//            
//        }
//        if(tab_h_pagos.isSelected()){
//            
//        }
    }

    /**
     * 
     */
    @FXML private void cambiarTabPrecio(){
//        if(tab_h_precio.isSelected()){
//            
//        }
//        if(tab_h_consumo.isSelected()){
//            
//        }
//        if(tab_h_pagos.isSelected()){
//            
//        }
    }
    
    /**
     * 
     * @param E 
     */
    @FXML private void generarReporte(ActionEvent E){        
        
        Conexion con = new Conexion();
        Consultas consul = new Consultas();
        
        ArrayList<String> tablaSubConWhere = new ArrayList();
        ArrayList<String> id_tablaSubConWhere = new ArrayList();
        ArrayList<String> valoresWhere = new ArrayList();
        
        if(combo_ciudad.getValue().equals("Todos"))
        {
        }
        else
        {
            tablaSubConWhere.add("ciudad");
            id_tablaSubConWhere.add("id_ciudad");
            ObservableList<String> datosComboValues=con.LlenarCommbo("select id_ciudad from ciudad order by id_ciudad");            
            valoresWhere.add(datosComboValues.get(combo_ciudad.getSelectionModel().getSelectedIndex()));
        }
        
        if(combo_empresa.getValue().equals("Todos"))
        {
        }
        else
        {
             tablaSubConWhere.add("empresa");
             id_tablaSubConWhere.add("id_empresa");
             ObservableList<String> datosComboEmpresa=con.LlenarCommbo("select id_empresa from empresa order by id_empresa");
             valoresWhere.add(datosComboEmpresa.get(combo_empresa.getSelectionModel().getSelectedIndex()));
        }
        
        if(combo_medida.getValue().equals("Todos"))
        {
        }
        else
        {
             tablaSubConWhere.add("medida");
             id_tablaSubConWhere.add("id_medida");
             ObservableList<String> datosComboMedida=con.LlenarCommbo("select id_medida from medida order by id_medida");
             valoresWhere.add(datosComboMedida.get(combo_medida.getSelectionModel().getSelectedIndex()));
        }
        
        String[] tablaSubConWhereA = new String[tablaSubConWhere.size()];
        tablaSubConWhereA = tablaSubConWhere.toArray(tablaSubConWhereA);
        
        String [] id_tablaSubConWhereA = new String[id_tablaSubConWhere.size()];
        id_tablaSubConWhereA = id_tablaSubConWhere.toArray(id_tablaSubConWhereA);
        
        String [] valoresWhereA = new String[valoresWhere.size()];
        valoresWhereA = valoresWhere.toArray(valoresWhereA);
        
        String SQL = "";
        SQL += consul.generarSQL(combo_tipo.getValue().toString().toUpperCase(), tipo_filtro.getValue().toString(), "id_"+tipo_filtro.getValue().toString(), tipo_filtro.getValue().toString(), tablaSubConWhereA, id_tablaSubConWhereA, valoresWhereA);
        System.out.print(SQL);
        
        ObservableList<PieChart.Data> pieChartData = con.EjecutarConsultaPieChart(SQL);   
        ((Parent) mibarchar).getStylesheets().add(drilldownCss);        
        mibarchar.setData(pieChartData);      
    }
    
    /**
     * 
     */
    public void evento(){
        
        caption.setTextFill(Color.BURLYWOOD);
        caption.setStyle("-fx-font: 29 arial;");

        assert caption != null : "fx:id=\"caption\" was not injected: check your FXML file 'FXMLDocumetn.fxml'."; 
        for (final PieChart.Data data : mibarchar.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED , new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                caption.setTranslateX(e.getSceneX());                        
                caption.setTranslateY(e.getSceneY());
                System.out.println("=======>"+data.getPieValue());
                caption.setText(String.valueOf(data.getPieValue()) + "%");
                }
            });
        }
    }
    
    /**
     * 
     * @param E 
     */
    @FXML private void reporteLine(ActionEvent E){
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();  
        LineChart.Series<Double, Double> series = new LineChart.Series<Double, Double>(); 
        LineChart.Series<Double, Double> series3 = new LineChart.Series<Double, Double>();
       
        series.setName("Portfolio 1");
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
       
        
        series3.setName("Portfolio 2");
        series3.getData().add(new XYChart.Data(12, 44));
        series3.getData().add(new XYChart.Data(11, 35));
        series3.getData().add(new XYChart.Data(10, 36));
        series3.getData().add(new XYChart.Data(9, 33));
        series3.getData().add(new XYChart.Data(8, 31));
        series3.getData().add(new XYChart.Data(7, 26));
        series3.getData().add(new XYChart.Data(6, 22));
        series3.getData().add(new XYChart.Data(5, 25));
        series3.getData().add(new XYChart.Data(4, 43));
        series3.getData().add(new XYChart.Data(3, 44));
        series3.getData().add(new XYChart.Data(2, 45));
        series3.getData().add(new XYChart.Data(1, 44));
       
        
        lineChartData.addAll(series,series3);
        graph.setCreateSymbols(true); 
        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }
    
    @FXML private void reportePrecio(ActionEvent e){
        final NumberAxis xAxis = new NumberAxis(1, 53, 4);
        final NumberAxis yAxis = new NumberAxis(0, 80, 10);
        
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        BubbleChart.Series<Double,Double>series=new BubbleChart.Series<Double, Double>();
//        for (double i = 0; i < 100; i++) {
            series.getData().add(new XYChart.Data(8, 15, 2));
            series.getData().add(new XYChart.Data(13, 23, 1));
            series.getData().add(new XYChart.Data(15, 45, 3));
            series.getData().add(new XYChart.Data(24, 30, 4.5));
            series.getData().add(new XYChart.Data(38, 78, 1));
            series.getData().add(new XYChart.Data(40, 41, 7.5));
            series.getData().add(new XYChart.Data(45, 57, 2));
            series.getData().add(new XYChart.Data(47, 23, 3.8));
//        } 
         lineChartData.add(series);
         buble.setData(lineChartData);    
         
         
    }

    @FXML private void menuBar(){
        
         String styledToolBarCss = FXMLDocumentController.class.getResource("/fxml/FXMLDocument.fxml").toExternalForm();
        
             
      toolbar.getStylesheets().add(styledToolBarCss);
       
       
        final Label outputLabel = new Label(); 
        MenuItem menu111 = MenuItemBuilder.create().text("blah").build();
         final MenuItem menu112 = MenuItemBuilder.create().text("foo").build();
         final CheckMenuItem menu113 = CheckMenuItemBuilder.create().text("Show \"foo\" item").selected(true).build();
         menu113.selectedProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable valueModel) {
                menu112.setVisible(menu113.isSelected());
                System.err.println("MenuItem \"foo\" is now " + (menu112.isVisible() ? "" : "not") + " visible.");
            }
        });
        Menu menu11 = MenuBuilder.create()
                .text("Submenu 1")
              //  .graphic(new ImageView(new Image(MenuSample.class.getResourceAsStream("menuInfo.png"))))
                .items(menu111, menu112, menu113)
                .build();
        
        // Options->Submenu 2 submenu
        MenuItem menu121 = MenuItemBuilder.create().text("Item 1").build();
        MenuItem menu122 = MenuItemBuilder.create().text("Item 2").build();
        Menu menu12 = MenuBuilder.create().text("Submenu 2").items(menu121, menu122).build();

        // Options->Change Text
        final String change[] = {"Change Text", "Change Back"};
        final MenuItem menu13 = MenuItemBuilder.create().text(change[0]).accelerator(KeyCombination.keyCombination("Shortcut+C")).build();
        menu13.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                menu13.setText((menu13.getText().equals(change[0])) ? change[1] : change[0]);
                outputLabel.setText(((MenuItem) t.getTarget()).getText() + " - action called");
            }
        });        
        
        // Options menu
        Menu menu1 = MenuBuilder.create().text("Options").items(menu11, menu12, menu13).build();

        menubar.getMenus().addAll(menu1);
   }
}
