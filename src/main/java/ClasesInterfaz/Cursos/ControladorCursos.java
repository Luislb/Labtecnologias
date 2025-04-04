
package ClasesInterfaz.Cursos;
import com.mycompany.laboratoriopoo.Clases.Curso;
import com.mycompany.laboratoriopoo.Clases.CursosInscritos;
import com.mycompany.laboratoriopoo.Clases.CursosProfesores;
import com.mycompany.laboratoriopoo.Clases.Estudiante;
import com.mycompany.laboratoriopoo.Clases.InscripcionesPersonas;
import com.mycompany.laboratoriopoo.Clases.Persona;
import com.mycompany.laboratoriopoo.Clases.Profesor;
import com.mycompany.laboratoriopoo.Clases.Programa;
import ClasesBusquedas.BusquedasPersonas;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author klmxl
 */
public class ControladorCursos extends JFrame{
        public JPanel coursePanel;
        private Connection connection;
        private CursosInscritos cursosInscritos;
        private CursosProfesores cursosProfesores;
        private BusquedasPersonas busquedasPersonas;

        public ControladorCursos(Connection connection, CursosInscritos cursosInscritos, CursosProfesores cursosProfesores, BusquedasPersonas busquedasPersonas) {
            this.connection = connection;
            this.cursosInscritos = cursosInscritos;
            this.cursosProfesores = cursosProfesores;
            this.busquedasPersonas = busquedasPersonas;
            this.coursePanel = new JPanel(new BorderLayout());
        }
        
