/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JOptionPane;
import static pl_metodosimplex_consola.PL_metodoSimplex_Consola.funcion;
import static pl_metodosimplex_consola.PL_metodoSimplex_Consola.opcionFuncion;

/**
 *
 * @author Darwin
 */
public class operacion {

    DecimalFormat df = new DecimalFormat("#.00");
    int pivoteAux = 0;
    Double aux = 0.0;
    int intIndice = 0;
    boolean restriccion = false;

    Double valorP = 0.0;

    /*
    Metodo para presentar las ecuaciones en forma de ecuación
     */
    public void presentarFuncionObj() {
        String cadena = "";
        //For que recorre función objetivo para añadir a la variable cadena 
        for (int i = 0; i < funcion.get(0).variables.size(); i++) {
            if (i == 0) {
                cadena += funcion.get(0).variables.get(i) + "X1 ";
            } else {
                cadena += "| " + funcion.get(0).variables.get(i) + "X" + (i + 1) + " ";
            }
        }
        if (!String.valueOf(funcion.get(0).condicion).equals(null)) {
            int op = funcion.get(0).condicion;
            switch (op) {
                case 0:
                    cadena += "<= ";
                    break;
                case 1:
                    cadena += "= ";
                    break;
                case 2:
                    cadena += ">= ";
                    break;
            }
            cadena += funcion.get(0).valorIndependiente;
        }
        System.out.println("Función Objetiva");
        System.out.println(cadena);
        System.out.println("RESTRICCIONES");
        //For que recorre las restricciones para añadir variable cadena
        for (int i = 1; i < funcion.size(); i++) {
            cadena = "";
            for (int j = 0; j < funcion.get(i).variables.size(); j++) {
                if (j == 0) {
                    cadena += funcion.get(i).variables.get(j) + "X1 ";
                } else {
                    cadena += "| " + funcion.get(i).variables.get(j) + "X"
                            + (j + 1) + " ";
                }
            }
            //Switch utilizado para comprobar signo y añadirlo a presentación
            int op = funcion.get(i).condicion;
            switch (op) {
                case 0:
                    cadena += "<= ";
                    break;
                case 1:
                    cadena += "= ";
                    break;
                case 2:
                    cadena += ">= ";
                    break;
            }
            cadena += funcion.get(i).valorIndependiente;
            System.out.print(cadena);
            System.out.println("");
        }
    }

    /*
    Método implementado para presentar función en tablas
     */
    int con = 1;

