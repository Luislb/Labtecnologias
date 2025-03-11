
package Clases.BaseDeDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author klmxl
 */
public class BaseDatos {
    private static final String URL = "jdbc:mysql://trolley.proxy.rlwy.net:21639/universidad";
    private static final String USER = "root";
    private static final String PASSWORD = "hgJIRkqzGGypoobLFoigLcUFYotBMVTP";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
