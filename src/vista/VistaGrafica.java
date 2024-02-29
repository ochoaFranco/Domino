package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;

public class VistaGrafica extends JFrame implements IVista {
    private String nombre;
    private Controlador controlador;

    public VistaGrafica(String nombre) {
        setTitle("Domino");
        this.nombre = nombre;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);

        // creo un panel junto con su backgorund.
        JPanel panel = Lobby.getjPanel();

        this.getContentPane().add(panel);
    }


    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Recibiendo fichas", JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    public void mostrarFichasRecibidas(IJugador jugador) {

    }

    @Override
    public void mostrarFichasJugador(IJugador jugador) {

    }

    @Override
    public void mostrarFicha(IFicha ficha) {

    }

    public void jugar() {
        controlador.iniciarJuego();
    }


    @Override
    public void mostrar() {
        setVisible(true);
    }

    @Override
    public void mostrarTablero(Object o) {

    }

    @Override
    public void mostrarTablaPuntos(Object o) {

    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
        if (nombre != null)
            controlador.conectarJugador(nombre);
    }
}
