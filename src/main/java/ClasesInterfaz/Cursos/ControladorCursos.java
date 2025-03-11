
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
        public JPanel panelCurso;
        private Connection connection;
        private CursosInscritos cursosInscritos;
        private CursosProfesores cursosProfesores;
        private BusquedasPersonas busquedasPersonas;

        public ControladorCursos(Connection connection, CursosInscritos cursosInscritos, CursosProfesores cursosProfesores, BusquedasPersonas busquedasPersonas) {
            this.connection = connection;
            this.cursosInscritos = cursosInscritos;
            this.cursosProfesores = cursosProfesores;
            this.busquedasPersonas = busquedasPersonas;
            this.panelCurso = new JPanel(new BorderLayout());
        }
        
        public void configurarPanelCursos() {
            panelCurso.setLayout(new BorderLayout());

            JPanel panelFormulario = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;

            JLabel etiquetaID = new JLabel("ID Curso:");
            JTextField campoID = new JTextField(10);
            JButton botonBuscar = new JButton("Buscar");

            JLabel etiquetaNombre = new JLabel("Nombre:");
            JTextField campoNombre = new JTextField(20);
            campoNombre.setEditable(true);

            JLabel etiquetaPrograma = new JLabel("Programa:");
            JTextField campoPrograma = new JTextField(20);
            campoPrograma.setEditable(true);

            JLabel etiquetaActivo = new JLabel("Activo:");
            JCheckBox checkActivo = new JCheckBox();

            JButton botonAgregar = new JButton("Agregar");
            JButton botonActualizar = new JButton("Modificar");
            JButton botonEliminar = new JButton("Eliminar");


            gbc.gridx = 0; gbc.gridy = 0;
            panelFormulario.add(etiquetaID, gbc);
            gbc.gridx = 1;
            panelFormulario.add(campoID, gbc);
            gbc.gridx = 2;
            panelFormulario.add(botonBuscar, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panelFormulario.add(etiquetaNombre, gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            panelFormulario.add(campoNombre, gbc);
            gbc.gridwidth = 1;

            gbc.gridx = 0; gbc.gridy = 2;
            panelFormulario.add(etiquetaPrograma, gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            panelFormulario.add(campoPrograma, gbc);
            gbc.gridwidth = 1;

            gbc.gridx = 0; gbc.gridy = 3;
            panelFormulario.add(etiquetaActivo, gbc);
            gbc.gridx = 1;
            panelFormulario.add(checkActivo, gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            panelFormulario.add(botonAgregar, gbc);
            gbc.gridx = 1;
            panelFormulario.add(botonActualizar, gbc);
            gbc.gridx = 2;
            panelFormulario.add(botonEliminar, gbc);


            JLabel etiquetaEstudianteID = new JLabel("ID Estudiante:");
            JTextField campoEstudianteID = new JTextField(10);
            JButton botonBuscarEstudiante = new JButton("Buscar Estudiante");

            JLabel etiquetaNombreEstudiante = new JLabel("Nombre:");
            JTextField campoNombreEstudiante = new JTextField(20);
            campoNombreEstudiante.setEditable(false);

            JButton botonInscribirEstudiante = new JButton("Inscribir Estudiante");
            JButton botonEliminarInscripcionEstudiante = new JButton("Eliminar Inscripción Estudiante");


            JLabel etiquetaProfesorID = new JLabel("ID Profesor:");
            JTextField campoProfesorID = new JTextField(10);
            JButton botonBuscarProfesor = new JButton("Buscar Profesor");

            JLabel etiquetaNombreProfesor = new JLabel("Nombre:");
            JTextField campoNombreProfesor = new JTextField(20);
            campoNombreProfesor.setEditable(false);

            JButton botonInscribirProfesor = new JButton("Inscribir Profesor");
            JButton botonEliminarInscripcionProfesor = new JButton("Eliminar Inscripción Profesor");

            gbc.gridx = 0; gbc.gridy = 5;
            panelFormulario.add(etiquetaEstudianteID, gbc);
            gbc.gridx = 1;
            panelFormulario.add(campoEstudianteID, gbc);
            gbc.gridx = 2;
            panelFormulario.add(botonBuscarEstudiante, gbc);

            gbc.gridx = 0; gbc.gridy = 6;
            panelFormulario.add(etiquetaNombreEstudiante, gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            panelFormulario.add(campoNombreEstudiante, gbc);
            gbc.gridwidth = 1;

            gbc.gridx = 0; gbc.gridy = 7;
            panelFormulario.add(botonInscribirEstudiante, gbc);
            gbc.gridx = 1;
            panelFormulario.add(botonEliminarInscripcionEstudiante, gbc);

            gbc.gridx = 0; gbc.gridy = 8;
            panelFormulario.add(etiquetaProfesorID, gbc);
            gbc.gridx = 1;
            panelFormulario.add(campoProfesorID, gbc);
            gbc.gridx = 2;
            panelFormulario.add(botonBuscarProfesor, gbc);

            gbc.gridx = 0; gbc.gridy = 9;
            panelFormulario.add(etiquetaNombreProfesor, gbc);
            gbc.gridx = 1; gbc.gridwidth = 2;
            panelFormulario.add(campoNombreProfesor, gbc);
            gbc.gridwidth = 1;

            gbc.gridx = 0; gbc.gridy = 10;
            panelFormulario.add(botonInscribirProfesor, gbc);
            gbc.gridx = 1;
            panelFormulario.add(botonEliminarInscripcionProfesor, gbc);

            panelCurso.add(panelFormulario, BorderLayout.NORTH);
            panelCurso.revalidate();
            panelCurso.repaint();

            botonBuscar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(campoID.getText().trim());
                        cursosInscritos.cargarDatos();
                        Curso curso = cursosInscritos.buscarCursoPorID(id);

                        if (curso != null) {

                            campoNombre.setText(curso.getNombre());
                            campoPrograma.setText(curso.getPrograma().getNombre());
                            checkActivo.setSelected(curso.isActivo());
                        } else {
                            JOptionPane.showMessageDialog(panelCurso, "Curso no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            botonAgregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(campoID.getText().trim());
                        String nombre = campoNombre.getText().trim();
                        String nombrePrograma = campoPrograma.getText().trim();
                        boolean activo = checkActivo.isSelected();
                    
                        if (nombre.isEmpty() || nombrePrograma.isEmpty()) {
                            JOptionPane.showMessageDialog(panelCurso, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Programa programa = busquedasPersonas.obtenerProgramaPorNombre(nombrePrograma);
                        if (programa == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El programa no existe. Verifique el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Curso nuevoCurso = new Curso(id, nombre, programa, activo);
                        cursosInscritos.inscribirCurso(nuevoCurso);
                        JOptionPane.showMessageDialog(panelCurso, "Curso agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                        campoID.setText("");
                        campoNombre.setText("");
                        campoPrograma.setText("");
                        checkActivo.setSelected(false);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            botonActualizar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(campoID.getText().trim());
                        String nuevoNombre = campoNombre.getText().trim();
                        String nombrePrograma = campoPrograma.getText().trim();
                        boolean activo = checkActivo.isSelected();

                        if (nuevoNombre.isEmpty() || nombrePrograma.isEmpty()) {
                            JOptionPane.showMessageDialog(panelCurso, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Curso cursoExistente = cursosInscritos.buscarCursoPorID(id);
                        if (cursoExistente == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El curso con el ID especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Programa programa = busquedasPersonas.obtenerProgramaPorNombre(nombrePrograma);
                        if (programa == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El programa no existe. Verifique el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Curso cursoActualizado = new Curso(id, nuevoNombre, programa, activo);
                        cursosInscritos.actualizarCurso(cursoActualizado);
                        JOptionPane.showMessageDialog(panelCurso, "Curso actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            botonEliminar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(campoID.getText().trim());
                        int confirm = JOptionPane.showConfirmDialog(panelCurso, 
                            "¿Está seguro de que desea eliminar este curso?", 
                            "Confirmar eliminación", 
                            JOptionPane.YES_NO_OPTION);

                        if (confirm != JOptionPane.YES_OPTION) {
                            return;
                        }
                        Curso cursoExistente = cursosInscritos.buscarCursoPorID(id);
                        if (cursoExistente == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El curso con el ID especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        cursosInscritos.eliminarCurso(id);
                        JOptionPane.showMessageDialog(panelCurso, "Curso eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        campoID.setText("");
                        campoNombre.setText("");
                        campoPrograma.setText("");
                        checkActivo.setSelected(false);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            botonBuscarEstudiante.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int idEstudiante = Integer.parseInt(campoEstudianteID.getText().trim());

                        Persona estudiante = busquedasPersonas.buscarEstudiantePorID(idEstudiante);
                        if (estudiante != null && estudiante instanceof Estudiante) {
                            campoNombreEstudiante.setText(estudiante.getNombres());
                        } else {
                            JOptionPane.showMessageDialog(panelCurso, "Estudiante no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            botonBuscarProfesor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int idProfesor = Integer.parseInt(campoProfesorID.getText().trim());

                        Persona profesor = busquedasPersonas.buscarProfesorPorID(idProfesor);

                        if (profesor != null) {
                            campoNombreProfesor.setText(profesor.getNombres() + " " + profesor.getApellidos());
                        } else {
                            JOptionPane.showMessageDialog(panelCurso, "Profesor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            botonInscribirEstudiante.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int estudianteID = Integer.parseInt(campoEstudianteID.getText().trim());
                        int cursoID = Integer.parseInt(campoID.getText().trim());

                        Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
                        if (curso == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Persona estudiante = busquedasPersonas.buscarEstudiantePorID(estudianteID);
                        if (estudiante == null || !(estudiante instanceof Estudiante)) {
                            JOptionPane.showMessageDialog(panelCurso, "El estudiante no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }


                        cursosInscritos.inscribirEstudianteEnCurso(estudianteID, cursoID);
                        JOptionPane.showMessageDialog(panelCurso, "Estudiante inscrito en el curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            botonEliminarInscripcionEstudiante.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int estudianteID = Integer.parseInt(campoEstudianteID.getText().trim());
                        int cursoID = Integer.parseInt(campoID.getText().trim());


                        Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
                        if (curso == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Persona estudiante = busquedasPersonas.buscarEstudiantePorID(estudianteID);
                        if (estudiante == null || !(estudiante instanceof Estudiante)) {
                            JOptionPane.showMessageDialog(panelCurso, "El estudiante no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        cursosInscritos.eliminarInscripcionEstudiante(estudianteID, cursoID);
                        JOptionPane.showMessageDialog(panelCurso, "Estudiante eliminado del curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            botonInscribirProfesor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int profesorID = Integer.parseInt(campoProfesorID.getText().trim());
                        int cursoID = Integer.parseInt(campoID.getText().trim());

                        Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
                        if (curso == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Persona profesor = busquedasPersonas.buscarProfesorPorID(profesorID);
                        if (profesor == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El profesor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (cursosProfesores.cursoTieneProfesor(cursoID)) {
                            JOptionPane.showMessageDialog(panelCurso, "Este curso ya tiene un profesor asignado.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        cursosProfesores.inscribirProfesorEnCurso(profesorID, cursoID);
                        JOptionPane.showMessageDialog(panelCurso, "Profesor inscrito en el curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            botonEliminarInscripcionProfesor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int profesorID = Integer.parseInt(campoProfesorID.getText().trim());
                        int cursoID = Integer.parseInt(campoID.getText().trim());

                        Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
                        if (curso == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Persona profesor = busquedasPersonas.buscarProfesorPorID(profesorID);
                        if (profesor == null) {
                            JOptionPane.showMessageDialog(panelCurso, "El profesor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        cursosProfesores.eliminarInscripcionProfesor(profesorID, cursoID);
                        JOptionPane.showMessageDialog(panelCurso, "Profesor eliminado del curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelCurso, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
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