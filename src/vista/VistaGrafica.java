package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VistaGrafica extends JFrame implements IVista {
    private String nombre;
    private Controlador controlador;
    private JPanel panel;
    public VistaGrafica(String nombre) {
        setTitle("Domino");
        this.nombre = nombre;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);

        // creo un panel junto con su backgorund.
        panel = Lobby.getjPanel("img/tablero.png");
        panel.setLayout(null);


        this.getContentPane().add(panel);
    }



    @Override
    public void mostrarMensaje(String mensaje) {
        JLabel lbl = new JLabel(mensaje);
        lbl.setForeground(Color.black);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT); // alinear el lbl al centro.
        panel.add(lbl);

    }

    @Override
    public void mostrarFichasRecibidas(IJugador jugador) {

    }

    @Override
    public void mostrarFichasJugador(IJugador jugador)  {
        ArrayList<IFicha> fichas = controlador.getFichasJugador(jugador);

        for (IFicha ficha: fichas) {
            VistaFicha fichaComponente = new VistaFicha(ficha);
            panel.add(fichaComponente);
        }

        panel.revalidate();
        panel.repaint();

    }

    // muestra la primera ficha.
    @Override
    public void mostrarFicha(IFicha ficha) {
        VistaFicha f = new VistaFicha(ficha);
        f.setBounds(300, 100, 100, 100);
        panel.add(f);
        panel.revalidate();
        panel.repaint();
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
