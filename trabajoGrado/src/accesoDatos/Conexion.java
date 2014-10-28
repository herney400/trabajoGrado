/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package accesoDatos;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 *
 * @author Lucy
 */
public class Conexion {
    
    static String url = "jdbc:postgresql://190.85.249.22/trabajodegrado";
    static String user = "postgres";
    static String password = "tesis";
    
    /**
     * 
     * @param consulta
     * @param datos
     * @param tipos
     * @return 
     */
    public ResultSet EjecutarConsulta(String consulta, Object[] datos, String[] tipos) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement(consulta);
            
            int i;
            
            if(datos != null)
            {
                int tam = datos.length;
                for(i = 1; i <= tam; i++)
                {
                    if(tipos[i].equals("string"))
                    {
                        pst.setString(i, datos[i].toString());
                    }
                    if(tipos[i].equals("int"))
                    {
                        pst.setInt(i, Integer.parseInt(datos[i].toString()));
                    }
                    if(tipos[i].equals("double"))
                    {
                        pst.setDouble(i, Double.parseDouble(datos[i].toString()));
                    }
                }  
            }
            rs = pst.executeQuery();
        } 
        catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return rs;
    }
    
    /**
     * 
     * @param consulta
     * @return 
     */
    public ArrayList<String> EjecutarConsultaComboBox(String consulta) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<String> resultado = new ArrayList<>(0);

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement(consulta);
            rs = pst.executeQuery();
            while(rs.next())
            {
                resultado.add(rs.getString(1));
            }
        } 
        catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return resultado;
    }
    
    /**
     * 
     * @param consulta
     * @return 
     */
    public ObservableList EjecutarConsultaPieChart(String consulta) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList resultado = FXCollections.observableArrayList();

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement(consulta);
            rs = pst.executeQuery();
            while(rs.next())
            {
                resultado.add(new PieChart.Data(rs.getString(2),rs.getInt(1)));
            }
        } 
        catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return resultado;
    }
    
    /**
     * 
     * @param consulta
     * @return 
     */
    public ObservableList EjecutarConsultaXYChart(String consulta) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList resultado = FXCollections.observableArrayList();

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement(consulta);
            rs = pst.executeQuery();
            while(rs.next())
            {
                resultado.add(new PieChart.Data(rs.getString(2),rs.getInt(1)));
            }
        } 
        catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return resultado;
    }
    
    /**
     * 
     * @param consulta
     * @return 
     */
    public ObservableList LlenarCommbo(String consulta){
    
        ObservableList resultado = FXCollections.observableArrayList();        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement(consulta);
            rs = pst.executeQuery();
        
            while(rs.next())
            {
               resultado.addAll(rs.getString(1));
               
            }
        } 
        catch (SQLException ex){
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Conexion.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return resultado;
    } 
}