package vista;

import modelo.IFicha;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;

//public class VistaFicha extends JComponent {
//    private IFicha ficha;
//    private ImageIcon imageIcon;
//    private boolean elegida = false;
//
//
//    public VistaFicha(IFicha ficha, boolean cambiarTamanio) {
//        this.ficha = ficha;
//        cargarImagen();
//        setVisible(true);
//
//
//            // cambia el tamanio de la ficha al hacer hover.
//        // cambia el tamanio de la ficha al hacer hover.
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                // mover la ficha hacia arrba
//                if (cambiarTamanio)
//                    setElegida(true);
//            }
//
//            // se resetea el borde.
//            @Override
//            public void mouseExited(MouseEvent e) {
//                // reestablacer la escala cuando el mouse sale.
//                elegida = false;
//
//                repaint();
//            }
//        });
//
//
//
//
//        // it only lets me pick up a tile when it is not my turn.
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                setVisible(false);
//            }
//        });
//    }
//
//
//
//
//
//    private void cargarImagen() {
//        String nombreArchivo = "img/" + ficha.getIzquierdo() + "-" + ficha.getDerecho() + ".png";
//        // cargo la imagen y manejo errores.
//        try {
//            imageIcon = new ImageIcon(getClass().getResource(nombreArchivo));
//        } catch (NullPointerException e) {
//            throw new RuntimeException();
//        }
//        if (imageIcon.getImageLoadStatus() == ImageObserver.ERROR) {
//            System.err.println("Error cargando imagen: " + nombreArchivo);
//        }
//
//    }
//
//    public ImageIcon getImageIcon() {
//        return imageIcon;
//    }
//
//    public void setImageIcon(ImageIcon imageIcon) {
//        this.imageIcon = imageIcon;
//        repaint();
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (imageIcon != null) {
//            Graphics2D graphics2D = (Graphics2D) g.create();
//
//            if (elegida) {
//                graphics2D.scale(1.1, 1.1);
//            }
//            imageIcon.paintIcon(this, graphics2D, 0, 0);
//            graphics2D.dispose();
//        }
//    }
//
//    public boolean isElegida() {
//        return elegida;
//    }
//
//    public void setElegida(boolean elegida) {
//        this.elegida = elegida;
//        repaint();
//    }
//
//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
//    }
//
//
//}




public class VistaFicha extends JLabel {
    private IFicha ficha;
    private boolean elegida = false;
    private boolean isDoble;

    public VistaFicha(IFicha ficha, boolean cambiarTamanio) {
        this.ficha = ficha;
        this.isDoble = ficha.esFichaDoble();
        cargarImagen();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (cambiarTamanio) {
                    setPreferredSize(new Dimension(60, 60)); // incrementa el tamanio
                    setLocation(getX() - 5, getY() - 5); // mueve la ficha.
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setPreferredSize(null); // resetea el tamanio cuando sale.
                setLocation(getX() + 5, getY() + 5); // resetea la posiion
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false); // oculta la ficha.
            }
        });
    }

    private void cargarImagen() {
        String nombreArchivo = "img/" + ficha.getIzquierdo() + "-" + ficha.getDerecho() + ".png";
        // cargar imagen.
        ImageIcon icon = new ImageIcon(getClass().getResource(nombreArchivo));
        setIcon(icon);
    }

    public boolean isElegida() {
        return elegida;
    }

    public void setElegida(boolean elegida) {
        this.elegida = elegida;
        if (elegida) {
            setBorder(BorderFactory.createLineBorder(Color.RED)); // agrega un borde.
        } else {
            setBorder(null); // saca el borde.
        }
    }
}
























