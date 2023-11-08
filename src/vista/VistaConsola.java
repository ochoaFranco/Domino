package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class VistaConsola implements IVista {
    private Controlador controlador;
    private final JFrame frame;
    private JTextArea consolaOutput;
    private JTextField inputCMD;
    private JButton ejecutarBtn;

    public VistaConsola() {
        frame = new JFrame("Domino");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        consolaOutput = new JTextArea();
        consolaOutput.setEditable(false);

        inputCMD = new JTextField();
        ejecutarBtn = new JButton("Ejecutar");

        JPanel inputPanel = new JPanel(new BorderLayout()); // se crea un panel del tipo BorderLayout.
        inputPanel.add(inputCMD, BorderLayout.CENTER);
        inputPanel.add(ejecutarBtn, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(consolaOutput), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        //frame.setVisible(true);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    @Override
    public void mostrarMensaje(String mensaje) {

    }

    @Override
    public void mostrarFichas(Object jugador) {

    }

    @Override
    public void setControlador(Controlador controlador) {

    }
}
