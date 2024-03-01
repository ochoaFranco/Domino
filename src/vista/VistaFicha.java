package vista;

import modelo.IFicha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class VistaFicha extends JComponent {
    private IFicha ficha;
    private ImageIcon imageIcon;
    private boolean elegida = false;
    private boolean cambiarTamanio = false;

    public VistaFicha(IFicha ficha, boolean cambiarTamanio) {
        this.ficha = ficha;
        this.cambiarTamanio = cambiarTamanio;
        cargarImagen();


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // mover la ficha hacia arrba
                if (cambiarTamanio)
                    setElegida(true);
            }

            // se resetea el borde.
            @Override
            public void mouseExited(MouseEvent e) {
                // reestablacer la escala cuando el mouse sale.
                elegida = false;
                repaint();
            }
        });




        // funcionalidad para cuando una ficha es elegida.
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (contains(e.getPoint())) {
//                    elegida = !elegida; // activo el estado de elegida.
//                    repaint();
//                }
//            }
//        });
    }

    private void cargarImagen() {
        String nombreArchivo = "img/" + ficha.getIzquierdo() + "-" + ficha.getDerecho() + ".png";
        // cargo la imagen y manejo errores.
        try {
            imageIcon = new ImageIcon(getClass().getResource(nombreArchivo));
        } catch (NullPointerException e) {
            throw new RuntimeException();
        }
        if (imageIcon.getImageLoadStatus() == ImageObserver.ERROR) {
            System.err.println("Error cargando imagen: " + nombreArchivo);
        }

    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageIcon != null) {
            Graphics2D graphics2D = (Graphics2D) g.create();

            if (elegida) {
                graphics2D.scale(1.1, 1.1);
            }
        imageIcon.paintIcon(this, graphics2D, 0, 0);
            graphics2D.dispose();
        }
    }

    public boolean isElegida() {
        return elegida;
    }

    public void setElegida(boolean elegida) {
        this.elegida = elegida;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }
}
