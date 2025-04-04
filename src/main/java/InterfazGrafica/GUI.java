package InterfazGrafica;
import javax.swing.*;
import java.sql.*;
import com.mycompany.laboratoriopoo.Clases.InscripcionesPersonas;
import com.mycompany.laboratoriopoo.Clases.CursosInscritos;
import com.mycompany.laboratoriopoo.Clases.CursosProfesores;
import ClasesInterfaz.Cursos.ControladorCursos;
import ClasesInterfaz.Estudiantes.ControladorEstudiante;
import ClasesInterfaz.Profesores.ControladorProfesor;
import ClasesInterfaz.EstudianteDetalle.ControladorEstudianteDetalle;
import Clases.BaseDeDatos.BaseDatos;
import ClasesBusquedas.BusquedasPersonas;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class GUI extends JFrame {
    private Connection connection;
    
    private CursosInscritos cursosInscritos;
    private CursosProfesores cursosProfesores;
    private InscripcionesPersonas inscripcionesPersonas;
    private BusquedasPersonas busquedasPersonas;
    
    private ControladorProfesor controladorProfesor;
    private ControladorEstudiante controladorEstudiante;
    private ControladorCursos controladorCursos;
    private ControladorEstudianteDetalle controladorEstudianteDetalle;

    public GUI() {
        setTitle("Gestión Universitaria");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        inicializarConexion();
        inicializarControladores();
        setJMenuBar(crearMenu());
        
        setVisible(true);
    }

    private void inicializarConexion() {
        try {
            connection = BaseDatos.getConnection();
            System.out.println("Conectado a la base de datos");
            
            busquedasPersonas = new BusquedasPersonas(connection);
            inscripcionesPersonas = new InscripcionesPersonas(connection);           
            cursosInscritos = new CursosInscritos(connection, inscripcionesPersonas);
            cursosProfesores = new CursosProfesores(connection);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void inicializarControladores() {
        controladorProfesor = new ControladorProfesor(connection, inscripcionesPersonas);
        controladorEstudiante = new ControladorEstudiante(connection, inscripcionesPersonas);
        controladorCursos = new ControladorCursos(connection, cursosInscritos, cursosProfesores, busquedasPersonas);
        controladorEstudianteDetalle = new ControladorEstudianteDetalle(connection, inscripcionesPersonas);
    }

    private JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        JMenuItem estudiantesItem = new JMenuItem("Estudiantes");
        JMenuItem profesoresItem = new JMenuItem("Profesores");
        JMenuItem cursosItem = new JMenuItem("Cursos");
        JMenuItem estudianteDetalleItem = new JMenuItem("estudianteDetalle");

        estudiantesItem.addActionListener(e -> abrirVentana(new VentanaEstudiantes(controladorEstudiante)));
        profesoresItem.addActionListener(e -> abrirVentana(new VentanaProfesores(controladorProfesor)));
        cursosItem.addActionListener(e -> abrirVentana(new VentanaCursos(controladorCursos)));
        estudianteDetalleItem.addActionListener(e -> abrirVentana(new VentanaEstudianteDetalle(controladorEstudianteDetalle)));

        menu.add(estudiantesItem);
        menu.add(profesoresItem);
        menu.add(cursosItem);
        menu.add(estudianteDetalleItem);
        menuBar.add(menu);
        
        return menuBar;
    }
    
    private void abrirVentana(JFrame ventana) {
        ventana.setVisible(true);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
class VentanaEstudiantes extends JFrame {
    public VentanaEstudiantes(ControladorEstudiante controladorEstudiante) {
        setTitle("Gestión de Estudiantes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        add(controladorEstudiante.panelEstudiante);
        
        controladorEstudiante.configurarPanelEstudiante();
    }
}
class VentanaEstudianteDetalle extends JFrame {
    public VentanaEstudianteDetalle(ControladorEstudianteDetalle controladorEstudianteDetalle) {
        setTitle("Gestión de Estudiantes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        add(controladorEstudianteDetalle.panelEstudianteDetalle);
        
        controladorEstudianteDetalle.crearPanelEstudiante();
    }
}
class VentanaProfesores extends JFrame {
    public VentanaProfesores(ControladorProfesor controladorProfesor) {
        setTitle("Gestión de Profesores");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        add(controladorProfesor.panelProfesor);
        
        controladorProfesor.configuracionPanelProfesor();
    }
}

class VentanaCursos extends JFrame {
    public VentanaCursos(ControladorCursos controladorCursos) {
        setTitle("Gestión de Cursos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        add(controladorCursos.coursePanel);
        
        controladorCursos.setupCoursePanel();
    }
}