        public void setupCoursePanel() {
            coursePanel.setLayout(new BorderLayout());

            JPanel formPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;

            JLabel idLabel = new JLabel("ID Curso:");
            JTextField idField = new JTextField(10);
            JButton searchButton = new JButton("Buscar");

            JLabel nameLabel = new JLabel("Nombre:");
            JTextField nameField = new JTextField(20);
            nameField.setEditable(true);

            JLabel programLabel = new JLabel("Programa:");
            JTextField programField = new JTextField(20);
            programField.setEditable(true);

            JLabel activeLabel = new JLabel("Activo:");
            JCheckBox activeCheckBox = new JCheckBox();

            JButton addButton = new JButton("Agregar");
            JButton updateButton = new JButton("Modificar");
            JButton deleteButton = new JButton("Eliminar");


            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(idLabel, gbc);
            gbc.gridx = 1;
            formPanel.add(idField, gbc);
            gbc.gridx = 2;
            formPanel.add(searchButton, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            formPanel.add(nameLabel, gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            formPanel.add(nameField, gbc);
            gbc.gridwidth = 1;

            gbc.gridx = 0; gbc.gridy = 2;
            formPanel.add(programLabel, gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            formPanel.add(programField, gbc);
            gbc.gridwidth = 1;

            gbc.gridx = 0; gbc.gridy = 3;
            formPanel.add(activeLabel, gbc);
            gbc.gridx = 1;
            formPanel.add(activeCheckBox, gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            formPanel.add(addButton, gbc);
            gbc.gridx = 1;
            formPanel.add(updateButton, gbc);
            gbc.gridx = 2;
            formPanel.add(deleteButton, gbc);

            JLabel professorIdLabel = new JLabel("ID Profesor:");
            JTextField professorIdField = new JTextField(10);
            JButton searchProfessorButton = new JButton("Buscar Profesor");

            JLabel professorNameLabel = new JLabel("Nombre:");
            JTextField professorNameField = new JTextField(20);
            professorNameField.setEditable(false);

            JButton enrollProfessorButton = new JButton("Inscribir Profesor");
            JButton removeProfessorEnrollmentButton = new JButton("Eliminar Inscripción Profesor");

            gbc.gridx = 0; gbc.gridy = 8;
            formPanel.add(professorIdLabel, gbc);
            gbc.gridx = 1;
            formPanel.add(professorIdField, gbc);
            gbc.gridx = 2;
            formPanel.add(searchProfessorButton, gbc);

            gbc.gridx = 0; gbc.gridy = 9;
            formPanel.add(professorNameLabel, gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            formPanel.add(professorNameField, gbc);
            gbc.gridwidth = 1;

            gbc.gridx = 0; gbc.gridy = 10;
            formPanel.add(enrollProfessorButton, gbc);
            gbc.gridx = 1;
            formPanel.add(removeProfessorEnrollmentButton, gbc);

            coursePanel.add(formPanel, BorderLayout.NORTH);
            coursePanel.revalidate();
            coursePanel.repaint();

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(idField.getText().trim());
                        cursosInscritos.cargarDatos();
                        Curso curso = cursosInscritos.buscarCursoPorID(id);

                        if (curso != null) {

                            nameField.setText(curso.getNombre());
                            programField.setText(curso.getPrograma().getNombre());
                            activeCheckBox.setSelected(curso.isActivo());
                        } else {
                            JOptionPane.showMessageDialog(coursePanel, "Curso no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(idField.getText().trim());
                        String nombre = nameField.getText().trim();
                        String nombrePrograma = programField.getText().trim();
                        boolean activo = activeCheckBox.isSelected();
                    
                        if (nombre.isEmpty() || nombrePrograma.isEmpty()) {
                            JOptionPane.showMessageDialog(coursePanel, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Programa programa = busquedasPersonas.obtenerProgramaPorNombre(nombrePrograma);
                        if (programa == null) {
                            JOptionPane.showMessageDialog(coursePanel, "El programa no existe. Verifique el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Curso nuevoCurso = new Curso(id, nombre, programa, activo);
                        cursosInscritos.inscribirCurso(nuevoCurso);
                        JOptionPane.showMessageDialog(coursePanel, "Curso agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                        idField.setText("");
                        nameField.setText("");
                        programField.setText("");
                        activeCheckBox.setSelected(false);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(idField.getText().trim());
                        String nuevoNombre = nameField.getText().trim();
                        String nombrePrograma = programField.getText().trim();
                        boolean activo = activeCheckBox.isSelected();

                        if (nuevoNombre.isEmpty() || nombrePrograma.isEmpty()) {
                            JOptionPane.showMessageDialog(coursePanel, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Curso cursoExistente = cursosInscritos.buscarCursoPorID(id);
                        if (cursoExistente == null) {
                            JOptionPane.showMessageDialog(coursePanel, "El curso con el ID especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Programa programa = busquedasPersonas.obtenerProgramaPorNombre(nombrePrograma);
                        if (programa == null) {
                            JOptionPane.showMessageDialog(coursePanel, "El programa no existe. Verifique el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Curso cursoActualizado = new Curso(id, nuevoNombre, programa, activo);
                        cursosInscritos.actualizarCurso(cursoActualizado);
                        JOptionPane.showMessageDialog(coursePanel, "Curso actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(idField.getText().trim());
                        int confirm = JOptionPane.showConfirmDialog(coursePanel, 
                            "¿Está seguro de que desea eliminar este curso?", 
                            "Confirmar eliminación", 
                            JOptionPane.YES_NO_OPTION);

                        if (confirm != JOptionPane.YES_OPTION) {
                            return;
                        }
                        Curso cursoExistente = cursosInscritos.buscarCursoPorID(id);
                        if (cursoExistente == null) {
                            JOptionPane.showMessageDialog(coursePanel, "El curso con el ID especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        cursosInscritos.eliminarCurso(id);
                        JOptionPane.showMessageDialog(coursePanel, "Curso eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        idField.setText("");
                        nameField.setText("");
                        programField.setText("");
                        activeCheckBox.setSelected(false);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            searchProfessorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int idProfesor = Integer.parseInt(professorIdField.getText().trim());

                        Persona profesor = busquedasPersonas.buscarProfesorPorID(idProfesor);

                        if (profesor != null) {
                            professorNameField.setText(profesor.getNombres() + " " + profesor.getApellidos());
                        } else {
                            JOptionPane.showMessageDialog(coursePanel, "Profesor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            enrollProfessorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int profesorID = Integer.parseInt(professorIdField.getText().trim());
                        int cursoID = Integer.parseInt(idField.getText().trim());

                        Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
                        if (curso == null) {
                            JOptionPane.showMessageDialog(coursePanel, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Persona profesor = busquedasPersonas.buscarProfesorPorID(profesorID);
                        if (profesor == null) {
                            JOptionPane.showMessageDialog(coursePanel, "El profesor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (cursosProfesores.cursoTieneProfesor(cursoID)) {
                            JOptionPane.showMessageDialog(coursePanel, "Este curso ya tiene un profesor asignado.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        cursosProfesores.inscribirProfesorEnCurso(profesorID, cursoID);
                        JOptionPane.showMessageDialog(coursePanel, "Profesor inscrito en el curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(coursePanel, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            removeProfessorEnrollmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int profesorID = Integer.parseInt(professorIdField.getText().trim());
                        int cursoID = Integer.parseInt(idField.getText().trim());

                        Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
                        if (curso == null) {
                            JOptionPane.showMessageDialog(coursePanel, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Persona profesor = busquedasPersonas.buscarProfesorPorID(profesorID);
                        if (profesor == null) {
                            JOptionPane.showMessageDialog(coursePanel, "El profesor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        cursosProfesores.eliminarInscripcionProfesor(profesorID, cursoID);
                        JOptionPane.showMessageDialog(coursePanel, "Profesor eliminado del curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(coursePanel, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        }

        private void addCourse(int id, String nombre, String programa, boolean activo) {
            Programa prog = busquedasPersonas.obtenerProgramaPorNombre(programa);
            if (prog == null) {
                JOptionPane.showMessageDialog(this, "Programa no encontrado.");
                return;
            }

            Curso nuevoCurso = new Curso(id, nombre, prog, activo);
            cursosInscritos.inscribirCurso(nuevoCurso);
            JOptionPane.showMessageDialog(this, "Curso agregado correctamente.");
        }
    }
