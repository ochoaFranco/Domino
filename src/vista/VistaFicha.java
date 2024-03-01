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

    public VistaFicha(IFicha ficha) {
        this.ficha = ficha;
        cargarImagen();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (contains(e.getPoint())) {
                    elegida = !elegida; // activo el estado de elegida.
                    repaint();
                }
            }
        });
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
            imageIcon.paintIcon(this, g, 0, 0);

            if (elegida) {
                g.setColor(Color.red);
                g.drawRect(0, 0, getWidth() - 10, getHeight() - 2);
            }
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
