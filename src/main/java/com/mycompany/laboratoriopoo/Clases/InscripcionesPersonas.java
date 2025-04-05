

package com.mycompany.laboratoriopoo.Clases;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.laboratoriopoo.Clases.Programa;
import ClasesBusquedas.BusquedasPersonas;
import Interfaces.SqlPersona;
import Interfaces.Observador;

public class InscripcionesPersonas {
    public List<SqlPersona> listado;
    public List<Persona> listadoPersonas;
    private List<Observador> observadores;
    private Connection connection;
    private BusquedasPersonas busquedasPersonas;
    
    public InscripcionesPersonas(Connection connection) {
        this.connection = connection;
        this.listado = new ArrayList<>();
        this.listadoPersonas = new ArrayList<>();
        this.observadores = new ArrayList<>();
        this.busquedasPersonas = new BusquedasPersonas(connection);
    }
    public void agregarObservador(Observador obs) {
        observadores.add(obs);
    }

    public void notificarObservadores() {
        for (Observador obs : observadores) {
            obs.actualizarTabla();
        }
    }
    public boolean existeID(double id, String tabla) throws SQLException {
        if (!esTablaValida(tabla)) {
            throw new IllegalArgumentException("Nombre de tabla no válido: " + tabla);
        }

        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    private boolean esTablaValida(String tabla) {
        List<String> tablasPermitidas = List.of("estudiantes", "profesores", "cursos");
        return tablasPermitidas.contains(tabla.toLowerCase());
    }
    public void inscribir(SqlPersona persona) {
        listado.add(persona);

        try (PreparedStatement stmt = connection.prepareStatement(persona.getSQLInsercion())) {
            persona.setParametros(stmt);
            stmt.executeUpdate();
            System.out.println("Persona agregada a la BD.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al inscribir persona en la BD", e);
        }
        notificarObservadores();
    }

    public void eliminar(SqlPersona persona) {
        listado.remove(persona);

        try (PreparedStatement stmt = connection.prepareStatement(persona.getSQLEliminacion())) {
            stmt.setDouble(1, persona.getID());
            stmt.executeUpdate();
            System.out.println(persona.getClass().getSimpleName() + " eliminado de la BD.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar persona en la BD", e);
        }
        notificarObservadores();
    }

    public void actualizar(SqlPersona persona) {
        for (int i = 0; i < listado.size(); i++) {
            if (listado.get(i).getID() == persona.getID()) {
                listado.set(i, persona);
                break;
            }
        }

        try (PreparedStatement stmt = connection.prepareStatement(persona.getSQLActualizacion())) {
            persona.setParametros(stmt);
            stmt.executeUpdate();
            System.out.println(persona.getClass().getSimpleName() + " actualizado en la BD.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar persona en la BD", e);
        }
        notificarObservadores();
    }

    public void cargarDatos() {
        File archivo = new File("personas_registradas.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            SqlPersona estudianteSQL = new Estudiante(0, "", "", "", 0, null, false, 0);
            String sqlEstudiantes = estudianteSQL.getSQLSeleccion();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlEstudiantes)) {

                while (rs.next()) {
                    Programa programa = busquedasPersonas.obtenerProgramaPorNombre(rs.getString("Programa"));
                    if (programa == null) {
                        programa = new Programa(0, "Programa Desconocido", 0, new java.util.Date(), new Facultad(0, "Facultad Desconocida", null));
                    }

                    Estudiante estudiante = new Estudiante(
                        rs.getDouble("ID"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getDouble("Codigo"),
                        programa,
                        rs.getBoolean("Activo"),
                        rs.getDouble("Promedio")
                    );
                    listadoPersonas.add(estudiante);

                    writer.write("Estudiante," + estudiante.getID() + "," + estudiante.getNombres() + "," +
                                 estudiante.getApellidos() + "," + estudiante.getEmail() + "," +
                                 estudiante.getCodigo() + "," +
                                 estudiante.getPrograma().getNombre() + "," +
                                 estudiante.isActivo() + "," +
                                 estudiante.getPromedio());
                    writer.newLine();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al cargar estudiantes desde la BD.", e);
            }

            // --- Cargar profesores ---
            SqlPersona ProfesorSQL = new Profesor(0, "", "", "", "");
            String sqlProfesores = ProfesorSQL.getSQLSeleccion();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlProfesores)) {

                while (rs.next()) {
                    Profesor profesor = new Profesor(
                        rs.getDouble("ID"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getString("TipoContrato")
                    );
                    listadoPersonas.add(profesor);

                    writer.write("Profesor," + profesor.getID() + "," + profesor.getNombres() + "," +
                                 profesor.getApellidos() + "," + profesor.getEmail() + "," +
                                 profesor.getTipoContrato());
                    writer.newLine();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al cargar profesores desde la BD.", e);
            }

            System.out.println("Datos guardados en personas_registradas.txt");
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo de personas.", e);
        }
        notificarObservadores();
    }

    public Integer cantidadActual() {
            return listado.size();
        }

    public List<String> imprimirListado(String tipo) {
        List<String> resultado = new ArrayList<>();
        listado.clear();
        
        String sql = "";
        if ("profesor".equalsIgnoreCase(tipo)) {
            SqlPersona ProfesorSQL = new Profesor(0, "", "", "", "");
            String sqlProfesores = ProfesorSQL.getSQLSeleccion();
            sql = sqlProfesores;
        } else if ("estudiante".equalsIgnoreCase(tipo)) {
            SqlPersona estudianteSQL = new Estudiante(0, "", "", "", 0, null, false, 0);
            String sqlEstudiantes = estudianteSQL.getSQLSeleccion();
            sql = sqlEstudiantes;
        } else {
            System.out.println("Tipo de persona no válido.");
            return resultado;
        }


        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if ("profesor".equalsIgnoreCase(tipo)) {
                    Profesor profe = new Profesor(
                        rs.getDouble("ID"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getString("TipoContrato")
                    );
                    listado.add(profe);
                    resultado.add(profe.getID() + "," + profe.getNombres() + "," + profe.getApellidos() + "," +
                                  profe.getEmail() + "," + profe.getTipoContrato());
                    } else {
                        Programa programa = busquedasPersonas.obtenerProgramaPorNombre(rs.getString("Programa"));
                    if (programa == null) {
                        programa = new Programa(0, "Programa Desconocido", 0, new java.util.Date(), new Facultad(0, "Facultad Desconocida", null));
                    }

                    Estudiante est = new Estudiante(
                        rs.getDouble("ID"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getDouble("Codigo"),
                        programa,
                        rs.getBoolean("Activo"),
                        rs.getDouble("Promedio")
                    );
                    listado.add(est);
                    resultado.add(est.getID() + "," + est.getNombres() + "," + est.getApellidos() + "," +
                                  est.getEmail() + "," + est.getCodigo() + "," + est.getPrograma().getNombre() + "," +
                                  est.isActivo() + "," + est.getPromedio());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener listado de " + tipo + " desde la BD.", e);
        }
        System.out.println("total de datos: " + cantidadActual());
        return resultado;
    }
}

