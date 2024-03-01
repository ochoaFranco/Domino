package vista;

import modelo.IFicha;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class VistaFicha extends JComponent {
    private IFicha ficha;
    private ImageIcon imageIcon;

    public VistaFicha(IFicha ficha) {
        this.ficha = ficha;
        cargarImagen();
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




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageIcon != null) {
            imageIcon.paintIcon(this, g, 0, 0);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }
}
