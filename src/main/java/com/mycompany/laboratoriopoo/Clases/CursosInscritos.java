package com.mycompany.laboratoriopoo.Clases;
import Interfaces.Servicios;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;  
import java.sql.ResultSet;
import com.mycompany.laboratoriopoo.Clases.Curso;
import ClasesBusquedas.BusquedasPersonas;
import Interfaces.Observador;
import java.io.File;
import java.sql.Statement;


public class CursosInscritos implements Servicios {
    private List<Inscripcion> listado;
     private List<Curso> listadoCurso;
     private List<Observador> observadores;
    private InscripcionesPersonas inscripciones;
    private BusquedasPersonas busquedasPersonas;
    private Connection connection;
    private File archivo = new File("personas_registradas.txt");
    
    public CursosInscritos(Connection connection, InscripcionesPersonas inscripciones) {
        this.connection = connection;
        this.inscripciones = inscripciones;
        this.listado = new ArrayList<>();
        this.listadoCurso = new ArrayList<>();
        this.observadores = new ArrayList<>();
        this.busquedasPersonas = new BusquedasPersonas(connection);
    }
    public void agregarObservador(Observador obs) {
        observadores.add(obs);
    }
    
    public void notificarObservadores() {
        for (Observador obs : observadores) {
            obs.actualizarTablaCursos();
        }
    }
    public void inscribir(Inscripcion inscripcion) {
        listado.add(inscripcion);
        guardarInformacion();
    }
    
    public void eliminar(Inscripcion inscripcion) {
        listado.remove(inscripcion);
        guardarInformacion();
    }
    
    public void actualizar(Inscripcion inscripcion) {
        for (int i = 0; i < listado.size(); i++) {
            if (listado.get(i).getCurso().getID() == inscripcion.getCurso().getID() &&
                listado.get(i).getEstudiante().getID() == inscripcion.getEstudiante().getID()) {
                listado.set(i, inscripcion);
                break;
            }
        }
        guardarInformacion();
    }
    
