
package ClasesBusquedas;

import Interfaces.Observador;
import com.mycompany.laboratoriopoo.Clases.Estudiante;
import com.mycompany.laboratoriopoo.Clases.Facultad;
import com.mycompany.laboratoriopoo.Clases.Persona;
import com.mycompany.laboratoriopoo.Clases.Profesor;
import com.mycompany.laboratoriopoo.Clases.Programa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author klmxl
 */
public class BusquedasPersonas {
    private Connection connection;
    
    public BusquedasPersonas(Connection connection) {
        this.connection = connection;
    }

    private Facultad obtenerFacultad(int facultadID) {
        String sql = "SELECT f.ID, f.Nombre, p.ID AS ID, p.Nombre, p.Apellido, p.Email " +
                     "FROM facultades f " +
                     "LEFT JOIN decano p ON f.ID = p.ID " +
                     "WHERE f.ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, facultadID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Profesor decano = null;
                if (rs.getObject("ID") != null) {
                    decano = new Profesor(
                        rs.getDouble("ID"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido"),
                        rs.getString("Email"),
                        "Contrato indefinido"  // Si necesitas especificar un contrato
                    );
                }

                return new Facultad(rs.getDouble("ID"), rs.getString("Nombre"), decano);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener facultad con ID " + facultadID, e);
        }
        return null;
    }

    public Programa obtenerProgramaPorNombre(String nombrePrograma) {
        String sql = "SELECT ID, Nombre, IFNULL(Duracion, 0) AS Duracion, Fecha_inicio, Facultad_id FROM programas WHERE Nombre = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombrePrograma);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Programa(
                    rs.getDouble("ID"),
                    rs.getString("Nombre"),
                    rs.getDouble("Duracion"),  
                    rs.getDate("Fecha_inicio"), 
                    obtenerFacultad(rs.getInt("Facultad_id"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el programa: " + nombrePrograma, e);
        }
        return null;
    }
    
    public Persona buscarEstudiantePorID(int id) {
        String sql = "SELECT * FROM estudiantes WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estudiante(
                    rs.getLong("ID"),  
                    rs.getString("Nombres"),
                    rs.getString("Apellidos"),
                    rs.getString("Email"),
                    rs.getLong("Codigo"), 
                    new Programa(rs.getString("Programa")),  
                    rs.getBoolean("Activo"),
                    rs.getDouble("Promedio")
                );
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estudiante con ID: " + id, e);
        }

        return null;
    }
    public Persona buscarProfesorPorID(long id) {
        String sql = "SELECT * FROM profesores WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Profesor(
                    rs.getLong("ID"),  
                    rs.getString("Nombres"),
                    rs.getString("Apellidos"),
                    rs.getString("Email"),
                    rs.getString("TipoContrato")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estudiante con ID: " + id, e);
        }

        return null;
    }
}
