/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package accesoDatos;

import java.util.Date;

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
     * @return 
     */
    public String generarSQL(String tipoConsulta, String tablaSubCon, String id_tablaSubCon, String nombreCampo, String [] tablaSubConWhere, String [] id_tablaSubConWhere, String [] valoresWhere)
    {
        String SQL = "";

        SQL += generarSelect(tipoConsulta, tablaSubCon, id_tablaSubCon, nombreCampo);
        SQL += " from historico_consumo ";
        SQL += generarWhere(tablaSubConWhere, id_tablaSubConWhere, valoresWhere);
        SQL += " group by "+ nombreCampo + "\n";
        SQL += " order by consumo";
        
        return SQL;
    }
    
    /**
     * 
     * @param tipoConsulta el tipo de consulta determina si se va a calcular un promedio o un consolidado
     * @param tablaSubCon este campo identifica que filtro se va a aplicar a la consulta
     * @param id_tablaSubCon 
     * @param nombreCampo
     * @return 
     */
    public String generarSelect (String tipoConsulta, String tablaSubCon, String id_tablaSubCon, String nombreCampo)
    {
        String select = "";
        
        select += "select ";
        switch (tipoConsulta) {
            case "CONSOLIDADO":
                select += "sum(cast(total_consumo as numeric)) as consumo, ";
                break;
            case "PROMEDIO":
                select += "avg(cast(total_consumo as numeric)) as consumo, ";
                break;
        }
        
        select += "(select "+nombreCampo+" from "+tablaSubCon+" where "+tablaSubCon+"."+id_tablaSubCon+" = historico_consumo."+id_tablaSubCon+") as "+tablaSubCon+"\n";
          
        return select;
    }
    
    /**
     * 
     * @param tablaSubCon
     * @param id_tablaSubCon
     * @param valores
     * @return 
     */
    public String generarWhere(String [] tablaSubCon, String [] id_tablaSubCon, String [] valores)
    {
        String where = "";
        
        where += "where 1 = 1\n";
        
        int i;
        for(i=0; i<tablaSubCon.length; i++){
            where += "and " + id_tablaSubCon[i] + " in (select "+ id_tablaSubCon[i] +" from "+ tablaSubCon[i] +" where "+ tablaSubCon[i]+ "."+ id_tablaSubCon[i] +" = "+valores[i]+") \n"; 
        }      
        return where;
    }
}
