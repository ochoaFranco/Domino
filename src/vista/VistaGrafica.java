package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;
import modelo.exceptions.FichaIncorrecta;
import modelo.exceptions.FichaInexistente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VistaGrafica extends JFrame implements IVista, MouseListener {
    private final String nombre;
    private static Controlador controlador;
    private JPanel panel;
    private static IFicha primeraFicha;
    private static int cantClicks = 0;
    private final ComponenteJugadorMano jugadorManoComponente;
    private final ComponenteTablero componenteTablero;
    private final JButton robarBtn;
    private final JLabel mensaje = new JLabel();
    private final JLabel lblJ1Nombre = new JLabel();
    private final JLabel lblJ2Nombre = new JLabel();
    private final JLabel lblJ1Pts = new JLabel();
    private JLabel lblJ2Pts = new JLabel();
    private final List<MouseListener> mouseListenersGuardados = new ArrayList<>();

    public VistaGrafica(String nombre, Controlador controlador) {
        VistaGrafica.controlador = controlador;
        setTitle("Domino");
        this.nombre = nombre;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 650);
        setResizable(false);

        // desconecta al jugador cuando cierra la ventana.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controlador.desconectarJugador();
                System.exit(0);
            }
        });

        // creo un panel junto con su background
        panel = Lobby.getjPanel("img/tablero.png");
        panel.setLayout(null);

        // conecto el jugador al controlador.
        controlador.conectarJugador(nombre);

        // agrego el tablero.
        componenteTablero = new ComponenteTablero();
        componenteTablero.setBounds(0,0, 1200, 650);
        panel.add(componenteTablero);

        // agrego boton
        robarBtn = new JButton("Robar");
        robarBtn.setBounds(1000, 500, 100, 20);
        panel.add(robarBtn);

        // agrego la seccion de las fichas del jugador.
        jugadorManoComponente = new ComponenteJugadorMano();
        jugadorManoComponente.setBounds(0, 450, 800, 200); // Set position and size
        panel.add(jugadorManoComponente);

        // tamanio pantalla
        setLocationRelativeTo(null);
        this.getContentPane().add(panel);
        this.addMouseListener(this);
        robarBtn.addActionListener(actionEvent -> actualizarManoJugador());
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
        int y = 400;
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
        List<IFicha> fichas = controlador.getFichasJugador(jugador);
        System.out.println("TILES: " + fichas + "\n");
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
            JOptionPane.showMessageDialog(null, "Ficha Incorrecta!!!", "Error", JOptionPane.ERROR_MESSAGE);
            vFicha.setVisible(true);
            VistaGrafica.decrementarClicks();
        } catch (FichaInexistente i) {
            JOptionPane.showMessageDialog(null, "Espera tu turno!!!", "Error", JOptionPane.ERROR_MESSAGE);
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
        rotarFicha(ficha, f, false, false, false, false, false);
        componenteTablero.agregarFicha(f);
        componenteTablero.revalidate();
        componenteTablero.repaint();
    }

    @Override
    public void mostrarTablero(Object o) {
        limpiarTablero();
        List<IFicha> fichas = (ArrayList<IFicha>) o;
        List<IFicha> fichasVerticales = buscarFichasVerticales(fichas);
        Collections.reverse(fichasVerticales);

        // itero sobre las fichas derechas para agregarlas al tablero.
        for (IFicha f : fichas) {
            VistaFicha vFicha;
            boolean rotar;
            // agrega fichas centrales.
            boolean rotarHorizontAbaj = componenteTablero.rotarHorizontalesAbajo();
            if (!(f.isVertical() && f.isIzquierdo()) && !rotarHorizontAbaj) {
                vFicha = new VistaFicha(f, false, false, false);
                rotar = f.isVertical();
                rotarFicha(f, vFicha, rotar, false, false, false, false);
                componenteTablero.agregarFicha(vFicha);

                // agrega fichas derechas panel horizontal inferior.
            } else if (f.isVertical() && f.isDerecho()) {
                vFicha = new VistaFicha(f, false, false, false);
                rotar = f.isVertical();
                boolean rotarVerticalAbajo = componenteTablero.rotarVerticalesAbajo();
                boolean rotarHorizontalAbajo = componenteTablero.rotarHorizontalesAbajo();
                boolean rotarHorizontalAbajoDer = componenteTablero.rotarHorizontalAbajoDerecha();
                rotarFicha(f, vFicha, rotar, false, rotarHorizontalAbajo,  rotarVerticalAbajo, rotarHorizontalAbajoDer);
                componenteTablero.agregarFicha(vFicha);
            }
        }

        // itero sobre las fichas verticales izquierdas para agregarlas al tablero.
        for (IFicha f : fichasVerticales) {
            VistaFicha vistaFicha = new VistaFicha(f, false, false, false);
            rotarFicha(f, vistaFicha, true, componenteTablero.rotarHorizontalesArriba(),
                    false, false, false);
            componenteTablero.agregarFicha(vistaFicha);
        }
        componenteTablero.revalidate();
        componenteTablero.repaint();
    }

    // Dado una listas de fichas retorna una nueva lista con fichas verticales.
    private List<IFicha> buscarFichasVerticales(List<IFicha> fichas) {
        List<IFicha> fichasVerticales = new ArrayList<>();
        for (IFicha f: fichas) {
            if (f.isVertical() && f.isIzquierdo())
                fichasVerticales.add(f);
        }
        return fichasVerticales;
    }

    // dado una ficha, la rota y la muestra en las coordenadas indicadas.
    private static void rotarFicha(IFicha f,
                                   VistaFicha vistaFicha,
                                   boolean rotar,
                                   boolean rotarHorizontales,
                                   boolean rotarHorizAbajo,
                                   boolean rotarVerticalAbajo,
                                   boolean rotarHorizontalAbajoDer) {
        if (!f.esFichaDoble()) {
            if (!rotar) {
                // roto la ficha dependiendo si esta dada vuelta o no.
                vistaFicha.setAnguloRotacion(f.isDadaVuelta() ? 90 : -90);
            } else {
                if (!rotarHorizAbajo) {
                    // si se debe rotar, se jira la ficha 180 grados.
                    if (f.isDadaVuelta() && !rotarHorizontales)
                        vistaFicha.setAnguloRotacion(180);
                     else if (rotarHorizontales && !f.isDadaVuelta())
                        vistaFicha.setAnguloRotacion(90);
                    else if (f.isDadaVuelta() && rotarHorizontales)
                        vistaFicha.setAnguloRotacion(-90);
                    // rota fichas del panel inferior horizontal
                } else if (!rotarVerticalAbajo) {
                    if (f.isDadaVuelta())
                        vistaFicha.setAnguloRotacion(-90);
                    else
                        vistaFicha.setAnguloRotacion(90);
                    // rotar ficha vertical abajo derecha.
                } else if (!rotarHorizontalAbajoDer){
                    if (f.isDadaVuelta())
                        vistaFicha.setAnguloRotacion(-180);
                    else
                        vistaFicha.setAnguloRotacion(360);
                    // rota fichas del panel inferior horizontal derecho
                } else {
                    if (f.isDadaVuelta())
                        vistaFicha.setAnguloRotacion(90);
                    else
                        vistaFicha.setAnguloRotacion(-90);
                }
            }
        } else if (rotar) {
            if (!rotarHorizontales && !rotarHorizAbajo)
                vistaFicha.setAnguloRotacion(f.isDadaVuelta() ? 90 : -90);
            else
                vistaFicha.setAnguloRotacion(360);
        }
    }

    public void jugar() {
        controlador.iniciarJuego();
    }

    @Override
    public void iniciar() {
        setVisible(true);
    }

    @Override
    public void mostrarTablaPuntos(Object o) {
        List<IJugador> jugadores = (ArrayList<IJugador>) o;
        IJugador j1 = jugadores.getFirst();
        IJugador j2 = jugadores.getLast();

        // atributos del j1
        lblJ1Nombre.setForeground(Color.black);
        lblJ1Nombre.setText(j1.getNombre());
        lblJ1Nombre.setFont(new Font("Arial", Font.BOLD, 24));
        lblJ1Nombre.setBounds(10, 250, 200, 200);
        // puntos j1 label
        lblJ1Pts.setForeground(Color.black);
        lblJ1Pts.setText(Integer.toString(j1.getPuntos()));
        lblJ1Pts.setFont(new Font("Arial", Font.BOLD, 24));
        lblJ1Pts.setBounds(10, 270, 200, 200);

        // atributos del j2.
        lblJ2Nombre.setText(j2.getNombre());
        lblJ2Nombre.setBounds(200, 250, 200, 200);
        lblJ2Nombre.setForeground(Color.black);
        lblJ2Nombre.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblJ1Nombre);
        panel.add(lblJ2Nombre);
        // puntos j2 label
        lblJ2Pts.setForeground(Color.black);
        lblJ2Pts.setText(Integer.toString(j2.getPuntos()));
        lblJ2Pts.setFont(new Font("Arial", Font.BOLD, 24));
        lblJ2Pts.setBounds(200, 270, 200, 200);
        panel.add(lblJ1Pts);
        panel.add(lblJ2Pts);
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void ocultarBoton() {
        robarBtn.setVisible(false);
        habilitarComponentes(jugadorManoComponente, false);
    }
    @Override
    public void mostrarBoton() {
        robarBtn.setVisible(true);
        habilitarComponentes(jugadorManoComponente, true);
    }

    private void habilitarComponentes(Container container, boolean habilitar) {
        Component[] componentes = container.getComponents();
        for (Component c: componentes) {
            if (c instanceof VistaFicha){
                if (habilitar) {
                    // habilito nuevamente los listeners
                    for (MouseListener listener: mouseListenersGuardados) {
                        c.addMouseListener(listener);
                    }
                    mouseListenersGuardados.clear();
                } else {
                    // deshabilito los listeners
                    MouseListener[] listeners = c.getMouseListeners();
                    for (MouseListener listener : listeners) {
                        c.removeMouseListener(listener);
                        mouseListenersGuardados.add(listener);
                    }
                }
            }
        }
    }

    @Override
    public void limpiarTablero() {
        componenteTablero.limpiarFicha();
    }

    // cambia la pantalla actual para mostrar el fin del juego.
    @Override
    public void finalizarJuego(String mensaje) {
        this.remove(panel);
        JPanel nuevoPanel = Lobby.getjPanel("img/dominoes.jpg");
        nuevoPanel.setLayout(null);
        JLabel lbl = new JLabel(mensaje);
        // set texto attributos.
        lbl.setForeground(Color.white);
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT); // alinear el texto al centro.
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setVerticalAlignment(SwingConstants.CENTER);
        lbl.setBounds(0, 60, 1000, 100);
        nuevoPanel.setBounds(0,0,1200, 650);
        nuevoPanel.add(lbl);
        this.add(nuevoPanel);
        revalidate();
        repaint();
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
