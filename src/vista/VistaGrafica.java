package vista;

import controlador.Controlador;
import modelo.IFicha;
import modelo.IJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class VistaGrafica extends JFrame implements IVista {
    private String nombre;
    private Controlador controlador;
    private JPanel panel;
    private static int cantMensajes = 0;
    private static IFicha primeraFicha;
    private static int cantClicks = 0;

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
        int y = 370;
        mostrarFichasJugador(jugador, x, y);
    }


    public void mostrarFichasJugador(IJugador jugador, int x, int y)  {
        ArrayList<IFicha> fichas = controlador.getFichasJugador(jugador);
        int i = 0;
        for (IFicha ficha: fichas) {
            VistaFicha fichaComponente = new VistaFicha(ficha, true);
            fichaComponente.setBounds(x + i, y, 50, 100);
            panel.add(fichaComponente);
            i += 35;
        }
        panel.revalidate();
        panel.repaint();

    }


//     muestra la primera ficha.
    @Override
    public void mostrarFicha(IFicha ficha) {
        VistaGrafica.primeraFicha = ficha;
        VistaFicha f = new VistaFicha(ficha, false);
        f.setBounds(300, 100, 40, 52);
        panel.add(f);


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


    public static int getCantClicks() {
        return cantClicks;
    }
    public static void incrementarClicks() {
        VistaGrafica.cantClicks += 1;
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

    @Override
    public void ocultarBoton() {
    }

    @Override
    public void mostrarBoton() {

    }
}
