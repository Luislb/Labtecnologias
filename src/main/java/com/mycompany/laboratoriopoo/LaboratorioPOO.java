
package com.mycompany.laboratoriopoo;
import javax.swing.SwingUtilities;
import InterfazGrafica.GUI;
import javax.swing.SwingUtilities;

public class LaboratorioPOO {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}

