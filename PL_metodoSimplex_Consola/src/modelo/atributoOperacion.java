/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Darwin
 */
public class atributoOperacion {
    public ArrayList<Double> variables = new ArrayList(); // numero de incognitas de la ecuacion
    public int condicion = 0; // [-1 = menor_igual, 0 = igual, 1 = mayor_igual]
    public Double valorIndependiente = 0d; // parte derecha de la ecuacion (despues del igual)
    public int pivote = 0; // almacena el indice del pivote actual
}
