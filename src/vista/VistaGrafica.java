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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VistaGrafica extends JFrame implements IVista, MouseListener {
    private String nombre;
    private static Controlador controlador;
    private JPanel panel;
    private static IFicha primeraFicha;
    private static int cantClicks = 0;
    private ComponenteJugadorMano jugadorManoComponente;
    private ComponenteTablero componenteTablero;
    private JButton robarBtn;
    private static int offsetFicha = 1;
    private static int posicionX = 350;
    private static int posicionY = 150;
    JLabel mensaje = new JLabel();

    public VistaGrafica(String nombre) {
        setTitle("Domino");
        this.nombre = nombre;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 558);
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
        jugadorManoComponente.setBounds(0, 390, 800, 200); // Set position and size
        panel.add(jugadorManoComponente);

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
        mensaje.setBounds(x, y, 800, 40);
        panel.add(mensaje);
        panel.revalidate();
        panel.repaint();
    }

    // funcionalidad encargada de mostrar las fichas del jugador.
    @Override
    public void mostrarFichasJugador(IJugador jugador)  {
        jugadorManoComponente.removeAll();
        ArrayList<IFicha> fichas = controlador.getFichasJugador(jugador);
        for (IFicha ficha: fichas) {
            VistaFicha fichaComponente = new VistaFicha(ficha, true, true, false);
            jugadorManoComponente.agregarFichaJugador(fichaComponente);
        }
        panel.revalidate();
        panel.repaint();
    }

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
        componenteTablero.limpiarFicha();
        VistaGrafica.primeraFicha = ficha;
        VistaFicha f = new VistaFicha(ficha, false, false, true);
        rotarFicha(ficha, f, false, false);
        componenteTablero.agregarFicha(f);
        componenteTablero.revalidate();
        componenteTablero.repaint();
    }
    @Override
    public void mostrarTablero(Object o) {
        componenteTablero.limpiarFicha();
        List<IFicha> fichas = (ArrayList<IFicha>) o;
        List<IFicha> fichasVerticales = buscarFichasVerticales(fichas);
        Collections.reverse(fichasVerticales);

        // itero sobre las fichas para agregarlas al tablero.
        for (IFicha f : fichas) {
            if (!(f.isVertical() && f.isIzquierdo())) {
                VistaFicha vistaFicha = new VistaFicha(f, false, false, false);
                boolean rotar = f.isVertical();
                rotarFicha(f, vistaFicha, rotar, false);
                componenteTablero.agregarFicha(vistaFicha);
            }
        }
        // itero sobre las fichas verticales izquierdas para agregarlas al tablero.
        for (IFicha f : fichasVerticales) {
            VistaFicha vistaFicha = new VistaFicha(f, false, false, false);
            rotarFicha(f, vistaFicha, true, componenteTablero.rotarHorizontalesArriba());
            componenteTablero.agregarFicha(vistaFicha);
        }
        componenteTablero.revalidate();
        componenteTablero.repaint();
    }

    // Dado una listas de fichas retorna una nueva lista con fichas verticales.
    private List<IFicha> buscarFichasVerticales(List<IFicha> fichas) {
        List<IFicha> fichasVerticales = new ArrayList<IFicha>();
        for (IFicha f: fichas) {
            if (f.isVertical() && f.isIzquierdo())
                fichasVerticales.add(f);
        }
        return fichasVerticales;
    }

    // dado una ficha, la rota y la muestra en las coordenadas indicadas.
    private static void rotarFicha(IFicha f, VistaFicha vistaFicha, boolean rotar, boolean rotarHorizontales) {
        if (!f.esFichaDoble()) {
            if (!rotar) {
                // roto la ficha dependiendo si esta dada vuelta o no.
                vistaFicha.setAnguloRotacion(f.isDadaVuelta() ? 90 : -90);
            } else {
                if (f.isDadaVuelta() && !rotarHorizontales) // si se debe rotar, se gira la ficha 180 grados.
                    vistaFicha.setAnguloRotacion(180);
                else if (rotarHorizontales && !f.isDadaVuelta()) {
                    vistaFicha.setAnguloRotacion(90);
                    System.out.printf("FIcha:" + f.getIzquierdo() + "|" + f.getDerecho() + "\n");
                }
                else if (f.isDadaVuelta() && rotarHorizontales) {
                    vistaFicha.setAnguloRotacion(-90);
                    System.out.printf("FIcha:" + f.getIzquierdo() + "|" + f.getDerecho() + "\n");
                }
            }
        } else if (rotar) {
            if (!rotarHorizontales)
                vistaFicha.setAnguloRotacion(f.isDadaVuelta() ? 90 : -90);
            else
                vistaFicha.setAnguloRotacion(360);
        }



    }

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
        robarBtn.setVisible(false);
    }

    @Override
    public void mostrarBoton() {
        robarBtn.setVisible(true);
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
