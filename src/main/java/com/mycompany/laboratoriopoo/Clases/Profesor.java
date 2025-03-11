
package com.mycompany.laboratoriopoo.Clases;
import Interfaces.SqlPersona;
import com.mycompany.laboratoriopoo.Clases.Persona;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Estudiante_MCA
 */
public class Profesor extends Persona implements SqlPersona{
    private String tipoContrato;
    
    public Profesor(double ID, String nombres, String apellidos, String email, String tipoContrato) {
        super(ID, nombres, apellidos, email);
        this.tipoContrato = tipoContrato;
    }
    
    public String toString() {
        return super.toString() + ", " + tipoContrato;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    @Override
    public String getSQLInsercion() {
        return "INSERT INTO profesores (Nombres, Apellidos, Email, TipoContrato, ID) VALUES (?, ?, ?, ?, ?)";
    }
    @Override
    public String getSQLEliminacion() {
        return "DELETE FROM profesores WHERE ID = ?";
    }
    @Override
    public String getSQLActualizacion() {
        return "UPDATE profesores SET Nombres=?, Apellidos=?, Email=?, TipoContrato=? WHERE ID=?";
    }
    @Override
    public String getSQLSeleccion(){
        return "SELECT * FROM profesores";
    }
    @Override
    public void setParametros(PreparedStatement stmt) throws SQLException {      
        stmt.setString(1, getNombres());
        stmt.setString(2, getApellidos());
        stmt.setString(3, getEmail());
        stmt.setString(4, tipoContrato);
        stmt.setDouble(5, getID());
    }
}
