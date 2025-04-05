
package ClasesInterfaz.Profesores;
import Interfaces.SqlPersona;
import com.mycompany.laboratoriopoo.Clases.InscripcionesPersonas;
import com.mycompany.laboratoriopoo.Clases.Persona;
import com.mycompany.laboratoriopoo.Clases.Profesor;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
/**
 *
 * @author klmxl
 */
public class ControladorProfesor extends JFrame{
    public JPanel panelProfesor;
    private Connection connection;
    private InscripcionesPersonas inscripciones;

    public ControladorProfesor(Connection connection, InscripcionesPersonas inscripciones) {
        this.connection = connection;
        this.inscripciones = inscripciones;
        this.panelProfesor = new JPanel(new BorderLayout());
        configuracionPanelProfesor();
    }
       
    public void configuracionPanelProfesor() {
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

        JLabel etiquetaContrato = new JLabel("Tipo de Contrato:");
        JTextField campoContrato = new JTextField(15);

        JButton botonInscribir = new JButton("Inscribir");
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");

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

        gbc.gridx = 0; gbc.gridy = 4; panelFormulario.add(etiquetaContrato, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoContrato, gbc);
        gbc.gridwidth = 1;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.add(botonInscribir);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        panelFormulario.add(panelBotones, gbc);

        panelProfesor.add(panelFormulario, BorderLayout.NORTH);

        botonBuscar.addActionListener(e -> {
            try {
                Double id = Double.parseDouble(campoID.getText());
                buscarProfesor(id, campoNombres, campoApellidos, campoCorreo, campoContrato);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonInscribir.addActionListener(e -> {
            try {
                double id = Double.parseDouble(campoID.getText());
                inscribirProfesor(id, campoNombres.getText(), campoApellidos.getText(), campoCorreo.getText(), campoContrato.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonModificar.addActionListener(e -> {
            try {
                double id = Double.parseDouble(campoID.getText());
                modificarProfesor(id, campoNombres.getText(), campoApellidos.getText(), campoCorreo.getText(), campoContrato.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonEliminar.addActionListener(e -> {
            try {
                double id = Double.parseDouble(campoID.getText());
                eliminarProfesor(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public void inscribirProfesor(double ID, String nombres, String apellidos, String email, String tipoContrato) {
        try {
            
            if (inscripciones.existeID(ID, "profesores")) {
                JOptionPane.showMessageDialog(this, "Error: El ID ya existe en la base de datos.", "ID Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Profesor profesor = new Profesor(ID, nombres, apellidos, email, tipoContrato);
            inscripciones.inscribir(profesor);
            JOptionPane.showMessageDialog(this, "Profesor inscrito correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al inscribir el estudiante: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarProfesor(double ID, JTextField campoNombre, JTextField campoApellido, JTextField campoCorreo, JTextField campoContrato) {
        inscripciones.cargarDatos(); // Recargar la lista desde la BD

        for (Persona p : inscripciones.listadoPersonas) {
            if (p.getID() == ID && p instanceof Profesor) {
                Profesor profesor = (Profesor) p;
                campoNombre.setText(profesor.getNombres());
                campoApellido.setText(profesor.getApellidos());
                campoCorreo.setText(profesor.getEmail());
                campoContrato.setText(profesor.getTipoContrato());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Profesor no encontrado");
    }
    
    private void modificarProfesor(double ID, String nombres, String apellidos, String email, String tipoContrato) {
        Profesor profesor = new Profesor(ID, nombres, apellidos, email, tipoContrato);
        inscripciones.actualizar(profesor);
        JOptionPane.showMessageDialog(this, "Profesor actualizado correctamente");
    }
    
    private void eliminarProfesor(double ID) {
        for (SqlPersona p : inscripciones.listado) { 
            if (p.getID() == ID && p instanceof Profesor) {
                inscripciones.eliminar(p);
                JOptionPane.showMessageDialog(this, "Profesor eliminado correctamente");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Profesor no encontrado");
    }
}
