package vista;

import modelo.IFicha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VistaFicha extends JLabel {
    private IFicha ficha;
    private boolean elegida = false;
    private boolean isDoble;
    private boolean eventosMouseHabilitados;
    private static IFicha fichaEnMano = null;

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
                VistaGrafica.incrementarClicks();
                if (!(VistaGrafica.getCantClicks() > 1)) {
                    setVisible(false); // oculta la ficha.
                    VistaFicha.fichaEnMano = ficha;
                    MenuFicha menuFicha = new MenuFicha();
                }
                System.out.printf("Tile on your hand: " + VistaFicha.fichaEnMano.getIzquierdo() + "|" + VistaFicha.fichaEnMano.getDerecho());
            }
        });
    }



    private void cargarImagen() {
        String nombreArchivo = "img/" + ficha.getIzquierdo() + "-" + ficha.getDerecho() + ".png";
        // cargar imagen.
        ImageIcon icon = new ImageIcon(getClass().getResource(nombreArchivo));
        setIcon(icon);
    }

}
























