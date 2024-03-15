package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;
import modelo.exceptions.FichaIncorrecta;
import modelo.exceptions.FichaInexistente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class VistaGrafica extends JFrame implements IVista, MouseListener {
    private String nombre;
    private static Controlador controlador;
    private JPanel panel;
    private static int cantMensajes = 0;
    private static IFicha primeraFicha;
    private static int cantClicks = 0;
    private ComponenteJugadorMano jugadorManoComponente;
    private ComponenteTablero componenteTablero;
    private JButton robarBtn;
    private static int offsetFicha = 1;
    JLabel mensaje = new JLabel();

    public VistaGrafica(String nombre) {
        setTitle("Domino");
        this.nombre = nombre;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);

        // creo un panel junto con su background
        panel = Lobby.getjPanel("img/tablero.png");
        panel.setLayout(null);

        // agrego el tablero.
        componenteTablero = new ComponenteTablero();
        componenteTablero.setBounds(0,0, 800, 382);
        panel.add(componenteTablero);

        // agrego boton
        robarBtn = new JButton("Robar");
        robarBtn.setBounds(670, 350, 100, 20);
        panel.add(robarBtn);

        // agrego la seccion de las fichas del jugador.
        jugadorManoComponente = new ComponenteJugadorMano();
        jugadorManoComponente.setBounds(0, 390, 800, 100); // Set position and size
        panel.add(jugadorManoComponente); // Add ComponenteJugadorMano panel

        // tamanio pantalla
        setLocationRelativeTo(null);
        this.getContentPane().add(panel);
        this.addMouseListener(this);

        robarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actualizarManoJugador();
            }
        });
    }

    // se roba una ficha.
    private void actualizarManoJugador() {
        controlador.robarFicha();
    }


    public static int getCantClicks() {
        return cantClicks;
    }
    public static void incrementarClicks() {
        VistaGrafica.cantClicks += 1;
    }
    public static void decrementarClicks() {
        VistaGrafica.cantClicks -=1;
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        // calculo la posicion del mensaj en la pantalla.
        int x = 30;
        int y = 350;
        mostrarMensaje(mensaje, x, y);
    }

    public void mostrarMensaje(String msj, int x, int y) {
        mensaje.setText(msj);
        mensaje.setForeground(Color.black);
        mensaje.setFont(new Font("Arial", Font.BOLD, 18));
        mensaje.setBounds(x, y, 400, 40);
        panel.add(mensaje);
        panel.revalidate();
        panel.repaint();
    }


    // funcionalidad encargada de mostrar las fichas del jugador.
    @Override
    public void mostrarFichasJugador(IJugador jugador)  {
        int x = 10;
        int y = 370;
        mostrarFichasJugador(jugador, x, y);
    }


    public void mostrarFichasJugador(IJugador jugador, int x, int y)  {
        jugadorManoComponente.removeAll();
        ArrayList<IFicha> fichas = controlador.getFichasJugador(jugador);
        for (IFicha ficha: fichas) {
            VistaFicha fichaComponente = new VistaFicha(ficha, true, true, false);
            jugadorManoComponente.add(fichaComponente);
        }
        panel.revalidate();
        panel.repaint();
    }


    //TODO add funcionality for adding new tiles on the board.
    public static void realizarJugada(String extremo, VistaFicha vFicha) {
        IFicha ficha = vFicha.getFicha();
        try {
            VistaGrafica.controlador.gestionarTurnos(ficha.getIzquierdo(), ficha.getDerecho(), extremo);
        } catch (FichaIncorrecta f) {
            System.out.printf("WRONG TILE!!!!\n");
            vFicha.setVisible(true);
            VistaGrafica.decrementarClicks();
        } catch (FichaInexistente i) {
            System.out.printf("The tile does not exist!!!\n");
            vFicha.setVisible(true);
            VistaGrafica.decrementarClicks();
        }
    }


//     muestra la primera ficha.
    @Override
    public void mostrarFicha(IFicha ficha) {
        componenteTablero.removeAll();
        VistaGrafica.primeraFicha = ficha;
        VistaFicha f = new VistaFicha(ficha, false, false, true);
        componenteTablero.agregarFicha(f);
    }

    @Override
    public void mostrarTablero(Object o) {
        int offsetX = 0;
        componenteTablero.removeAll();
        for (IFicha f : (ArrayList<IFicha>) o) {
            VistaFicha vistaFicha = new VistaFicha(f, false, false, false);
            if (f.isDerecho()) {
                offsetX += 20 * VistaGrafica.offsetFicha;

            } else {
                offsetX -= 40;
            }
            componenteTablero.agregarFicha(vistaFicha);
            VistaGrafica.offsetFicha += 1;
        }
        panel.revalidate();
        panel.repaint();
    }
    

    // dado una ficha, la rota y la muestra en las coordenadas indicadas.

    public void jugar() {
        controlador.iniciarJuego();
    }



    @Override
    public void mostrar() {
        setVisible(true);
    }



    @Override
    public void mostrarTablaPuntos(Object o) {

    }

    @Override
    public void setControlador(Controlador controlador) {
        VistaGrafica.controlador = controlador;
        if (nombre != null)
            controlador.conectarJugador(nombre);
    }

    @Override
    public void ocultarBoton() {
    }

    @Override
    public void mostrarBoton() {

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
