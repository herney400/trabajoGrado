/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejemplos;

import accesoDatos.Conexion;
import accesoDatos.Consultas;
import eu.schudt.javafx.controls.calendar.DatePicker;
import fxml.ControlledScreen;
import fxml.ScreensController;

import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.scene.chart.BarChart;
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
public class FXMLDocumentController   implements Initializable, ControlledScreen {
    
    ScreensController myController;
    @FXML private LineChart<Double, Double> graph;
    @FXML private BubbleChart<Double, Double> buble;
    static final Duration ANIMATION_DURATION = new Duration(500);
    static final double ANIMATION_DISTANCE = 0.15;
    String drilldownCss="";
    
    //Graficos
    @FXML private PieChart mibarchar;
    @FXML private PieChart chart_Pagos;
    @FXML private PieChart chart_Precio;
    
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
    @FXML private ComboBox combo_mes;
    @FXML private ComboBox combo_tipo_filtro_estrato;
    @FXML private ComboBox combo_tipo_filtro_altitud;
    //Varios
    @FXML private Label caption;
    @FXML private MenuBar  menubar;
    @FXML private ToolBar toolbar;
    @FXML private GridPane gridPane;
    //Tabs
    @FXML private Tab tab_h_consumo;
    @FXML private Tab tab_h_precio;
    @FXML private Tab tab_h_pagos;
    @FXML private Tab tab_filtros_generales;
    @FXML private Tab tab_filtros_demo;
    @FXML private Tab tab_filtros_geo;
    @FXML private Tab tab_filtros_time;
    @FXML private Tab tab_filtros_varios;
    //Calendarios
    @FXML private DatePicker fechaInicial;
    @FXML private DatePicker fechaFinal;
     @FXML Button botonInteligencia,botonmineria,botonred,boton_mineria,boton_inteligencia;
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciar_varios();
   //     menuBar();
        iniciarCombos();
        iniciarCalendarios();
         Image imageMine = new Image(getClass().getResourceAsStream("/imagenes/mineri.png"));
        Image imageRed = new Image(getClass().getResourceAsStream("/imagenes/red3.png")); 
       boton_mineria.setGraphic(new ImageView(imageMine));
       boton_inteligencia.setGraphic(new ImageView(imageRed));
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
    
    public void iniciar_varios(){
        drilldownCss = FXMLDocumentController.class.getResource("/estilos/DrilldownChart.css").toExternalForm();
        assert toolbar != null : "fx:id=\"toolbar\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert menubar != null : "fx:id=\"menubar\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_h_consumo != null : "fx:id=\"tab_h_consumo\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_h_precio != null : "fx:id=\"tab_h_precio\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_h_pagos != null : "fx:id=\"tab_h_pagos\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_filtros_generales != null : "fx:id=\"tab_filtros_generales\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_filtros_demo != null : "fx:id=\"tab_filtros_demo\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_filtros_geo != null : "fx:id=\"tab_filtros_geo\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_filtros_time != null : "fx:id=\"tab_filtros_time\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert tab_filtros_varios != null : "fx:id=\"tab_filtros_varios\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert gridPane != null : "fx:id=\"gridPane\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
    }
    
    public void iniciarCombos(){
        Conexion con = new Conexion(); 
        assert mibarchar != null : "fx:id=\"mibarchar\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert chart_Pagos != null : "fx:id=\"chart_Pagos\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        assert chart_Precio != null : "fx:id=\"chart_Precio\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        
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
        
        assert combo_mes != null : "fx:id=\"combo_mes\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";      
        ObservableList<String> datoscombo_mes = FXCollections.observableArrayList("Mayor", "Menor", "Igual", "Mayor o igual", "Menor o igual");
        combo_mes.getItems().clear();
        combo_mes.setItems(datoscombo_mes);
        combo_mes.getSelectionModel().selectLast();
        
        assert combo_tipo_filtro_estrato != null : "fx:id=\"combo_tipo_filtro_estrato\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        ObservableList<String> optionscombo_tipo_filtro_estrato = FXCollections.observableArrayList("Mayor", "Menor", "Igual", "Mayor o igual", "Menor o igual");
        combo_tipo_filtro_estrato.setItems(optionscombo_tipo_filtro_estrato);
        combo_tipo_filtro_estrato.getSelectionModel().selectLast();
        
        assert combo_tipo_filtro_altitud != null : "fx:id=\"combo_tipo_filtro_altitud\" was not injected: check your FXML file 'FXMLDocumetn.fxml'.";
        ObservableList<String> optionscombo_tipo_filtro_altitud = FXCollections.observableArrayList("Por encima de", "Por debajo de", "Igual a", "Mayor o igual a", "Menor o igual a");
        combo_tipo_filtro_altitud.setItems(optionscombo_tipo_filtro_altitud);
        combo_tipo_filtro_altitud.getSelectionModel().selectLast();
    }
    