    public void presentarFuncionTableu() {
        System.out.println("");
        System.out.println("Iteración " + con);
        String cadena = "";
        for (int i = 0; i < funcion.get(0).variables.size(); i++) {
            cadena += "  \tX" + (i + 1);
        }
        cadena += "\t" + "V.Ind";
        System.out.println(cadena);
        for (int i = 1; i < funcion.size(); i++) {
            for (int k = 0; k < funcion.get(i).variables.size(); k++) {
                if (funcion.get(i).variables.get(k) == 1) {
                    cadena = "X" + (k + 1);
                    break;
                }
            }
            for (int j = 0; j < funcion.get(i).variables.size(); j++) {
                if (j == 0) {
                    if (funcion.get(i).variables.get(j) < 0) {
                        BigDecimal bigDecimal = new BigDecimal(funcion.get(i).variables.get(j)).setScale(3, RoundingMode.UP);
                        cadena += "\t" + bigDecimal.doubleValue();
                    } else {
                        BigDecimal bigDecimal = new BigDecimal(funcion.get(i).variables.get(j)).setScale(4, RoundingMode.UP);
                        cadena += "\t" + bigDecimal.doubleValue();
                    }
                } else {
                    if (funcion.get(i).variables.get(j) < 0) {
                        BigDecimal bigDecimal = new BigDecimal(funcion.get(i).variables.get(j)).setScale(3, RoundingMode.UP);
                        cadena += "\t" + bigDecimal.doubleValue();
                    } else {
                        BigDecimal bigDecimal = new BigDecimal(funcion.get(i).variables.get(j)).setScale(4, RoundingMode.UP);
                        cadena += "\t" + bigDecimal.doubleValue();
                    }
                }
            }
            if (funcion.get(i).valorIndependiente < 0) {
                BigDecimal bigDecimal = new BigDecimal(funcion.get(i).valorIndependiente).setScale(3, RoundingMode.UP);
                cadena += "\t " + bigDecimal.doubleValue();
            } else {
                BigDecimal bigDecimal = new BigDecimal(funcion.get(i).valorIndependiente).setScale(4, RoundingMode.UP);
                cadena += "\t " + bigDecimal.doubleValue();
            }
            System.out.print(cadena);
            System.out.println("");
        }
        cadena = " Z";
        for (int i = 0; i < funcion.get(0).variables.size(); i++) {
            if (funcion.get(0).variables.get(i) < 0) {
                BigDecimal bigDecimal = new BigDecimal(funcion.get(0).variables.get(i)).setScale(3, RoundingMode.UP);
                cadena += "\t" + bigDecimal.doubleValue();
            } else {
                BigDecimal bigDecimal = new BigDecimal(funcion.get(0).variables.get(i)).setScale(4, RoundingMode.UP);
                cadena += "\t" + bigDecimal.doubleValue();
            }
        }
        if (funcion.get(0).valorIndependiente < 0) {
            BigDecimal bigDecimal = new BigDecimal(funcion.get(0).valorIndependiente).setScale(3, RoundingMode.UP);
            cadena += "\t " + bigDecimal.doubleValue();
        } else {
            BigDecimal bigDecimal = new BigDecimal(funcion.get(0).valorIndependiente).setScale(4, RoundingMode.UP);
            cadena += "\t " + bigDecimal.doubleValue();
        }
        System.out.println("");
        cadena += "\t" + funcion.get(0).variables;
        System.out.println(cadena);
        System.out.println("");
        con++;
    }

    /*
    Metodo implementado para estandarizar la función
     */
    public void estandarizarFuncion() {
        if (!opcionFuncion) {
            System.out.println("");
            System.out.println("Funión Maximizada");
            //For utilizado para multiplicar por (-1) las variables
            for (int i = 0; i < funcion.get(0).variables.size(); i++) {
                funcion.get(0).variables.set(i, funcion.get(0).variables.get(i) * -1);
            }
            funcion.get(0).condicion = 1;
            funcion.get(0).valorIndependiente = funcion.get(0).valorIndependiente * -1;
        }
        //For utilizado para recorrer restricciones
        for (int i = 1; i < funcion.size(); i++) {
            int intOp = funcion.get(i).condicion;
            switch (intOp) {
                case 0:
                    funcion.get(i).condicion = 1;
                    //se agregan varibales de holgura con +1
                    funcion.get(i).variables.add(1.0);
                    for (int j = 0; j < funcion.size(); j++) {
                        if (j != i) {
                            funcion.get(j).variables.add(0.0);
                        }
                    }
                    break;
                case 2:
                    funcion.get(i).condicion = 1;
                    //se agregan varibales de holgura con -1
                    funcion.get(i).variables.add(-1.0);
                    for (int j = 0; j < funcion.size(); j++) {
                        if (j != i) {
                            funcion.get(j).variables.add(0.0);
                        }
                    }
                    break;
            }
        }
        presentarFuncionObj();
    }

    /*
    Metodo implementado para añadir variables
    de holgura en función Objetiva
     */
    public void metodoTableu() {
        if (!opcionFuncion) {
            for (int i = 1; i < funcion.size(); i++) {
                funcion.get(0).variables.add(0.0);
            }
        }
        presentarFuncionTableu();
    }

    /*
    Metodo implementado para comprobar si el ejercicio esta terminado
     */
    public boolean comprobar() {
        for (int i = 0; i < funcion.get(0).variables.size(); i++) {
            //si encuentra un valor negativo sigue resolviendo
            if (funcion.get(0).variables.get(i) < 0) {
                return true;
            }
        }
        return false;
    }

