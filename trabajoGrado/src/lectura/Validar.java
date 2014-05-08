/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lectura;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author N550J
 */
public class Validar {

    public Validar() {
    }
    public boolean validarEntero(TextField txf){        
        boolean salid=false;
        if((txf.getText().matches("[0-9--]*"))){
                  salid=true;                    
        }else{
          txf.setText("");
          txf.requestFocus(); 
        }
        return salid;
    }
     public boolean validaDouble( TextField txf){           
      boolean salida=false;
         
         if((txf.getText().matches("[0-9-.-]*"))){
              salida=true;                        
          }else{
            txf.setText("");
            txf.requestFocus();  
         }
         return salida;
    }
    
}