    /**
     * 
     */
    @FXML private void cambiarTabPagos(){
                
         if(tab_h_precio.isSelected()){
            tab_filtros_generales.setDisable(false);
            tab_filtros_demo.setDisable(true);
            tab_filtros_geo.setDisable(false);
            tab_filtros_time.setDisable(false);
            tab_filtros_varios.setDisable(false);
        }
        if(tab_h_consumo.isSelected()){
            tab_filtros_generales.setDisable(false);
            tab_filtros_demo.setDisable(false);
            tab_filtros_geo.setDisable(false);
            tab_filtros_time.setDisable(false);
            tab_filtros_varios.setDisable(true);
        }
        if(tab_h_pagos.isSelected()){
            tab_filtros_generales.setDisable(false);
            tab_filtros_demo.setDisable(false);
            tab_filtros_geo.setDisable(true);
            tab_filtros_time.setDisable(false);
            tab_filtros_varios.setDisable(true);
        }
    }
    
    /**
     * 
     */
    @FXML private void cambiarTabConsu(){ 
        
        if(tab_h_precio.isSelected()){
            tab_filtros_generales.setDisable(false);
            tab_filtros_demo.setDisable(true);
            tab_filtros_geo.setDisable(false);
            tab_filtros_time.setDisable(false);
            tab_filtros_varios.setDisable(false);
        }
        if(tab_h_consumo.isSelected()){
            tab_filtros_generales.setDisable(false);
            tab_filtros_demo.setDisable(false);
            tab_filtros_geo.setDisable(false);
            tab_filtros_time.setDisable(false);
            tab_filtros_varios.setDisable(true);
        }
        if(tab_h_pagos.isSelected()){
            tab_filtros_generales.setDisable(false);
            tab_filtros_demo.setDisable(false);
            tab_filtros_geo.setDisable(true);
            tab_filtros_time.setDisable(false);
            tab_filtros_varios.setDisable(true);
        }
    }

    /**
     * 
     */
    @FXML private void cambiarTabPrecio(){
        if(tab_h_precio.isSelected()){
            tab_filtros_generales.setDisable(false);
            tab_filtros_demo.setDisable(true);
            tab_filtros_geo.setDisable(false);
            tab_filtros_time.setDisable(false);
            tab_filtros_varios.setDisable(false);
        }
        if(tab_h_consumo.isSelected()){
            tab_filtros_generales.setDisable(false);
            tab_filtros_demo.setDisable(false);
            tab_filtros_geo.setDisable(false);
            tab_filtros_time.setDisable(false);
            tab_filtros_varios.setDisable(true);
        }
        if(tab_h_pagos.isSelected()){
            tab_filtros_generales.setDisable(false);
            tab_filtros_demo.setDisable(false);
            tab_filtros_geo.setDisable(true);
            tab_filtros_time.setDisable(false);
            tab_filtros_varios.setDisable(true);
        }
    }
    