    private void guardarInformacion() {
        File archivo = new File("cursos_registrados.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            // --- Obtener cursos desde la BD ---
            String sqlCursos = "SELECT * FROM cursos";

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlCursos)) {

                while (rs.next()) {
                    Programa programa = busquedasPersonas.obtenerProgramaPorNombre(rs.getString("Programa"));
                    if (programa == null) {
                        programa = new Programa(0, "Programa Desconocido", 0, new java.util.Date(), new Facultad(0, "Facultad Desconocida", null));
                    }

                    Curso curso = new Curso(
                        rs.getInt("ID"),
                        rs.getString("Nombre"),
                        programa,
                        rs.getBoolean("Activo")
                    );

                    writer.write(curso.getID() + "," + curso.getNombre() + "," +
                                 curso.getPrograma().getNombre() + "," + curso.isActivo());
                    writer.newLine();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al obtener cursos desde la BD.", e);
            }

            System.out.println("Cursos guardados en cursos_registrados.txt");
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo de cursos.", e);
        }
    }
    public void cargarDatos() {
        listadoCurso.clear();

        // --- Leer cursos desde la base de datos ---
        String sqlCursos = "SELECT * FROM cursos";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlCursos)) {

            while (rs.next()) {
                Programa programa = busquedasPersonas.obtenerProgramaPorNombre(rs.getString("Programa"));
                if (programa == null) {
                    programa = new Programa(0, "Programa Desconocido", 0, new java.util.Date(), new Facultad(0, "Facultad Desconocida", null));
                }

                Curso curso = new Curso(
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    programa,
                    rs.getBoolean("Activo")
                );
                listadoCurso.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar cursos desde la BD.", e);
        }

        // --- Leer cursos desde el archivo de respaldo ---
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    Programa programa = busquedasPersonas.obtenerProgramaPorNombre(datos[2]);
                    boolean activo = Boolean.parseBoolean(datos[3]);

                    Curso curso = new Curso(id, nombre, programa, activo);
                    if (!listado.contains(curso)) {
                        listadoCurso.add(curso);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo " + archivo + ". Se continuará con los datos de la BD.");
        }
    }
    
    public void inscribirCurso(Curso curso) {
        String checkSql = "SELECT COUNT(*) FROM cursos WHERE ID = ?";
        String insertSql = "INSERT INTO cursos (ID, Nombre, Programa, Activo) VALUES (?, ?, ?, ?)";

        try {
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setInt(1, curso.getID());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("ID duplicado detectado. Saliendo del método.");
                        JOptionPane.showMessageDialog(null, "Error: El ID ya existe en la base de datos.", "ID Duplicado", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            System.out.println("ID no duplicado. Procediendo con la inserción.");

            try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
                stmt.setInt(1, curso.getID());
                stmt.setString(2, curso.getNombre());
                stmt.setString(3, curso.getPrograma().getNombre());
                stmt.setBoolean(4, curso.isActivo());

                stmt.executeUpdate();
                System.out.println("Curso insertado en la base de datos.");
                JOptionPane.showMessageDialog(null, "Curso agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            notificarObservadores();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
  
    public Curso buscarCursoPorID(int id) {
        String sql = "SELECT ID, Nombre, Programa, Activo FROM cursos WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Programa programa = busquedasPersonas.obtenerProgramaPorNombre(rs.getString("Programa"));
                    boolean activo = rs.getBoolean("Activo");

                    return new Curso(
                        rs.getInt("ID"),
                        rs.getString("Nombre"),
                        programa,
                        activo
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
  
    public void actualizarCurso(Curso curso) {
        String sql = "UPDATE cursos SET Nombre = ?, Programa = ?, Activo = ? WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, curso.getNombre());
            stmt.setString(2, curso.getPrograma().getNombre());
            stmt.setBoolean(3, curso.isActivo());
            stmt.setInt(4, curso.getID());

            int filasActualizadas = stmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Curso actualizado correctamente en la base de datos.");
            } else {
                System.out.println("No se encontró el curso con el ID proporcionado.");
            }
            notificarObservadores();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    public void eliminarCurso(int cursoID) {
        String checkStudentsSql = "SELECT COUNT(*) FROM estudiantes_cursos WHERE curso_id = ?";
        String checkProfessorSql = "SELECT COUNT(*) FROM profesores_cursos WHERE curso_id = ?";

        try {

            try (PreparedStatement checkStudentsStmt = connection.prepareStatement(checkStudentsSql)) {
                checkStudentsStmt.setInt(1, cursoID);
                ResultSet rsStudents = checkStudentsStmt.executeQuery();
                if (rsStudents.next() && rsStudents.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el curso porque tiene estudiantes inscritos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }


            try (PreparedStatement checkProfessorStmt = connection.prepareStatement(checkProfessorSql)) {
                checkProfessorStmt.setInt(1, cursoID);
                ResultSet rsProfessor = checkProfessorStmt.executeQuery();
                if (rsProfessor.next() && rsProfessor.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el curso porque tiene un profesor asignado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String deleteCourseSql = "DELETE FROM cursos WHERE ID = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteCourseSql)) {
                deleteStmt.setInt(1, cursoID);
                deleteStmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Curso eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            notificarObservadores();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el curso.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
  
    public void inscribirEstudianteEnCurso(int estudianteID, int cursoID) {
        String sql = "INSERT INTO estudiantes_cursos (estudiante_id, curso_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, estudianteID);
            stmt.setInt(2, cursoID);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estudiante inscrito en el curso correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al inscribir al estudiante en el curso.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
  
    public void eliminarInscripcionEstudiante(int estudianteID, int cursoID) {
       String sql = "DELETE FROM estudiantes_cursos WHERE estudiante_id = ? AND curso_id = ?";
       try (PreparedStatement stmt = connection.prepareStatement(sql)) {
           stmt.setInt(1, estudianteID);
           stmt.setInt(2, cursoID);
           stmt.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion >= 0 && posicion < listado.size()) {
            return listado.get(posicion).toString();
        }
        return "Posición inválida";
    }

    @Override
    public int cantidadActual() {
        return listado.size();
    }

    @Override
    public List<String> imprimirListado() {
        List<String> lista = new ArrayList<>();
        for (Inscripcion ins : listado) {
            lista.add(ins.toString() + "\n");
        }
        return lista;
    }
    public List<Curso> obtenerTodosLosCursos() {
        List<Curso> cursos = new ArrayList<>();
        try {
            String query = "SELECT * FROM cursos";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    new Programa(rs.getString("programa")), // ajusta si es necesario
                    rs.getBoolean("activo")
                );
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }
}
