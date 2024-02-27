package vista;

import controlador.Controlador;
import modelo.Ficha;
import modelo.IFicha;
import modelo.IJugador;
import modelo.exceptions.FichaIncorrecta;
import modelo.exceptions.FichaInexistente;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
        inputCMD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejadorComandos();
            }
        });

        ejecutarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejadorComandos();
            }
        });
    }

    private void manejadorComandos() {
        String comando =  inputCMD.getText();
        determinarComando(comando);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        consolaOutput.append(mensaje);
    }

    @Override
    public void mostrarFichasRecibidas(IJugador jugador) {
        String ficha = "|" + jugador.getUltimaFicha().getIzquierdo() + "|" + jugador.getUltimaFicha().getDerecho() + "|  \n";
        consolaOutput.append(ficha);
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void mostrarFichasJugador(IJugador jugador) {
        ArrayList<IFicha> fichas = controlador.getFichasJugador(jugador);
        StringBuilder ficha = new StringBuilder();
        for (IFicha f : fichas) {
            ficha.append("|").append(f.getIzquierdo()).append("|").append(f.getDerecho()).append("|").append(" ");
        }
        consolaOutput.append(ficha.toString());
        consolaOutput.append("\n");
    }

    @Override
    public void mostrarFicha(IFicha f) {
        String ficha = "|" + f.getIzquierdo() + "|" + f.getDerecho() + "|  \n";
        consolaOutput.append(ficha);
    }

    private void determinarComando(String comando) {
        comando = comando.toLowerCase();
        if (comando.startsWith("nombre:")) {
            altaJugador(comando);
        } else if (comando.equals("jugar")) {
            jugar();
        } else if (comando.startsWith("ficha:")) {
            jugada(comando);
        } else if (comando.equalsIgnoreCase("robar")) {
            actualizarManoJugador();
        } else if (comando.startsWith("jugadores:")) {
            setearJugadores(comando);
        }
    }

    // el jugador roba una ficha del pozo y se actualiza la mano.
    private void actualizarManoJugador() {
        controlador.robarFicha();
    }

    @SuppressWarnings("unchecked") // elimina el warning del tipo de dato
    public void mostrarTablero(Object o) {
        consolaOutput.append("TABLERO\n");
        consolaOutput.append("-------------------------------------------------------------\n");
        StringBuilder ficha = new StringBuilder();
        String espacios = generarEspacios(54);
        ArrayList<IFicha> FichasVerticalesIzquierdas = new ArrayList<>();
        ArrayList<IFicha> fichasVerticalesDerechas = new ArrayList<>();

        // Busco tiles verticales.
        for (IFicha f : (ArrayList<IFicha>)o) {
            if (f.isVertical()) {
                if (f.isDerecho()) {
                    if (!fichasVerticalesDerechas.contains(f))
                        fichasVerticalesDerechas.add(f);
                } else {
                    if (!FichasVerticalesIzquierdas.contains(f))
                        FichasVerticalesIzquierdas.add(f);
                }
            }
        }

        //Muestro las fichas verticales derechas.
        Collections.reverse(fichasVerticalesDerechas);
        if (!fichasVerticalesDerechas.isEmpty()) {
            for (IFicha f : fichasVerticalesDerechas) {
                ficha.append(espacios).append("|").append(f.getDerecho()).append("|\n").append(espacios + "|").append(f.getIzquierdo()).append("|").append("\n");
            }
        }

        // mostrar ficha horizontal.
        for (IFicha f : (ArrayList<IFicha>)o) {
            if (!f.isVertical()) {
                ficha.append("|").append(f.getIzquierdo()).append("|").append(f.getDerecho()).append("|").append(" ");
            }
        }

        // mostrar fichas verticales izquierdas.
        Collections.reverse(FichasVerticalesIzquierdas);
        if (!FichasVerticalesIzquierdas.isEmpty()) {
            for (IFicha f : FichasVerticalesIzquierdas) {
                ficha.append("\n|").append(f.getDerecho()).append("|\n").append("|").append(f.getIzquierdo()).append("|").append(" ");
            }
        }

        consolaOutput.append(ficha.toString());
        consolaOutput.append("\n-------------------------------------------------------------\n");
    }

    // establezco la cantidad maxima de jugadores.
    private void setearJugadores(String comando) {
        String[] partes = comando.split("\\s+"); // express.regular para separar por caracteres en blanco.
        int numero = 0;
        if (partes.length == 2) {
            try {
                numero = Integer.parseInt(partes[1]);
            } catch (NumberFormatException N) {
                consolaOutput.append("Debe ingresar un numero como valor para jugadores.\n");
            }
        }
        consolaOutput.append("Cantidad de jugadores establecida.\n");
    }


    private void altaJugador(String nombre) {
        String jugadorNombre = nombre.substring("NOMBRE:".length());
        consolaOutput.append("\nBienvenido " + jugadorNombre + "!\n");
        controlador.conectarJugador(jugadorNombre);
    }

    private void jugar() {
        controlador.iniciarJuego();
    }

    // metodo que permite generar espacios.
    private String generarEspacios(int espacios) {
        return String.format("%" + espacios + "s", "");
    }


    private void jugada(String comando) {
        String[] partes = comando.split("\\s+"); // express.regular para separar por caracteres en blanco.
        if (partes.length == 4) {
            try {
                // Obtener informacion de la ficha.
                int izq = Integer.parseInt(partes[1]);
                int der = Integer.parseInt(partes[2]);
                String extremo = partes[3];
                try {
                    controlador.gestionarTurnos(izq, der, extremo);
                } catch (FichaIncorrecta f) {
                    consolaOutput.append("Ficha incorrecta!!! \n");
                }

            } catch (NumberFormatException ex) {
                consolaOutput.append("Formato de ficha invalido (Ficha I o Ficha D)\n");
            } catch (FichaInexistente e ) {
                consolaOutput.append("Ficha inexistente!\n");
            }
        } else {
            consolaOutput.append("Formato de ficha invalido (Ficha I o Ficha D)\n");
        }
    }
    @SuppressWarnings("unchecked") // elimina el warning del tipo de dato
    public void mostrarTablaPuntos(Object o) {
        for (IJugador f : (ArrayList<IJugador>)o) {
            consolaOutput.append("Jugador: " + f.getNombre() + " Puntos: " + f.getPuntos() + "\n");
        }
        consolaOutput.append("\n");
    }
}

