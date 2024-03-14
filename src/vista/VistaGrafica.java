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

/* TODO
* HACER QUE EL TABLERO COMPONENTE Y EL JUGADORCOMPONENTE CHECKEEN SI SUS FICHAS FUERON AGREGADAS O  NO.
* */

public class VistaGrafica extends JFrame implements IVista, MouseListener {
    private String nombre;
    private static Controlador controlador;
    private JPanel panel;
    private static int cantMensajes = 0;
    private static IFicha primeraFicha;
    private static int cantClicks = 0;
    private static int offsetFicha = 1;
    private static int fichasJugadorMostradas = 0;
    private ArrayList<VistaFicha> componentesEnTablero = new ArrayList<>();
    private ArrayList<VistaFicha> componentesEnJugadores = new ArrayList<>();
    private ComponenteJugadorMano jugadorManoComponente;
    private JButton robarBtn;
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
        int i = 0;

        if (primeraFicha != null)
            limpiarFichasJugador(fichas);
        for (IFicha ficha: fichas) {
            if (VistaGrafica.fichasJugadorMostradas == 2) { // cantJugadores.
//                colocarComponenteFicha(x, y, ficha, i);
                VistaFicha fichaComponente = new VistaFicha(ficha, true, true, false);
                jugadorManoComponente.add(fichaComponente);

            } else {
                if (!existeComponente(ficha, componentesEnJugadores)) {
//                    i = colocarComponenteFicha(x, y, ficha, i);
                    VistaFicha fichaComponente = new VistaFicha(ficha, true, true, false);
                    jugadorManoComponente.add(fichaComponente);
                }
            }

        }

        VistaGrafica.fichasJugadorMostradas += 1;
        componentesEnJugadores.clear();
        panel.revalidate();
        panel.repaint();
    }



    // recibe las coordenadas, una ficha y una variable que sirve como offset y coloca un componenteFicha en el tablero.
    private int colocarComponenteFicha(int x, int y, IFicha ficha, int i) {
        VistaFicha fichaComponente = new VistaFicha(ficha, true, true, false);
        fichaComponente.setBounds(x + i, y, 50, 100);
        panel.add(fichaComponente);
        i += 35;
        componentesEnJugadores.add(fichaComponente);
        return i;
    }

    // Recibe una ficha y devuelve un booleano que indica si existe el componente en el tablero.
    private boolean existeComponente(IFicha ficha, ArrayList<VistaFicha> fichasComponente) {
        for (VistaFicha vistaFicha: fichasComponente) {
            IFicha f = vistaFicha.getFicha();
            if (f.getIzquierdo() == ficha.getIzquierdo() && f.getDerecho() == ficha.getDerecho())
                return true;
        }
        return false;
    }

    // limpia las fichas del jugador.
    private void limpiarFichasJugador(ArrayList<IFicha> fichasJ) {
//        Component[] componentes = panel.getComponents();
//        for (Component c: componentes) {
//            if (c instanceof VistaFicha) {
//                if (fichasJ.contains(((VistaFicha) c).getFicha()))
//                    ((VistaFicha)c).eliminarFicha();
//            }
//        }
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
        VistaGrafica.primeraFicha = ficha;
        VistaFicha f = new VistaFicha(ficha, false, false, true);
        componentesEnTablero.add(f);
        f.setBounds(300, 100, 40, 52);
        panel.add(f);
    }

    @Override
    public void mostrarTablero(Object o) {
        int x = 300;
        int y = 100;
        int offsetX = 0;
        int width = 40;
        int height = 52;

        int i = 0;
        for (IFicha f : (ArrayList<IFicha>) o) {
            VistaFicha vistaFicha = new VistaFicha(f, false, false, false);
            if (existeComponente(f, componentesEnTablero))
                continue;
            if (f.isDerecho()) {
                offsetX += 20 * VistaGrafica.offsetFicha;
                vistaFicha.setBounds(x + offsetX, y, width, height);

            } else {
                offsetX -= 40;
                vistaFicha.setBounds(x + offsetX, y, width, height);
            }
            panel.add(vistaFicha);
            VistaGrafica.offsetFicha += 1;
            componentesEnTablero.add(vistaFicha);
            i+= 1;
        }


        componentesEnTablero.clear();
        panel.revalidate();
        panel.repaint();
    }
    

    // dado una ficha, la rota y la muestra en las coordenadas indicadas.
//    private void rotarImagenWrapper(VistaFicha f, int x, int y, boolean isDoble) {
//        // obtengo la imagen de la ficha
//        ImageIcon imagenFicha = f.getImageIcon();
//
//        // si es doble la muestro vertical, caso contrario horizontal.
//        if (isDoble) {
//            f.setBounds(x, y, imagenFicha.getIconWidth(), imagenFicha.getIconHeight());
//        } else {
//            // convierto el toolkitimage  a bufferedIMage
//            Image imagenOriginal = imagenFicha.getImage();
//            BufferedImage bufferedImage = new BufferedImage(imagenOriginal.getWidth(null), imagenOriginal.getHeight(null), BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g2d = bufferedImage.createGraphics();
//            g2d.drawImage(imagenOriginal, 0, 0, null);
//            g2d.dispose();
//
//            // creo una nueva imagen con la imagen rotada.
//            BufferedImage imagenRotada = rotarImagen(bufferedImage, Math.toRadians(90));
//
//            // creo una imagen con la imagen rotada.
//            ImageIcon iconoImagenRotada = new ImageIcon(imagenRotada);
//            f.setImageIcon(iconoImagenRotada);
//            f.setBounds(x, y, imagenRotada.getWidth(), imagenRotada.getHeight());
//        }
//    }
//
//    private BufferedImage rotarImagen(BufferedImage img, double angulo) {
//        AffineTransform transformar = new AffineTransform();
//        transformar.rotate(angulo, img.getWidth() / 2, img.getHeight() / 2);
//        return new AffineTransformOp(transformar, AffineTransformOp.TYPE_BILINEAR).filter(img, null);
//    }

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
