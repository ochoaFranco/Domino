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
    private static int cantMensajes = 0;
    private static IFicha primeraFicha;

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
        // calculo la posicion del mensaj en la pantalla.
        int x = 30;
        int y = 200 + cantMensajes * 50;
        mostrarMensaje(mensaje, x, y);
        VistaGrafica.cantMensajes += 1;
    }

    public void mostrarMensaje(String mensaje, int x, int y) {
        JLabel lbl = new JLabel(mensaje);
        lbl.setForeground(Color.black);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setBounds(x, y, 400, 40);
        panel.add(lbl);
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void mostrarFichasRecibidas(IJugador jugador) {

    }

    // funcionalidad encargada de mostrar las fichas del jugador.
    @Override
    public void mostrarFichasJugador(IJugador jugador)  {
        int x = 10;
        int y = 400;
        mostrarFichasJugador(jugador, x, y);
    }

    public void mostrarFichasJugador(IJugador jugador, int x, int y)  {
        ArrayList<IFicha> fichas = controlador.getFichasJugador(jugador);
        int i = 0;
        for (IFicha ficha: fichas) {
            VistaFicha fichaComponente = new VistaFicha(ficha);
            fichaComponente.setBounds(x + i, y, 50, 100);
            panel.add(fichaComponente);
            i += 35;
        }
        panel.revalidate();
        panel.repaint();

    }



    // muestra la primera ficha.
    @Override
    public void mostrarFicha(IFicha ficha) {
        VistaGrafica.primeraFicha = ficha;
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
