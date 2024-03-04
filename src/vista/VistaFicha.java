package vista;

import modelo.IFicha;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;


public class VistaFicha extends JLabel {
    private IFicha ficha;
    private boolean elegida = false;
    private boolean isDoble;
    private boolean eventosMouseHabilitados;

    public VistaFicha(IFicha ficha, boolean cambiarTamanio, boolean eventosMouseHabilitados) {
        this.ficha = ficha;
        this.isDoble = ficha.esFichaDoble();
        this.eventosMouseHabilitados = eventosMouseHabilitados;
        if (eventosMouseHabilitados) {
            agregarListeners(cambiarTamanio);
        }
        cargarImagen();


    }

    private void agregarListeners(boolean cambiarTamanio) {
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
























