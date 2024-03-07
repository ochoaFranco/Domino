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

public class VistaGrafica extends JFrame implements IVista, MouseListener {
    private String nombre;
    private static Controlador controlador;
    private JPanel panel;
    private static int cantMensajes = 0;
    private static IFicha primeraFicha;
    private static int cantClicks = 0;
    private JButton robarBtn;
    JLabel mensaje = new JLabel();

    public VistaGrafica(String nombre) {
        setTitle("Domino");
        this.nombre = nombre;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);

        // creo un panel junto con su backgorund.
        panel = Lobby.getjPanel("img/tablero.png");
        panel.setLayout(null);

        // agrego boton
        robarBtn = new JButton("Robar");
        robarBtn.setBounds(670, 350, 100, 20);
        panel.add(robarBtn);

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
        ArrayList<IFicha> fichas = controlador.getFichasJugador(jugador);
        limpiarFichasJugador(fichas);
        int i = 0;
        for (IFicha ficha: fichas) {
            VistaFicha fichaComponente = new VistaFicha(ficha, true, true);
            fichaComponente.setBounds(x + i, y, 50, 100);
            panel.add(fichaComponente);
            i += 35;
        }
        panel.revalidate();
        panel.repaint();
    }

    // limpia las fichas del jugador.
    private void limpiarFichasJugador(ArrayList<IFicha> fichasJ) {
        Component[] componentes = panel.getComponents();
        for (Component c: componentes) {
            if (c instanceof VistaFicha) {
                IFicha f =  ((VistaFicha )c).getFicha();
                boolean estaEnTablero = f.getIzquierdo() == primeraFicha.getIzquierdo() && f.getDerecho() == primeraFicha.getDerecho();
                if (fichasJ.contains(((VistaFicha) c).getFicha()) ||  estaEnTablero)
                    panel.remove(c);
            }
        }
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
        VistaFicha f = new VistaFicha(ficha, false, false);
        f.setBounds(300, 100, 40, 52);
        panel.add(f);
    }

    @Override
    public void mostrarTablero(Object o) {
        int x = 300;
        int y = 100;
        int offsetX = 0;
        int offsetY= 0;
        int width = 40;
        int height = 52;

        for (IFicha f : (ArrayList<IFicha>) o) {
            VistaFicha vistaFicha = new VistaFicha(f, false, false);
            if (!f.isVertical()) {
                if (f.isDerecho()) {
                    vistaFicha.setBounds(x + offsetX, y, width, height);
                    offsetX += 35;

                } else {
                    offsetX -= 30;
                    vistaFicha.setBounds(x + offsetX, y, width, height);
                }
                panel.add(vistaFicha);
            }
        }
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
