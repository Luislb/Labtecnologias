package interfaz;
import javax.swing.*;
import java.sql.*;
import com.mycompany.laboratoriopoo.Clases.InscripcionesPersonas;
import com.mycompany.laboratoriopoo.Clases.CursosInscritos;
import com.mycompany.laboratoriopoo.Clases.CursosProfesores;
import ClasesInterfaz.Cursos.ControladorCursos;
import ClasesInterfaz.Estudiantes.ControladorEstudiante;
import ClasesInterfaz.Profesores.ControladorProfesor;
import Clases.BaseDeDatos.BaseDatos;
import ClasesBusquedas.BusquedasPersonas;

public class GUI extends JFrame {
    private JTabbedPane tabbedPane;
    private Connection connection;
    
    private CursosInscritos cursosInscritos;
    private CursosProfesores cursosProfesores;
    private InscripcionesPersonas inscripcionesPersonas;
    private BusquedasPersonas busquedasPersonas;
    
    private ControladorProfesor controladorProfesor;
    private ControladorEstudiante controladorEstudiante;
    private ControladorCursos controladorCursos;

    public GUI() {
        setTitle("Gestión Universitaria");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        inicializarConexion();
        inicializarControladores();
        inicializarInterfaz();
        
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
            System.exit(1);  // Salimos si no hay conexión
        }
    }

    private void inicializarControladores() {
        controladorProfesor = new ControladorProfesor(connection, inscripcionesPersonas);
        controladorEstudiante = new ControladorEstudiante(connection, inscripcionesPersonas);
        controladorCursos = new ControladorCursos(connection, cursosInscritos, cursosProfesores, busquedasPersonas);
    }

    private void inicializarInterfaz() {
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Estudiantes", controladorEstudiante.panelEstudiante);
        tabbedPane.addTab("Profesores", controladorProfesor.panelProfesor);
        tabbedPane.addTab("Cursos", controladorCursos.coursePanel);
        
        setJMenuBar(crearMenu());
        add(tabbedPane);
        
        controladorEstudiante.configurarPanelEstudiante();
        controladorProfesor.configuracionPanelProfesor();
        controladorCursos.setupCoursePanel();
    }

    private JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        JMenuItem estudiantesItem = new JMenuItem("Estudiantes");
        JMenuItem profesoresItem = new JMenuItem("Profesores");

        estudiantesItem.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        profesoresItem.addActionListener(e -> tabbedPane.setSelectedIndex(1));

        menu.add(estudiantesItem);
        menu.add(profesoresItem);
        menuBar.add(menu);

        return menuBar;
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
