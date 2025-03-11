
package Interfaces;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author klmxl
 */
public interface SqlPersona {
    public abstract String getSQLInsercion();
    public abstract String getSQLEliminacion();
    public abstract String getSQLActualizacion();
    public String getSQLSeleccion();
    public abstract void setParametros(PreparedStatement stmt) throws SQLException;
    public abstract double getID();
}
