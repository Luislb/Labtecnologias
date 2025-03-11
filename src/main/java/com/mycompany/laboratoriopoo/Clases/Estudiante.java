

package com.mycompany.laboratoriopoo.Clases;
import com.mycompany.laboratoriopoo.Clases.Persona;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Interfaces.SqlPersona;
/**
 *
 * @author Estudiante_MCA
 */
public class Estudiante extends Persona implements SqlPersona{
    private double codigo, promedio;
    private boolean activo;
    private Programa programa;

    public Estudiante(double ID, String nombres, String apellidos, String email, double codigo, Programa programa, boolean activo, double promedio) {
        super(ID, nombres, apellidos, email);
        this.codigo = codigo;
        this.programa = programa;
        this.activo = activo;
        this.promedio = promedio;
    }
    
    public String toString() {
        return super.toString() + ", " + codigo + ", " + programa.getNombre() + ", " + activo + ", " + promedio;
    }

    public double getCodigo() {
        return codigo;
    }

    public void setCodigo(double codigo) {
        this.codigo = codigo;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }
    @Override
    public String getSQLInsercion() {
        return "INSERT INTO estudiantes (Nombres, Apellidos, Email, Codigo, Programa, Activo, Promedio, ID) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }
    @Override
    public String getSQLEliminacion() {
        return "DELETE FROM estudiantes WHERE ID = ?";
    }
    @Override
    public String getSQLActualizacion() {
        return "UPDATE estudiantes SET Nombres=?, Apellidos=?, Email=?, Codigo=?, Programa=?, Activo=?, Promedio=? WHERE ID=?";
    }
    @Override
    public String getSQLSeleccion(){
        return "SELECT * FROM estudiantes";
    }
    @Override
    public void setParametros(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, getNombres());      
        stmt.setString(2, getApellidos());    
        stmt.setString(3, getEmail());        
        stmt.setDouble(4, codigo);            
        stmt.setString(5, programa.getNombre()); 
        stmt.setBoolean(6, activo);           
        stmt.setDouble(7, promedio);          
        stmt.setDouble(8, getID());           
    }
    
    
}
