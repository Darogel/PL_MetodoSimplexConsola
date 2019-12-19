/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.DecimalFormat;
import java.util.Objects;
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
    public void presentarFuncionTableu() {
        System.out.println("");
        System.out.println("Tablas del método simplex ");
        String cadena = "";
        for (int i = 0; i < funcion.size(); i++) {
            cadena = "";
            //For que recorre el objeto de las ecuaciones y añade a variable cadena
            for (int j = 0; j < funcion.get(i).variables.size(); j++) {
                if (j == 0) {
                    //cadena += df.format(funcion.get(i).variables.get(j));
                    cadena += funcion.get(i).variables.get(j);
                } else {
                    //cadena += "|\t " + df.format(funcion.get(i).variables.get(j)) + " ";
                    cadena += "|\t " + funcion.get(i).variables.get(j) + " ";
                }
            }
            cadena += "|\t ";
            //cadena += df.format(funcion.get(i).valorIndependiente);
            cadena += funcion.get(i).valorIndependiente;
            System.out.print(cadena);
            System.out.println("");
        }
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
            funcion.get(0).valorIndependiente = 0.0;
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
        Double doPivoteo = 0.0;
        //While para resolver el problema mientras metodo comprobar sea true
        while (comprobar()) {
            operacionPivoteo();
            if (restriccion) {
                if (funcion.get(intIndice).variables.get(pivoteAux) != 1) {
                    doPivoteo = funcion.get(intIndice).variables.get(pivoteAux);
                    //for donde se recorre restricción de pivote y se divide para
                    //indice del valor la razón
                    for (int i = 0; i < funcion.get(intIndice).variables.size(); i++) {
                        funcion.get(intIndice).variables.set(i,
                                funcion.get(intIndice).variables.get(i) / doPivoteo);
                    }
                    funcion.get(intIndice).valorIndependiente = funcion.get(intIndice).valorIndependiente
                            / doPivoteo;
                }
                //For que recorre las restricciones
                for (int i = 0; i < funcion.size(); i++) {
                    Double doPivot = funcion.get(i).variables.get(pivoteAux);
                    if (i != intIndice && doPivot != 0) {
                        //For utilizado para recorrer demas ecuaciones y dejarlas en 1
                        for (int j = 0; j < funcion.get(i).variables.size(); j++) {
                            funcion.get(i).variables.set(j, funcion.get(i).variables.get(j)
                                    / Math.abs(doPivot));
//                        System.out.println("Funcion Dividida" + funcion.get(i).variables.get(j)
//                                + "Divisor" + doPivot);
                        }
                        funcion.get(i).pivote = Math.abs(doPivot);
//                    System.out.println("Valor Indi" + funcion.get(i).valorIndependiente
//                            + ":" + Math.abs(doPivot));
                        funcion.get(i).valorIndependiente = funcion.get(i).valorIndependiente
                                / Math.abs(doPivot);
                    }

                }
                operacionMultiplicar();
                presentarFuncionTableu();
            }

        }
        System.out.println("Ejercicio Terminado");
    }

    /*
    Metodo implementado para resolver el problema 
     */
    public void operacionMultiplicar() {
        //for que recorre todas las restricciones y F.o
        for (int i = 0; i < funcion.size(); i++) {
            if (funcion.get(i).variables.get(pivoteAux) != 0) {
                if (funcion.get(i).variables.get(pivoteAux) > 0) {
                    if (i != intIndice) {
                        //For para recorrer las variables de cada restriccion
                        //Se multiplica por -1 la variable que contiene columna pivote
                        //Se suma con la variable que se desea eliminar
                        //Se multiplica por el numero que se dividio para no alterar
                        for (int j = 0; j < funcion.get(i).variables.size(); j++) {
                            funcion.get(i).variables.set(j, funcion.get(i).variables.get(j)
                                    + (funcion.get(intIndice).variables.get(j) * -1));

                            funcion.get(i).variables.set(j, funcion.get(i).variables.get(j)
                                    * Math.abs(funcion.get(i).pivote));
                        }
                        funcion.get(i).valorIndependiente = funcion.get(i).valorIndependiente
                                + (funcion.get(intIndice).valorIndependiente * -1);
                        funcion.get(i).valorIndependiente = funcion.get(i).valorIndependiente
                                * Math.abs(funcion.get(i).pivote);
                    }
                } else {
                    if (i != intIndice) {
                        //For para recorrer las variables de cada restriccion
                        //Se suma con la variable que se desea eliminar
                        //Se multiplica por el numero que se dividio para no alterar
                        for (int j = 0; j < funcion.get(i).variables.size(); j++) {
                            funcion.get(i).variables.set(j, funcion.get(i).variables.get(j)
                                    + funcion.get(intIndice).variables.get(j));
                            funcion.get(i).variables.set(j, funcion.get(i).variables.get(j)
                                    * Math.abs(funcion.get(i).pivote));
                        }
                        funcion.get(i).valorIndependiente = funcion.get(i).valorIndependiente
                                + funcion.get(intIndice).valorIndependiente;
                        funcion.get(i).valorIndependiente = funcion.get(i).valorIndependiente
                                * Math.abs(funcion.get(i).pivote);
                    }
                }
            }
        }
    }

    public void operacionPivoteo() {
        Double valorMenor = 0.0;
        Double razon = 0d;
        int[] pivote = new int[funcion.get(0).variables.size()];
        boolean terminarPivote = false;
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
        System.out.println("El Pivote es en: " + valorMenor);
        //For para recorrer arreglo pivote 
        for(int i = 0; i < pivote.length; i++) {
            if (pivote[i] != 800) {
                for (int k = 1; k < funcion.size(); k++) {
                    if (funcion.get(k).variables.get(pivote[i]) > 0) {
                        terminarPivote = true;
                        pivoteAux = pivote[i];
                    }
                }
            }

        }
        System.out.println("El indice del Pivote: " + pivoteAux);
        aux = Double.MAX_VALUE;
        if (terminarPivote) {
            //for para encontrar razón e indice de razón
            for (int i = 1; i < funcion.size(); i++) {
                if (funcion.get(i).variables.get(pivoteAux) > 0) {
                    razon = funcion.get(i).valorIndependiente
                            / funcion.get(i).variables.get(pivoteAux);
                    if (razon < aux) {
                        aux = razon;
                        intIndice = i;
                        restriccion = true;
                    }
                }

            }
            System.out.println("La razón es: " + razon);
            System.out.println("El indice de la razón es: " + intIndice);
        }
    }
}
