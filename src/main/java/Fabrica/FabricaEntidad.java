package Fabrica;

import com.mycompany.laboratoriopoo.Clases.*;

public class FabricaEntidad {

    public static Object crearEntidad(String tipo, Object... datos) {
        switch (tipo.toLowerCase()) {

            case "estudiante":
                return new Estudiante(
                    (double) datos[0],         // ID
                    (String) datos[1],         // Nombres
                    (String) datos[2],         // Apellidos
                    (String) datos[3],         // Email
                    (double) datos[4],         // Código
                    (Programa) datos[5],       // Programa
                    (boolean) datos[6],        // Activo
                    (double) datos[7]          // Promedio
                );

            case "profesor":
                return new Profesor(
                    (double) datos[0],         // ID
                    (String) datos[1],         // Nombres
                    (String) datos[2],         // Apellidos
                    (String) datos[3],         // Email
                    (String) datos[4]          // Tipo de Contrato
                );

            case "curso":
                return new Curso(
                    (int) datos[0],            // ID
                    (String) datos[1],         // Nombre
                    (Programa) datos[2],       // Programa
                    (boolean) datos[3]         // Activo
                );

            default:
                throw new IllegalArgumentException("Tipo de entidad no válido: " + tipo);
        }
    }
}
