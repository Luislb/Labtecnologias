
package ClasesInterfaz.EstudianteDetalle;

import ClasesBusquedas.BusquedasPersonas;
import com.mycompany.laboratoriopoo.Clases.Estudiante;
import com.mycompany.laboratoriopoo.Clases.Persona;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import com.mycompany.laboratoriopoo.Clases.Inscripcion;
import com.mycompany.laboratoriopoo.Clases.InscripcionesPersonas;
import javax.swing.table.DefaultTableModel;
import Interfaces.Observador;
import com.mycompany.laboratoriopoo.Clases.Curso;
import com.mycompany.laboratoriopoo.Clases.CursosInscritos;
import com.mycompany.laboratoriopoo.Clases.CursosProfesores;

public class ControladorEstudianteDetalle extends JFrame implements Observador{
    private JTextField txtCodigoEstudiante;
    private JTextField txtNombreEstudiante;
    private JTextField txtCodigoCurso;
    private JTextField txtNombreCurso;
    private JTextField txtAnio;
    private JTextField campoID;
    private JTable tablaHistorial;
    private DefaultTableModel modeloHistorial;
    private JComboBox<String> comboPeriodo;
    private JButton btnInscribir;
    private Connection connection;
    private InscripcionesPersonas inscripciones;
    public JPanel panelEstudianteDetalle;
    private DefaultTableModel modeloTabla;
    private CursosInscritos cursosInscritos;
    private JTable tablaCursos;
    private DefaultTableModel modeloTablaCursos;
    private CursosProfesores cursosProfesores;
    private BusquedasPersonas busquedasPersonas;

