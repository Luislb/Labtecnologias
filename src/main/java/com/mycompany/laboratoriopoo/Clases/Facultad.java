
package com.mycompany.laboratoriopoo.Clases;

/**
 *
 * @author Estudiante_MCA
 */
public class Facultad {
    private double ID;
    private String nombre;
    private Persona decano;
    
    public Facultad(double ID, String nombre, Persona decano) {
        this.ID = ID;
        this.nombre = nombre;
        this.decano = decano;
    }
    
    public void asignarDecano(Persona decano) {
        this.decano = decano;
    }
    
    public String toString() {
        return ID + ", " + nombre + ", Decano: " + (decano != null ? decano.toString() : "No asignado");
    }
}
