/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejemplos;

import java.io.IOException;
import javafx.fxml.FXML;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.norm.DecimalScaleNormalizer;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author N550J
 */
public class CrearRed {
@FXML    double t_error,t_tasa_aprendizaje;
@FXML    int t_max_iteraciones,t_num_capas,t_num_neu_entrada,t_num_neu_ocul;
@FXML    double momentun;
@FXML   String funcionTransferencia;
    DecimalScaleNormalizer normalizar=new DecimalScaleNormalizer();
    double erroTotal;
    public CrearRed( ) { }
    
    public void CrearRedMomentum(double error, double tasaAprendizaje, int maxIteraciones,
                                    int numCapas, int neuEntrada, int neuOculta, double momentum ){
        t_error=error;
        t_tasa_aprendizaje=tasaAprendizaje;
        t_max_iteraciones= maxIteraciones;
        t_num_neu_entrada=neuEntrada;
        t_num_neu_ocul=neuOculta;
        momentun =momentum;        
       
     }
    
    @FXML public void crearRedconMomentun(double error,int maxIteraciones, double tazaAprendi, int numCapas,
                                          int neur_entrada,int neu_oculta,int neu_salida, double momentun,
                                          String funcionTransferencia, String regla_aprendizaje, String file, boolean  biass){ 
         String funcion_trans =   funcionTransferencia;
         String regla_aprendiz = regla_aprendizaje;
         MultiLayerPerceptron redNeu;
         String archivoEnte =file ;
         DataSet conjEntre=null;          
         try {
             conjEntre=TrainingSetImport.importFromFile(archivoEnte, neur_entrada, neu_salida, ",");
         } catch (IOException ex) {
            System.out.println("Archivo no encontrado");
         } catch (NumberFormatException ex) {
            System.out.println("Error leyendo archivo o el formato esta dañado!");
         }         
         
         if(biass) {
             BiasNeuron bias=new BiasNeuron();
             if(funcion_trans.equals("Tangencial")){
                   redNeu=new MultiLayerPerceptron(TransferFunctionType.TANH, neur_entrada,neu_oculta,neu_salida);
                    if(regla_aprendiz.equals("BackPropagation")){
                       BackPropagation reglaApre=(BackPropagation)redNeu.getLearningRule();
                        reglaApre.setLearningRate(tazaAprendi);
                        reglaApre.setMaxError(error);
                        reglaApre.setMaxIterations(maxIteraciones);
                        normalizar.normalize(conjEntre);
                        redNeu.learn(conjEntre);
                        erroTotal=reglaApre.getTotalNetworkError();
                        System.out.println("wwwww");
                        /*colocar el maximo y el minimo error*/
                    }if(regla_aprendiz.equals("BackPropagation con Momentum")){                        
                        MomentumBackpropagation reglaAprendisaje=(MomentumBackpropagation)redNeu.getLearningRule();
                        reglaAprendisaje.setLearningRate(tazaAprendi);
                        reglaAprendisaje.setMomentum(momentun);
                        reglaAprendisaje.setMaxError(error);
                        reglaAprendisaje.setMaxIterations(maxIteraciones);                       
                        
                        normalizar.normalize(conjEntre);                        
                        redNeu.learn(conjEntre);
                        erroTotal=  reglaAprendisaje.getTotalNetworkError();
                        int erroMin=redNeu.getLearningRule().getMinErrorChangeIterationsCount();/*posible error de cada neurona*/
                        System.out.println("Error"+erroTotal);
                    }  
             }else if(funcion_trans.equals("Sigmoidal")){
                 redNeu=new MultiLayerPerceptron(TransferFunctionType.SIGMOID, neur_entrada,neu_oculta,neu_salida);
                   if(regla_aprendiz.equals("BackPropagation")){
                       BackPropagation reglaApre=(BackPropagation)redNeu.getLearningRule();
                        reglaApre.setLearningRate(tazaAprendi);
                        reglaApre.setMaxError(error);
                        reglaApre.setMaxIterations(maxIteraciones);
                        redNeu.learn(conjEntre);
                    }if(regla_aprendiz.equals("BackPropagation con Momentum")){                        
                        MomentumBackpropagation reglaAprendisaje=(MomentumBackpropagation)redNeu.getLearningRule();
                        reglaAprendisaje.setLearningRate(tazaAprendi);
                        reglaAprendisaje.setMomentum(momentun);
                        reglaAprendisaje.setMaxError(error);
                        reglaAprendisaje.setMaxIterations(maxIteraciones);
                        redNeu.learn(conjEntre);
                    }  
             }              
         }else{ /*codigo sin bias para crear red neuronal */
             if(funcion_trans.equals("Tangencial")){
                       redNeu=new MultiLayerPerceptron(TransferFunctionType.TANH, neur_entrada,neu_oculta,neu_salida);
                    if(regla_aprendiz.equals("BackPropagation")){
                       BackPropagation reglaApre=(BackPropagation)redNeu.getLearningRule();
                        reglaApre.setLearningRate(tazaAprendi);
                        reglaApre.setMaxError(error);
                        reglaApre.setMaxIterations(maxIteraciones);
                        redNeu.learn(conjEntre);
                        
                    }if(regla_aprendiz.equals("BackPropagation con Momentum")){                        
                        MomentumBackpropagation reglaAprendisaje=(MomentumBackpropagation)redNeu.getLearningRule();
                        reglaAprendisaje.setLearningRate(tazaAprendi);
                        reglaAprendisaje.setMomentum(momentun);
                        reglaAprendisaje.setMaxError(error);
                        reglaAprendisaje.setMaxIterations(maxIteraciones);                       
                        redNeu.learn(conjEntre);
                        redNeu.randomizeWeights();                       
                    } 
                  
             }else if(funcion_trans.equals("Sigmoidal")){
                 redNeu=new MultiLayerPerceptron(TransferFunctionType.SIGMOID, neur_entrada,neu_oculta,neu_salida);
                   if(regla_aprendiz.equals("BackPropagation")){
                       BackPropagation reglaApre=(BackPropagation)redNeu.getLearningRule();
                        reglaApre.setLearningRate(tazaAprendi);
                        reglaApre.setMaxError(error);
                        reglaApre.setMaxIterations(maxIteraciones);
                        redNeu.learn(conjEntre);
                    }if(regla_aprendiz.equals("BackPropagation con Momentum")){                        
                        MomentumBackpropagation reglaAprendisaje=(MomentumBackpropagation)redNeu.getLearningRule();
                        reglaAprendisaje.setLearningRate(tazaAprendi);
                        reglaAprendisaje.setMomentum(momentun);
                        reglaAprendisaje.setMaxError(error);
                        reglaAprendisaje.setMaxIterations(maxIteraciones);
                        redNeu.learn(conjEntre);
                    }  
             } 
             
         }
        
    }
    
   
    
    
    
    
    
    
}