    /**
     * 
     * @param E 
     */
    @FXML private void generarReporte(ActionEvent E){        
        
        Conexion con = new Conexion();
        Consultas consul = new Consultas();
        
        if(tab_h_consumo.isSelected())
        {        
            ArrayList<String> tablaSubConWhere = new ArrayList();
            ArrayList<String> id_tablaSubConWhere = new ArrayList();
            ArrayList<String> valoresWhere = new ArrayList();
            ArrayList<String> subConsAdd = new ArrayList();

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

            //Filtros Demograficos
            String sqlCliente = "";
            if(combo_tipo_cliente.getValue().equals("Todos") && combo_estrato.getValue().equals("Todos") && combo_barrio.getValue().equals("Todos"))
            {
            }
            else
            {
                sqlCliente += "and id_cliente in (select id_cliente from cliente where 1 = 1";

                if(!combo_tipo_cliente.getValue().equals("Todos")){
                    sqlCliente += " and id_tipo_cliente in (select id_tipo_cliente from tipo_cliente where tipo_cliente = '"+combo_tipo_cliente.getValue()+"')";
                }

                if(!combo_estrato.getValue().equals("Todos")){
                    sqlCliente += " and estrato ";
                    if(combo_tipo_filtro_estrato.getValue().equals("Mayor"))
                    {
                        sqlCliente += "> " + combo_estrato.getValue();
                    }
                    if(combo_tipo_filtro_estrato.getValue().equals("Menor"))
                    {
                        sqlCliente += "< " + combo_estrato.getValue();
                    }
                    if(combo_tipo_filtro_estrato.getValue().equals("Igual"))
                    {
                        sqlCliente += "= " + combo_estrato.getValue();
                    }
                    if(combo_tipo_filtro_estrato.getValue().equals("Mayor o igual"))
                    {
                        sqlCliente += ">= " + combo_estrato.getValue();
                    }
                    if(combo_tipo_filtro_estrato.getValue().equals("Menor o igual"))
                    {
                        sqlCliente += "<= " + combo_estrato.getValue();
                    }         
                }

                if(!combo_barrio.getValue().equals("Todos")){
                    sqlCliente += " and barrio = '"+combo_barrio.getValue()+"')";
                }
                sqlCliente+=")";
                subConsAdd.add(sqlCliente);
            }

            //Filtros Geograficos
            String subConGeo = "";
            if(combo_region.getValue().equals("Todos") && combo_ciudad.getValue().equals("Todos") && combo_altitud.getValue().equals("Todos") && combo_zona.getValue().equals("Todos"))
            {
            }
            else
            {
                subConGeo += " and id_ciudad in (select id_ciudad from ciudad where 1=1 ";
                if(!combo_region.getValue().equals("Todos"))
                {
                    subConGeo += "and id_region in (select id_region from region where region = '"+combo_region.getValue()+"') ";
                }
                if(!combo_ciudad.getValue().equals("Todos"))
                {
                    subConGeo += "and ciudad = '"+combo_ciudad.getValue()+"' ";
                }
                if(!combo_zona.getValue().equals("Todos"))
                {
                    subConGeo += "and zona = '"+combo_zona.getValue()+"' ";
                }
                if(!combo_altitud.getValue().equals("Todos"))
                {
                    subConGeo += "and altitud ";
                    if(combo_tipo_filtro_altitud.getValue().equals("Por encima de"))
                    {
                        subConGeo += "> " + combo_altitud.getValue() + " ";
                    }
                    if(combo_tipo_filtro_altitud.getValue().equals("Por debajo de"))
                    {
                        subConGeo += "< " + combo_altitud.getValue() + " ";
                    }
                    if(combo_tipo_filtro_altitud.getValue().equals("Igual a"))
                    {
                        subConGeo += "= " + combo_altitud.getValue() + " ";
                    }
                    if(combo_tipo_filtro_altitud.getValue().equals("Mayor o igual a"))
                    {
                        subConGeo += ">= " + combo_altitud.getValue() + " ";
                    }
                    if(combo_tipo_filtro_altitud.getValue().equals("Menor o igual a"))
                    {
                        subConGeo += "<= " + combo_altitud.getValue() + " ";
                    }
                }
                subConGeo += ")";
                subConsAdd.add(subConGeo);
            }

            //Filtros de Tiempo
            String subConTiempo = "";
            if(combo_franja.getValue().equals("Todos") && combo_hora.getValue().equals("Todos") && combo_dia_semana.getValue().equals("Todos"))
            {
            }
            else
            {
                subConTiempo += "and id_tiempo in (select id_tiempo from tiempo where 1=1 ";
                if(!combo_franja.getValue().equals("Todos"))
                {
                    subConTiempo += "and franja_horaria = '"+combo_franja.getValue()+"' ";
                }
                if(!combo_dia_semana.getValue().equals("Todos"))
                {
                    subConTiempo += " and dia_semana = '"+combo_dia_semana.getValue()+"'";
                }
                if(!combo_hora.getValue().equals("Todos"))
                {
                    subConTiempo += " and estrato ";
                    if(combo_mes.getValue().equals("Mayor"))
                    {
                        subConTiempo += "> " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Menor"))
                    {
                        subConTiempo += "< " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Igual"))
                    {
                        subConTiempo += "= " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Mayor o igual"))
                    {
                        subConTiempo += ">= " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Menor o igual"))
                    {
                        subConTiempo += "<= " + combo_hora.getValue();
                    } 
                }
                subConTiempo += ")";
                subConsAdd.add(subConTiempo);
            }

            String[] tablaSubConWhereA = new String[tablaSubConWhere.size()];
            tablaSubConWhereA = tablaSubConWhere.toArray(tablaSubConWhereA);

            String [] id_tablaSubConWhereA = new String[id_tablaSubConWhere.size()];
            id_tablaSubConWhereA = id_tablaSubConWhere.toArray(id_tablaSubConWhereA);

            String [] valoresWhereA = new String[valoresWhere.size()];
            valoresWhereA = valoresWhere.toArray(valoresWhereA);

            String SQL = "";
            String fechaInicialS = "";
            String fechaFinalS = "";

            if(fechaInicial.getSelectedDate() != null)
            {
                fechaInicialS = fechaInicial.getSelectedDate().toString();
            }

            if(fechaFinal.getSelectedDate() != null)
            {
                fechaFinalS = fechaFinal.getSelectedDate().toString();
            }

            SQL += consul.generarSQL(combo_tipo.getValue().toString().toUpperCase(), tipo_filtro.getValue().toString(), "id_"+tipo_filtro.getValue().toString(), tipo_filtro.getValue().toString(), tablaSubConWhereA, id_tablaSubConWhereA, valoresWhereA, fechaInicialS, fechaFinalS, subConsAdd, "historico_consumo", "total_consumo", "consumo");
            System.out.print(SQL);

            ObservableList<PieChart.Data> pieChartData = con.EjecutarConsultaPieChart(SQL);   
            ((Parent) mibarchar).getStylesheets().add(drilldownCss);        
            mibarchar.setData(pieChartData);
        }
        
        if(tab_h_precio.isSelected())
        {        
            ArrayList<String> tablaSubConWhere = new ArrayList();
            ArrayList<String> id_tablaSubConWhere = new ArrayList();
            ArrayList<String> valoresWhere = new ArrayList();
            ArrayList<String> subConsAdd = new ArrayList();

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

            //Filtros Geograficos
            String subConGeo = "";
            if(combo_region.getValue().equals("Todos") && combo_ciudad.getValue().equals("Todos") && combo_altitud.getValue().equals("Todos") && combo_zona.getValue().equals("Todos"))
            {
            }
            else
            {
                subConGeo += " and id_ciudad in (select id_ciudad from ciudad where 1=1 ";
                if(!combo_region.getValue().equals("Todos"))
                {
                    subConGeo += "and id_region in (select id_region from region where region = '"+combo_region.getValue()+"') ";
                }
                if(!combo_ciudad.getValue().equals("Todos"))
                {
                    subConGeo += "and ciudad = '"+combo_ciudad.getValue()+"' ";
                }
                if(!combo_zona.getValue().equals("Todos"))
                {
                    subConGeo += "and zona = '"+combo_zona.getValue()+"' ";
                }
                if(!combo_altitud.getValue().equals("Todos"))
                {
                    subConGeo += "and altitud ";
                    if(combo_tipo_filtro_altitud.getValue().equals("Por encima de"))
                    {
                        subConGeo += "> " + combo_altitud.getValue() + " ";
                    }
                    if(combo_tipo_filtro_altitud.getValue().equals("Por debajo de"))
                    {
                        subConGeo += "< " + combo_altitud.getValue() + " ";
                    }
                    if(combo_tipo_filtro_altitud.getValue().equals("Igual a"))
                    {
                        subConGeo += "= " + combo_altitud.getValue() + " ";
                    }
                    if(combo_tipo_filtro_altitud.getValue().equals("Mayor o igual a"))
                    {
                        subConGeo += ">= " + combo_altitud.getValue() + " ";
                    }
                    if(combo_tipo_filtro_altitud.getValue().equals("Menor o igual a"))
                    {
                        subConGeo += "<= " + combo_altitud.getValue() + " ";
                    }
                }
                subConGeo += ")";
                subConsAdd.add(subConGeo);
            }

            //Filtros de Tiempo
            String subConTiempo = "";
            if(combo_franja.getValue().equals("Todos") && combo_hora.getValue().equals("Todos") && combo_dia_semana.getValue().equals("Todos"))
            {
            }
            else
            {
                subConTiempo += "and id_tiempo in (select id_tiempo from tiempo where 1=1 ";
                if(!combo_franja.getValue().equals("Todos"))
                {
                    subConTiempo += "and franja_horaria = '"+combo_franja.getValue()+"' ";
                }
                if(!combo_dia_semana.getValue().equals("Todos"))
                {
                    subConTiempo += " and dia_semana = '"+combo_dia_semana.getValue()+"'";
                }
                if(!combo_hora.getValue().equals("Todos"))
                {
                    subConTiempo += " and estrato ";
                    if(combo_mes.getValue().equals("Mayor"))
                    {
                        subConTiempo += "> " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Menor"))
                    {
                        subConTiempo += "< " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Igual"))
                    {
                        subConTiempo += "= " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Mayor o igual"))
                    {
                        subConTiempo += ">= " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Menor o igual"))
                    {
                        subConTiempo += "<= " + combo_hora.getValue();
                    } 
                }
                subConTiempo += ")";
                subConsAdd.add(subConTiempo);
            }

            String[] tablaSubConWhereA = new String[tablaSubConWhere.size()];
            tablaSubConWhereA = tablaSubConWhere.toArray(tablaSubConWhereA);

            String [] id_tablaSubConWhereA = new String[id_tablaSubConWhere.size()];
            id_tablaSubConWhereA = id_tablaSubConWhere.toArray(id_tablaSubConWhereA);

            String [] valoresWhereA = new String[valoresWhere.size()];
            valoresWhereA = valoresWhere.toArray(valoresWhereA);

            String SQL = "";
            String fechaInicialS = "";
            String fechaFinalS = "";

            if(fechaInicial.getSelectedDate() != null)
            {
                fechaInicialS = fechaInicial.getSelectedDate().toString();
            }

            if(fechaFinal.getSelectedDate() != null)
            {
                fechaFinalS = fechaFinal.getSelectedDate().toString();
            }

            SQL += consul.generarSQL(combo_tipo.getValue().toString().toUpperCase(), tipo_filtro.getValue().toString(), "id_"+tipo_filtro.getValue().toString(), tipo_filtro.getValue().toString(), tablaSubConWhereA, id_tablaSubConWhereA, valoresWhereA, fechaInicialS, fechaFinalS, subConsAdd, "historico_precio", "precio", "precio");
            System.out.print(SQL);

            ObservableList<PieChart.Data> pieChartData = con.EjecutarConsultaPieChart(SQL);   
            ((Parent) chart_Precio).getStylesheets().add(drilldownCss);        
            chart_Precio.setData(pieChartData);
        }
        
        if(tab_h_pagos.isSelected())
        {        
            ArrayList<String> tablaSubConWhere = new ArrayList();
            ArrayList<String> id_tablaSubConWhere = new ArrayList();
            ArrayList<String> valoresWhere = new ArrayList();
            ArrayList<String> subConsAdd = new ArrayList();

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

            //Filtros Demograficos
            String sqlCliente = "";
            if(combo_tipo_cliente.getValue().equals("Todos") && combo_estrato.getValue().equals("Todos") && combo_barrio.getValue().equals("Todos"))
            {
            }
            else
            {
                sqlCliente += "and id_cliente in (select id_cliente from cliente where 1 = 1";

                if(!combo_tipo_cliente.getValue().equals("Todos")){
                    sqlCliente += " and id_tipo_cliente in (select id_tipo_cliente from tipo_cliente where tipo_cliente = '"+combo_tipo_cliente.getValue()+"')";
                }

                if(!combo_estrato.getValue().equals("Todos")){
                    sqlCliente += " and estrato ";
                    if(combo_tipo_filtro_estrato.getValue().equals("Mayor"))
                    {
                        sqlCliente += "> " + combo_estrato.getValue();
                    }
                    if(combo_tipo_filtro_estrato.getValue().equals("Menor"))
                    {
                        sqlCliente += "< " + combo_estrato.getValue();
                    }
                    if(combo_tipo_filtro_estrato.getValue().equals("Igual"))
                    {
                        sqlCliente += "= " + combo_estrato.getValue();
                    }
                    if(combo_tipo_filtro_estrato.getValue().equals("Mayor o igual"))
                    {
                        sqlCliente += ">= " + combo_estrato.getValue();
                    }
                    if(combo_tipo_filtro_estrato.getValue().equals("Menor o igual"))
                    {
                        sqlCliente += "<= " + combo_estrato.getValue();
                    }         
                }

                if(!combo_barrio.getValue().equals("Todos")){
                    sqlCliente += " and barrio = '"+combo_barrio.getValue()+"')";
                }
                sqlCliente+=")";
                subConsAdd.add(sqlCliente);
            }

            //Filtros de Tiempo
            String subConTiempo = "";
            if(combo_franja.getValue().equals("Todos") && combo_hora.getValue().equals("Todos") && combo_dia_semana.getValue().equals("Todos"))
            {
            }
            else
            {
                subConTiempo += "and id_tiempo in (select id_tiempo from tiempo where 1=1 ";
                if(!combo_franja.getValue().equals("Todos"))
                {
                    subConTiempo += "and franja_horaria = '"+combo_franja.getValue()+"' ";
                }
                if(!combo_dia_semana.getValue().equals("Todos"))
                {
                    subConTiempo += " and dia_semana = '"+combo_dia_semana.getValue()+"'";
                }
                if(!combo_hora.getValue().equals("Todos"))
                {
                    subConTiempo += " and estrato ";
                    if(combo_mes.getValue().equals("Mayor"))
                    {
                        subConTiempo += "> " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Menor"))
                    {
                        subConTiempo += "< " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Igual"))
                    {
                        subConTiempo += "= " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Mayor o igual"))
                    {
                        subConTiempo += ">= " + combo_hora.getValue();
                    }
                    if(combo_mes.getValue().equals("Menor o igual"))
                    {
                        subConTiempo += "<= " + combo_hora.getValue();
                    } 
                }
                subConTiempo += ")";
                subConsAdd.add(subConTiempo);
            }

            String[] tablaSubConWhereA = new String[tablaSubConWhere.size()];
            tablaSubConWhereA = tablaSubConWhere.toArray(tablaSubConWhereA);

            String [] id_tablaSubConWhereA = new String[id_tablaSubConWhere.size()];
            id_tablaSubConWhereA = id_tablaSubConWhere.toArray(id_tablaSubConWhereA);

            String [] valoresWhereA = new String[valoresWhere.size()];
            valoresWhereA = valoresWhere.toArray(valoresWhereA);

            String SQL = "";
            String fechaInicialS = "";
            String fechaFinalS = "";

            if(fechaInicial.getSelectedDate() != null)
            {
                fechaInicialS = fechaInicial.getSelectedDate().toString();
            }

            if(fechaFinal.getSelectedDate() != null)
            {
                fechaFinalS = fechaFinal.getSelectedDate().toString();
            }

            SQL += consul.generarSQL(combo_tipo.getValue().toString().toUpperCase(), tipo_filtro.getValue().toString(), "id_"+tipo_filtro.getValue().toString(), tipo_filtro.getValue().toString(), tablaSubConWhereA, id_tablaSubConWhereA, valoresWhereA, fechaInicialS, fechaFinalS, subConsAdd, "historico_Pagos", "valor", "valor");
            System.out.print(SQL);

            ObservableList<PieChart.Data> pieChartData = con.EjecutarConsultaPieChart(SQL);   
            ((Parent) chart_Pagos).getStylesheets().add(drilldownCss);        
            chart_Pagos.setData(pieChartData);
        }
    }
    
    @FXML private void activarFiltro(){
        if(!combo_estrato.getValue().equals("Todos"))
        {
            combo_tipo_filtro_estrato.setDisable(false);
        }
        else
        {
            combo_tipo_filtro_estrato.setDisable(true);
        }
    }
    
    @FXML private void activarFiltroAltitud(){
        if(!combo_altitud.getValue().equals("Todos"))
        {
            combo_tipo_filtro_altitud.setDisable(false);
        }
        else
        {
            combo_tipo_filtro_altitud.setDisable(true);
        }
    }
    
    @FXML private void activaFiltroHora(){
        if(!combo_hora.getValue().equals("Todos"))
        {
            combo_mes.setDisable(false);
        }
        else
        {
            combo_mes.setDisable(true);
        }
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

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
    
    
     private void irMineria(ActionEvent event){
       myController.setScreen(Ejemplos.screen1ID);
    }
     @FXML
    private void irInteligencia(ActionEvent event){
       myController.setScreen(Ejemplos.screen2ID);
    }
}
