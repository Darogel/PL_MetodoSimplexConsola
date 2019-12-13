/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static pl_metodosimplex_consola.PL_metodoSimplex_Consola.funcion;

/**
 *
 * @author Darwin
 */
public class operacion {

    /*
    Metodo imlementado para la presentación de:
    Función Objetiva
    Restricciones
     */
    public void presentarFuncionObj() {
        String cadena = "";
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
        for (int i = 1; i < funcion.size(); i++) { // imprimir restricciones
            cadena = "";
            for (int j = 0; j < funcion.get(i).variables.size(); j++) {
                if (j == 0) {
                    cadena += funcion.get(i).variables.get(j) + "X1 ";
                } else {
                    cadena += "| " + funcion.get(i).variables.get(j) + "X" + (j + 1) + " ";
                }
            }
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
    Metodo implementado para estandarizar la función
     */
    public void estandarizarFuncion() {
        //For utilizado para multiplicar por (-1) las variables
        for (int i = 0; i < funcion.get(0).variables.size(); i++) {
            funcion.get(0).variables.set(i, funcion.get(0).variables.get(i) * -1);
        }
        funcion.get(0).condicion = 1;
        funcion.get(0).valorIndependiente = 0.0;
        //For utilizado para recorrer
        for (int i = 1; i < funcion.size(); i++) {
            int intOp = funcion.get(i).condicion;
            switch (intOp) {
                case 0:
                    funcion.get(i).condicion = 1;
                    // agrega variable de holgura
                    funcion.get(i).variables.add(1.0);
                    //funcion.get(i).pivote = funcion.get(i).variables.size() - 1; // obtiene la incognita creada
                    for (int j = 0; j < funcion.size(); j++) { // agregar incognita en las demas ecuaciones
                        if (j != i && j > 0) { // que no sea la misma ecuacion...
                            funcion.get(j).variables.add(0.0);
                        }
                    }
                    break;
                case 2:
                    funcion.get(i).condicion = 1; // cambia la condicion (De menor_igual a igual)
                    funcion.get(i).variables.add(-1.0); // agrega una incognita auxiliar
                    for (int j = 0; j < funcion.size(); j++) { // agregar incognita en las demas ecuaciones
                        if (j != i && j > 0) { // que no sea la misma ecuacion...
                            funcion.get(j).variables.add(0.0);
                        }
                    }
                    break;
            }
        }
        presentarFuncionObj();
    }

    public boolean comprobar() {
        for (int i = 0; i < funcion.get(0).variables.size(); i++) {
            if (funcion.get(0).variables.get(i) > 0) {
                System.out.println("Ejercicio Terminado");
            } else {
                return true;
            }
        }
        return false;
    }

    public void pivoteo() {
        while (comprobar()) {
            break;

        }
    }
}