    /*
    Metodo implementado para resolver el problema
     */
    public void pivoteo() {
        //While para resolver el problema mientras metodo comprobar sea true
        while (comprobar()) {
            operacionPivoteo();
            if (restriccion) {
                operacionMultiplicar();
                presentarFuncionTableu();
            } else {
                System.out.println("No existe solución");
                JOptionPane.showMessageDialog(null, "No existe solución");
                break;
            }
        }
        JOptionPane.showMessageDialog(null, "Ejercicio Terminado");
    }

    /*
    Metodo implementado para resolver el problema 
     */
    public void operacionMultiplicar() {
        //for que recorre todas las restricciones y F.o
        Double doPivoteo = 0.0;
        Double valorDiv = 0.0;
        ArrayList<Double> funcionPivote = new ArrayList();
        Double indipendiente = 0.0;
        for (int i = 0; i < funcion.get(intIndice).variables.size(); i++) {
            funcionPivote.add(funcion.get(intIndice).variables.get(i));
        }
        indipendiente = funcion.get(intIndice).valorIndependiente;
        for (int i = 0; i < funcion.size(); i++) {
            if (funcion.get(i).variables.get(pivoteAux) != 0) {
                if (i != intIndice) {
                    for (int j = 0; j < funcion.get(i).variables.size(); j++) {
                        if (j == 0) {
                            valorDiv = (funcion.get(i).variables.get(pivoteAux)
                                    / funcionPivote.get(pivoteAux)) * -1;
                        }
                        Double valorS = funcion.get(i).variables.get(j);
                        funcion.get(i).variables.set(j, (funcionPivote.get(j)
                                * valorDiv) + valorS);
                    }
                    Double valorI = funcion.get(i).valorIndependiente;
                    funcion.get(i).valorIndependiente = (indipendiente
                            * valorDiv) + valorI;
                } else {
                    if (funcion.get(intIndice).variables.get(pivoteAux) != 1) {
                        doPivoteo = funcion.get(intIndice).variables.get(pivoteAux);
                        //for donde se recorre restricción de pivote y se divide para
                        //indice del valor la razón
                        for (int k = 0; k < funcion.get(intIndice).variables.size(); k++) {
                            funcion.get(intIndice).variables.set(k,
                                    funcion.get(intIndice).variables.get(k) / doPivoteo);
                        }
                        funcion.get(intIndice).valorIndependiente = funcion.get(intIndice).valorIndependiente
                                / doPivoteo;
                    }
                }
            }
        }
    }

    public void operacionPivoteo() {
        Double valorMenor = 0.0;
        Double razon = 0d;
        restriccion = false;
        intIndice = 0;
        pivoteAux = 0;
        valorP = 0.0;
        boolean terminarPivote = false;
        int[] pivote = new int[funcion.get(0).variables.size()];
        //Se recorre F.O para encontrar valor mas negativo
        for (int i = 0; i < funcion.get(0).variables.size(); i++) {
            if (funcion.get(0).variables.get(i) < valorMenor) {
                valorMenor = funcion.get(0).variables.get(i);
                pivote[i] = i;
            } else {
                if (Objects.equals(funcion.get(0).variables.get(i), valorMenor)) {
                    pivote[i] = i;
                } else {
                    //Se añade un valor cualquiera
                    pivote[i] = 800;
                }
            }
        }
        //For para recorrer arreglo pivote 
        for (int i = 0; i < pivote.length; i++) {
            if (pivote[i] != 800) {
                for (int k = 1; k < funcion.size(); k++) {
                    if (funcion.get(k).variables.get(pivote[i]) > 0) {
                        terminarPivote = true;
                        pivoteAux = pivote[i];
                    }
                }
            }
        }
        aux = Double.MAX_VALUE;
        if (terminarPivote) {
            System.out.println("El Pivote es en: " + valorMenor);
            //for para encontrar razón e indice de razón
            for (int i = 1; i < funcion.size(); i++) {
                if (funcion.get(i).variables.get(pivoteAux) > 0) {
                    razon = funcion.get(i).valorIndependiente
                            / funcion.get(i).variables.get(pivoteAux);
                    if (razon < aux) {
                        aux = razon;
                        intIndice = i;
                        valorP = funcion.get(i).variables.get(pivoteAux);
                        restriccion = true;
                    }
                }
            }
            System.out.println("La razón se da en: " + valorP);
        }
    }
}
