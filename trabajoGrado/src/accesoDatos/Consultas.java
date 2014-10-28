/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package accesoDatos;

import java.util.ArrayList;

/**
 *
 * @author Luis Carlos
 */
public class Consultas {
    
    /**
     * 
     * @param tipoConsulta
     * @param tablaSubCon
     * @param id_tablaSubCon
     * @param nombreCampo
     * @param tablaSubConWhere
     * @param id_tablaSubConWhere
     * @param valoresWhere
     * @param fechaInicial
     * @param fechaFinal
     * @param whereAux
     * @param tabla
     * @param campoTabla
     * @param renombre
     * @return 
     */
    public String generarSQL(String tipoConsulta, String tablaSubCon, String id_tablaSubCon, String nombreCampo,
            String [] tablaSubConWhere, String [] id_tablaSubConWhere, String [] valoresWhere, String fechaInicial,
            String fechaFinal, ArrayList whereAux, String tabla, String campoTabla, String renombre){
        String SQL = "";

        SQL += generarSelect(tipoConsulta, tablaSubCon, id_tablaSubCon, nombreCampo, campoTabla, tabla, renombre);
        SQL += "from " + tabla + " ";
        SQL += generarWhere(tablaSubConWhere, id_tablaSubConWhere, valoresWhere);
        
        for (Object whereAux1 : whereAux) {
            SQL += whereAux1 + "\n";
        }
        SQL += "and " + campoTabla + " > 0" + "\n";
        SQL += generarSubConsultaFecha(fechaInicial, fechaFinal);
        SQL += "group by "+ nombreCampo + "\n";
        SQL += "order by "+renombre +  " desc \n";
        SQL += "limit 20;";
        
        return SQL;
    }
    
    /**
     * 
     * @param tipoConsulta el tipo de consulta determina si se va a calcular un promedio o un consolidado
     * @param tablaSubCon este campo identifica que filtro se va a aplicar a la consulta
     * @param id_tablaSubCon 
     * @param nombreCampo
     * @param campoTabla 
     * @param tabla
     * @param renombre
     * @return 
     */
    public String generarSelect (String tipoConsulta, String tablaSubCon, String id_tablaSubCon, 
            String nombreCampo, String campoTabla, String tabla, String renombre){
        String select = "";
        
        select += "select ";
        switch (tipoConsulta) {
            case "CONSOLIDADO":
                select += "sum(cast(" + campoTabla + " as numeric)) as "+renombre+", ";
                break;
            case "PROMEDIO":
                select += "avg(cast(" + campoTabla + " as numeric)) as "+renombre+", ";
                break;
        }
        
        select += "(select "+nombreCampo+" from "+tablaSubCon+" where "+tablaSubCon+"."+id_tablaSubCon+" = " + tabla + "."+id_tablaSubCon+") as "+tablaSubCon+"\n";
          
        return select;
    }
    
    /**
     * 
     * @param tablaSubCon
     * @param id_tablaSubCon
     * @param valores
     * @return 
     */
    public String generarWhere(String [] tablaSubCon, String [] id_tablaSubCon, String [] valores){
        String where = "";
        
        where += "\nwhere 1 = 1\n";
        
        int i;
        for(i=0; i<tablaSubCon.length; i++){
            where += "and " + id_tablaSubCon[i] + " in (select "+ id_tablaSubCon[i] +" from "+ tablaSubCon[i] +" where "+ tablaSubCon[i]+ "."+ id_tablaSubCon[i] +" = "+valores[i]+") \n"; 
        }      
        return where;
    }
    
    /**
     * 
     * @param fechaInicial
     * @param fechaFinal
     * @return 
     */
    public String generarSubConsultaFecha(String fechaInicial, String fechaFinal){
        String subCon="";
        if(fechaInicial.equals("") && !fechaFinal.equals(""))
        {
            subCon +=" and id_fecha in (select id_fecha from fecha where fecha_c < '"+ fechaFinal +"')";
        }
        else if(fechaFinal.equals("") && !fechaInicial.equals(""))
        {
            subCon +=" and id_fecha in (select id_fecha from fecha where fecha_c > '"+ fechaInicial +"')";
        }
        else if(!fechaFinal.equals("") && !fechaInicial.equals(""))
        {
            subCon += " and id_fecha in (select id_fecha from fecha where fecha_c > '"+ fechaInicial +"' and fecha_c < '"+ fechaFinal +"')";
        }
        else
        {
            subCon = "";
        }
        return subCon;
    }
}