    public ControladorEstudianteDetalle(Connection connection, InscripcionesPersonas inscripciones, CursosInscritos cursosInscritos, BusquedasPersonas busquedasPersonas) {
        this.connection = connection;
        this.inscripciones = inscripciones;
        this.cursosInscritos = cursosInscritos;
        this.cursosProfesores = cursosProfesores;
        this.busquedasPersonas = busquedasPersonas;
        this.panelEstudianteDetalle = new JPanel(new BorderLayout());
        this.inscripciones.agregarObservador(this);
        this.cursosInscritos.agregarObservador(this);
        this.cursosInscritos.agregarObservadorHistorial(this);

        // Configuración de la ventana
        setTitle("Estudiante - Detalle");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Crear panel de estudiante
        crearPanelEstudiante();
        
        // Crear las pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Historial Cursos", crearPanelHistorialCursos());  // Pestaña vacía de ejemplo
        tabbedPane.addTab("Inscribir Curso", crearPanelInscribirCurso()); // Pestaña con formulario de inscripción
        tabbedPane.addTab("Cursos", crearPanelCursos()); // Pestaña con lista de cursos
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
        this.campoID = new JTextField(10);
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
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID Curso:");
        JTextField idField = new JTextField(20);
        JButton searchButton = new JButton("Buscar");

        JLabel nameLabel = new JLabel("Nombre:");
        JTextField nameField = new JTextField(20);
        nameField.setEditable(false);

        JLabel programLabel = new JLabel("Programa:");
        JTextField programField = new JTextField(20);
        programField.setEditable(false);

        JLabel activeLabel = new JLabel("Activo:");
        JCheckBox activeCheckBox = new JCheckBox();

        JButton btnInscribir = new JButton("Inscribir");

        // --- Añadir componentes con GridBagLayout ---

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 2;
        panel.add(searchButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(nameField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(programLabel, gbc);

        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(programField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(activeLabel, gbc);

        gbc.gridx = 1;
        panel.add(activeCheckBox, gbc);

        gbc.gridx = 2; // dejar espacio para alinear
        panel.add(Box.createGlue(), gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnInscribir, gbc);

        // --- Acciones de los botones ---
        searchButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                cursosInscritos.cargarDatos();
                Curso curso = cursosInscritos.buscarCursoPorID(id);

                if (curso != null) {
                    nameField.setText(curso.getNombre());
                    programField.setText(curso.getPrograma().getNombre());
                    activeCheckBox.setSelected(curso.isActivo());
                } else {
                    JOptionPane.showMessageDialog(panelEstudianteDetalle, "Curso no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelEstudianteDetalle, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnInscribir.addActionListener(e -> {
            try {
                int estudianteID = Integer.parseInt(campoID.getText().trim());
                int cursoID = Integer.parseInt(idField.getText().trim());

                Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
                if (curso == null) {
                    JOptionPane.showMessageDialog(panel, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Persona estudiante = busquedasPersonas.buscarEstudiantePorID(estudianteID);
                if (estudiante == null || !(estudiante instanceof Estudiante)) {
                    JOptionPane.showMessageDialog(panel, "El estudiante no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                cursosInscritos.inscribirEstudianteEnCurso(estudianteID, cursoID);
                JOptionPane.showMessageDialog(panel, "Estudiante inscrito exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

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
                
                cursosInscritos.notificarObservadoresHistorialCursos((int) estudiante.getID());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Estudiante no encontrado");
    }
    
    public JPanel crearPanelProfesor() {
        JPanel panelProfesor = new JPanel(new BorderLayout());

        String[] nombresColumnas = {"ID", "Nombres", "Apellidos", "Correo Electrónico", "Tipo de Contrato"};
        modeloTabla = new DefaultTableModel(nombresColumnas, 0);
        JTable tablaProfesores = new JTable(modeloTabla);
        JScrollPane panelDesplazamiento = new JScrollPane(tablaProfesores);
        panelProfesor.add(panelDesplazamiento, BorderLayout.CENTER);

        actualizarTabla();

        return panelProfesor;
    }
    private JPanel crearPanelCursos() {
        JPanel panel = new JPanel(new BorderLayout());

        modeloTablaCursos = new DefaultTableModel(new Object[]{"ID", "Nombre", "Programa", "Activo"}, 0);
        tablaCursos = new JTable(modeloTablaCursos);
        JScrollPane scrollPane = new JScrollPane(tablaCursos);
        panel.add(scrollPane, BorderLayout.CENTER);

        actualizarTablaCursos(); // llenar al inicio

        return panel;
    }
    private JPanel crearPanelHistorialCursos() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"ID Curso", "Nombre", "Programa", "Activo"};
        modeloHistorial = new DefaultTableModel(columnas, 0);  // ← se usa el de la clase
        tablaHistorial = new JTable(modeloHistorial);
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    private void actualizarTablaHistorial(int estudianteID) {
        modeloHistorial.setRowCount(0); // Limpiar tabla

        List<Curso> cursos = cursosInscritos.obtenerCursosPorEstudiante(estudianteID);
        for (Curso curso : cursos) {
            modeloHistorial.addRow(new Object[]{
                curso.getID(),
                curso.getNombre(),
                curso.getPrograma().getNombre(),
                curso.isActivo() ? "Sí" : "No"
            });
        }
    }
    @Override
    public void actualizarHistorialCursos(int estudianteID) {
        if (!campoID.getText().isEmpty()) {
            try {
                estudianteID = Integer.parseInt(campoID.getText().trim());
                actualizarTablaHistorial(estudianteID);
            } catch (NumberFormatException ex) {
                System.err.println("ID inválido en actualizar(): " + ex.getMessage());
            }
        }
    }
    
    @Override
    public void actualizarTablaCursos() {
        modeloTablaCursos.setRowCount(0); // limpiar

        for (Curso curso : cursosInscritos.obtenerTodosLosCursos()) {
            modeloTablaCursos.addRow(new Object[]{
                curso.getID(),
                curso.getNombre(),
                curso.getPrograma().getNombre(),
                curso.isActivo() ? "Sí" : "No"
            });
        }
    }
    @Override
    public void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<String> profesores = inscripciones.imprimirListado("profesor");
        for (String profe : profesores) {
            String[] datos = profe.split(",");
            modeloTabla.addRow(datos);
        }
    }
}

