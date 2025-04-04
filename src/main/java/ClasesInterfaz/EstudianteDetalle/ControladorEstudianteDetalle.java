
package ClasesInterfaz.EstudianteDetalle;

import com.mycompany.laboratoriopoo.Clases.Estudiante;
import com.mycompany.laboratoriopoo.Clases.Persona;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import com.mycompany.laboratoriopoo.Clases.Inscripcion;
import com.mycompany.laboratoriopoo.Clases.InscripcionesPersonas;
import javax.swing.table.DefaultTableModel;

public class ControladorEstudianteDetalle extends JFrame {
    private JTextField txtCodigoEstudiante;
    private JTextField txtNombreEstudiante;
    private JTextField txtCodigoCurso;
    private JTextField txtNombreCurso;
    private JTextField txtAnio;
    private JComboBox<String> comboPeriodo;
    private Connection connection;
    private InscripcionesPersonas inscripciones;
    public JPanel panelEstudianteDetalle;

    public ControladorEstudianteDetalle(Connection connection, InscripcionesPersonas inscripciones) {
        this.connection = connection;
        this.inscripciones = inscripciones;
        this.panelEstudianteDetalle = new JPanel(new BorderLayout());

        // Configuración de la ventana
        setTitle("Estudiante - Detalle");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Crear panel de estudiante
        crearPanelEstudiante();
        
        // Crear las pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Historial Cursos", new JPanel());  // Pestaña vacía de ejemplo
        tabbedPane.addTab("Inscribir Curso", crearPanelInscribirCurso()); // Pestaña con formulario de inscripción
        tabbedPane.addTab("Cursos", new JPanel()); // Pestaña con lista de cursos
        tabbedPane.addTab("Docentes", crearPanelProfesor()); // Pestaña con lista de docentes

        // Agregar las pestañas dentro del panelEstudianteDetalle
        panelEstudianteDetalle.add(tabbedPane, BorderLayout.CENTER);

        // Establecer el layout y agregar el panel principal
        setLayout(new BorderLayout());
        add(panelEstudianteDetalle, BorderLayout.CENTER); // Ahora el panel con las pestañas está en el centro de la ventana
    }

