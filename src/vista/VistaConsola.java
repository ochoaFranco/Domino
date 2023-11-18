package vista;

import controlador.Controlador;
import modelo.Interfaces.IJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        Font font = new Font("Arial", Font.PLAIN, 16);
        consolaOutput.setFont(font);
        consolaOutput.setText("Ingrese el nombre del jugador");

        inputCMD = new JTextField();
        ejecutarBtn = new JButton("Ejecutar");

        JPanel inputPanel = new JPanel(new BorderLayout()); // se crea un panel del tipo BorderLayout.
        inputPanel.add(inputCMD, BorderLayout.CENTER);
        inputPanel.add(ejecutarBtn, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(consolaOutput), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // FUNCIONALIDAD DEL BOTON
        ejecutarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comando =  inputCMD.getText();
                determinarComando(comando);
            }
        });
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        consolaOutput.append(mensaje);
    }

    @Override
    public void mostrarFichas(IJugador jugador, boolean mostrarNombre) {
        if (mostrarNombre) {
            consolaOutput.append("Jugador" + jugador.getNombre());
        }
        String ficha = "|" + jugador.getUltimaFicha().getIzquierdo() + "|" + jugador.getUltimaFicha().getDerecho() + "|  \n";
        consolaOutput.append(ficha);
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private void determinarComando(String comando) {
        comando = comando.toLowerCase();
        if (comando.startsWith("nombre:")) {
            altaJugador(comando);
        } else if (comando.equals("jugar")) {
            jugar();
        } else if (comando.startsWith("ficha")) {
            jugada(comando);
        }
    }

    private void altaJugador(String nombre) {
        String jugadorNombre = nombre.substring("NOMBRE:".length());
        consolaOutput.append("\nBienvenido " + jugadorNombre + "!\n");
        controlador.conectarJugador(jugadorNombre);
    }

    private void jugar() {
        limpiarPantalla();
        controlador.repartirFichas();
    }

    private void jugada(String comando) {
        String[] partes = comando.split("\\s+"); // ??
        if (partes.length == 3) {
            try {
                // Extract relevant information
                int nroFicha = Integer.parseInt(partes[1]);
                String position = partes[2];


                // Process the information
                consolaOutput.append("Ficha number: " + nroFicha + "\n");
                consolaOutput.append("Position: " + position + "\n");

            } catch (NumberFormatException ex) {
                consolaOutput.append("Formato de ficha invalido (NroFicha I o NroFicha D)");
            }
        } else {
            consolaOutput.append("Formato de ficha invalido (NroFicha I o NroFicha D)");
        }
        limpiarPantalla();
    }


    private void limpiarPantalla() {
        consolaOutput.setText("");
    }
}

