package com.mycompany.laboratoriopoo.Clases;
import Interfaces.Servicios;
import com.mycompany.laboratoriopoo.Clases.CursoProfesor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CursosProfesores implements Servicios {
    private List<CursoProfesor> listado ;
    private Connection connection;
    
    public CursosProfesores(Connection connection) {
        this.connection = connection;
        this.listado = new ArrayList<>();
    }
    
    public void inscribir(CursoProfesor cursoProfesor) {
        listado.add(cursoProfesor);
        guardarInformacion();
    }
    
    private void guardarInformacion() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cursos_profesores.txt", true))) {
            writer.write(listado.get(listado.size() - 1).toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarDatos() {
        File archivo = new File("personas_registradas.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
            }
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
    
    public boolean cursoTieneProfesor(int cursoID) {
        String sql = "SELECT COUNT(*) FROM profesores_cursos WHERE curso_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cursoID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }
    
    public void inscribirProfesorEnCurso(int profesorID, int cursoID) {
        String sql = "INSERT INTO profesores_cursos (curso_id, profesor_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cursoID);
            stmt.setInt(2, profesorID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarInscripcionProfesor(int profesorID, int cursoID) {
        String sql = "DELETE FROM profesores_cursos WHERE profesor_id = ? AND curso_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, profesorID);
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
        for (CursoProfesor cp : listado) {
            lista.add(cp.toString() + "\n");
        }
        return lista;
    }
}