    public void crearPanelEstudiante() {
        // Panel del formulario del estudiante
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiquetaID = new JLabel("ID:");
        JTextField campoID = new JTextField(15);
        JButton botonBuscar = new JButton("Buscar");

        JLabel etiquetaNombres = new JLabel("Nombres:");
        JTextField campoNombres = new JTextField(15);

        JLabel etiquetaApellidos = new JLabel("Apellidos:");
        JTextField campoApellidos = new JTextField(15);

        JLabel etiquetaCorreo = new JLabel("Correo Electrónico:");
        JTextField campoCorreo = new JTextField(15);

        JLabel etiquetaCodigo = new JLabel("Código:");
        JTextField campoCodigo = new JTextField(15);

        JLabel etiquetaPrograma = new JLabel("Programa:");
        JTextField campoPrograma = new JTextField(15);

        JLabel etiquetaActivo = new JLabel("Activo:");
        JCheckBox checkActivo = new JCheckBox();

        JLabel etiquetaPromedio = new JLabel("Promedio:");
        JTextField campoPromedio = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(etiquetaID, gbc);
        gbc.gridx = 1; panelFormulario.add(campoID, gbc);
        gbc.gridx = 2; panelFormulario.add(botonBuscar, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(etiquetaNombres, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoNombres, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(etiquetaApellidos, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoApellidos, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 3; panelFormulario.add(etiquetaCorreo, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoCorreo, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 4; panelFormulario.add(etiquetaCodigo, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoCodigo, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 5; panelFormulario.add(etiquetaPrograma, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoPrograma, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 6; panelFormulario.add(etiquetaActivo, gbc);
        gbc.gridx = 1; panelFormulario.add(checkActivo, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panelFormulario.add(etiquetaPromedio, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoPromedio, gbc);
        gbc.gridwidth = 1;

        // Ahora agregamos el panelFormulario al panelEstudianteDetalle en la parte superior
        panelEstudianteDetalle.add(panelFormulario, BorderLayout.NORTH);
        botonBuscar.addActionListener(e -> buscarEstudiante(
            Double.parseDouble(campoID.getText()), campoNombres, campoApellidos, campoCorreo, campoCodigo, campoPrograma, checkActivo, campoPromedio
        ));
    }
    
    private JPanel crearPanelInscribirCurso() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        txtCodigoCurso = new JTextField(10);
        txtNombreCurso = new JTextField(20);
        txtNombreCurso.setEditable(false);
        txtAnio = new JTextField(4);
        comboPeriodo = new JComboBox<>(new String[]{"1", "2"});
        JButton btnBuscarCurso = new JButton("Buscar");
        JButton btnInscribir = new JButton("Inscribir");
        
        btnBuscarCurso.addActionListener(e -> buscarCurso());
        btnInscribir.addActionListener(e -> inscribirCurso());
        
        panel.add(new JLabel("Código Curso: "));
        panel.add(txtCodigoCurso);
        panel.add(btnBuscarCurso);
        panel.add(txtNombreCurso);
        panel.add(new JLabel("Año: "));
        panel.add(txtAnio);
        panel.add(new JLabel("Período: "));
        panel.add(comboPeriodo);
        panel.add(btnInscribir);
        
        return panel;
    }
    
    private void buscarEstudiante(double ID, JTextField campoNombre, JTextField campoApellido, JTextField campoCorreo, JTextField campoCodigo, JTextField campoPrograma, JCheckBox checkActivo, JTextField campoPromedio) {
        inscripciones.cargarDatos();

        for (Persona p : inscripciones.listadoPersonas) {
            if (p.getID() == ID && p instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) p;
                campoNombre.setText(estudiante.getNombres());
                campoApellido.setText(estudiante.getApellidos());
                campoCorreo.setText(estudiante.getEmail());
                campoCodigo.setText(String.valueOf(estudiante.getCodigo()));
                campoPrograma.setText(estudiante.getPrograma().getNombre());
                checkActivo.setSelected(estudiante.isActivo());
                campoPromedio.setText(String.valueOf(estudiante.getPromedio()));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Estudiante no encontrado");
    }
    
    private void buscarCurso() {
        // Implementar búsqueda en la base de datos
        txtNombreCurso.setText("Curso Ejemplo");
    }
    
    private void inscribirCurso() {
        // Implementar lógica de inscripción
        JOptionPane.showMessageDialog(this, "Inscripción realizada con éxito.");
    }
    private JPanel crearPanelProfesor(){
        JPanel panelProfesor = new JPanel(new GridLayout(1, 2, 10, 1));
 
        String[] nombresColumnas = {"ID", "Nombres", "Apellidos", "Correo Electrónico", "Tipo de Contrato"};
        DefaultTableModel modeloTabla = new DefaultTableModel(nombresColumnas, 0);
        JTable tablaProfesores = new JTable(modeloTabla);
        JScrollPane panelDesplazamiento = new JScrollPane(tablaProfesores);
        panelProfesor.add(panelDesplazamiento, BorderLayout.CENTER);

        JButton botonRefrescar = new JButton("Refrescar Tabla");
        JPanel panelRefrescar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelRefrescar.add(botonRefrescar);
        panelProfesor.add(panelRefrescar, BorderLayout.SOUTH);

        botonRefrescar.addActionListener(e -> {
            modeloTabla.setRowCount(0); // Limpiar tabla
            java.util.List<String> profesores = inscripciones.imprimirListado("profesor");
            for (String profe : profesores) {
                String[] datos = profe.split(",");
                modeloTabla.addRow(datos);
            }
        });
        return panelProfesor;
    }
}

