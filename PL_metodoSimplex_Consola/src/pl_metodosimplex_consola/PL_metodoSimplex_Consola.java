/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl_metodosimplex_consola;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import modelo.atributoOperacion;
import modelo.operacion;

/**
 *
 * @author Darwin
 */
public class PL_metodoSimplex_Consola {

    //Objetos Utilizados para llenar Variables de clase atributoFunción
    public static atributoOperacion funcionObj = new atributoOperacion();
    public static atributoOperacion restriccion = new atributoOperacion();

    //ArrayList de la clase atributosFunción
    public static ArrayList<atributoOperacion> funcion = new ArrayList<>();

    public static void main(String[] args) {

        //Variables para utilizar en modelo
        String cad = " UNIVERSIDAD NACIONAL DE LOJA \n"
                + "          INGENIERÍA EN SISTEMAS \n"
                + "               MÉTODO SIMPLEX \n"
                + "Ingresar cantidad variables: ";
        //Variable utilizada para llamar metodos de Operaiòn
        operacion opera = new operacion();
        // TODO code application logic here
        Scanner reload = new Scanner(System.in);

        int intVar = Integer.parseInt(JOptionPane.showInputDialog(null, cad));
        JOptionPane.showMessageDialog(null, "Ingrese Valores de Función Objetiva");
        //For para el ingreso de Función Objetivo
        for (int i = 0; i < intVar; i++) {
            Double doValor = Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Ingrese valor Variable X" + (i + 1) + ": "));
            //se llenan los valores de las variables
            funcionObj.variables.add(doValor);
        }
        funcion.add(funcionObj);
        //Ingreso de RESTRICCIONES
        int intRest = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Ingrese cantidad de Restricciones: "));
        JOptionPane.showMessageDialog(null, "Ingrese Valores de Restricciones: ");
        for (int i = 0; i < intRest; i++) {
            restriccion = new atributoOperacion();
            JOptionPane.showMessageDialog(null, "Restricción: " + (i + 1));
            //Se ingresan las variables de cada restricción
            for (int j = 0; j < intVar; j++) {
                Double doValor = Double.parseDouble(JOptionPane.showInputDialog(null,
                        "Ingrese valor Variable X" + (i + 1) + ": "));
                restriccion.variables.add(doValor);
            }
            //Se selecciona la condición de la función
            int intCon = JOptionPane.showOptionDialog(null, "Seleccione el operador",
                            "Ingresar funcion", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, 
                            new Object[]{"≤", "=", "≥"}, "≥");
            
            restriccion.condicion = intCon;
            //se ingresa valor de termino independiente
            Double doValor = Double.parseDouble(JOptionPane.showInputDialog(null,
                        "Ingrese termino independiente: "));
            restriccion.valorIndependiente = doValor;
            funcion.add(restriccion);
        }
        
        int intMaxmin = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Ingrese Tipo de Operación \n"
                        + "1. Maximizar \n"
                        + "2. Minimizar "));
        if (intMaxmin == 1) {
            opera.presentarFuncionObj();
            System.out.println("");
            System.out.println("Funión Maximizada");
            System.out.println("");
            opera.estandarizarFuncion();
            opera.pivoteo();
        } else {
            opera.presentarFuncionObj();
            opera.pivoteo();
        }

    }

}
