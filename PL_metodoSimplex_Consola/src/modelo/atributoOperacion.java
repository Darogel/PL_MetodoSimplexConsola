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

    public ArrayList<Double> variables = new ArrayList();
    public int condicion = 0; // [0 = menor_igual, 1 = igual, 2 = mayor_igual]
    public Double valorIndependiente = 0d;
}
